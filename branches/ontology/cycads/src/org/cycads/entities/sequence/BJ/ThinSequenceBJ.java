/*
 * Created on 29/10/2008
 */
package org.cycads.entities.sequence.BJ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.biojavax.RankedCrossRef;
import org.biojavax.RichAnnotation;
import org.biojavax.SimpleRankedCrossRef;
import org.biojavax.bio.seq.CompoundRichLocation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichLocation;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.SimpleRichLocation;
import org.biojavax.bio.seq.ThinRichSequence;
import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.BJ.AnnotationMethodBJ;
import org.cycads.entities.annotation.BJ.DBRecordBJ;
import org.cycads.entities.annotation.BJ.SimpleFeatureBJ;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesHashTable;
import org.cycads.entities.note.NotesToAnnotationBJ;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.Sequence;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.BioSql;
import org.hibernate.Query;

public class ThinSequenceBJ implements Sequence<ThinSequenceBJ, DBRecordBJ, SubsequenceBJ, SimpleFeatureBJ> {
	int										id;
	RichSequence							richSeq	= null;
	NotesHashTable<Note<ThinSequenceBJ>>	notes;
	NCBIOrganismBJ							organism;

	public ThinSequenceBJ(int id, NCBIOrganismBJ organism) {
		this.id = id;
		this.organism = organism;
	}

	public ThinSequenceBJ(RichSequence richSeq) {
		this.richSeq = richSeq;
		organism = new NCBIOrganismBJ(richSeq.getTaxon());
		id = BioSql.getSequenceId(richSeq);
	}

	public int getId() {
		return id;
	}

	public NotesHashTable<Note<ThinSequenceBJ>> getNotesHash() {
		if (notes == null) {
			notes = NotesToAnnotationBJ.createNotesHashTable((RichAnnotation) getRichSeq().getAnnotation(), this);
		}
		return notes;
	}

	public RichSequence getRichSeq() {
		if (richSeq == null) {
			richSeq = getRichSeq(getId());
		}
		return richSeq;
	}

	private static ThinRichSequence getRichSeq(int id) {
		Query query = BioJavaxSession.createQuery("from ThinSequence where id=:id");
		query.setInteger("id", id);
		return (ThinRichSequence) query.uniqueResult();
	}

	@Override
	public Note<ThinSequenceBJ> addNote(Note< ? > note) {
		if (note.getHolder() != this) {
			note = createNote(note);
		}
		return getNotesHash().addNote(note);
	}

	@Override
	public Note<ThinSequenceBJ> getNote(String noteTypeName, String value) {
		return getNotesHash().getNote(noteTypeName, value);
	}

	@Override
	public Collection<Note<ThinSequenceBJ>> getNotes() {
		return getNotesHash().getNotes();
	}

	@Override
	public Collection<Note<ThinSequenceBJ>> getNotes(String noteTypeName) {
		return getNotesHash().getNotes(noteTypeName);
	}

	@Override
	public Collection<String> getNotesValues(String noteTypeName) {
		Collection<Note<ThinSequenceBJ>> notes = getNotes(noteTypeName);
		Collection<String> values = new ArrayList<String>();
		for (Note<ThinSequenceBJ> note : notes) {
			values.add(note.getValue());
		}
		return values;
	}

	@Override
	public void addChangeListener(ChangeListener<Note<ThinSequenceBJ>> cl, ChangeType ct) {
		getNotesHash().addChangeListener(cl, ct);
	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		return getNotesHash().isUnchanging(ct);
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<ThinSequenceBJ>> cl, ChangeType ct) {
		getNotesHash().removeChangeListener(cl, ct);
	}

	@Override
	public Note<ThinSequenceBJ> createNote(String type, String value) {
		return addNote(new SimpleNote<ThinSequenceBJ>(this, type, value));
	}

	@Override
	public Note<ThinSequenceBJ> createNote(Note< ? > note) {
		return createNote(note.getType(), note.getValue());
	}

	@Override
	public Note<ThinSequenceBJ> addNote(String type, String value) {
		return addNote(createNote(type, value));
	}

	public SubsequenceBJ getOrCreateSubsequence(int start, int end, Collection<Intron> introns) {
		RichLocation location = SubsequenceBJ.createRichLocation(start, end, introns);
		RichLocation simpleRichLocation = location;
		if (!(simpleRichLocation instanceof SimpleRichLocation)) {
			simpleRichLocation = (SimpleRichLocation) ((CompoundRichLocation) simpleRichLocation).blockIterator().next();
		}
		Collection<RichLocation> locations = BioSql.getLocationsEqual((SimpleRichLocation) simpleRichLocation, this);
		for (RichLocation richLocation : locations) {
			if (richLocation.getFeature() != null) {
				richLocation = (RichLocation) richLocation.getFeature().getLocation();
				if (richLocation.equals(location) && richLocation != location) {
					return new SubsequenceBJ(richLocation);
				}
			}
		}
		return new SubsequenceBJ(SubsequenceBJ.fillRichFeature(location,
			AnnotationMethodBJ.getMethodGeneral().getTerm(), this));
	}

	public String getDescription() {
		return getRichSeq().getDescription();
	}

	public String getName() {
		return getRichSeq().getName();
	}

	public NCBIOrganismBJ getOrganism() {
		return organism;
	}

	public double getVersion() {
		return getRichSeq().getVersion();
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(AnnotationFilter<SimpleFeatureBJ> featureFilter) {
		Collection<Integer> results = BioSql.getFeaturesId(getId());
		Collection<SimpleFeatureBJ> ret = new ArrayList<SimpleFeatureBJ>();
		for (Integer featureId : results) {
			SimpleFeatureBJ f = new SimpleFeatureBJ(featureId);
			if (featureFilter.accept(f)) {
				ret.add(f);
			}
		}
		return ret;
	}

	@Override
	public String toString() {
		return getRichSeq().getName();
	}

	@Override
	public void addDBRecord(DBRecordBJ dbRecord) {
		getRichSeq().addRankedCrossRef(new SimpleRankedCrossRef(dbRecord.getCrossRef(), 0));
	}

	@Override
	public void addDBRecord(String database, String accession) {
		addDBRecord(DBRecordBJ.getOrCreateDBRecordBJ(database, accession));
	}

	@Override
	public Collection<DBRecordBJ> getDBRecords() {
		Collection<DBRecordBJ> dbRecords = new ArrayList<DBRecordBJ>();
		Set<RankedCrossRef> crossRefs = getRichSeq().getRankedCrossRefs();
		for (RankedCrossRef crossRef : crossRefs) {
			dbRecords.add(DBRecordBJ.getOrCreateDBRecordBJ(crossRef.getCrossRef().getDbname(),
				crossRef.getCrossRef().getAccession()));
		}
		return dbRecords;
	}

	@Override
	public Collection<DBRecordBJ> getDBrecords(String dbName) {
		Collection<DBRecordBJ> dbRecords = new ArrayList<DBRecordBJ>();
		Set<RankedCrossRef> crossRefs = getRichSeq().getRankedCrossRefs();
		for (RankedCrossRef crossRef : crossRefs) {
			if (crossRef.getCrossRef().getDbname().equals(dbName)) {
				dbRecords.add(DBRecordBJ.getOrCreateDBRecordBJ(crossRef.getCrossRef().getDbname(),
					crossRef.getCrossRef().getAccession()));
			}
		}
		return dbRecords;
	}

	@Override
	public Collection<SubsequenceBJ> getSubSeqsByDBRecord(String dbName, String accession) {
		ArrayList<SubsequenceBJ> ret = new ArrayList<SubsequenceBJ>();
		Collection<RichFeature> features = BioSql.getFeaturesByDBXRef(DBRecordBJ.getOrCreateDBRecordBJ(dbName,
			accession).getCrossRef(), (ThinRichSequence) this.getRichSeq());
		for (RichFeature feature : features) {
			if (SubsequenceBJ.isSubsequence(feature)) {
				ret.add(new SubsequenceBJ(feature));
			}
		}
		return ret;
	}

}

/*
 * Created on 29/10/2008
 */
package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;

import org.biojavax.RichAnnotation;
import org.biojavax.bio.seq.ThinRichSequence;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.DBLinkSource;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureBJ;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.AssociateNotesToBJ;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesContainer;
import org.cycads.entities.note.NotesHashTable;
import org.cycads.entities.note.SimpleNote;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.BioSql;
import org.hibernate.Query;

public class ThinSequenceBJ implements Sequence
{
	int								id;
	ThinRichSequence				richSeq	= null;
	NotesHashTable<Note<Sequence>>	notes;
	Organism						organism;

	public ThinSequenceBJ(int id, Organism organism) {
		this.id = id;
		this.organism = organism;
	}

	public int getId() {
		return id;
	}

	private void adjusteNotes() {
		adjusteRichSeq();
		if (notes == null) {
			notes = AssociateNotesToBJ.createNotesHashTable((RichAnnotation) richSeq.getAnnotation(), (Sequence) this);
		}
	}

	private void adjusteRichSeq() {
		if (richSeq == null) {
			richSeq = getRichSeq(getId());
		}
	}

	private static ThinRichSequence getRichSeq(int id) {
		Query query = BioJavaxSession.createQuery("from ThinSequence where id=:id");
		query.setInteger("id", id);
		return (ThinRichSequence) query.uniqueResult();
	}

	@Override
	public Note<Sequence> addNote(Note<Sequence> note) {
		adjusteNotes();
		return notes.addNote(note);
	}

	@Override
	public Note<Sequence> getNote(String value, String noteTypeName) {
		adjusteNotes();
		return notes.getNote(value, noteTypeName);
	}

	@Override
	public Collection<Note<Sequence>> getNotes() {
		adjusteNotes();
		return notes.getNotes();
	}

	@Override
	public Collection<Note<Sequence>> getNotes(String noteTypeName) {
		adjusteNotes();
		return notes.getNotes(noteTypeName);
	}

	@Override
	public void addChangeListener(ChangeListener<Note<Sequence>> cl, ChangeType ct) {
		adjusteNotes();
		notes.addChangeListener(cl, ct);
	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		adjusteNotes();
		return notes.isUnchanging(ct);
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<Sequence>> cl, ChangeType ct) {
		adjusteNotes();
		notes.removeChangeListener(cl, ct);
	}

	public Note<Sequence> createNote(String value, String noteTypeName) {
		adjusteNotes();
		return new SimpleNote<Sequence>(this, value, noteTypeName);
	}

	public Location createLocation(int start, int end, Collection<Intron> introns) {
		return new SimpleLocation(start, end, this, introns);
	}

	public String getDescription() {
		adjusteRichSeq();
		return richSeq.getDescription();
	}

	public String getName() {
		adjusteRichSeq();
		return richSeq.getName();
	}

	public Organism getOrganism() {
		return organism;
	}

	public double getVersion() {
		adjusteRichSeq();
		return richSeq.getVersion();
	}

	public DBLink createDBLink(AnnotationMethod method, DBRecord record, NotesContainer<Note<DBLink>> notes) {
		// TODO Auto-generated method stub
		return null;
	}

	public DBLink createDBLink(AnnotationMethod method, String accession, String dbName,
			NotesContainer<Note<DBLink>> notes) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addDBLink(DBLink link) {
		// TODO Auto-generated method stub

	}

	public DBLink getDBLink(AnnotationMethod method, DBRecord record, DBLinkSource source) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<DBLink> getDBLinks(AnnotationMethod method, DBRecord record) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<DBLink> getDBLinks(AnnotationMethod method, String accession, String dbName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<DBLink> getDBLinks(DBLinkFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Feature> getFeatures(FeatureFilter featureFilter) {
		Collection<Integer> results = BioSql.getFeaturesId(getId());
		Collection<Feature> ret = new ArrayList<Feature>();
		for (Integer featureId : results) {
			Feature f = new FeatureBJ(featureId, this);
			if (featureFilter.accept(f)) {
				ret.add(f);
			}
		}
		return ret;
	}

}

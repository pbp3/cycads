/*
 * Created on 31/10/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.biojavax.RichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichLocation;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesHashTable;
import org.cycads.entities.note.NotesToAnnotationBJ;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;
import org.cycads.general.biojava.BioSql;
import org.cycads.general.biojava.TermsAndOntologies;

//F must be the type of this object
public class FeatureBJ<F extends FeatureBJ< ? >> implements Feature<F, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ>
{
	LocationBJ				location;
	NotesHashTable<Note<F>>	notes;
	ThinSequenceBJ			sequence;

	public FeatureBJ(RichFeature feature) {
		this(new LocationBJ((RichLocation) feature.getLocation()));
	}

	public FeatureBJ(int featureId) {
		this(BioSql.getRichFeature(featureId));
	}

	public FeatureBJ(RichLocation location) {
		this(new LocationBJ(location));
	}

	public FeatureBJ(LocationBJ location) {
		//verify consistency of parameters
		if (!location.getRichFeature().getTypeTerm().getOntology().equals(TermsAndOntologies.getOntologyFeatureType())
			|| !location.getRichFeature().getSourceTerm().getOntology().equals(TermsAndOntologies.getOntologyMethods())) {
			throw new IllegalArgumentException();
		}
		this.location = location;
	}

	public RichLocation getRichLocation() {
		return getSource().getRichLocation();
	}

	public RichFeature getRichFeature() {
		return getRichLocation().getFeature();
	}

	public NotesHashTable<Note<F>> getNotesHash() {
		if (notes == null) {
			notes = NotesToAnnotationBJ.createNotesHashTable((RichAnnotation) getRichFeature().getAnnotation(),
				(F) this);
		}
		return notes;
	}

	@Override
	public ThinSequenceBJ getSequence() {
		if (sequence == null) {
			sequence = new ThinSequenceBJ((RichSequence) getRichFeature().getSequence());
		}
		return sequence;
	}

	@Override
	public String getType() {
		return getRichFeature().getType();
	}

	public ComparableTerm getTypeTerm() {
		return (ComparableTerm) getRichFeature().getTypeTerm();
	}

	@Override
	public AnnotationMethodBJ getAnnotationMethod() {
		return AnnotationMethodBJ.getInstance(getRichFeature().getSource());
	}

	@Override
	public LocationBJ getSource() {
		return location;
	}

	@Override
	public Note<F> createNote(String value, String noteTypeName) {
		return addNote(new SimpleNote<F>((F) this, value, noteTypeName));
	}

	@Override
	public Note<F> addNote(Note<F> note) {
		return getNotesHash().addNote(note);
	}

	@Override
	public Note<F> getNote(String value, String noteTypeName) {
		return getNotesHash().getNote(value, noteTypeName);
	}

	@Override
	public Collection<Note<F>> getNotes() {
		return getNotesHash().getNotes();
	}

	@Override
	public Collection<Note<F>> getNotes(String noteTypeName) {
		return getNotesHash().getNotes(noteTypeName);
	}

	@Override
	public void addChangeListener(ChangeListener<Note<F>> cl, ChangeType ct) {
		getNotesHash().addChangeListener(cl, ct);
	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		return getNotesHash().isUnchanging(ct);
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<F>> cl, ChangeType ct) {
		getNotesHash().removeChangeListener(cl, ct);
	}

	//	@Override
	//	public void addFeature(F feature) {
	//		// TODO Auto-generated method stub
	//
	//	}
	//
	//	@Override
	//	public Collection<F> getFeatures() {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}
	//
	//	@Override
	//	public Collection<F> getFeaturesContainers() {
	//		ArrayList<F> features = new ArrayList<F>();
	//		Collection<RichFeature> RichFeatures = BioSql.getFeatureContains(getRichFeature());
	//		for (RichFeature feature : RichFeatures) {
	//			features.add(new FeatureBJ(feature));
	//		}
	//		return features;
	//	}
	//
}

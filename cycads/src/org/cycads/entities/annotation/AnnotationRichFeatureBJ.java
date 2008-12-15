/*
 * Created on 31/10/2008
 */
package org.cycads.entities.annotation;

import java.util.ArrayList;
import java.util.Collection;

import org.biojavax.RichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichLocation;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.feature.Feature;
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
public class AnnotationRichFeatureBJ implements Feature<AnnotationRichFeatureBJ, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ>
{
	LocationBJ						location;
	NotesHashTable<Note<AnnotationRichFeatureBJ>>	notes;
	ThinSequenceBJ					sequence;

	public AnnotationRichFeatureBJ(RichFeature feature) {
		this(new LocationBJ((RichLocation) feature.getLocation()));
	}

	public AnnotationRichFeatureBJ(int featureId) {
		this(BioSql.getRichFeature(featureId));
	}

	public AnnotationRichFeatureBJ(RichLocation location) {
		this(new LocationBJ(location));
	}

	public AnnotationRichFeatureBJ(LocationBJ location) {
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

	public NotesHashTable<Note<AnnotationRichFeatureBJ>> getNotesHash() {
		if (notes == null) {
			notes = NotesToAnnotationBJ.createNotesHashTable((RichAnnotation) getRichFeature().getAnnotation(), this);
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
	public Note<AnnotationRichFeatureBJ> createNote(String value, String noteTypeName) {
		return addNote(new SimpleNote<AnnotationRichFeatureBJ>(this, value, noteTypeName));
	}

	@Override
	public Note<AnnotationRichFeatureBJ> addNote(Note<AnnotationRichFeatureBJ> note) {
		return getNotesHash().addNote(note);
	}

	@Override
	public Note<AnnotationRichFeatureBJ> getNote(String value, String noteTypeName) {
		return getNotesHash().getNote(value, noteTypeName);
	}

	@Override
	public Collection<Note<AnnotationRichFeatureBJ>> getNotes() {
		return getNotesHash().getNotes();
	}

	@Override
	public Collection<Note<AnnotationRichFeatureBJ>> getNotes(String noteTypeName) {
		return getNotesHash().getNotes(noteTypeName);
	}

	@Override
	public void addChangeListener(ChangeListener<Note<AnnotationRichFeatureBJ>> cl, ChangeType ct) {
		getNotesHash().addChangeListener(cl, ct);
	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		return getNotesHash().isUnchanging(ct);
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<AnnotationRichFeatureBJ>> cl, ChangeType ct) {
		getNotesHash().removeChangeListener(cl, ct);
	}

	@Override
	public Collection<AnnotationRichFeatureBJ> getFeaturesContainers() {
		ArrayList<AnnotationRichFeatureBJ> features = new ArrayList<AnnotationRichFeatureBJ>();
		Collection<RichFeature> RichFeatures = BioSql.getFeatureContainers(getRichFeature());
		for (RichFeature feature : RichFeatures) {
			features.add(new AnnotationRichFeatureBJ(feature));
		}
		return features;
	}

	@Override
	public void addContainFeature(AnnotationRichFeatureBJ feature) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<AnnotationRichFeatureBJ> getFeaturesContains() {
		// TODO Auto-generated method stub
		return null;
	}

}

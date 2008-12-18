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
public class AnnotationRichFeatureBJ<ANNOTATION_TYPE extends AnnotationRichFeatureBJ< ? , ? , ? >, ANNOTATION_TYPE_CONTAINS extends AnnotationRichFeatureBJ< ? , ? , ? >, ANNOTATION_TYPE_CONTAINER extends AnnotationRichFeatureBJ< ? , ? , ? >>
		implements Feature<ANNOTATION_TYPE, LocationBJ, ThinSequenceBJ, AnnotationMethodBJ>
{
	RichFeature								richFeature;
	NotesHashTable<Note<ANNOTATION_TYPE>>	notes;
	ThinSequenceBJ							sequence;

	public AnnotationRichFeatureBJ(RichFeature feature) {
		this.richFeature = feature;
		//verify consistency of parameters
		if (!getRichFeature().getTypeTerm().getOntology().equals(TermsAndOntologies.getOntologyFeatureType())
			|| !getRichFeature().getSourceTerm().getOntology().equals(TermsAndOntologies.getOntologyMethods())
			|| getSource() == null) {
			throw new IllegalArgumentException();
		}
	}

	public AnnotationRichFeatureBJ(int featureId) {
		this(BioSql.getRichFeature(featureId));
	}

	public RichLocation getRichLocation() {
		return (RichLocation) getRichFeature().getLocation();
	}

	public RichFeature getRichFeature() {
		return richFeature;
	}

	public NotesHashTable<Note<ANNOTATION_TYPE>> getNotesHash() {
		if (notes == null) {
			notes = NotesToAnnotationBJ.createNotesHashTable((RichAnnotation) getRichFeature().getAnnotation(),
				(ANNOTATION_TYPE) this);
		}
		return notes;
	}

	public ThinSequenceBJ getSequence() {
		if (sequence == null) {
			sequence = new ThinSequenceBJ((RichSequence) getRichFeature().getSequence());
		}
		return sequence;
	}

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
	public Note<ANNOTATION_TYPE> createNote(String value, String noteTypeName) {
		return addNote(new SimpleNote<ANNOTATION_TYPE>((ANNOTATION_TYPE) this, value, noteTypeName));
	}

	@Override
	public Note<ANNOTATION_TYPE> addNote(Note<ANNOTATION_TYPE> note) {
		return getNotesHash().addNote(note);
	}

	@Override
	public Note<ANNOTATION_TYPE> getNote(String value, String noteTypeName) {
		return getNotesHash().getNote(value, noteTypeName);
	}

	@Override
	public Collection<Note<ANNOTATION_TYPE>> getNotes() {
		return getNotesHash().getNotes();
	}

	@Override
	public Collection<Note<ANNOTATION_TYPE>> getNotes(String noteTypeName) {
		return getNotesHash().getNotes(noteTypeName);
	}

	@Override
	public void addChangeListener(ChangeListener<Note<ANNOTATION_TYPE>> cl, ChangeType ct) {
		getNotesHash().addChangeListener(cl, ct);
	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		return getNotesHash().isUnchanging(ct);
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<ANNOTATION_TYPE>> cl, ChangeType ct) {
		getNotesHash().removeChangeListener(cl, ct);
	}

	protected Collection<ANNOTATION_TYPE_CONTAINER> getFeaturesContainers(
			AnnotationRichFeatureBJFactory<ANNOTATION_TYPE_CONTAINS, ANNOTATION_TYPE_CONTAINER> factory) {
		ArrayList<ANNOTATION_TYPE_CONTAINER> features = new ArrayList<ANNOTATION_TYPE_CONTAINER>();
		Collection<RichFeature> RichFeatures = BioSql.getFeatureContainers(getRichFeature());
		for (RichFeature feature : RichFeatures) {
			features.add(factory.createObjectContainer(feature));
		}
		return features;
	}

	protected Collection<ANNOTATION_TYPE_CONTAINS> getFeaturesContains(
			AnnotationRichFeatureBJFactory<ANNOTATION_TYPE_CONTAINS, ANNOTATION_TYPE_CONTAINER> factory) {
		ArrayList<ANNOTATION_TYPE_CONTAINS> features = new ArrayList<ANNOTATION_TYPE_CONTAINS>();
		Collection<RichFeature> RichFeatures = BioSql.getFeatureContains(getRichFeature());
		for (RichFeature feature : RichFeatures) {
			features.add(factory.createObjectContains(feature));
		}
		return features;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof AnnotationRichFeatureBJ< ? , ? , ? >) {
			AnnotationRichFeatureBJ< ? , ? , ? > obj = (AnnotationRichFeatureBJ< ? , ? , ? >) o;
			return this.getRichFeature().equals(obj.getRichFeature());
		}
		return false;
	}

}

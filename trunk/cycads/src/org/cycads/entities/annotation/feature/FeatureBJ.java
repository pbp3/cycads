/*
 * Created on 31/10/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.biojavax.RichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesHashTable;
import org.cycads.entities.note.NotesToAnnotationBJ;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.entities.sequence.ThinSequenceBJ;
import org.cycads.general.biojava.BioJavaxSession;
import org.hibernate.Query;

public class FeatureBJ implements Feature<LocationBJ, ThinSequenceBJ, AnnotationMethodBJ>
{
	RichFeature						feature;
	NotesHashTable<Note<FeatureBJ>>	notes;
	ThinSequenceBJ					sequence;
	LocationBJ						location;

	public FeatureBJ(RichFeature feature) {
		this.feature = feature;
	}

	public FeatureBJ(int featureId, ThinSequenceBJ sequence) {
		this(featureId);
		this.sequence = sequence;
	}

	public FeatureBJ(int featureId) {
		this(getRichFeature(featureId));
	}

	private void adjusteNotes() {
		if (notes == null) {
			notes = NotesToAnnotationBJ.createNotesHashTable((RichAnnotation) feature.getAnnotation(), this);
		}
	}

	public static RichFeature getRichFeature(int id) {
		Query query = BioJavaxSession.createQuery("from Feature where id=:id");
		query.setInteger("id", id);
		return (RichFeature) query.uniqueResult();
	}

	public RichFeature getRichFeature() {
		return feature;
	}

	@Override
	public ThinSequenceBJ getSequence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnnotationMethodBJ getAnnotationMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocationBJ getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends NoteSource> createNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends Annotation<LocationBJ, AnnotationMethodBJ>> addNote(
			Note< ? extends Annotation<LocationBJ, AnnotationMethodBJ>> note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends Annotation<LocationBJ, AnnotationMethodBJ>> getNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note< ? extends Annotation<LocationBJ, AnnotationMethodBJ>>> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note< ? extends Annotation<LocationBJ, AnnotationMethodBJ>>> getNotes(String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note< ? extends Annotation<LocationBJ, AnnotationMethodBJ>>> cl,
			ChangeType ct) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeChangeListener(ChangeListener<Note< ? extends Annotation<LocationBJ, AnnotationMethodBJ>>> cl,
			ChangeType ct) {
		// TODO Auto-generated method stub

	}

	// @Override
	// public Note<Feature> getNote(String value, String noteTypeName) {
	// adjusteNotes();
	// return notes.getNote(value, noteTypeName);
	// }
	//
	// @Override
	// public Collection<Note<Feature>> getNotes() {
	// adjusteNotes();
	// return notes.getNotes();
	// }
	//
	// @Override
	// public Collection<Note<Feature>> getNotes(String noteTypeName) {
	// adjusteNotes();
	// return notes.getNotes(noteTypeName);
	// }
	//
	// @Override
	// public void addChangeListener(ChangeListener<Note<Feature>> cl, ChangeType ct) {
	// adjusteNotes();
	// notes.addChangeListener(cl, ct);
	// }
	//
	// @Override
	// public boolean isUnchanging(ChangeType ct) {
	// adjusteNotes();
	// return notes.isUnchanging(ct);
	// }
	//
	// @Override
	// public void removeChangeListener(ChangeListener<Note<Feature>> cl, ChangeType ct) {
	// adjusteNotes();
	// notes.removeChangeListener(cl, ct);
	// }
	//
	// public Note<Feature> createNote(String value, String noteTypeName) {
	// adjusteNotes();
	// return new SimpleNote<Feature>(this, value, noteTypeName);
	// }
	//
	// public String getType() {
	// return feature.getType();
	// }
	//
	// public String getName() {
	// return feature.getName();
	// }
	//
	// public Sequence getSequence() {
	// if (sequence == null) {
	// sequence = new SequenceBJ(feature.getSequence());
	// }
	// return sequence;
	// }
	//
	// public Location getLocation() {
	// if (location == null) {
	// return new LocationBJ(feature.getLocation());
	// }
	// return location;
	// }
	//
	//
}

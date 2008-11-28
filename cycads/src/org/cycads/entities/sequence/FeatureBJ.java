/*
 * Created on 31/10/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.DBAnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteBJ;
import org.cycads.entities.note.NotesHashTable;
import org.cycads.general.biojava.BioJavaxSession;
import org.hibernate.Query;

public class FeatureBJ implements Feature
{
	RichFeature					feature;
	NotesHashTable<Feature>	notes;
	Sequence					sequence;
	Location					location;

	public FeatureBJ(RichFeature feature) {
		this.feature = feature;
		notes = new NotesHashTable<Feature>(this, feature);
	}

	public FeatureBJ(int featureId) {
		this(getRichFeature(featureId));
	}

	public static RichFeature getRichFeature(int id) {
		Query query = BioJavaxSession.createQuery("from Feature where id=:id");
		query.setInteger("id", id);
		return (RichFeature) query.uniqueResult();
	}

	public String getType() {
		return feature.getType();
	}

	public String getName() {
		return feature.getName();
	}

	public Sequence getSequence() {
		if (sequence == null) {
			sequence = new SequenceBJ(feature.getSequence());
		}
		return sequence;
	}

	public Location getLocation() {
		if (location == null) {
			return new LocationBJ(feature.getLocation());
		}
		return location;
	}

	public FeatureType getFeatureType() {
		// TODO Auto-generated method stub
		return null;
	}

	public FeatureAnnotationMethod getMethod() {
		//feature.source
		// TODO Auto-generated method stub
		return null;
	}

	public Note<Feature> createNote(String value, String noteTypeName) {
		return new NoteBJ<Feature>(this, value, noteTypeName);
	}

	public Note<Feature> addNote(Note<Feature> note) {
		return notes.addNote(note);
	}

	public Note<Feature> addNote(String value, String noteTypeName) {
		return notes.addNote(value, noteTypeName);
	}

	public Note<Feature> getNote(String value, String noteTypeName) {
		return notes.getNote(value, noteTypeName);
	}

	public Collection<Note<Feature>> getNotes() {
		return notes.getNotes();
	}

	public Collection<Note<Feature>> getNotes(String noteTypeName) {
		return notes.getNotes(noteTypeName);
	}

	public Note<Feature> getOrCreateNote(String value, String noteTypeName) {
		return notes.getOrCreateNote(value, noteTypeName);
	}

	public void addDBAnnotation(FeatureToDBAnnotation featureToDBAnnotation) {
		// TODO Auto-generated method stub

	}

	public FeatureToDBAnnotation addDBAnnotation(String db, String accession, DBAnnotationMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<FeatureToDBAnnotation> getdBAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

}

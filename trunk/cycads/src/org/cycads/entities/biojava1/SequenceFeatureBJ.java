/*
 * Created on 31/10/2008
 */
package org.cycads.entities.biojava1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.biojavax.Note;
import org.biojavax.bio.seq.RichFeature;
import org.cycads.general.biojava.BioJavaxSession;
import org.hibernate.Query;

public class SequenceFeatureBJ implements SequenceFeature
{
	RichFeature								feature;
	Collection<FeatureNote>					notes;
	Map<String, Collection<FeatureNote>>	mapNotes;

	public SequenceFeatureBJ(RichFeature feature) {
		this.feature = feature;
	}

	public SequenceFeatureBJ(int featureId) {
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

	public Collection<FeatureNote> getNotes() {
		if (notes == null) {
			createNotes();
		}
		return notes;
	}

	private void createNotes() {
		Set<Note> notesBJ = feature.getNoteSet();
		notes = new ArrayList<FeatureNote>(notesBJ.size());
		for (Note note : notesBJ) {
			FeatureNote featureNote = new SimpleFeatureNote(this, new FeatureNoteTypeBJ(note.getTerm()),
				note.getValue());
			notes.add(featureNote);
		}
	}

	public Collection<FeatureNote> getNotes(String noteTypeName) {
		if (mapNotes == null) {
			createMapNotes();
		}
		return mapNotes.get(noteTypeName);
	}

	//return the first element found
	public FeatureNote getNote(String noteTypeName) {
		if (mapNotes == null) {
			createMapNotes();
		}
		Collection<FeatureNote> notesKey = mapNotes.get(noteTypeName);
		if (notesKey == null || notesKey.isEmpty()) {
			return null;
		}
		else {
			return notesKey.iterator().next();
		}
	}

	public Collection<FeatureNote> getNotes(FeatureNoteType noteType) {
		Collection<FeatureNote> notes = getNotes(noteType.getName());
		Collection<FeatureNote> ret = notes;
		for (FeatureNote note : notes) {
			if (!note.getType().equals(noteType)) {
				if (ret == notes) {
					ret = new LinkedList<FeatureNote>(notes);
				}
				ret.remove(note);
			}
		}
		return ret;
	}

	//return the first element found
	public FeatureNote getNote(FeatureNoteType noteType) {
		return getNotes(noteType).iterator().next();
	}

	private void createMapNotes() {
		Set<Note> notesBJ = feature.getNoteSet();
		String key;
		mapNotes = new Hashtable<String, Collection<FeatureNote>>();
		for (Note note : notesBJ) {
			key = note.getTerm().getName();
			Collection<FeatureNote> notesKey = mapNotes.get(key);
			if (notesKey == null) {
				notesKey = new LinkedList<FeatureNote>();
				mapNotes.put(key, notesKey);
			}
			notesKey.add(new SimpleFeatureNote(this, new FeatureNoteTypeBJ(note.getTerm()), note.getValue()));
		}
	}

	//	public Collection<FeatureNote> getNotes(String noteTypeName) {
	//		if (mapNotes == null) {
	//			createMapNotes();
	//		}
	//		return mapNotes.get(noteTypeName);
	//	}
	//
	//	public FeatureNote getNote(String noteTypeName) {
	//		if (mapNotes == null) {
	//			createMapNotes();
	//		}
	//		Collection<FeatureNote> notesKey = mapNotes.get(noteTypeName);
	//		if (notesKey == null || notesKey.isEmpty()) {
	//			return null;
	//		}
	//		else {
	//			return notesKey.iterator().next();
	//		}
	//	}
	//
	public Sequence getSequence() {
		return new ThinSequenceBJ(feature.getSequence());
	}

	public Collection<DBRef> getDBRefs() {
		// TODO Auto-generated method stub
		return null;
	}

	public Location getLocation() {
		return new LocationBJ(feature.getLocation());
	}

}

/*
 * Created on 31/10/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.biojavax.RichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.LinkNotesToBJ;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesHashTable;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.refact.DBAnnotationMethod;
import org.cycads.entities.refact.FeatureAnnotationMethod;
import org.cycads.entities.refact.FeatureToDBAnnotation;
import org.cycads.entities.refact.FeatureType;
import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Sequence;
import org.cycads.general.biojava.BioJavaxSession;
import org.hibernate.Query;

public class FeatureBJ implements Feature
{
	RichFeature						feature;
	NotesHashTable<Note<Feature>>	notes;
	Sequence						sequence;
	Location						location;

	public FeatureBJ(RichFeature feature)
	{
		this.feature = feature;
	}

	public FeatureBJ(int featureId, Sequence sequence)
	{
		this(getRichFeature(featureId));
		this.sequence = sequence;
	}

	private void adjusteNotes()
	{
		if (notes == null)
		{
			notes = LinkNotesToBJ.createNotesHashTable((RichAnnotation) feature.getAnnotation(), (Feature) this);
		}
	}

	public static RichFeature getRichFeature(int id)
	{
		Query query = BioJavaxSession.createQuery("from Feature where id=:id");
		query.setInteger("id", id);
		return (RichFeature) query.uniqueResult();
	}

	@Override
	public Note<Feature> addNote(Note<Feature> note)
	{
		adjusteNotes();
		return notes.addNote(note);
	}

	@Override
	public Note<Feature> getNote(String value, String noteTypeName)
	{
		adjusteNotes();
		return notes.getNote(value, noteTypeName);
	}

	@Override
	public Collection<Note<Feature>> getNotes()
	{
		adjusteNotes();
		return notes.getNotes();
	}

	@Override
	public Collection<Note<Feature>> getNotes(String noteTypeName)
	{
		adjusteNotes();
		return notes.getNotes(noteTypeName);
	}

	@Override
	public void addChangeListener(ChangeListener<Note<Feature>> cl, ChangeType ct)
	{
		adjusteNotes();
		notes.addChangeListener(cl, ct);
	}

	@Override
	public boolean isUnchanging(ChangeType ct)
	{
		adjusteNotes();
		return notes.isUnchanging(ct);
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<Feature>> cl, ChangeType ct)
	{
		adjusteNotes();
		notes.removeChangeListener(cl, ct);
	}

	public Note<Feature> createNote(String value, String noteTypeName)
	{
		adjusteNotes();
		return new SimpleNote<Feature>(this, value, noteTypeName);
	}

	public String getType()
	{
		return feature.getType();
	}

	public String getName()
	{
		return feature.getName();
	}

	public Sequence getSequence()
	{
		if (sequence == null)
		{
			sequence = new SequenceBJ(feature.getSequence());
		}
		return sequence;
	}

	public Location getLocation()
	{
		if (location == null)
		{
			return new LocationBJ(feature.getLocation());
		}
		return location;
	}

	public FeatureType getFeatureType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public FeatureAnnotationMethod getMethod()
	{
		// feature.source
		// TODO Auto-generated method stub
		return null;
	}

	public void addDBAnnotation(FeatureToDBAnnotation featureToDBAnnotation)
	{
		// TODO Auto-generated method stub

	}

	public FeatureToDBAnnotation addDBAnnotation(String db, String accession, DBAnnotationMethod method)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<FeatureToDBAnnotation> getdBAnnotations()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnnotationMethod getAnnotationMethod()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getSource()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

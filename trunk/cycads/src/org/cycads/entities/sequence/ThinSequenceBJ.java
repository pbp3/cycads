/*
 * Created on 29/10/2008
 */
package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.biojava.FeatureFilterByType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteBJ;
import org.cycads.entities.note.NoteCollectionHash;
import org.cycads.entities.sequence.feature.Feature;
import org.cycads.entities.sequence.feature.FeatureBJ;
import org.cycads.entities.sequence.feature.FeatureFilter;
import org.cycads.general.biojava.BioJavaxSession;
import org.hibernate.Query;

public class ThinSequenceBJ implements Sequence
{
	int								id;
	NoteCollectionHash<Sequence>	notes;

	public ThinSequenceBJ(int id)
	{
		this.id = id;
		notes = new NoteCollectionHash<Sequence>(this);
	}

	public int getId()
	{
		return id;
	}

	public Collection<Feature> getFeatures(FeatureFilter featureFilter)
	{
		if (featureFilter instanceof FeatureFilterByType)
		{

			Query query = BioJavaxSession.createQuery("select f.id from Feature as f join f.parent as b where "
				+ "b.id=:seqId ");
			query.setInteger("seqId", id);
			Collection<Integer> results = query.list();
			Collection<Feature> ret = new ArrayList<Feature>();
			for (Integer featureId : results)
			{
				Feature f = new FeatureBJ(featureId);
				if (featureFilter.accept(f))
				{
					ret.add(f);
				}
			}
			return ret;
		}
		else
		{
			return null;
		}
	}

	public Collection<SequenceToDBAnnotation> getDBLinks()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Feature> getFeatures()
	{
		Query query = BioJavaxSession.createQuery("select f.id from Feature as f join f.parent as b where "
			+ "b.id=:seqId ");
		query.setInteger("seqId", id);
		Collection<Integer> results = query.list();
		Collection<Feature> ret = new ArrayList<Feature>();
		for (Integer featureId : results)
		{

			ret.add(new FeatureBJ(featureId));
		}
		return ret;
	}

	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Organism getOrganism()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public double getVersion()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public Note<Sequence> addNote(Note<Sequence> note)
	{
		return notes.addNote(note);
	}

	public Note<Sequence> addNote(String value, String noteTypeName)
	{
		return notes.addNote(value, noteTypeName);
	}

	public Note<Sequence> getNote(String value, String noteTypeName)
	{
		return notes.getNote(value, noteTypeName);
	}

	public Collection<Note<Sequence>> getNotes()
	{
		return notes.getNotes();
	}

	public Collection<Note<Sequence>> getNotes(String noteTypeName)
	{
		return notes.getNotes(noteTypeName);
	}

	public Note<Sequence> getOrCreateNote(String value, String noteTypeName)
	{
		return notes.getOrCreateNote(value, noteTypeName);
	}

	public Note<Sequence> createNote(String value, String noteTypeName)
	{
		return new NoteBJ<Sequence>(this, value, noteTypeName);
	}

}

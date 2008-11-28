/*
 * Created on 29/10/2008
 */
package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.biojavax.RichAnnotation;
import org.biojavax.SimpleNote;
import org.biojavax.bio.seq.ThinRichSequence;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DBLink;
import org.cycads.entities.annotation.DBLinkFilter;
import org.cycads.entities.annotation.DBLinkSource;
import org.cycads.entities.annotation.DBRecord;
import org.cycads.entities.annotation.Feature;
import org.cycads.entities.annotation.FeatureFilter;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteBJ;
import org.cycads.entities.note.NotesContainer;
import org.cycads.general.biojava.BioSql;

public class ThinSequenceBJ implements Sequence
{
	int					id;
	ThinRichSequence	richSeq	= null;

	// NoteHashTable<Note<Sequence>> notes = new NoteHashTable<Note<Sequence>>();

	public ThinSequenceBJ(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public Collection<Feature> getFeatures(FeatureFilter featureFilter)
	{
		Collection<Integer> results = BioSql.getFeaturesId(getId());
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

	public Note<Sequence> addNote(Note<Sequence> note)
	{
		if (richSeq == null)
		{
			richSeq = getRichSeq(getId());
		}
		RichAnnotation annot = (RichAnnotation) richSeq.getAnnotation();
		Set<Note> notes = annot.getNoteSet();
		int rank = 0;
		for (Note note : notes)
		{
			if (note.getTerm().equals(meth.getTerm()))
			{
				if (note.getValue().equalsIgnoreCase(value))
				{
					return;
				}
				rank++;
			}
		}
		annot.addNote(new SimpleNote(meth.getTerm(), value, rank));
		return notes.addNote(note);
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

	public Note<Sequence> createNote(String value, String noteTypeName)
	{
		return new NoteBJ<Sequence>(this, value, noteTypeName);
	}

	public Location createLocation(int start, int end, Collection<Intron> introns)
	{
		return new SimpleLocation(start, end, introns, this);
	}

	public String getDescription()
	{
		// TODO Auto-generated method stub
		return null;
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

	public DBLink createDBLink(AnnotationMethod method, DBRecord record, NotesContainer<Note<DBLink>> notes)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public DBLink createDBLink(AnnotationMethod method, String accession, String dbName,
			NotesContainer<Note<DBLink>> notes)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void addDBLink(DBLink link)
	{
		// TODO Auto-generated method stub

	}

	public DBLink getDBLink(AnnotationMethod method, DBRecord record, DBLinkSource source)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<DBLink> getDBLinks(AnnotationMethod method, DBRecord record)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<DBLink> getDBLinks(AnnotationMethod method, String accession, String dbName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<DBLink> getDBLinks(DBLinkFilter filter)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

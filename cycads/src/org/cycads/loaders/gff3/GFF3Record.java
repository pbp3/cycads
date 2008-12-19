package org.cycads.loaders.gff3;

import java.util.Collection;

import org.biojava.bio.program.gff.GFFTools;
import org.biojava.utils.AbstractChangeable;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.entities.note.NotesArrayList;
import org.cycads.entities.note.NotesContainer;
import org.cycads.entities.note.SimpleNote;

/**
 * A record in a GFF3 formatted file.
 * 
 * @author Matthew Pocock
 */
public interface GFF3Record<N extends Note< ? >> extends NotesContainer<N>, NoteSource
{
	public String getSequenceID();

	public String getSource();

	public String getType();

	public int getStart();

	public int getEnd();

	public double getScore();

	public int getStrand();

	public int getPhase();

	public N addNote(String value, String type);

	public static final class Impl extends AbstractChangeable implements GFF3Record<SimpleNote<Impl>>
	{

		private String								sequenceID;
		private String								source;
		private String								type;
		private int									start;
		private int									end;
		private double								score;
		private int									strand;
		private int									phase;
		private NotesArrayList<SimpleNote<Impl>>	notes;

		public Impl()
		{
			// do nothing much - initialize us with uninformative data
			sequenceID = null;
			source = null;
			type = null;
			start = Integer.MAX_VALUE;
			end = Integer.MIN_VALUE;
			score = GFFTools.NO_SCORE;
			strand = 0;
			phase = GFFTools.NO_FRAME;
			notes = new NotesArrayList<SimpleNote<Impl>>();
		}

		// public Impl(GFF3Record rec) {
		// this.sequenceID = rec.getSequenceID();
		// this.source = rec.getSource();
		// this.type = rec.getType();
		// this.start = rec.getStart();
		// this.end = rec.getEnd();
		// this.score = rec.getScore();
		// this.strand = rec.getStrand();
		// this.phase = rec.getPhase();
		// }

		public String getSequenceID()
		{
			return this.sequenceID;
		}

		public void setSequenceID(String sequenceID)
		{
			this.sequenceID = sequenceID;
		}

		public String getSource()
		{
			return this.source;
		}

		public void setSource(String source)
		{
			this.source = source;
		}

		public String getType()
		{
			return this.type;
		}

		public void setType(String type)
		{
			this.type = type;
		}

		public int getStart()
		{
			return this.start;
		}

		public void setStart(int start)
		{
			this.start = start;
		}

		public int getEnd()
		{
			return this.end;
		}

		public void setEnd(int end)
		{
			this.end = end;
		}

		public double getScore()
		{
			return this.score;
		}

		public void setScore(double score)
		{
			this.score = score;
		}

		public int getStrand()
		{
			return this.strand;
		}

		public void setStrand(int strand)
		{
			this.strand = strand;
		}

		public int getPhase()
		{
			return this.phase;
		}

		public void setPhase(int phase)
		{
			this.phase = phase;
		}

		@Override
		public SimpleNote<Impl> createNote(String value, String noteTypeName)
		{
			return new SimpleNote<Impl>(this, value, type);
		}

		public SimpleNote<Impl> addNote(SimpleNote<Impl> note)
		{
			return notes.addNote(note);
		}

		public SimpleNote<Impl> getNote(String value, String noteTypeName)
		{
			return notes.getNote(value, noteTypeName);
		}

		public Collection<SimpleNote<Impl>> getNotes()
		{
			return notes;
		}

		public Collection<SimpleNote<Impl>> getNotes(String noteTypeName)
		{
			return notes.getNotes(noteTypeName);
		}

		public void addChangeListener(ChangeListener<SimpleNote<Impl>> cl, ChangeType ct)
		{
			notes.addChangeListener(cl, ct);
		}

		public boolean isUnchanging(ChangeType ct)
		{
			return notes.isUnchanging(ct);
		}

		@Override
		public void removeChangeListener(ChangeListener<SimpleNote<Impl>> cl, ChangeType ct)
		{
			notes.removeChangeListener(cl, ct);
		}

		@Override
		public SimpleNote<Impl> addNote(String value, String type)
		{
			return addNote(createNote(value, type));
		}

	}
}

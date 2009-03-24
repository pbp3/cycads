package org.cycads.parser.gff3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

import org.cycads.entities.note.Note;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.note.SimpleType;

public class GFF3Record
{
	public static double						NO_SCORE		= Double.NEGATIVE_INFINITY;
	public static int							NO_STRAND		= 0;
	public static int							POSITIVE_STRAND	= 1;
	public static int							NEGATIVE_STRAND	= -1;
	public static int							UNKNOW_STRAND	= 0;
	public static int							NO_PHASE		= -1;

	private String								sequenceID;
	private String								source;
	private String								type;
	private int									start;
	private int									end;
	private double								score;
	private int									strand;
	private int									phase;
	private Hashtable<String, HashSet<String>>	notes			= new Hashtable<String, HashSet<String>>();

	public String getSequenceID() {
		return sequenceID;
	}

	public void setSequenceID(String sequenceID) {
		this.sequenceID = sequenceID;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getStrand() {
		return strand;
	}

	public void setStrand(int strand) {
		this.strand = strand;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public void addNote(String type, String value) {
		HashSet<String> values = notes.get(type);
		if (values == null) {
			values = new HashSet<String>();
			notes.put(type, values);
		}
		values.add(value);
	}

	public String getNoteValue(String type) {
		HashSet<String> values = notes.get(type);
		if (values == null) {
			return null;
		}
		if (values.size() > 1) {
			throw new RuntimeException();
		}
		return values.iterator().next();
	}

	public Collection<String> getNoteValues(String type) {
		HashSet<String> values = notes.get(type);
		if (values == null) {
			return null;
		}
		return values;
	}

	public Collection<Note> getNotes() {
		Collection<Note> ret = new ArrayList<Note>(notes.size());
		Collection<String> noteKeys = notes.keySet();
		for (String noteKey : noteKeys) {
			Collection<String> noteValues = notes.get(noteKey);
			for (String noteValue : noteValues) {
				ret.add(new SimpleNote(new SimpleType(noteKey, null), noteValue));
			}
		}
		return ret;
	}

	/*	public static final class Impl extends AbstractChangeable implements GFF3Record<SimpleNote<Impl>>
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

			public Impl() {
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

			public String getSequenceID() {
				return this.sequenceID;
			}

			public void setSequenceID(String sequenceID) {
				this.sequenceID = sequenceID;
			}

			public String getSource() {
				return this.source;
			}

			public void setSource(String source) {
				this.source = source;
			}

			public String getType() {
				return this.type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public int getStart() {
				return this.start;
			}

			public void setStart(int start) {
				this.start = start;
			}

			public int getEnd() {
				return this.end;
			}

			public void setEnd(int end) {
				this.end = end;
			}

			public double getScore() {
				return this.score;
			}

			public void setScore(double score) {
				this.score = score;
			}

			public int getStrand() {
				return this.strand;
			}

			public void setStrand(int strand) {
				this.strand = strand;
			}

			public int getPhase() {
				return this.phase;
			}

			public void setPhase(int phase) {
				this.phase = phase;
			}

			@Override
			public SimpleNote<Impl> createNote(String type, String value) {
				return new SimpleNote<Impl>(this, type, value);
			}

			public SimpleNote<Impl> addNote(SimpleNote<Impl> note) {
				return notes.addNote(note);
			}

			public SimpleNote<Impl> getNote(String noteTypeName, String value) {
				return notes.getNote(noteTypeName, value);
			}

			public Collection<SimpleNote<Impl>> getNotes() {
				return notes;
			}

			public Collection<SimpleNote<Impl>> getNotes(String noteTypeName) {
				return notes.getNotes(noteTypeName);
			}

			public void addChangeListener(ChangeListener<SimpleNote<Impl>> cl, ChangeType ct) {
				notes.addChangeListener(cl, ct);
			}

			public boolean isUnchanging(ChangeType ct) {
				return notes.isUnchanging(ct);
			}

			@Override
			public void removeChangeListener(ChangeListener<SimpleNote<Impl>> cl, ChangeType ct) {
				notes.removeChangeListener(cl, ct);
			}

			@Override
			public SimpleNote<Impl> addNote(String value, String type) {
				return addNote(createNote(type, value));
			}

			@Override
			public SimpleNote<Impl> addNote(Note< ? > note) {
				return notes.addNote(note);
			}

			@Override
			public Collection<String> getNotesValues(String noteTypeName) {
				return notes.getNotesValues(noteTypeName);
			}

			@Override
			public Note createNote(Note note) {
				return createNote(note.getType(), note.getValue());
			}

		}*/
}

package org.cycads.parser.gff3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

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

	@Override
	public String toString() {
		String ret = sequenceID + "\t" + source + "\t" + type + "\t" + start + "\t" + end;
		if (score == NO_SCORE) {
			ret += "\t.";
		}
		else {
			ret += "\t" + score;
		}
		if (strand > 0) {
			ret += "\t+";
		}
		else if (strand < 0) {
			ret += "\t+";
		}
		else {
			ret += "\t.";
		}
		if (phase == NO_PHASE) {
			ret += "\t.";
		}
		else {
			ret += "\t" + phase;
		}
		ret+="\t";
		for (Map.Entry<String, HashSet<String>> entry : notes.entrySet()) {
			for (String value : entry.getValue()) {
				ret += entry.getKey() + "=" + value + ";";
			}
		}
		if (ret.endsWith(";")) {
			ret = ret.substring(0, ret.length() - 1);
		}
		return ret;
	}

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
		if (start < 0) {
			start = 0;
		}
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		if (end < 0) {
			end = 0;
		}
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

	public Note addNote(String type, String value) {
		HashSet<String> values = notes.get(type);
		if (values == null) {
			values = new HashSet<String>();
			notes.put(type, values);
		}
		values.add(value);
		return new SimpleNote(new SimpleType(type, null), value);
	}

	public Note removeNote(Note note) {
		HashSet<String> values = notes.get(note.getType().getName());
		if (values == null || values.isEmpty()) {
			return null;
		}
		if (values.remove(note.getValue())) {
			return note;
		}
		return null;
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

}

/*
 * Created on 03/03/2009
 */
package org.cycads.entities.sequence.SQL;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public class SubsequenceSQL implements Subsequence
{

	@Override
	public boolean contains(Subsequence subseq) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection getIntrons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Sequence getSequence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPositiveStrand() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Note addNote(String noteType, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type createNoteType(String noteType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note getNote(String noteType, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getNoteType(String noteType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note> getNotes(String noteType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getNotesValues(String noteType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dbxref addSynonym(String dbName, String accession) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dbxref getSynonym(String dbName, String accession) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSynonyms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSynonyms(String dbName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubseqAnnotations(Dbxref synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubseqAnnotations(Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubseqAnnotations(AnnotationMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubseqAnnotations(AnnotationMethod method, Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubseqAnnotations(AnnotationFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}

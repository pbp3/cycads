/*
 * Created on 03/03/2009
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public class SequenceSQL implements Sequence
{

	@Override
	public Subsequence createSubsequence(int start, int end, Collection introns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Organism getOrganism() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSequenceString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subsequence getSubsequence(int start, int end, Collection introns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubsequences(int start) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getSubsequences(Dbxref synonym) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSequenceString(String seqStr) {
		// TODO Auto-generated method stub

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

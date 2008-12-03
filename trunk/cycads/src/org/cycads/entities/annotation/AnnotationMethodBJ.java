/*
 * Created on 03/12/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;
import java.util.Hashtable;

import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.general.biojava.TermsAndOntologies;

public class AnnotationMethodBJ implements AnnotationMethod
{
	static Hashtable<String, AnnotationMethodBJ>	methods	= new Hashtable<String, AnnotationMethodBJ>();
	ComparableTerm									term;

	private AnnotationMethodBJ(String methodName) {
		term = TermsAndOntologies.getMethodTerm(methodName);
	}

	public static AnnotationMethodBJ getInstance(String methodName) {
		AnnotationMethodBJ ret = methods.get(methodName);
		if (ret == null) {
			ret = new AnnotationMethodBJ(methodName);
			methods.put(methodName, ret);
		}
		return ret;
	}

	public ComparableTerm getTerm() {
		return term;
	}

	@Override
	public String getName() {
		return term.getName();
	}

	@Override
	public int getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Note< ? extends NoteSource> createNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note<AnnotationMethod> addNote(Note<AnnotationMethod> note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note<AnnotationMethod> getNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note<AnnotationMethod>> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note<AnnotationMethod>> getNotes(String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note<AnnotationMethod>> cl, ChangeType ct) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<AnnotationMethod>> cl, ChangeType ct) {
		// TODO Auto-generated method stub

	}

}

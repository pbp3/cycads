/*
 * Created on 03/12/2008
 */
package org.cycads.entities.annotation.BJ;

import java.util.Collection;
import java.util.Hashtable;

import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.exceptions.MethodNotImplemented;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.TermsAndOntologies;

public class AnnotationMethodBJ implements AnnotationMethod
{
	static Hashtable<String, AnnotationMethodBJ>	methods	= new Hashtable<String, AnnotationMethodBJ>();
	ComparableTerm									term;
	int												weight	= 0;

	private AnnotationMethodBJ(String methodName) {
		term = TermsAndOntologies.getOntologyMethods().getOrCreateTerm(methodName);
	}

	public static AnnotationMethodBJ getInstance(String methodName) {
		AnnotationMethodBJ ret = methods.get(methodName);
		if (ret == null) {
			ret = new AnnotationMethodBJ(methodName);
			methods.put(methodName, ret);
		}
		return ret;
	}

	public static AnnotationMethodBJ getInstance(ComparableTerm method) {
		AnnotationMethodBJ ret = getInstance(method.getName());
		if (!ret.getTerm().equals(method)) {
			throw new IllegalArgumentException(Messages.ExceptionAnnotationMethodBJConstructorTerm());
		}
		return ret;
	}

	public static AnnotationMethodBJ getMethodGeneral() {
		return getInstance(ParametersDefault.getMethodNameDefault());
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
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public Note<AnnotationMethod> createNote(String type, String value) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<AnnotationMethod> createNote(Note< ? > note) {
		return createNote(note.getType(), note.getValue());
	}

	@Override
	public Note<AnnotationMethod> addNote(Note< ? > note) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<AnnotationMethod> getNote(String noteTypeName, String value) {
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note<AnnotationMethod>> getNotes() {
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note<AnnotationMethod>> getNotes(String noteTypeName) {
		throw new MethodNotImplemented();
	}

	@Override
	public void addChangeListener(ChangeListener<Note<AnnotationMethod>> cl, ChangeType ct) {
		throw new MethodNotImplemented();
	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		throw new MethodNotImplemented();
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<AnnotationMethod>> cl, ChangeType ct) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<AnnotationMethod> addNote(String type, String value) {
		return addNote(createNote(type, value));
	}

	@Override
	public Collection<String> getNotesValues(String noteTypeName) {
		throw new MethodNotImplemented();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof AnnotationMethodBJ)) {
			return false;
		}
		return this.getTerm().equals(((AnnotationMethodBJ) o).getTerm());
	}

}

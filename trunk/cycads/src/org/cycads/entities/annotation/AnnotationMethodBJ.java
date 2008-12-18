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
		return getInstance(ParametersDefault.getMethodNameGeneral());
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
	public Note< ? extends NoteSource> createNote(String value, String noteTypeName) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<AnnotationMethod> addNote(Note<AnnotationMethod> note) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<AnnotationMethod> getNote(String value, String noteTypeName) {
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

}

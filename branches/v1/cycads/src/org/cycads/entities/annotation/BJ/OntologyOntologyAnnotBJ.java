/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.BJ;

import java.util.Collection;

import org.biojavax.SimpleRankedCrossRef;
import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotOntology;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.exceptions.MethodNotImplemented;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.TermsAndOntologies;

public class OntologyOntologyAnnotBJ
		implements AnnotOntology<OntologyOntologyAnnotBJ, OntologyBJ, OntologyBJ, AnnotationMethodBJ>
{

	public static ComparableOntology	ONTBJ_ONTOLOGY_ONTOLOGY_ANNOT	= TermsAndOntologies.getOntologyAnnotOntologyOntology();
	OntologyBJ							source;
	AnnotationMethodBJ					method;
	OntologyBJ							target;
	// TermWithNotes term;
	ComparableTerm						term;

	protected OntologyOntologyAnnotBJ(OntologyBJ source, AnnotationMethodBJ method, OntologyBJ target) {
		if (source == null || target == null) {
			throw new IllegalArgumentException(Messages.exceptionDBRecordDbRecordLinkBJSourceOrTargetNull());
		}
		this.source = source;
		this.method = method;
		this.target = target;
		term = createTermAnnotation(source, method, target);
		// term.addNote(TermsAndOntologies.getTermNoteTypeAnnotationMethod(), method.getTerm());
	}

	public OntologyOntologyAnnotBJ(ComparableTerm term) {
		if (term.getOntology() != ONTBJ_ONTOLOGY_ONTOLOGY_ANNOT) {
			throw new IllegalArgumentException(Messages.ExceptionDBRecordDbRecordLinkBJConstructorTerm());
		}
		this.term = term;
	}

	public static String joinTermName(String source, String method, String target) {
		return source + ParametersDefault.DBRecordDBRecordSeparator() + method
			+ ParametersDefault.DBRecordDBRecordSeparator() + target;
	}

	public static String[] splitTermName(String termName) {
		String[] a = termName.split(ParametersDefault.DBRecordDBRecordSeparator());
		if (a.length != 3 || a[0].length() == 0 || a[2].length() == 0) {
			throw new IllegalArgumentException(Messages.ExceptionInvalidDBRecordDBRecordLinkName(termName));
		}
		return a;
	}

	@Override
	public OntologyBJ getOntologyTarget() {
		if (target == null) {
			String targetDBNameAndAccession = splitTermName(term.getName())[2];
			String[] a = DBRecordBJ.splitDBNameAndAccession(targetDBNameAndAccession);
			target = OntologyBJ.getOrCreateOntologyBJ(a[0], a[1]);
		}
		return target;
	}

	@Override
	public AnnotationMethodBJ getAnnotationMethod() {
		if (method == null) {
			String methodName = splitTermName(term.getName())[1];
			// ComparableTerm termMethod = term.getNoteTerm(TermsAndOntologies.getTermNoteTypeAnnotationMethod());
			method = AnnotationMethodBJ.getInstance(methodName);
		}
		return method;
	}

	@Override
	public OntologyBJ getSource() {
		if (source == null) {
			String sourceDBNameAndAccession = splitTermName(term.getName())[0];
			String[] a = DBRecordBJ.splitDBNameAndAccession(sourceDBNameAndAccession);
			source = OntologyBJ.getOrCreateOntologyBJ(a[0], a[1]);
		}
		return source;
	}

	@Override
	public Note<OntologyOntologyAnnotBJ> createNote(String type, String value) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<OntologyOntologyAnnotBJ> createNote(Note< ? > note) {
		return createNote(note.getType(), note.getValue());
	}

	@Override
	public Note<OntologyOntologyAnnotBJ> addNote(Note< ? > note) {
		throw new MethodNotImplemented();
	}

	@Override
	public Note<OntologyOntologyAnnotBJ> addNote(String type, String value) {
		return addNote(createNote(type, value));
	}

	@Override
	public Note<OntologyOntologyAnnotBJ> getNote(String noteTypeName, String value) {
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note<OntologyOntologyAnnotBJ>> getNotes() {
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<String> getNotesValues(String noteTypeName) {
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note<OntologyOntologyAnnotBJ>> getNotes(String noteTypeName) {
		throw new MethodNotImplemented();
	}

	@Override
	public void addChangeListener(ChangeListener<Note<OntologyOntologyAnnotBJ>> cl, ChangeType ct) {
		throw new MethodNotImplemented();
	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		throw new MethodNotImplemented();
	}

	@Override
	public void removeChangeListener(ChangeListener<Note<OntologyOntologyAnnotBJ>> cl, ChangeType ct) {
		throw new MethodNotImplemented();
	}

	@Override
	public String toString() {
		return joinTermName(getSource().toString(), getAnnotationMethod().getName(), getOntologyTarget().toString());
	}

	public static ComparableTerm createTermAnnotation(OntologyBJ source, AnnotationMethodBJ method, OntologyBJ target) {
		ComparableTerm term;
		term = ONTBJ_ONTOLOGY_ONTOLOGY_ANNOT.getOrCreateTerm(joinTermName(source.toString(),
			method.getTerm().getName(), target.toString()));
		term.addRankedCrossRef(new SimpleRankedCrossRef(source.getCrossRef(), 0));
		term.addRankedCrossRef(new SimpleRankedCrossRef(target.getCrossRef(), 0));
		return term;
	}

}

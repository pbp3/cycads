/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.biojavax.SimpleRankedCrossRef;
import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.exceptions.MethodNotImplemented;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.TermsAndOntologies;

public class DBRecordDBRecordLinkBJ implements DBLink<DBRecordBJ, DBRecordBJ, AnnotationMethodBJ>
{

	public static ComparableOntology	ontDBRecordDBRecordLink	= TermsAndOntologies.getOntologyDBRecordDBRecordLink();
	DBRecordBJ							source;
	AnnotationMethodBJ					method;
	DBRecordBJ							target;
	// TermWithNotes term;
	ComparableTerm						term;

	protected DBRecordDBRecordLinkBJ(DBRecordBJ source, AnnotationMethodBJ method, DBRecordBJ target)
	{
		if (source == null || target == null)
		{
			throw new IllegalArgumentException(Messages.exceptionDBRecordDbRecordLinkBJSourceOrTargetNull());
		}
		this.source = source;
		this.method = method;
		this.target = target;
		term = ontDBRecordDBRecordLink.getOrCreateTerm(joinTermName(source.toString(), method.getTerm().getName(),
			target.toString()));
		term.addRankedCrossRef(new SimpleRankedCrossRef(source.getCrossRef(), 0));
		term.addRankedCrossRef(new SimpleRankedCrossRef(target.getCrossRef(), 0));
		// term.addNote(TermsAndOntologies.getTermNoteTypeAnnotationMethod(), method.getTerm());
	}

	protected DBRecordDBRecordLinkBJ(ComparableTerm term)
	{
		if (term.getOntology() != ontDBRecordDBRecordLink)
		{
			throw new IllegalArgumentException(Messages.ExceptionDBRecordDbRecordLinkBJConstructorTerm());
		}
		this.term = term;
	}

	public static String joinTermName(String source, String method, String target)
	{
		return source + ParametersDefault.DBRecordDBRecordSeparator() + method
			+ ParametersDefault.DBRecordDBRecordSeparator() + target;
	}

	public static String[] splitTermName(String termName)
	{
		String[] a = termName.split(ParametersDefault.DBRecordDBRecordSeparator());
		if (a.length != 3 || a[0].length() == 0 || a[2].length() == 0)
		{
			throw new IllegalArgumentException(Messages.ExceptionInvalidDBRecordDBRecordLinkName(termName));
		}
		return a;
	}

	@Override
	public DBRecordBJ getDBRecordTarget()
	{
		if (target == null)
		{
			String targetDBNameAndAccession = splitTermName(term.getName())[2];
			String[] a = DBRecordBJ.splitDBNameAndAccession(targetDBNameAndAccession);
			target = DBRecordBJ.getOrCreateDBRecordBJ(a[0], a[1]);
		}
		return target;
	}

	@Override
	public AnnotationMethodBJ getAnnotationMethod()
	{
		if (method == null)
		{
			String methodName = splitTermName(term.getName())[1];
			// ComparableTerm termMethod = term.getNoteTerm(TermsAndOntologies.getTermNoteTypeAnnotationMethod());
			method = AnnotationMethodBJ.getInstance(methodName);
		}
		return method;
	}

	@Override
	public DBRecordBJ getSource()
	{
		if (source == null)
		{
			String sourceDBNameAndAccession = splitTermName(term.getName())[0];
			String[] a = DBRecordBJ.splitDBNameAndAccession(sourceDBNameAndAccession);
			source = DBRecordBJ.getOrCreateDBRecordBJ(a[0], a[1]);
		}
		return source;
	}

	@Override
	public Note< ? extends NoteSource> createNote(String value, String noteTypeName)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>> addNote(
			Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>> note)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>> getNote(String value, String noteTypeName)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>>> getNotes()
	{
		throw new MethodNotImplemented();
	}

	@Override
	public Collection<Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>>> getNotes(String noteTypeName)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public void addChangeListener(ChangeListener<Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>>> cl,
			ChangeType ct)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public boolean isUnchanging(ChangeType ct)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public void removeChangeListener(ChangeListener<Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>>> cl,
			ChangeType ct)
	{
		throw new MethodNotImplemented();
	}

	@Override
	public String toString()
	{
		return joinTermName(getSource().toString(), getAnnotationMethod().getName(), getDBRecordTarget().toString());
	}

}

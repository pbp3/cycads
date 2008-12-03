/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTriple;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NoteSource;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.TermsAndOntologies;

public class DBRecordDBRecordLinkBJ implements DBLink<DBRecordBJ, DBRecordBJ, AnnotationMethodBJ>
{

	public static ComparableOntology	ontDBRecordDBRecordLink	= TermsAndOntologies.getOntologyDBRecordDBRecordLink();
	DBRecordBJ							source;
	AnnotationMethodBJ					method;
	DBRecordBJ							target;
	TermWithNotes						term;

	protected DBRecordDBRecordLinkBJ(DBRecordBJ source, AnnotationMethodBJ method, DBRecordBJ target) {
		if (source == null || target == null) {
			throw new IllegalArgumentException(Messages.ExceptionDBRecordDbRecordLinkBJSourceOrTargetNull());
		}
		this.source = source;
		this.method = method;
		this.target = target;
		term = new TermWithNotes(ontDBRecordDBRecordLink.getOrCreateTerm(source.toString()
			+ ParametersDefault.DBRecordDBRecordSeparator() + target.toString()));
		term.addNote(TermsAndOntologies.getTermNoteTypeAnnotationMethod(), method.getTerm());
	}

	public DBRecordDBRecordLinkBJ(ComparableTriple triple) {
		this.triple = triple;
	}

	@Override
	public DBRecordBJ getDBRecordTarget() {
		if (target==null)
		{
			target=
		}
		return target;
	}

	@Override
	public AnnotationMethodBJ getAnnotationMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBRecordBJ getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends NoteSource> createNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>> addNote(
			Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>> note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>> getNote(String value, String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>>> getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>>> getNotes(String noteTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>>> cl,
			ChangeType ct) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUnchanging(ChangeType ct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeChangeListener(ChangeListener<Note< ? extends Annotation<DBRecordBJ, AnnotationMethodBJ>>> cl,
			ChangeType ct) {
		// TODO Auto-generated method stub

	}
}

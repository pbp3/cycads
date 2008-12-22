/*
 * Created on 22/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.dBLink.Ontology;
import org.cycads.entities.change.ChangeListener;
import org.cycads.entities.change.ChangeType;
import org.cycads.entities.note.Note;

public class OntologyBJ extends DBRecordBJ implements Ontology<OntologyOntologyAnnotBJ, OntologyBJ, AnnotationMethodBJ>
{

	@Override
	public String[] getFunctions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChangeListener(ChangeListener<Note<DBRecordBJ>> cl, ChangeType ct) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeChangeListener(ChangeListener<Note<DBRecord>> cl, ChangeType ct) {
		// TODO Auto-generated method stub

	}

	@Override
	public OntologyOntologyAnnotBJ createOntologyAnnot(AnnotationMethodBJ method, OntologyBJ target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntologyOntologyAnnotBJ createOntologyAnnot(AnnotationMethodBJ method, String accession, String dbName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntologyOntologyAnnotBJ createOntologyAnnot(String method, OntologyBJ target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OntologyOntologyAnnotBJ createOntologyAnnot(String method, String accession, String dbName) {
		// TODO Auto-generated method stub
		return null;
	}

}

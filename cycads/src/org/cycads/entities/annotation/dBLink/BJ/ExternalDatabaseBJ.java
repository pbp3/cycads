/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.dBLink.ExternalDatabase;

public class ExternalDatabaseBJ implements ExternalDatabase
{
	ComparableOntology	ont;

	public ExternalDatabaseBJ(ComparableOntology ont) {
		this.ont = ont;
	}

	@Override
	public String getDbName() {
		return ont.getName();
	}

	@Override
	public Collection<DBRecordBJ> getRecords() {
		Set<ComparableTerm> records = ((Set<ComparableTerm>) ont.getTermSet());
		ArrayList<DBRecordBJ> result = new ArrayList<DBRecordBJ>(records.size());
		for (ComparableTerm recordTerm : records) {
			result.add(new DBRecordBJ(recordTerm));
		}
		return result;
	}

}

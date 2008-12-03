/*
 * Created on 02/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.dBLink.ExternalDatabase;
import org.cycads.general.biojava.TermsAndOntologies;

public class ExternalDatabaseBJ implements ExternalDatabase<DBRecordBJ>
{
	static Hashtable<ComparableOntology, ExternalDatabaseBJ> externalDBs = new Hashtable<ComparableOntology, ExternalDatabaseBJ>();

	Hashtable<Term,DBRecodBJ>
	ComparableOntology	ont;
	
	private ExternalDatabaseBJ(ComparableOntology ont)
	{
		this.ont = ont;
	}

	private ExternalDatabaseBJ(String ontName)
	{
		this.ont = TermsAndOntologies.getOntologyExternalDB(ontName);
	}

	@Override
	public String getDbName()
	{
		return ont.getName();
	}

	@Override
	public Collection<DBRecordBJ> getRecords()
	{
		Set<ComparableTerm> records = ((Set<ComparableTerm>) ont.getTermSet());
		ArrayList<DBRecordBJ> result = new ArrayList<DBRecordBJ>(records.size());
		for (ComparableTerm recordTerm : records)
		{
			result.add(new DBRecordBJ(recordTerm));
		}
		return result;
	}

	@Override
	public DBRecordBJ getOrCreateDBRecord(String accession)
	{
		return getOrCreateDBRecord(ont.getOrCreateTerm(accession));
	}

	public DBRecordBJ getOrCreateDBRecord(ComparableTerm accession)
	{
		return new DBRecordBJ(accession);
	}

	public static ExternalDatabaseBJ getOrCreateExternalDB(ComparableOntology ontology)
	{
		ExternalDatabaseBJ externalDB = externalDBs.get(ontology);
		if (externalDB==null)
		{
			externalDB= new ExternalDatabaseBJ(ontology);
			externalDBs.put(ontology, externalDB);
		}
		return externalDB;
	}

}

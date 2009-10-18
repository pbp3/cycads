package org.cycads.entities.synonym;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.SimpleNoteble;

public class SimpleHasSynonymsNoteble extends SimpleNoteble implements HasSynonyms
{

	protected EntityDbxrefFactory< ? >				dbxrefFactory;
	private Hashtable<String, Collection<String>>	synonyms	= new Hashtable<String, Collection<String>>();

	public SimpleHasSynonymsNoteble(EntityTypeFactory< ? > typeFactory, EntityDbxrefFactory< ? > factory) {
		super(typeFactory);
		this.dbxrefFactory = factory;
	}

	@Override
	public Dbxref addSynonym(String dbName, String accession) {
		Collection<String> accessions = synonyms.get(dbName);
		if (accessions == null) {
			accessions = new TreeSet<String>();
			synonyms.put(dbName, accessions);
		}
		if (accessions.add(accession)) {
			return dbxrefFactory.getDbxref(dbName, accession);
		}
		else {
			return null;
		}
	}

	@Override
	public void addSynonym(Dbxref dbxref) {
		String dbName = dbxref.getDbName();
		String accession = dbxref.getAccession();
		Collection<String> accessions = synonyms.get(dbName);
		if (accessions == null) {
			accessions = new TreeSet<String>();
			synonyms.put(dbName, accessions);
		}
		accessions.add(accession);
	}

	@Override
	public Dbxref getSynonym(String dbName, String accession) {
		Collection<String> values = synonyms.get(dbName);
		if (values == null || !values.contains(accession)) {
			return null;
		}
		return dbxrefFactory.getDbxref(dbName, accession);
	}

	@Override
	public Collection<Dbxref> getSynonyms() {
		Collection<Dbxref> ret = new ArrayList<Dbxref>();
		Set<Entry<String, Collection<String>>> entries = synonyms.entrySet();
		for (Entry<String, Collection<String>> entry : entries) {
			String dbName = entry.getKey();
			for (String accession : entry.getValue()) {
				ret.add(dbxrefFactory.getDbxref(dbName, accession));
			}
		}
		return ret;
	}

	@Override
	public Collection<Dbxref> getSynonyms(String dbName) {
		Collection<Dbxref> ret = new ArrayList<Dbxref>();
		Collection<String> accessions = synonyms.get(dbName);
		for (String accession : accessions) {
			ret.add(dbxrefFactory.getDbxref(dbName, accession));
		}
		return ret;
	}

	@Override
	public boolean isSynonym(Dbxref dbxref) {
		return isSynonym(dbxref.getDbName(), dbxref.getAccession());
	}

	@Override
	public boolean isSynonym(String dbName, String accession) {
		return (getSynonym(dbName, accession) != null);
	}

}

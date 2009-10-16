package org.cycads.entities.synonym;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.note.SimpleNoteble;

public class SimpleHasSynonymsNoteble<X extends Dbxref< ? , ? , ? , ? >> extends SimpleNoteble
		implements HasSynonyms<X>
{

	protected EntityFactory< ? extends X, ? , ? , ? , ? , ? >	factory;
	private Hashtable<String, Collection<String>>				synonyms	= new Hashtable<String, Collection<String>>();

	public SimpleHasSynonymsNoteble(EntityFactory< ? extends X, ? , ? , ? , ? , ? > factory) {
		super(factory);
		this.factory = factory;
	}

	@Override
	public X addSynonym(String dbName, String accession) {
		Collection<String> accessions = synonyms.get(dbName);
		if (accessions == null) {
			accessions = new TreeSet<String>();
			synonyms.put(dbName, accessions);
		}
		if (accessions.add(accession)) {
			return factory.getDbxref(dbName, accession);
		}
		else {
			return null;
		}
	}

	@Override
	public void addSynonym(X dbxref) {
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
	public X getSynonym(String dbName, String accession) {
		Collection<String> values = synonyms.get(dbName);
		if (values == null || !values.contains(accession)) {
			return null;
		}
		return factory.getDbxref(dbName, accession);
	}

	@Override
	public Collection< ? extends X> getSynonyms() {
		Collection<X> ret = new ArrayList<X>();
		Set<Entry<String, Collection<String>>> entries = synonyms.entrySet();
		for (Entry<String, Collection<String>> entry : entries) {
			String dbName = entry.getKey();
			for (String accession : entry.getValue()) {
				ret.add(factory.getDbxref(dbName, accession));
			}
		}
		return ret;
	}

	@Override
	public Collection< ? extends X> getSynonyms(String dbName) {
		Collection<X> ret = new ArrayList<X>();
		Collection<String> accessions = synonyms.get(dbName);
		for (String accession : accessions) {
			ret.add(factory.getDbxref(dbName, accession));
		}
		return ret;
	}

	@Override
	public boolean isSynonym(X dbxref) {
		return isSynonym(dbxref.getDbName(), dbxref.getAccession());
	}

	@Override
	public boolean isSynonym(String dbName, String accession) {
		return (getSynonym(dbName, accession) != null);
	}

}

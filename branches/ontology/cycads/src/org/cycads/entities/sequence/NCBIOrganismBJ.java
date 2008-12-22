/*
 * Created on 06/06/2008
 */
package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.biojavax.bio.taxa.NCBITaxon;
import org.cycads.exceptions.DBObjectNotFound;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.BioSql;
import org.hibernate.Query;

public class NCBIOrganismBJ implements Organism<ThinSequenceBJ>
{
	NCBITaxon	taxon;

	public NCBIOrganismBJ(NCBITaxon taxon) {
		this.taxon = taxon;
	}

	public NCBIOrganismBJ(int ncbiTaxonNumber) throws DBObjectNotFound {
		this.taxon = getTaxon(ncbiTaxonNumber);
		if (this.taxon == null) {
			throw new DBObjectNotFound(new Object[] {ncbiTaxonNumber});
		}
	}

	public String getName() {
		return taxon.getDisplayName();
	}

	public NCBITaxon getTaxon() {
		return taxon;
	}

	public int getId() {
		return taxon.getNCBITaxID();
	}

	public static NCBITaxon getTaxon(int ncbiTaxonNumber) {
		Query taxonsQuery = BioJavaxSession.createQuery("from Taxon where ncbi_taxon_id=:ncbiTaxonNumber");
		taxonsQuery.setInteger("ncbiTaxonNumber", ncbiTaxonNumber);
		return (NCBITaxon) taxonsQuery.uniqueResult();
	}

	public Collection<ThinSequenceBJ> getSequences(double version) {
		Query query = BioJavaxSession.createQuery("select id from ThinSequence where version=:version and taxon=:taxonId");
		query.setDouble("version", version);
		query.setParameter("taxonId", getTaxon());
		Collection<Integer> seqIds = (Collection<Integer>) query.list();
		Collection<ThinSequenceBJ> seqs = new LinkedList<ThinSequenceBJ>();
		for (int seqId : seqIds) {
			seqs.add(new ThinSequenceBJ(seqId, this));
		}
		return seqs;
	}

	public Collection<ThinSequenceBJ> getSequences() {
		Query query = BioJavaxSession.createQuery("select id from ThinSequence where taxon=:taxonId");
		query.setParameter("taxonId", getTaxon());
		Collection<Integer> seqIds = (Collection<Integer>) query.list();
		Collection<ThinSequenceBJ> seqs = new LinkedList<ThinSequenceBJ>();
		for (int seqId : seqIds) {
			seqs.add(new ThinSequenceBJ(seqId, this));
		}
		return seqs;
	}

	@Override
	public ThinSequenceBJ getOrCreateSequence(String seqAccession, int version) {
		ThinSequenceBJ seq;
		int seqId = BioSql.getSequenceId(seqAccession, version);
		if (seqId > 0) {
			//there is in the database: get the sequence
			return new ThinSequenceBJ(seqId, this);
		}
		else {
			//create in the database
			seq = this.createSequence(seqAccession, version);
		}
		return seq;
	}

	@Override
	public Collection<ThinSequenceBJ> getSequences(String seqDatabase, String seqAccession) {
		ArrayList<ThinSequenceBJ> ret = new ArrayList<ThinSequenceBJ>();
		Collection<Integer> seqIds = BioSql.getSequencesIdWithExternalDBLink(seqDatabase, seqAccession, this);
		for (int seqId : seqIds) {
			ret.add(new ThinSequenceBJ(seqId, this));
		}
		return ret;
	}

}

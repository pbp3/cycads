/*
 * Created on 06/06/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;
import java.util.LinkedList;

import org.biojavax.bio.taxa.NCBITaxon;
import org.cycads.exceptions.DBObjectNotFound;
import org.cycads.general.biojava.BioJavaxSession;
import org.hibernate.Query;

public class NCBIOrganismBJ implements Organism
{
	NCBITaxon	taxon;

	public NCBIOrganismBJ(NCBITaxon taxon)
	{
		this.taxon = taxon;
	}

	public NCBIOrganismBJ(int ncbiTaxonNumber) throws DBObjectNotFound
	{
		this.taxon = getTaxon(ncbiTaxonNumber);
		if (this.taxon == null)
		{
			throw new DBObjectNotFound(new Object[] {ncbiTaxonNumber});
		}
	}

	public String getName()
	{
		return taxon.getDisplayName();
	}

	public NCBITaxon getTaxon()
	{
		return taxon;
	}

	public int getId()
	{
		return taxon.getNCBITaxID();
	}

	public static NCBITaxon getTaxon(int ncbiTaxonNumber)
	{
		Query taxonsQuery = BioJavaxSession.createQuery("from Taxon where ncbi_taxon_id=:ncbiTaxonNumber");
		taxonsQuery.setInteger("ncbiTaxonNumber", ncbiTaxonNumber);
		return (NCBITaxon) taxonsQuery.uniqueResult();
	}

	public Collection<Sequence> getSequences(double version)
	{
		Query query = BioJavaxSession.createQuery("select id from ThinSequence where version=:version and taxon=:taxonId");
		query.setDouble("version", version);
		query.setParameter("taxonId", getTaxon());
		Collection<Integer> seqIds = (Collection<Integer>) query.list();
		Collection<Sequence> seqs = new LinkedList<Sequence>();
		for (int seqId : seqIds)
		{
			seqs.add(new ThinSequenceBJ(seqId));
		}
		return seqs;
	}

	public Collection<Sequence> getSequences()
	{
		Query query = BioJavaxSession.createQuery("select id from ThinSequence where taxon=:taxonId");
		query.setParameter("taxonId", getTaxon());
		Collection<Integer> seqIds = (Collection<Integer>) query.list();
		Collection<Sequence> seqs = new LinkedList<Sequence>();
		for (int seqId : seqIds)
		{
			seqs.add(new ThinSequenceBJ(seqId));
		}
		return seqs;
	}

}

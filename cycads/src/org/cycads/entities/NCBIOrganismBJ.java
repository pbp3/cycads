/*
 * Created on 06/06/2008
 */
package org.cycads.entities;

import org.biojavax.bio.taxa.NCBITaxon;
import org.cycads.exceptions.DBObjectNotFound;
import org.cycads.general.biojava.BioJavaxSession;
import org.hibernate.Query;

public class NCBIOrganismBJ implements Organism
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

	public int getNumberID() {
		return taxon.getNCBITaxID();
	}

	public static NCBITaxon getTaxon(int ncbiTaxonNumber) {
		Query taxonsQuery = BioJavaxSession.session.createQuery("from Taxon where ncbi_taxon_id=:ncbiTaxonNumber");
		taxonsQuery.setInteger("ncbiTaxonNumber", ncbiTaxonNumber);
		return (NCBITaxon) taxonsQuery.uniqueResult();
	}

}

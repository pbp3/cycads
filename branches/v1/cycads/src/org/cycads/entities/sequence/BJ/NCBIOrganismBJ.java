/*
 * Created on 06/06/2008
 */
package org.cycads.entities.sequence.BJ;

import java.util.Collection;
import java.util.LinkedList;

import org.biojavax.RichObjectFactory;
import org.biojavax.bio.seq.ThinRichSequence;
import org.biojavax.bio.taxa.NCBITaxon;
import org.cycads.entities.annotation.BJ.DBRecordBJ;
import org.cycads.entities.sequence.Organism;
import org.cycads.exceptions.DBObjectNotFound;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.BioSql;
import org.hibernate.Query;

public class NCBIOrganismBJ implements Organism<ThinSequenceBJ>
{

	static int	seqVersionDeafult	= ParametersDefault.sequenceVersion();

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

	public ThinSequenceBJ getOrCreateSequence(String seqDatabase, String seqAccession, int version) {
		ThinSequenceBJ seq = getSequence(seqDatabase, seqAccession);
		if (seq == null) {
			seq = createSequence(seqAccession, version);
			if (!seqDatabase.equals(RichObjectFactory.getDefaultNamespace())) {
				seq.addDBRecord(seqDatabase, seqAccession);
			}
		}
		return seq;
	}

	@Override
	public ThinSequenceBJ getOrCreateSequence(String seqDatabase, String seqAccession) {
		return getOrCreateSequence(seqDatabase, seqAccession, seqVersionDeafult);
	}

	private ThinSequenceBJ createSequence(String seqAccession, int version) {
		ThinRichSequence richSeq = new ThinRichSequence(RichObjectFactory.getDefaultNamespace(), seqAccession,
			seqAccession, version, null, null);
		richSeq.setTaxon(getTaxon());
		BioJavaxSession.saveOrUpdate("ThinSequence", richSeq);
		return new ThinSequenceBJ(richSeq);
	}

	@Override
	public ThinSequenceBJ getSequence(String seqDatabase, String seqAccession) {
		return getSequence(seqDatabase, seqAccession, seqVersionDeafult);
	}

	public ThinSequenceBJ getSequence(String seqDatabase, String seqAccession, int version) {
		if (seqDatabase.equals(RichObjectFactory.getDefaultNamespace())) {
			//search with internal id
			int seqId = BioSql.getSequenceId(seqAccession, version, RichObjectFactory.getDefaultNamespace());
			if (seqId > 0) {
				//there is in the database: get the sequence
				return new ThinSequenceBJ(seqId, this);
			}
			else {
				return null;
			}
		}
		else {
			Collection<Integer> seqIds = BioSql.getSequencesIdByDBXRef(DBRecordBJ.getOrCreateDBRecordBJ(seqDatabase,
				seqAccession).getCrossRef(), this.getTaxon());
			if (seqIds.size() == 1) {
				return new ThinSequenceBJ(seqIds.iterator().next(), this);
			}
			if (seqIds.isEmpty()) {
				return null;
			}
			throw new RuntimeException("External ID points to more than one sequence.");
		}
	}
}

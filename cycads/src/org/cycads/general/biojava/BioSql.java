/*
 * Created on 10/06/2008
 */
package org.cycads.general.biojava;

import java.util.ArrayList;
import java.util.Collection;

import org.biojavax.CrossRef;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichLocation;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotationMethod;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

public class BioSql
{

	public static Collection<Integer> getFeaturesId(int seqId) {
		Query query = BioJavaxSession.createQuery("select f.id from Feature as f join f.parent as b where "
			+ "b.id=:seqId ");
		query.setInteger("seqId", seqId);
		Collection<Integer> results = query.list();
		return results;
	}

	public static Collection<String> getAccessions(String dbName) {
		Query query = BioJavaxSession.createQuery("select accession from CrossRef where dbname=:dbName ");
		query.setString("dbName", dbName);
		Collection<String> results = query.list();
		return results;
	}

	public static Collection<ComparableTerm> getTermsWithCrossRef(CrossRef crossRef) {
		Query query = BioJavaxSession.createQuery("select distinct t from Term as t where "
			+ "t.rankedCrossRefs.crossRef=:crossRef ");
		query.setEntity("crossRef", crossRef);
		Collection<ComparableTerm> results = query.list();
		return results;
	}

	public static Collection<RichFeature> getFeatures(RichLocation location, String type, AnnotationMethod method) {
		ComparableTerm termType = TermsAndOntologies.getTermFeatureType(type);
		ComparableTerm termMethod = TermsAndOntologies.getOntologyMethods().getOrCreateTerm(method.getName());
		SQLQuery query = BioJavaxSession.createSqlQuery("select f.Seqfeature_id from Seqfeature as f, Location as l  where "
			+ "f.Bioentry_id=:seqId and f.type_term_id=:typeId and f.source_term_id=:methodId"
			+ "and f.Seqfeature_id=l.Seqfeature_id and l.start_pos = :start");

		query.setInteger("seqId", getSequenceId((RichSequence) location.getFeature().getSequence()));
		query.setInteger("typeId", getTermId(termType));
		query.setInteger("methodId", getTermId(termMethod));
		query.setInteger("start", location.getMinPosition().getStart());
		Collection<Integer> featuresId = query.list();
		Collection<RichFeature> features = new ArrayList<RichFeature>();
		for (Integer featureId : featuresId) {
			RichFeature feature = getFeature(featureId);
			if (feature != null && feature.getLocation().equals(location)) {
				features.add(feature);
			}
		}
		return features;
	}

	public static Collection<RichFeature> getFeatures(RichLocation location, String type) {
		ComparableTerm termType = TermsAndOntologies.getTermFeatureType(type);
		SQLQuery query = BioJavaxSession.createSqlQuery("select f.Seqfeature_id from Seqfeature as f, Location as l  where "
			+ "f.Bioentry_id=:seqId and f.type_term_id=:typeId "
			+ "and f.Seqfeature_id=l.Seqfeature_id and l.start_pos = :start");

		query.setInteger("seqId", getSequenceId((RichSequence) location.getFeature().getSequence()));
		query.setInteger("typeId", getTermId(termType));
		query.setInteger("start", location.getMinPosition().getStart());
		Collection<Integer> featuresId = query.list();
		Collection<RichFeature> features = new ArrayList<RichFeature>();
		for (Integer featureId : featuresId) {
			RichFeature feature = getFeature(featureId);
			if (feature != null && feature.getLocation().equals(location)) {
				features.add(feature);
			}
		}
		return features;
	}

	public static Collection<RichFeature> getFeatures(RichLocation location) {
		SQLQuery query = BioJavaxSession.createSqlQuery("select f.Seqfeature_id from Seqfeature as f, Location as l  where "
			+ "f.Bioentry_id=:seqId " + "and f.Seqfeature_id=l.Seqfeature_id and l.start_pos = :start");

		query.setInteger("seqId", getSequenceId((RichSequence) location.getFeature().getSequence()));
		query.setInteger("start", location.getMinPosition().getStart());
		Collection<Integer> featuresId = query.list();
		Collection<RichFeature> features = new ArrayList<RichFeature>();
		for (Integer featureId : featuresId) {
			RichFeature feature = getFeature(featureId);
			if (feature != null && feature.getLocation().equals(location)) {
				features.add(feature);
			}
		}
		return features;
	}

	public static int getSequenceId(RichSequence richSeq) {
		Query query = BioJavaxSession.createQuery("select s.id from ThinSequence s where s=:seq ");
		query.setEntity("seq", richSeq);
		return (Integer) query.uniqueResult();
	}

	public static int getTermId(ComparableTerm term) {
		Query query = BioJavaxSession.createQuery("select t.id from Term t where t=:term ");
		query.setEntity("term", term);
		return (Integer) query.uniqueResult();
	}

	public static RichFeature getFeature(Integer id) {
		Query query = BioJavaxSession.createQuery("from Feature where id=:id");
		query.setInteger("id", id);
		return (RichFeature) query.uniqueResult();
	}

	// public static List<RichFeature> getFeatures(ComparableTerm type, Organism organism, int version) {
	// Query query = session.createQuery("select f from Feature as f join f.parent as b where "
	// + "b.version=:version and f.typeTerm=:typeTerm and b.taxon=:taxonId ");
	// query.setInteger("version", version);
	// query.setParameter("taxonId", organism.getTaxon());
	// query.setParameter("typeTerm", type);
	// return query.list();
	// }
	//
	// public static CrossRef getCrossRef(String dbName, String accession) {
	// Query query = BioJavaxSession.createQuery("from CrossRef where dbname=:dbName and accession=:accession");
	// query.setString("dbName", dbName);
	// query.setString("accession", accession);
	// return (CrossRef) query.uniqueResult();
	// }
	// public static NCBITaxon getTaxon(int ncbiTaxonNumber) {
	// Query taxonsQuery = session.createQuery("from Taxon where ncbi_taxon_id=:ncbiTaxonNumber");
	// taxonsQuery.setInteger("ncbiTaxonNumber", ncbiTaxonNumber);
	// List taxons = taxonsQuery.list();
	// if (taxons.size() != 1) {
	// return null;
	// }
	// return (NCBITaxon) taxons.get(0);
	// }
	//
	// public static Sequence getSequence(Organism organism, String seqName) {
	// Query query = session.createQuery("from Sequence where name=:seqName and taxon=:taxonId");
	// query.setString("seqName", seqName);
	// query.setParameter("taxonId", organism.getTaxon());
	// List seqs = query.list();
	// if (seqs.size() != 1) {
	// return null;
	// }
	// RichSequence seq = (RichSequence) seqs.get(0);
	// return new Sequence(seq, getCompilation(organism, seq.getVersion()));
	// }
	//
	// public static Collection<Integer> getSequencesIdCDStRNAmRNA(Organism organism, int version) {
	// Query query = session.createQuery("select distinct(b.id) from Feature as f join f.parent as b where "
	// + "b.version=:version and b.taxon=:taxonId and (f.typeTerm=:typeCDS or f.typeTerm=:typeMiscRNA or
	// f.typeTerm=:typetRNA)");
	// query.setInteger("version", version);
	// query.setParameter("taxonId", organism.getTaxon());
	// query.setParameter("typeCDS", TermsAndOntologies.getTermCDS());
	// query.setParameter("typeMiscRNA", TermsAndOntologies.getTermMiscRNA());
	// query.setParameter("typetRNA", TermsAndOntologies.getTermTRNA());
	// return (Collection<Integer>) query.list();
	// }
	//
	// public static Collection<Integer> getSequencesId(Organism organism, int version) {
	// Query query = session.createQuery("select id from ThinSequence where version=:version and taxon=:taxonId");
	// query.setInteger("version", version);
	// query.setParameter("taxonId", organism.getTaxon());
	// return (Collection<Integer>) query.list();
	// }
	//
	// public static String getStringSeq(Integer id) {
	// Query query = session.createQuery("select rs.stringSequence from Sequence as rs where rs.id=:id");
	// query.setInteger("id", id);
	// return (String) query.uniqueResult();
	// }
	//
	// public static RichSequence getSequence(Integer id) {
	// Query query = session.createQuery("from ThinSequence where id=:id");
	// query.setInteger("id", id);
	// return (RichSequence) query.uniqueResult();
	// }
	//
	// public static Compilation getCompilation(Organism organism, int seqVersion) {
	// ComparableOntology ont = TermsAndOntologies.getCompilationOnt(organism);
	// Set<ComparableTerm> comps = ont.getTermSet();
	// for (ComparableTerm comp : comps) {
	// if (Double.parseDouble(comp.getDescription()) == seqVersion) {
	// return new Compilation(organism, comp);
	// }
	// }
	// return null;
	// }
	//
	// public static List<Integer> getCDSMiscRNATRNA(Organism organism, int version) {
	// Query query = session.createQuery("select f.id from Feature as f join f.parent as b where "
	// + "b.version=:version and (f.typeTerm=:cdsTerm or f.typeTerm=:miscRNATerm or f.typeTerm=:tTRNATerm) "
	// + "and b.taxon=:taxonId order by b.id");
	// query.setInteger("version", version);
	// query.setParameter("taxonId", organism.getTaxon());
	// query.setParameter("cdsTerm", TermsAndOntologies.getTermCDS());
	// query.setParameter("miscRNATerm", TermsAndOntologies.getTermMiscRNA());
	// query.setParameter("tTRNATerm", TermsAndOntologies.getTermTRNA());
	// return query.list();
	// }
	//
	// public static List<Integer> getCDSMiscRNATRNABySeqId(Integer seqId) {
	// Query query = session.createQuery("select f.id from Feature as f join f.parent as b where "
	// + "b.id=:seqId and (f.typeTerm=:cdsTerm or f.typeTerm=:miscRNATerm or f.typeTerm=:tTRNATerm) ");
	// query.setInteger("seqId", seqId);
	// query.setParameter("cdsTerm", TermsAndOntologies.getTermCDS());
	// query.setParameter("miscRNATerm", TermsAndOntologies.getTermMiscRNA());
	// query.setParameter("tTRNATerm", TermsAndOntologies.getTermTRNA());
	// return query.list();
	// }
	//
	// public static Gene getGene(String geneName, Organism organism) {
	// Query query = session.createQuery("select f from Feature as f join f.parent as b where "
	// + "f.name=:geneName and f.typeTerm=:geneTerm and b.taxon=:taxonId ");
	// query.setString("geneName", geneName);
	// query.setParameter("taxonId", organism.getTaxon());
	// query.setParameter("geneTerm", TermsAndOntologies.getTermGene());
	// List features = query.list();
	// if (features.size() != 1) {
	// return null;
	// }
	// SimpleRichFeature feature = (SimpleRichFeature) features.get(0);
	// feature.toString();
	// return new Gene(feature);
	// }
	//
	// public static CDS getCDS(String cdsName, Organism organism) {
	// Query query = session.createQuery("select f from Feature as f join f.parent as b where "
	// + "f.name=:cdsName and f.typeTerm=:cdsTerm and b.taxon=:taxonId ");
	// query.setString("cdsName", cdsName);
	// query.setParameter("taxonId", organism.getTaxon());
	// query.setParameter("cdsTerm", TermsAndOntologies.getTermCDS());
	// List features = query.list();
	// if (features.size() != 1) {
	// return null;
	// }
	// SimpleRichFeature feature = (SimpleRichFeature) features.get(0);
	// feature.toString();
	// return new CDS(feature);
	// }
	//
	// public static RichFeature getParent(RichFeature feature) {
	// Query query = session.createQuery("from FeatureRelationship as f "
	// + "where f.term=:containsTerm and f.subject=:feature");
	// query.setParameter("containsTerm", SimpleRichFeatureRelationship.getContainsTerm());
	// query.setParameter("feature", feature);
	// // RichFeatureRelationship featureRelationship = (RichFeatureRelationship) query.uniqueResult();
	//
	// List features = query.list();
	// if (features.size() < 1) {
	// return null;
	// }
	// RichFeatureRelationship featureRelationship = (RichFeatureRelationship) features.get(0);
	// if (featureRelationship != null) {
	// return featureRelationship.getObject();
	// }
	// return null;
	// }
	//
	// public static void LoadScaffolds(String fileName, Progress progress, int stepToSave)
	// throws FileNotFoundException, BioException {
	// // an input FASTA file
	// BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
	//
	// if (progress != null) {
	// progress.begin();
	// }
	//
	// int i = 0;
	//
	// BioSql.beginTransaction();
	// RichSequenceIterator seqs;
	// // we are reading DNA sequences
	// if (fileName.endsWith("gbk")) {
	// seqs = RichSequence.IOTools.readGenbankDNA(fileReader, BioSql.getDefaultNamespace());
	// }
	// else {
	// seqs = RichSequence.IOTools.readFastaDNA(fileReader, BioSql.getDefaultNamespace());
	// }
	// while (seqs.hasNext()) {
	// RichSequence seq = seqs.nextRichSequence();
	// Set<RichFeature> features = seq.getFeatureSet();
	// ArrayList<RichFeature> genes = new ArrayList<RichFeature>();
	// ArrayList<RichFeature> mRNAs = new ArrayList<RichFeature>();
	// ArrayList<RichFeature> cDSs = new ArrayList<RichFeature>();
	// for (RichFeature feature : features) {
	// if (feature.getType().equalsIgnoreCase("gene")) {
	// genes.add(feature);
	// }
	// else if (feature.getType().equalsIgnoreCase("mRNA")) {
	// mRNAs.add(feature);
	// }
	// else if (feature.getType().equalsIgnoreCase("CDS")) {
	// cDSs.add(feature);
	// }
	// }
	// for (RichFeature gene : genes) {
	// for (RichFeature mRNA : mRNAs) {
	// for (RichFeature cDS : cDSs) {
	// if (mRNA.getLocation().contains(cDS.getLocation())) {
	// mRNA.addFeatureRelationship(new SimpleRichFeatureRelationship(mRNA, cDS,
	// SimpleRichFeatureRelationship.getContainsTerm(), 0));
	// }
	// }
	// if (gene.getLocation().contains(mRNA.getLocation())) {
	// gene.addFeatureRelationship(new SimpleRichFeatureRelationship(gene, mRNA,
	// SimpleRichFeatureRelationship.getContainsTerm(), 0));
	// }
	// }
	// }
	// session.saveOrUpdate("Sequence", seq);
	// if (progress != null) {
	// progress.completeStep();
	// }
	// i++;
	// if (i % stepToSave == 0) {
	// BioSql.restartTransaction();
	// }
	// }
	// BioSql.endTransactionOK();
	// if (progress != null) {
	// progress.end();
	// }
	// }
	//
	// public static void save(Gene gene) {
	// session.saveOrUpdate("Sequence", gene.getFeature().getSequence());
	// session.saveOrUpdate("Feature", gene.getFeature());
	// }
	//

}

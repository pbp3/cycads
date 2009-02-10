/*
 * Created on 28/11/2008
 */
package org.cycads.entities.sequence.BJ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.biojava.bio.BioException;
import org.biojavax.RankedCrossRef;
import org.biojavax.SimpleRankedCrossRef;
import org.biojavax.SimpleRichAnnotation;
import org.biojavax.bio.seq.CompoundRichLocation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichLocation;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.SimplePosition;
import org.biojavax.bio.seq.SimpleRichFeatureRelationship;
import org.biojavax.bio.seq.SimpleRichLocation;
import org.biojavax.bio.seq.RichLocation.Strand;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotFeature;
import org.cycads.entities.annotation.AnnotationFilter;
import org.cycads.entities.annotation.FeatureFilterByType;
import org.cycads.entities.annotation.BJ.AnnotationMethodBJ;
import org.cycads.entities.annotation.BJ.AnnotationRichFeatureBJ;
import org.cycads.entities.annotation.BJ.CDSBJ;
import org.cycads.entities.annotation.BJ.DBRecordBJ;
import org.cycads.entities.annotation.BJ.ExternalDatabaseBJ;
import org.cycads.entities.annotation.BJ.GeneBJ;
import org.cycads.entities.annotation.BJ.OntologyBJ;
import org.cycads.entities.annotation.BJ.RNABJ;
import org.cycads.entities.annotation.BJ.SimpleFeatureBJ;
import org.cycads.entities.annotation.BJ.SubseqOntologyAnnotBJ;
import org.cycads.entities.sequence.Intron;
import org.cycads.entities.sequence.SimpleIntron;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.general.biojava.TermsAndOntologies;

// receive just a RichLocation
// can add only a feature
public class SubsequenceBJ
		implements
		Subsequence<SubseqOntologyAnnotBJ, OntologyBJ, AnnotationMethodBJ, DBRecordBJ, ThinSequenceBJ, SimpleFeatureBJ, CDSBJ, RNABJ, GeneBJ> {
	Collection<Intron>	introns		= null;
	int					start		= -1;
	int					end			= -1;
	ThinSequenceBJ		sequence	= null;
	RichFeature			richFeature;

	public static RichLocation createRichLocation(int start, int end, Collection<Intron> introns) {
		int rank = 1;
		int min = start < end ? start : end;
		int max = start > end ? start : end;
		Strand strand = start <= end ? Strand.POSITIVE_STRAND : Strand.NEGATIVE_STRAND;
		if (introns == null || introns.size() == 0) {
			return new SimpleRichLocation(new SimplePosition(min), new SimplePosition(max), rank, strand);
		}
		ArrayList<Intron> intronsList = new ArrayList<Intron>(introns);
		Collections.sort(intronsList);
		int min1, max1, i = 0;
		ArrayList<SimpleRichLocation> members = new ArrayList<SimpleRichLocation>(introns.size() + 1);
		while (min <= max && i < intronsList.size()) {
			Intron intron = intronsList.get(i);
			i++;
			if (min < intron.getMin()) {
				members.add(new SimpleRichLocation(new SimplePosition(min), new SimplePosition(Math.min(
					intron.getMin() - 1, max)), rank++, strand));
			}
			min = intron.getMax() + 1;
		}
		if (min <= max) {
			members.add(new SimpleRichLocation(new SimplePosition(min), new SimplePosition(max), rank++, strand));
		}
		if (members.size() == 0) {
			return null;
		}
		else if (members.size() == 1) {
			return members.get(0);
		}
		else {
			return new CompoundRichLocation(members);
		}
	}

	public static RichFeature fillRichFeature(RichLocation richLocation, ComparableTerm method, ThinSequenceBJ sequence) {
		// create a feature template
		RichFeature.Template templ = new RichFeature.Template();
		// assign the feature template a subsequence, type, and source
		templ.typeTerm = TermsAndOntologies.getTermSubSequenceType();
		templ.sourceTerm = method;
		// assign the rest of the necessary stuff
		templ.annotation = new SimpleRichAnnotation();
		templ.featureRelationshipSet = new TreeSet();
		templ.rankedCrossRefs = new TreeSet();
		templ.location = richLocation;
		try {
			return (RichFeature) sequence.getRichSeq().createFeature(templ);
		}
		catch (BioException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public SubsequenceBJ(int start, int end, Collection<Intron> introns, AnnotationMethodBJ method,
			ThinSequenceBJ sequence) {
		this.start = start;
		this.end = end;
		this.introns = introns;
		this.sequence = sequence;
		richFeature = fillRichFeature(createRichLocation(start, end, introns), method.getTerm(), sequence);
	}

	public SubsequenceBJ(RichLocation richLocation) {
		if (!isSubsequence(richLocation)) {
			throw new IllegalArgumentException();
		}
		this.richFeature = richLocation.getFeature();
	}

	public SubsequenceBJ(RichFeature feature) {
		if (!isSubsequence(feature)) {
			throw new IllegalArgumentException();
		}
		this.richFeature = feature;
	}

	public static boolean isSubsequence(RichFeature feature) {
		return (feature != null && feature.getLocation() != null && feature.getTypeTerm().equals(
			TermsAndOntologies.getTermSubSequenceType()));
	}

	public static boolean isSubsequence(RichLocation location) {
		return (location != null && isSubsequence(location.getFeature()));
	}

	// protected LocationBJ(RichLocation richLocation, ThinSequenceBJ sequence) {
	// this.richLocation = richLocation;
	// if (richLocation == null) {
	// throw new IllegalArgumentException();
	// }
	// RichSequence seq = null;
	// if (sequence != null) {
	// seq = sequence.getRichSeq();
	// }
	// RichSequence seqLocation = null;
	// if (richLocation.getFeature() != null) {
	// seqLocation = (RichSequence) richLocation.getFeature().getSequence();
	// }
	// // needs at least one sequence. Exception if both don't point to the same sequence.
	// if (seq == null) {
	// if (seqLocation == null) {
	// throw new IllegalArgumentException();
	// }
	// else {
	// this.sequence = new ThinSequenceBJ(seqLocation);
	// }
	// }
	// else {
	// if (seqLocation != null && !seq.equals(seqLocation)) {
	// throw new IllegalArgumentException();
	// }
	// else {
	// this.sequence = sequence;
	// }
	//
	// }
	// }
	//
	public RichLocation getRichLocation() {
		return (RichLocation) richFeature.getLocation();
	}

	@Override
	public ThinSequenceBJ getSequence() {
		if (sequence == null) {
			sequence = new ThinSequenceBJ((RichSequence) getRichFeature().getSequence());
		}
		return sequence;
	}

	public RichFeature getRichFeature() {
		return getRichLocation().getFeature();
	}

	// invalid method. The subsequence is immutable
	// @Override
	// public boolean addIntron(Intron intron) {
	// throw new InvalidMethod();
	// }
	//
	// @Override
	// public Intron addIntron(int startPos, int endPos) {
	// Intron intron = new SimpleIntron(startPos, endPos);
	// addIntron(intron);
	// return intron;
	// }
	//
	@Override
	public Collection<Intron> getIntrons() {
		if (introns == null) {
			introns = new ArrayList<Intron>();
			if (!getRichLocation().isContiguous()) {
				Iterator it = getRichLocation().blockIterator();
				RichLocation loc1 = (RichLocation) it.next();
				while (it.hasNext()) {
					RichLocation loc2 = (RichLocation) it.next();
					if (loc1.getMax() < loc2.getMin()) {
						introns.add(new SimpleIntron(loc1.getMax() + 1, loc2.getMin() - 1));
					}
					else if (loc2.getMax() < loc1.getMin()) {
						introns.add(new SimpleIntron(loc2.getMax() + 1, loc1.getMin() - 1));
					}
					loc1 = loc2;
				}
			}
		}
		return introns;
	}

	@Override
	public int getStart() {
		if (start == -1) {
			start = getRichLocation().getMin();
		}
		return start;
	}

	@Override
	public int getEnd() {
		if (end == -1) {
			end = getRichLocation().getMax();
		}
		return end;
	}

	@Override
	public int getMinPosition() {
		return getStart() > getEnd() ? getEnd() : getStart();
	}

	@Override
	public int getMaxPosition() {
		return getStart() < getEnd() ? getEnd() : getStart();
	}

	@Override
	public boolean isPositiveStrand() {
		return getStart() <= getEnd();
	}

	protected RichFeature createRichFeatureForAnnotation(AnnotationMethodBJ method, ComparableTerm type) {
		// create a feature template
		RichFeature.Template templ = new RichFeature.Template();
		// assign the feature template a subsequence, type, and source
		templ.typeTerm = type;
		templ.sourceTerm = method.getTerm();
		// assign the rest of the necessary stuff
		templ.annotation = new SimpleRichAnnotation();
		templ.featureRelationshipSet = new TreeSet();
		templ.rankedCrossRefs = new TreeSet();
		// create new RichLocation
		templ.location = getRichLocation().translate(0);
		// richfeature = (RichFeature) getRichFeature().createFeature(templ);
		try {
			RichFeature richFeature = (RichFeature) getSequence().getRichSeq().createFeature(templ);
			getRichFeature().addFeatureRelationship(
				new SimpleRichFeatureRelationship(getRichFeature(), richFeature,
					TermsAndOntologies.getTermAnnotationRelationship(), 0));
			richFeature.addFeatureRelationship(new SimpleRichFeatureRelationship(richFeature, getRichFeature(),
				TermsAndOntologies.getTermSourceRelationship(), 0));
			return richFeature;
		}
		catch (BioException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public AnnotationMethodBJ getMethodInstance(String method) {
		return AnnotationMethodBJ.getInstance(method);
	}

	// create feature in the sequence
	@Override
	public SimpleFeatureBJ getOrCreateFeature(AnnotationMethodBJ method, String type) {
		SimpleFeatureBJ feature = getFeature(method, type);
		if (feature != null) {
			return feature;
		}
		feature = new SimpleFeatureBJ(createRichFeatureForAnnotation(method,
			TermsAndOntologies.getTermFeatureType(type)));
		getRichFeature().addFeatureRelationship(
			new SimpleRichFeatureRelationship(getRichFeature(), feature.getRichFeature(),
				TermsAndOntologies.getTermFeatureAnnotRelationship(), 0));
		return feature;
	}

	public SimpleFeatureBJ getOrCreateFeature(String method, String type) {
		return getOrCreateFeature(getMethodInstance(method), type);
	}

	@Override
	public CDSBJ getOrCreateCDS(AnnotationMethodBJ method) {
		return new CDSBJ(getOrCreateFeature(method, AnnotFeature.CDS_TYPE).getRichFeature());
	}

	public CDSBJ getOrCreateCDS(String method) {
		return getOrCreateCDS(getMethodInstance(method));
	}

	@Override
	public GeneBJ getOrCreateGene(AnnotationMethodBJ method) {
		return new GeneBJ(getOrCreateFeature(method, AnnotFeature.GENE_TYPE).getRichFeature());
	}

	public GeneBJ getOrCreateGene(String method) {
		return getOrCreateGene(getMethodInstance(method));
	}

	@Override
	public RNABJ getOrCreateRNA(AnnotationMethodBJ method, String type) {
		return new RNABJ(getOrCreateFeature(method, type).getRichFeature());
	}

	public RNABJ getOrCreateRNA(String method, String type) {
		return getOrCreateRNA(getMethodInstance(method), type);
	}

	@Override
	public RNABJ getOrCreateMRNA(AnnotationMethodBJ method) {
		return new RNABJ(getOrCreateFeature(method, AnnotFeature.MRNA_TYPE).getRichFeature());
	}

	public RNABJ getOrCreateMRNA(String method) {
		return getOrCreateMRNA(getMethodInstance(method));
	}

	protected Collection<RichFeature> getFeaturesAnnotated() {
		return AnnotationRichFeatureBJ.getFeaturesRelationed(getRichFeature(),
			TermsAndOntologies.getTermFeatureAnnotRelationship());
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(String type) {
		return getFeatures(new FeatureFilterByType<SimpleFeatureBJ>(type));
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(AnnotationFilter<SimpleFeatureBJ> featureFilter) {
		// get all features contained in this subsequence
		Collection<RichFeature> featuresAnnotated = getFeaturesAnnotated();
		Collection<SimpleFeatureBJ> features = new ArrayList<SimpleFeatureBJ>();
		for (RichFeature richFeature : featuresAnnotated) {
			SimpleFeatureBJ simpleFeature = new SimpleFeatureBJ(richFeature);
			if (featureFilter.accept(simpleFeature)) {
				features.add(simpleFeature);
			}
		}
		return features;
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(AnnotationMethodBJ method) {
		Collection<RichFeature> featuresAnnotated = getFeaturesAnnotated();
		Collection<SimpleFeatureBJ> features = new ArrayList<SimpleFeatureBJ>();
		for (RichFeature richFeature : featuresAnnotated) {
			SimpleFeatureBJ simpleFeature = new SimpleFeatureBJ(richFeature);
			if (simpleFeature.getAnnotationMethod().equals(method)) {
				features.add(simpleFeature);
			}
		}
		return features;
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures() {
		Collection<RichFeature> featuresAnnotated = getFeaturesAnnotated();
		Collection<SimpleFeatureBJ> features = new ArrayList<SimpleFeatureBJ>();
		for (RichFeature richFeature : featuresAnnotated) {
			features.add(new SimpleFeatureBJ(richFeature));
		}
		return features;
	}

	@Override
	public Collection<CDSBJ> getCDSs() {
		Collection<RichFeature> featuresAnnotated = getFeaturesAnnotated();
		Collection<CDSBJ> features = new ArrayList<CDSBJ>();
		for (RichFeature richFeature : featuresAnnotated) {
			if (CDSBJ.isCDS(richFeature)) {
				features.add(new CDSBJ(richFeature));
			}
		}
		return features;
	}

	@Override
	public Collection<GeneBJ> getGenes() {
		Collection<RichFeature> featuresAnnotated = getFeaturesAnnotated();
		Collection<GeneBJ> features = new ArrayList<GeneBJ>();
		for (RichFeature richFeature : featuresAnnotated) {
			if (GeneBJ.isGene(richFeature)) {
				features.add(new GeneBJ(richFeature));
			}
		}
		return features;
	}

	@Override
	public Collection<RNABJ> getMRNAs() {
		Collection<RichFeature> featuresAnnotated = getFeaturesAnnotated();
		Collection<RNABJ> features = new ArrayList<RNABJ>();
		for (RichFeature richFeature : featuresAnnotated) {
			if (RNABJ.isMRNA(richFeature)) {
				features.add(new RNABJ(richFeature));
			}
		}
		return features;
	}

	@Override
	public Collection<RNABJ> getRNAs() {
		Collection<RichFeature> featuresAnnotated = getFeaturesAnnotated();
		Collection<RNABJ> features = new ArrayList<RNABJ>();
		for (RichFeature richFeature : featuresAnnotated) {
			if (RNABJ.isRNA(richFeature)) {
				features.add(new RNABJ(richFeature));
			}
		}
		return features;
	}

	public Collection<RNABJ> getRNAs(String type) {
		Collection<RichFeature> featuresAnnotated = getFeaturesAnnotated();
		Collection<RNABJ> features = new ArrayList<RNABJ>();
		for (RichFeature richFeature : featuresAnnotated) {
			if (RNABJ.isRNA(richFeature, type)) {
				features.add(new RNABJ(richFeature));
			}
		}
		return features;
	}

	@Override
	public SimpleFeatureBJ getFeature(AnnotationMethodBJ method, String type) {
		Collection<RichFeature> featuresAnnotated = getFeaturesAnnotated();
		for (RichFeature richFeature : featuresAnnotated) {
			SimpleFeatureBJ simpleFeature = new SimpleFeatureBJ(richFeature);
			if (simpleFeature.getAnnotationMethod().equals(method) && simpleFeature.getType().equals(type)) {
				return simpleFeature;
			}
		}
		return null;
	}

	public SimpleFeatureBJ getFeature(String method, String type) {
		return getFeature(getMethodInstance(method), type);
	}

	@Override
	public CDSBJ getCDS(AnnotationMethodBJ method) {
		Collection<CDSBJ> features = getCDSs();
		for (CDSBJ feature : features) {
			if (feature.getAnnotationMethod().equals(method)) {
				return feature;
			}
		}
		return null;
	}

	@Override
	public GeneBJ getGene(AnnotationMethodBJ method) {
		Collection<GeneBJ> features = getGenes();
		for (GeneBJ feature : features) {
			if (feature.getAnnotationMethod().equals(method)) {
				return feature;
			}
		}
		return null;
	}

	@Override
	public RNABJ getMRNA(AnnotationMethodBJ method) {
		Collection<RNABJ> features = getMRNAs();
		for (RNABJ feature : features) {
			if (feature.getAnnotationMethod().equals(method)) {
				return feature;
			}
		}
		return null;
	}

	@Override
	public RNABJ getRNA(AnnotationMethodBJ method, String type) {
		Collection<RNABJ> features = getRNAs(type);
		for (RNABJ feature : features) {
			if (feature.getAnnotationMethod().equals(method)) {
				return feature;
			}
		}
		return null;
	}

	@Override
	public SubseqOntologyAnnotBJ getOrCreateOntologyAnnot(AnnotationMethodBJ method, OntologyBJ target) {
		SubseqOntologyAnnotBJ ontologyAnnot;
		if ((ontologyAnnot = getOntologyAnnot(method, target)) != null) {
			return ontologyAnnot;
		}
		RichFeature richFeature = createRichFeatureForAnnotation(method, TermsAndOntologies.getTermOntologyAnnotType());
		richFeature.addRankedCrossRef(new SimpleRankedCrossRef(target.getCrossRef(), 0));
		getRichFeature().addFeatureRelationship(
			new SimpleRichFeatureRelationship(getRichFeature(), richFeature,
				TermsAndOntologies.getTermOntologyAnnotRelationship(), 0));
		return new SubseqOntologyAnnotBJ(richFeature);
	}

	@Override
	public SubseqOntologyAnnotBJ getOrCreateOntologyAnnot(AnnotationMethodBJ method, String accession, String dbName) {
		return getOrCreateOntologyAnnot(method, ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateOntology(
			accession));
	}

	protected Collection<RichFeature> getOntologiesAnnotated() {
		return AnnotationRichFeatureBJ.getFeaturesRelationed(getRichFeature(),
			TermsAndOntologies.getTermOntologyAnnotRelationship());
	}

	@Override
	public SubseqOntologyAnnotBJ getOntologyAnnot(AnnotationMethodBJ method, OntologyBJ target) {
		Collection<RichFeature> features = getOntologiesAnnotated();
		for (RichFeature richFeature : features) {
			SubseqOntologyAnnotBJ ontAnnot = new SubseqOntologyAnnotBJ(richFeature);
			if (ontAnnot.getAnnotationMethod().equals(method) && ontAnnot.getOntologyTarget().equals(target)) {
				return ontAnnot;
			}
		}
		return null;
	}

	@Override
	public SubseqOntologyAnnotBJ getOntologyAnnot(AnnotationMethodBJ method, String dbName, String accession) {
		return getOntologyAnnot(method, ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateOntology(accession));
	}

	@Override
	public Collection<SubseqOntologyAnnotBJ> getOntologyAnnots(AnnotationFilter<SubseqOntologyAnnotBJ> filter) {
		Collection<RichFeature> features = getOntologiesAnnotated();
		Collection<SubseqOntologyAnnotBJ> annots = new ArrayList<SubseqOntologyAnnotBJ>();
		for (RichFeature richFeature : features) {
			SubseqOntologyAnnotBJ ontAnnot = new SubseqOntologyAnnotBJ(richFeature);
			if (filter.accept(ontAnnot)) {
				annots.add(ontAnnot);
			}
		}
		return annots;
	}

	@Override
	public Collection<SubseqOntologyAnnotBJ> getOntologyAnnots(OntologyBJ target) {
		Collection<RichFeature> features = getOntologiesAnnotated();
		Collection<SubseqOntologyAnnotBJ> annots = new ArrayList<SubseqOntologyAnnotBJ>();
		for (RichFeature richFeature : features) {
			SubseqOntologyAnnotBJ ontAnnot = new SubseqOntologyAnnotBJ(richFeature);
			if (ontAnnot.getOntologyTarget().equals(target)) {
				annots.add(ontAnnot);
			}
		}
		return annots;
	}

	@Override
	public Collection<SubseqOntologyAnnotBJ> getOntologyAnnots(String dbName, String accession) {
		return getOntologyAnnots(OntologyBJ.getOrCreateOntologyBJ(dbName, accession));
	}

	@Override
	public Collection<SubseqOntologyAnnotBJ> getOntologyAnnots(String dbName) {
		Collection<RichFeature> features = getOntologiesAnnotated();
		Collection<SubseqOntologyAnnotBJ> annots = new ArrayList<SubseqOntologyAnnotBJ>();
		for (RichFeature richFeature : features) {
			SubseqOntologyAnnotBJ ontAnnot = new SubseqOntologyAnnotBJ(richFeature);
			if (ontAnnot.getOntologyTarget().getDatabaseName().equals(dbName)) {
				annots.add(ontAnnot);
			}
		}
		return annots;
	}

	@Override
	public void addDBRecord(DBRecordBJ dbRecord) {
		getRichFeature().addRankedCrossRef(new SimpleRankedCrossRef(dbRecord.getCrossRef(), 0));
	}

	@Override
	public void addDBRecord(String database, String accession) {
		addDBRecord(DBRecordBJ.getOrCreateDBRecordBJ(database, accession));
	}

	@Override
	public Collection<DBRecordBJ> getDBRecords() {
		Collection<DBRecordBJ> dbRecords = new ArrayList<DBRecordBJ>();
		Set<RankedCrossRef> crossRefs = getRichFeature().getRankedCrossRefs();
		for (RankedCrossRef crossRef : crossRefs) {
			dbRecords.add(DBRecordBJ.getOrCreateDBRecordBJ(crossRef.getCrossRef().getDbname(),
				crossRef.getCrossRef().getAccession()));
		}
		return dbRecords;
	}

	@Override
	public Collection<DBRecordBJ> getDBrecords(String dbName) {
		Collection<DBRecordBJ> dbRecords = new ArrayList<DBRecordBJ>();
		Set<RankedCrossRef> crossRefs = getRichFeature().getRankedCrossRefs();
		for (RankedCrossRef crossRef : crossRefs) {
			if (crossRef.getCrossRef().getDbname().equals(dbName)) {
				dbRecords.add(DBRecordBJ.getOrCreateDBRecordBJ(crossRef.getCrossRef().getDbname(),
					crossRef.getCrossRef().getAccession()));
			}
		}
		return dbRecords;
	}

	@Override
	public boolean contains(Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? > subseq) {
		if (subseq.getMinPosition() < this.getMinPosition() || subseq.getMaxPosition() > this.getMaxPosition()) {
			return false;
		}
		// introns must be in natural order
		Iterator<Intron> itSubseq = subseq.getIntrons().iterator();
		Intron intronOtherSubseq = null;
		if (itSubseq.hasNext()) {
			intronOtherSubseq = itSubseq.next();
		}
		for (Intron intron : this.getIntrons()) {
			if (intronOtherSubseq == null) {
				return intron.getMin() > subseq.getMaxPosition();
			}
			// get intronOtherSubseq that overlap intron
			while (intronOtherSubseq.getMax() < intron.getMin()) {
				if (!itSubseq.hasNext()) {
					return intron.getMin() > subseq.getMaxPosition();
				}
				intronOtherSubseq = itSubseq.next();
			}
			if (intronOtherSubseq.getMin() > intron.getMin()) {
				return intron.getMin() > subseq.getMaxPosition();
			}
			else {
				// intronOtherSubseq.min<=intron.min
				while (intronOtherSubseq.getMax() < intron.getMax()) {
					// get nextIntron adjacent
					if (itSubseq.hasNext()) {
						Intron nextIntronOtherSubseq = itSubseq.next();
						if (intronOtherSubseq.getMax() + 1 == nextIntronOtherSubseq.getMin()) {
							intronOtherSubseq = nextIntronOtherSubseq;
						}
						else {
							return intron.getMin() > subseq.getMaxPosition();
						}
					}
					else {
						return intron.getMin() > subseq.getMaxPosition();
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SubsequenceBJ)) {
			return false;
		}
		return this.getRichFeature().equals(((SubsequenceBJ) obj).getRichFeature());
	}

}

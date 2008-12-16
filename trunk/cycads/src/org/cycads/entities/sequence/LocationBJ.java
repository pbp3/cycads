/*
 * Created on 28/11/2008
 */
package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.biojava.bio.BioException;
import org.biojavax.SimpleRankedCrossRef;
import org.biojavax.SimpleRichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichLocation;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.BJ.DBRecordBJ;
import org.cycads.entities.annotation.dBLink.BJ.ExternalDatabaseBJ;
import org.cycads.entities.annotation.dBLink.BJ.LocationDBLinkBJ;
import org.cycads.entities.annotation.feature.CDSBJ;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.annotation.feature.GeneBJ;
import org.cycads.entities.annotation.feature.RNABJ;
import org.cycads.entities.annotation.feature.SimpleFeatureBJ;
import org.cycads.exceptions.MethodNotImplemented;
import org.cycads.general.biojava.BioSql;
import org.cycads.general.biojava.TermsAndOntologies;

// receive just a RichLocation
// can add only a feature
public class LocationBJ
		implements
		Location<LocationDBLinkBJ, LocationBJ, DBRecordBJ, AnnotationMethodBJ, ThinSequenceBJ, SimpleFeatureBJ, CDSBJ, RNABJ, GeneBJ>
{
	Collection<Intron>	introns		= null;
	int					start		= -1;
	int					end			= -1;
	ThinSequenceBJ		sequence	= null;
	RichLocation		richLocation;

	public LocationBJ(RichLocation richLocation) {
		this.richLocation = richLocation;
		if (richLocation != null && richLocation.getFeature() != null
			&& richLocation.getFeature().getSequence() != null) {
			this.sequence = new ThinSequenceBJ((RichSequence) richLocation.getFeature().getSequence());
		}
		else {
			throw new IllegalArgumentException();
		}

	}

	protected LocationBJ(RichLocation richLocation, ThinSequenceBJ sequence) {
		this.richLocation = richLocation;
		if (richLocation == null) {
			throw new IllegalArgumentException();
		}
		RichSequence seq = null;
		if (sequence != null) {
			seq = sequence.getRichSeq();
		}
		RichSequence seqLocation = null;
		if (richLocation.getFeature() != null) {
			seqLocation = (RichSequence) richLocation.getFeature().getSequence();
		}
		// needs at least one sequence. Exception if both don't point to the same sequence.
		if (seq == null) {
			if (seqLocation == null) {
				throw new IllegalArgumentException();
			}
			else {
				this.sequence = new ThinSequenceBJ(seqLocation);
			}
		}
		else {
			if (seqLocation != null && !seq.equals(seqLocation)) {
				throw new IllegalArgumentException();
			}
			else {
				this.sequence = sequence;
			}

		}
	}

	public RichLocation getRichLocation() {
		return richLocation;
	}

	@Override
	public ThinSequenceBJ getSequence() {
		return sequence;
	}

	public RichFeature getRichFeature() {
		return getRichLocation().getFeature();
	}

	@Override
	public boolean addIntron(Intron intron) {
		throw new MethodNotImplemented();
		// return introns.add(intron);
	}

	@Override
	public Intron addIntron(int startPos, int endPos) {
		throw new MethodNotImplemented();
		// Intron intron = new SimpleIntron(startPos, endPos);
		// addIntron(intron);
		// return intron;
	}

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
						addIntron(new SimpleIntron(loc1.getMax() + 1, loc2.getMin() - 1));
					}
					else if (loc2.getMax() < loc1.getMin()) {
						addIntron(new SimpleIntron(loc2.getMax() + 1, loc1.getMin() - 1));
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
		if (getStart() < getEnd()) {
			return getStart();
		}
		else {
			return getEnd();
		}
	}

	public int getMaxPosition() {
		if (getStart() < getEnd()) {
			return getEnd();
		}
		else {
			return getStart();
		}
	}

	@Override
	public boolean isPositiveStrand() {
		return getStart() <= getEnd();
	}

	protected RichFeature createRichFeature(AnnotationMethodBJ method, ComparableTerm type) {
		// create a feature template
		RichFeature.Template templ = new RichFeature.Template();
		// assign the feature template a location, type, and source
		templ.typeTerm = type;
		templ.sourceTerm = method.getTerm();
		// assign the rest of the necessary stuff
		templ.annotation = new SimpleRichAnnotation();
		templ.featureRelationshipSet = new TreeSet();
		templ.rankedCrossRefs = new TreeSet();
		if (getRichFeature() != null) {
			// create new RichLocation
			templ.location = getRichLocation().translate(0);
			// richfeature = (RichFeature) getRichFeature().createFeature(templ);
		}
		else {
			templ.location = getRichLocation();
		}
		try {
			return (RichFeature) getSequence().getRichSeq().createFeature(templ);
		}
		catch (BioException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// create feaure in the sequence
	@Override
	public SimpleFeatureBJ createFeature(AnnotationMethodBJ method, String type) {
		return new SimpleFeatureBJ(createRichFeature(method, TermsAndOntologies.getTermFeatureType(type)));
	}

	@Override
	public CDSBJ createCDS(AnnotationMethodBJ method) {
		return new CDSBJ(createFeature(method, Feature.CDS_TYPE).getRichFeature());
	}

	@Override
	public GeneBJ createGene(AnnotationMethodBJ method) {
		return new GeneBJ(createFeature(method, Feature.GENE_TYPE).getRichFeature());
	}

	@Override
	public RNABJ createRNA(AnnotationMethodBJ method, String type) {
		return new RNABJ(createFeature(method, type).getRichFeature());
	}

	@Override
	public void addFeature(SimpleFeatureBJ feature) {
		// Do nothing. the collection of features of the location are the RichFeatures in the same location
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(LocationBJ source, AnnotationMethodBJ method, String type) {
		if (source != this && !source.getRichLocation().equals(this.getRichLocation())) {
			return null;
		}
		return getFeatures(method, type);
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(AnnotationMethodBJ method, String type) {
		Collection<RichFeature> richFeatures = BioSql.getFeatures(getRichLocation(), type, method);
		Collection<SimpleFeatureBJ> features = new ArrayList<SimpleFeatureBJ>();
		for (RichFeature richFeature : richFeatures) {
			features.add(new SimpleFeatureBJ(richFeature));
		}
		return features;
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(String type) {
		Collection<RichFeature> richFeatures = BioSql.getFeatures(getRichLocation(), type);
		Collection<SimpleFeatureBJ> features = new ArrayList<SimpleFeatureBJ>();
		for (RichFeature richFeature : richFeatures) {
			features.add(new SimpleFeatureBJ(richFeature));
		}
		return features;
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(FeatureFilter<SimpleFeatureBJ> featureFilter) {
		Collection<RichFeature> richFeatures = BioSql.getFeatures(getRichLocation());
		Collection<SimpleFeatureBJ> features = new ArrayList<SimpleFeatureBJ>();
		SimpleFeatureBJ feature;
		for (RichFeature richFeature : richFeatures) {
			feature = new SimpleFeatureBJ(richFeature);
			if (featureFilter.accept(feature)) {
				features.add(feature);
			}
		}
		return features;
	}

	@Override
	public LocationDBLinkBJ createDBLink(AnnotationMethodBJ method, DBRecordBJ target) {
		// create a feature template
		RichFeature.Template templ = new RichFeature.Template();
		// assign the feature template a location, type, and source
		templ.typeTerm = TermsAndOntologies.getTermDBLinkType();
		templ.sourceTerm = method.getTerm();
		// assign the rest of the necessary stuff
		templ.annotation = new SimpleRichAnnotation();
		templ.featureRelationshipSet = new TreeSet();
		templ.rankedCrossRefs = new TreeSet();
		templ.rankedCrossRefs.add(new SimpleRankedCrossRef(target.getCrossRef(), 0));
		try {
			if (getRichFeature() != null) {
				// create new RichLocation
				RichLocation loc = (RichLocation) getRichLocation().translate(0);
				templ.location = loc;
				getSequence().getRichSeq().createFeature(templ);
				return new LocationDBLinkBJ(new LocationBJ(loc));
			}
			else {
				templ.location = getRichLocation();
				getSequence().getRichSeq().createFeature(templ);
				return new LocationDBLinkBJ(this);
			}
		}
		catch (BioException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public LocationDBLinkBJ createDBLink(AnnotationMethodBJ method, String accession, String dbName) {
		return createDBLink(method, ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateDBRecord(accession));
	}

	@Override
	public void addDBLink(LocationDBLinkBJ link) {
		// Do nothing. the collection of DBLinks are DBLinks at the same richLocation
	}

	@Override
	public LocationDBLinkBJ getDBLink(LocationBJ source, AnnotationMethodBJ method, DBRecordBJ target) {
		if (source != this && !source.getRichLocation().equals(this.getRichLocation())) {
			return null;
		}
		Collection<LocationDBLinkBJ> dbLinks = getDBLinks(method, target);
		for (LocationDBLinkBJ dbLink : dbLinks) {
			return dbLink;
		}
		return null;
	}

	@Override
	public Collection<LocationDBLinkBJ> getDBLinks(AnnotationMethodBJ method, DBRecordBJ target) {
		Collection<RichFeature> richFeatures = BioSql.getFeatures(getRichLocation(),
			TermsAndOntologies.getTermDBLinkType(), method);
		Collection<LocationDBLinkBJ> dbLinks = new ArrayList<LocationDBLinkBJ>();
		for (RichFeature richFeature : richFeatures) {
			dbLinks.add(new LocationDBLinkBJ((RichLocation) richFeature.getLocation()));
		}
		return dbLinks;
	}

	@Override
	public Collection<LocationDBLinkBJ> getDBLinks(AnnotationMethodBJ method, String dbName, String accession) {
		return getDBLinks(method, ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateDBRecord(accession));
	}

	@Override
	public Collection<LocationDBLinkBJ> getDBLinks(DBLinkFilter<LocationDBLinkBJ> filter) {
		Collection<RichFeature> richFeatures = BioSql.getFeatures(getRichLocation(),
			TermsAndOntologies.getTermDBLinkType());
		Collection<LocationDBLinkBJ> dbLinks = new ArrayList<LocationDBLinkBJ>();
		LocationDBLinkBJ dbLink;
		for (RichFeature richFeature : richFeatures) {
			dbLink = new LocationDBLinkBJ((RichLocation) richFeature.getLocation());
			if (filter.accept(dbLink)) {
				dbLinks.add(dbLink);
			}
		}
		return dbLinks;
	}

}

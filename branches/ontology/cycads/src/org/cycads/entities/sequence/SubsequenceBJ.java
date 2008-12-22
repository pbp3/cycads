/*
 * Created on 28/11/2008
 */
package org.cycads.entities.sequence;

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
import org.biojavax.bio.seq.RichFeatureRelationship;
import org.biojavax.bio.seq.RichLocation;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.bio.seq.SimplePosition;
import org.biojavax.bio.seq.SimpleRichFeatureRelationship;
import org.biojavax.bio.seq.SimpleRichLocation;
import org.biojavax.bio.seq.RichLocation.Strand;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLinkAnnotFilter;
import org.cycads.entities.annotation.dBLink.BJ.DBRecordBJ;
import org.cycads.entities.annotation.dBLink.BJ.ExternalDatabaseBJ;
import org.cycads.entities.annotation.dBLink.BJ.LocationDBLinkBJ;
import org.cycads.entities.annotation.feature.CDSBJ;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.annotation.feature.FeatureFilterByType;
import org.cycads.entities.annotation.feature.GeneBJ;
import org.cycads.entities.annotation.feature.RNABJ;
import org.cycads.entities.annotation.feature.SimpleFeatureBJ;
import org.cycads.exceptions.InvalidMethod;
import org.cycads.general.biojava.TermsAndOntologies;

// receive just a RichLocation
// can add only a feature
public class SubsequenceBJ
		implements
		Subsequence<LocationDBLinkBJ, SubsequenceBJ, DBRecordBJ, AnnotationMethodBJ, ThinSequenceBJ, SimpleFeatureBJ, CDSBJ, RNABJ, GeneBJ>
{
	Collection<Intron>	introns		= null;
	int					start		= -1;
	int					end			= -1;
	ThinSequenceBJ		sequence	= null;
	RichFeature			richFeature;

	public static RichLocation createRichLocation(int start, int end, Collection<Intron> introns)
	{
		int rank = 1;
		int min = start < end ? start : end;
		int max = start > end ? start : end;
		Strand strand = start <= end ? Strand.POSITIVE_STRAND : Strand.NEGATIVE_STRAND;
		if (introns == null || introns.size() == 0)
		{
			return new SimpleRichLocation(new SimplePosition(min), new SimplePosition(max), rank, strand);
		}
		ArrayList<Intron> intronsList = new ArrayList<Intron>(introns);
		Collections.sort(intronsList);
		int min1, max1, i = 0;
		ArrayList<SimpleRichLocation> members = new ArrayList<SimpleRichLocation>(introns.size() + 1);
		while (min <= max && i < intronsList.size())
		{
			Intron intron = intronsList.get(i);
			i++;
			if (min < intron.getMin())
			{
				members.add(new SimpleRichLocation(new SimplePosition(min), new SimplePosition(Math.min(
					intron.getMin() - 1, max)), rank++, strand));
			}
			min = intron.getMax() + 1;
		}
		if (min <= max)
		{
			members.add(new SimpleRichLocation(new SimplePosition(min), new SimplePosition(max), rank++, strand));
		}
		if (members.size() == 0)
		{
			return null;
		}
		else if (members.size() == 1)
		{
			return members.get(0);
		}
		else
		{
			return new CompoundRichLocation(members);
		}
	}

	public static RichFeature fillRichFeature(RichLocation richLocation, ComparableTerm source, ThinSequenceBJ sequence)
	{
		// create a feature template
		RichFeature.Template templ = new RichFeature.Template();
		// assign the feature template a location, type, and source
		templ.typeTerm = TermsAndOntologies.getTermSubSequenceType();
		templ.sourceTerm = source;
		// assign the rest of the necessary stuff
		templ.annotation = new SimpleRichAnnotation();
		templ.featureRelationshipSet = new TreeSet();
		templ.rankedCrossRefs = new TreeSet();
		templ.location = richLocation;
		try
		{
			return (RichFeature) sequence.getRichSeq().createFeature(templ);
		}
		catch (BioException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public SubsequenceBJ(int start, int end, Collection<Intron> introns, AnnotationMethodBJ method, ThinSequenceBJ sequence)
	{
		this.start = start;
		this.end = end;
		this.introns = introns;
		this.sequence = sequence;
		richFeature = fillRichFeature(createRichLocation(start, end, introns), method.getTerm(), sequence);
	}

	public SubsequenceBJ(RichLocation richLocation)
	{
		if (!isLocationRichLocation(richLocation))
		{
			throw new IllegalArgumentException();
		}
		this.richFeature = richLocation.getFeature();
	}

	public SubsequenceBJ(RichFeature feature)
	{
		if (!isLocationRichFeature(feature))
		{
			throw new IllegalArgumentException();
		}
		this.richFeature = feature;
	}

	public static boolean isLocationRichFeature(RichFeature feature)
	{
		return (feature != null && feature.getLocation() != null && feature.getTypeTerm().equals(
			TermsAndOntologies.getTermSubSequenceType()));
	}

	public static boolean isLocationRichLocation(RichLocation location)
	{
		return (location != null && isLocationRichFeature(location.getFeature()));
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
	public RichLocation getRichLocation()
	{
		return (RichLocation) richFeature.getLocation();
	}

	@Override
	public ThinSequenceBJ getSequence()
	{
		if (sequence == null)
		{
			sequence = new ThinSequenceBJ((RichSequence) getRichFeature().getSequence());
		}
		return sequence;
	}

	public RichFeature getRichFeature()
	{
		return getRichLocation().getFeature();
	}

	// invalid method. The location is immutable
	@Override
	public boolean addIntron(Intron intron)
	{
		throw new InvalidMethod();
	}

	@Override
	public Intron addIntron(int startPos, int endPos)
	{
		Intron intron = new SimpleIntron(startPos, endPos);
		addIntron(intron);
		return intron;
	}

	@Override
	public Collection<Intron> getIntrons()
	{
		if (introns == null)
		{
			introns = new ArrayList<Intron>();
			if (!getRichLocation().isContiguous())
			{
				Iterator it = getRichLocation().blockIterator();
				RichLocation loc1 = (RichLocation) it.next();
				while (it.hasNext())
				{
					RichLocation loc2 = (RichLocation) it.next();
					if (loc1.getMax() < loc2.getMin())
					{
						addIntron(new SimpleIntron(loc1.getMax() + 1, loc2.getMin() - 1));
					}
					else if (loc2.getMax() < loc1.getMin())
					{
						addIntron(new SimpleIntron(loc2.getMax() + 1, loc1.getMin() - 1));
					}
					loc1 = loc2;
				}
			}
		}
		return introns;
	}

	@Override
	public int getStart()
	{
		if (start == -1)
		{
			start = getRichLocation().getMin();
		}
		return start;
	}

	@Override
	public int getEnd()
	{
		if (end == -1)
		{
			end = getRichLocation().getMax();
		}
		return end;
	}

	@Override
	public int getMinPosition()
	{
		return getStart() > getEnd() ? getEnd() : getStart();
	}

	@Override
	public int getMaxPosition()
	{
		return getStart() < getEnd() ? getEnd() : getStart();
	}

	@Override
	public boolean isPositiveStrand()
	{
		return getStart() <= getEnd();
	}

	protected RichFeature createRichFeatureForAnnotation(AnnotationMethodBJ method, ComparableTerm type)
	{
		// create a feature template
		RichFeature.Template templ = new RichFeature.Template();
		// assign the feature template a location, type, and source
		templ.typeTerm = type;
		templ.sourceTerm = method.getTerm();
		// assign the rest of the necessary stuff
		templ.annotation = new SimpleRichAnnotation();
		templ.featureRelationshipSet = new TreeSet();
		templ.rankedCrossRefs = new TreeSet();
		// create new RichLocation
		templ.location = getRichLocation().translate(0);
		// richfeature = (RichFeature) getRichFeature().createFeature(templ);
		try
		{
			RichFeature richFeature = (RichFeature) getSequence().getRichSeq().createFeature(templ);
			getRichFeature().addFeatureRelationship(
				new SimpleRichFeatureRelationship(getRichFeature(), richFeature,
					SimpleRichFeatureRelationship.getContainsTerm(), 0));
			return richFeature;
		}
		catch (BioException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// create feaure in the sequence
	@Override
	public SimpleFeatureBJ createFeature(AnnotationMethodBJ method, String type)
	{
		return new SimpleFeatureBJ(createRichFeatureForAnnotation(method, TermsAndOntologies.getTermFeatureType(type)));
	}

	@Override
	public CDSBJ createCDS(AnnotationMethodBJ method)
	{
		return new CDSBJ(createFeature(method, Feature.CDS_TYPE).getRichFeature());
	}

	@Override
	public GeneBJ createGene(AnnotationMethodBJ method)
	{
		return new GeneBJ(createFeature(method, Feature.GENE_TYPE).getRichFeature());
	}

	@Override
	public RNABJ createRNA(AnnotationMethodBJ method, String type)
	{
		return new RNABJ(createFeature(method, type).getRichFeature());
	}

	@Override
	public RNABJ createMRNA(AnnotationMethodBJ method)
	{
		return new RNABJ(createFeature(method, Feature.MRNA_TYPE).getRichFeature());
	}

	@Override
	public void addFeature(SimpleFeatureBJ feature)
	{
		// Do nothing. the feature must has already a location
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(SubsequenceBJ source, AnnotationMethodBJ method, String type)
	{
		if (source != this && !source.getRichLocation().equals(this.getRichLocation()))
		{
			return null;
		}
		return getFeatures(method, type);
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(AnnotationMethodBJ method, String type)
	{
		Collection<SimpleFeatureBJ> features = getFeatures(type);
		for (SimpleFeatureBJ feature : features)
		{
			if (feature.getAnnotationMethod() != method)
			{
				features.remove(feature);
			}
		}
		return features;
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(String type)
	{
		return getFeatures(new FeatureFilterByType<SimpleFeatureBJ>(type));
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(FeatureFilter<SimpleFeatureBJ> featureFilter)
	{
		// get all features contained in this location
		Set<RichFeatureRelationship> relations = getRichFeature().getFeatureRelationshipSet();
		Collection<SimpleFeatureBJ> features = new ArrayList<SimpleFeatureBJ>();
		for (RichFeatureRelationship relation : relations)
		{
			RichFeature richFeature = relation.getSubject();
			try
			{
				SimpleFeatureBJ simpleFeature = new SimpleFeatureBJ(richFeature);
				if (featureFilter.accept(simpleFeature))
				{
					features.add(simpleFeature);
				}
			}
			catch (IllegalArgumentException e)
			{

			}
		}
		return features;
	}

	@Override
	public LocationDBLinkBJ createDBLink(AnnotationMethodBJ method, DBRecordBJ target)
	{
		LocationDBLinkBJ dbLink;
		if ((dbLink = getDBLinkAnnot(this, method, target)) != null)
		{
			return dbLink;
		}
		RichFeature richFeature = createRichFeatureForAnnotation(method, TermsAndOntologies.getTermDBLinkType());
		richFeature.addRankedCrossRef(new SimpleRankedCrossRef(target.getCrossRef(), 0));
		return new LocationDBLinkBJ(richFeature);
	}

	@Override
	public LocationDBLinkBJ createDBLink(AnnotationMethodBJ method, String accession, String dbName)
	{
		return createDBLink(method, ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateDBRecord(accession));
	}

	@Override
	public void addDBLinkAnnot(LocationDBLinkBJ link)
	{
		// Do nothing. the collection of DBLinks are DBLinks contained at the same richLocation
	}

	@Override
	public LocationDBLinkBJ getDBLinkAnnot(SubsequenceBJ source, AnnotationMethodBJ method, DBRecordBJ target)
	{
		if (source != this && !source.getRichLocation().equals(this.getRichLocation()))
		{
			return null;
		}
		Collection<LocationDBLinkBJ> dbLinks = getDBLinkAnnots(method, target);
		for (LocationDBLinkBJ dbLink : dbLinks)
		{
			return dbLink;
		}
		return null;
	}

	@Override
	public Collection<LocationDBLinkBJ> getDBLinkAnnots(AnnotationMethodBJ method, DBRecordBJ target)
	{
		// get all features contained in this location
		Set<RichFeatureRelationship> relations = getRichFeature().getFeatureRelationshipSet();
		// get the feature of type dbLink with the method and target
		Collection<LocationDBLinkBJ> dbLinks = new ArrayList<LocationDBLinkBJ>();
		for (RichFeatureRelationship relation : relations)
		{
			RichFeature richFeature = relation.getSubject();
			if (richFeature.getTypeTerm().equals(TermsAndOntologies.getTermDBLinkType())
				&& richFeature.getSourceTerm().equals(method.getTerm()))
			{
				Set<RankedCrossRef> crossRefs = richFeature.getRankedCrossRefs();
				for (RankedCrossRef crossRef : crossRefs)
				{
					if (crossRef.getCrossRef().equals(target.getCrossRef()))
					{
						dbLinks.add(new LocationDBLinkBJ(richFeature));
					}
				}
			}
		}
		return dbLinks;
	}

	@Override
	public Collection<LocationDBLinkBJ> getDBLinkAnnots(AnnotationMethodBJ method, String dbName, String accession)
	{
		return getDBLinkAnnots(method, ExternalDatabaseBJ.getOrCreateExternalDB(dbName).getOrCreateDBRecord(accession));
	}

	@Override
	public Collection<LocationDBLinkBJ> getDBLinkAnnots(DBLinkAnnotFilter<LocationDBLinkBJ> filter)
	{
		// get all features contained in this location
		Set<RichFeatureRelationship> relations = getRichFeature().getFeatureRelationshipSet();
		Collection<LocationDBLinkBJ> dbLinks = new ArrayList<LocationDBLinkBJ>();
		for (RichFeatureRelationship relation : relations)
		{
			RichFeature richFeature = relation.getSubject();
			if (richFeature.getTypeTerm().equals(TermsAndOntologies.getTermDBLinkType()))
			{
				LocationDBLinkBJ dbLink = new LocationDBLinkBJ(richFeature);
				if (filter.accept(dbLink))
				{
					dbLinks.add(dbLink);
				}
			}
		}
		return dbLinks;
	}

	@Override
	public CDSBJ createCDS(String method)
	{
		return createCDS(AnnotationMethodBJ.getInstance(method));
	}

	@Override
	public SimpleFeatureBJ createFeature(String method, String type)
	{
		return createFeature(AnnotationMethodBJ.getInstance(method), type);
	}

	@Override
	public GeneBJ createGene(String method)
	{
		return createGene(AnnotationMethodBJ.getInstance(method));
	}

	@Override
	public RNABJ createRNA(String method, String type)
	{
		return createRNA(AnnotationMethodBJ.getInstance(method), type);
	}

	@Override
	public RNABJ createMRNA(String method)
	{
		return createMRNA(AnnotationMethodBJ.getInstance(method));
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(String method, String type)
	{
		return getFeatures(AnnotationMethodBJ.getInstance(method), type);
	}

	@Override
	public Collection<SimpleFeatureBJ> getFeatures(SubsequenceBJ source, String method, String type)
	{
		return getFeatures(source, AnnotationMethodBJ.getInstance(method), type);
	}

	@Override
	public LocationDBLinkBJ createDBLink(String method, DBRecordBJ target)
	{
		return createDBLink(AnnotationMethodBJ.getInstance(method), target);
	}

	@Override
	public LocationDBLinkBJ createDBLink(String method, String accession, String dbName)
	{
		return createDBLink(AnnotationMethodBJ.getInstance(method), accession, dbName);
	}

	@Override
	public LocationDBLinkBJ getDBLinkAnnot(SubsequenceBJ source, String method, DBRecordBJ target)
	{
		return getDBLinkAnnot(source, AnnotationMethodBJ.getInstance(method), target);
	}

	@Override
	public Collection<LocationDBLinkBJ> getDBLinkAnnots(String method, DBRecordBJ target)
	{
		return getDBLinkAnnots(AnnotationMethodBJ.getInstance(method), target);
	}

	@Override
	public Collection<LocationDBLinkBJ> getDBLinkAnnots(String method, String dbName, String accession)
	{
		return getDBLinkAnnots(AnnotationMethodBJ.getInstance(method), dbName, accession);
	}

	@Override
	public CDSBJ getCDS(AnnotationMethodBJ method)
	{
		Collection<SimpleFeatureBJ> features = getFeatures(method, Feature.CDS_TYPE);
		if (features == null || features.size() == 0)
		{
			return null;
		}
		return new CDSBJ(features.iterator().next().getRichFeature());
	}

	@Override
	public CDSBJ getCDS(String method)
	{
		return getCDS(AnnotationMethodBJ.getInstance(method));
	}

	@Override
	public GeneBJ getGene(AnnotationMethodBJ method)
	{
		Collection<SimpleFeatureBJ> features = getFeatures(method, Feature.GENE_TYPE);
		if (features == null || features.size() == 0)
		{
			return null;
		}
		return new GeneBJ(features.iterator().next().getRichFeature());
	}

	@Override
	public GeneBJ getGene(String method)
	{
		return getGene(AnnotationMethodBJ.getInstance(method));
	}

	@Override
	public RNABJ getMRNA(AnnotationMethodBJ method)
	{
		Collection<SimpleFeatureBJ> features = getFeatures(method, Feature.MRNA_TYPE);
		if (features == null || features.size() == 0)
		{
			return null;
		}
		return new RNABJ(features.iterator().next().getRichFeature());
	}

	@Override
	public RNABJ getMRNA(String method)
	{
		return getMRNA(AnnotationMethodBJ.getInstance(method));
	}

}

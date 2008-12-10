/*
 * Created on 28/11/2008
 */
package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import org.biojava.bio.BioException;
import org.biojavax.SimpleRichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichLocation;
import org.biojavax.bio.seq.RichSequence;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.BJ.DBRecordBJ;
import org.cycads.entities.annotation.dBLink.BJ.ThinDBLinkBJ;
import org.cycads.entities.annotation.feature.CDSBJ;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureBJ;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.annotation.feature.GeneBJ;
import org.cycads.entities.annotation.feature.RNABJ;
import org.cycads.exceptions.MethodNotImplemented;
import org.cycads.general.biojava.TermsAndOntologies;

//receive just a RichLocation
// can add only a feature
public class LocationBJ
		implements
		Location<ThinDBLinkBJ<LocationBJ>, LocationBJ, DBRecordBJ, AnnotationMethodBJ, ThinSequenceBJ, FeatureBJ, CDSBJ, RNABJ, GeneBJ>
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
		//needs at least one sequence. Exception if both don't point to the same sequence.
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

	@Override
	public FeatureBJ createFeature(AnnotationMethodBJ method, String type) {
		// create a feature template
		RichFeature.Template templ = new RichFeature.Template();
		// assign the feature template a location, type, and source
		templ.typeTerm = TermsAndOntologies.getTermFeatureType(type);
		templ.sourceTerm = method.getTerm();
		// assign the rest of the necessary stuff
		templ.annotation = new SimpleRichAnnotation();
		templ.featureRelationshipSet = new TreeSet();
		templ.rankedCrossRefs = new TreeSet();
		RichFeature richfeature;
		try {
			if (getRichFeature() != null) {
				//create new RichLocation
				templ.location = getRichLocation().translate(0);
				richfeature = (RichFeature) getRichFeature().createFeature(templ);
			}
			else {
				templ.location = getRichLocation();
				richfeature = (RichFeature) getSequence().getRichSeq().createFeature(templ);
			}
		}
		catch (BioException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return new FeatureBJ(richfeature);
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
	public void addFeature(FeatureBJ feature) {
		// TODO Auto-generated method stub

	}

	@Override
	public FeatureBJ getFeature(AnnotationMethodBJ method, String type, LocationBJ source) {
		if (source != this) {
			return null;
		}
		Collection<FeatureBJ> features = getFeatures(method, type);
		if (features.isEmpty()) {
			return null;
		}
		else {
			return features.iterator().next();
		}
	}

	@Override
	public Collection<FeatureBJ> getFeatures(AnnotationMethodBJ method, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FeatureBJ> getFeatures(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FeatureBJ> getFeatures(FeatureFilter<FeatureBJ> featureFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThinDBLinkBJ<LocationBJ> createDBLink(AnnotationMethodBJ method, DBRecordBJ target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThinDBLinkBJ<LocationBJ> createDBLink(AnnotationMethodBJ method, String accession, String dbName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDBLink(ThinDBLinkBJ<LocationBJ> link) {
		// TODO Auto-generated method stub

	}

	@Override
	public ThinDBLinkBJ<LocationBJ> getDBLink(LocationBJ source, AnnotationMethodBJ method, DBRecordBJ target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ThinDBLinkBJ<LocationBJ>> getDBLinks(AnnotationMethodBJ method, DBRecordBJ target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ThinDBLinkBJ<LocationBJ>> getDBLinks(AnnotationMethodBJ method, String dbName, String accession) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ThinDBLinkBJ<LocationBJ>> getDBLinks(DBLinkFilter<ThinDBLinkBJ<LocationBJ>> filter) {
		// TODO Auto-generated method stub
		return null;
	}

}

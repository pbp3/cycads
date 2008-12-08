/*
 * Created on 28/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.biojavax.bio.seq.RichLocation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.BJ.DBRecordBJ;
import org.cycads.entities.annotation.dBLink.BJ.ThinDBLinkBJ;
import org.cycads.entities.annotation.feature.CDS;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureBJ;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.annotation.feature.FeatureSource;
import org.cycads.entities.annotation.feature.Gene;
import org.cycads.entities.annotation.feature.RNA;

public class LocationBJ<SEQ extends Sequence< ? , ? , ? , ? , ? , ? >>
		implements Location<ThinDBLinkBJ<LocationBJ<SEQ>>, LocationBJ<SEQ>, DBRecordBJ, AnnotationMethodBJ, SEQ>
{
	Collection<Intron>	introns;
	int					start;
	int					end;
	SEQ					sequence;
	RichLocation		location;

	public LocationBJ(int start, int end, SEQ sequence, Collection<Intron> introns) {
		this.introns = introns;
		this.start = start;
		this.end = end;
		this.sequence = sequence;
	}

	@Override
	public boolean addIntron(Intron intron) {
		return introns.add(intron);
	}

	@Override
	public Intron addIntron(int startPos, int endPos) {
		Intron intron = new SimpleIntron(startPos, endPos);
		addIntron(intron);
		return intron;
	}

	@Override
	public Collection<Intron> getIntrons() {
		return introns;
	}

	@Override
	public SEQ getSequence() {
		return sequence;
	}

	@Override
	public int getStart() {
		return start;
	}

	@Override
	public int getEnd() {
		return end;
	}

	@Override
	public boolean isPositiveStrand() {
		return getStart() <= getEnd();
	}

	@Override
	public CDS createCDS(AnnotationMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FeatureBJ createFeature(AnnotationMethod method, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gene createGene(AnnotationMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RNA createRNA(AnnotationMethod method, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFeature(Feature feature) {
		// TODO Auto-generated method stub

	}

	@Override
	public Feature getFeature(AnnotationMethod method, String type, FeatureSource source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Feature> getFeatures(FeatureFilter featureFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Feature> getFeatures(AnnotationMethod method, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThinDBLinkBJ<LocationBJ<SEQ>> createDBLink(AnnotationMethodBJ method, DBRecordBJ target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThinDBLinkBJ<LocationBJ<SEQ>> createDBLink(AnnotationMethodBJ method, String accession, String dbName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDBLink(ThinDBLinkBJ<LocationBJ<SEQ>> link) {
		// TODO Auto-generated method stub

	}

	@Override
	public ThinDBLinkBJ<LocationBJ<SEQ>> getDBLink(LocationBJ<SEQ> source, AnnotationMethodBJ method, DBRecordBJ target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ThinDBLinkBJ<LocationBJ<SEQ>>> getDBLinks(AnnotationMethodBJ method, DBRecordBJ target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ThinDBLinkBJ<LocationBJ<SEQ>>> getDBLinks(AnnotationMethodBJ method, String dbName,
			String accession) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ThinDBLinkBJ<LocationBJ<SEQ>>> getDBLinks(DBLinkFilter<ThinDBLinkBJ<LocationBJ<SEQ>>> filter) {
		// TODO Auto-generated method stub
		return null;
	}

}

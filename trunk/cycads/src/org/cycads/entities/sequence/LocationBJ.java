/*
 * Created on 28/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.biojavax.bio.seq.RichLocation;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.BJ.DBRecordBJ;
import org.cycads.entities.annotation.dBLink.BJ.ThinDBLinkBJ;
import org.cycads.entities.annotation.feature.CDSBJ;
import org.cycads.entities.annotation.feature.FeatureBJ;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.annotation.feature.GeneBJ;
import org.cycads.entities.annotation.feature.RNABJ;

public class LocationBJ
		implements
		Location<ThinDBLinkBJ<LocationBJ>, LocationBJ, DBRecordBJ, AnnotationMethodBJ, ThinSequenceBJ, FeatureBJ, CDSBJ, RNABJ, GeneBJ>
{
	Collection<Intron>	introns;
	int					start;
	int					end;
	ThinSequenceBJ		sequence;
	RichLocation		location;

	public LocationBJ(int start, int end, ThinSequenceBJ sequence, Collection<Intron> introns) {
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
	public ThinSequenceBJ getSequence() {
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
	public CDSBJ createCDS(AnnotationMethodBJ method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FeatureBJ createFeature(AnnotationMethodBJ method, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneBJ createGene(AnnotationMethodBJ method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RNABJ createRNA(AnnotationMethodBJ method, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFeature(FeatureBJ feature) {
		// TODO Auto-generated method stub

	}

	@Override
	public FeatureBJ getFeature(AnnotationMethodBJ method, String type, LocationBJ source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FeatureBJ> getFeatures(FeatureFilter<FeatureBJ> featureFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FeatureBJ> getFeatures(AnnotationMethodBJ method, String type) {
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

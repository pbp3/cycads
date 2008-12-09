/*
 * Created on 28/11/2008
 */
package org.cycads.entities.sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichLocation;
import org.biojavax.bio.seq.RichSequence;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.BJ.DBRecordBJ;
import org.cycads.entities.annotation.dBLink.BJ.ThinDBLinkBJ;
import org.cycads.entities.annotation.feature.CDSBJ;
import org.cycads.entities.annotation.feature.FeatureBJ;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.annotation.feature.GeneBJ;
import org.cycads.entities.annotation.feature.RNABJ;
import org.cycads.exceptions.MethodNotImplemented;

public class LocationBJ
		implements
		Location<ThinDBLinkBJ<LocationBJ>, LocationBJ, DBRecordBJ, AnnotationMethodBJ, ThinSequenceBJ, FeatureBJ, CDSBJ, RNABJ, GeneBJ>
{
	Collection<Intron>	introns		= null;
	int					start		= -1;
	int					end			= -1;
	ThinSequenceBJ		sequence	= null;
	RichLocation		richLocation;

	public LocationBJ(RichLocation richLocation)
	{
		this.richLocation = richLocation;
	}

	public RichLocation getRichLocation()
	{
		return richLocation;
	}

	public RichFeature getFeature()
	{
		return getRichLocation().getFeature();
	}

	@Override
	public boolean addIntron(Intron intron)
	{
		throw new MethodNotImplemented();
		// return introns.add(intron);
	}

	@Override
	public Intron addIntron(int startPos, int endPos)
	{
		throw new MethodNotImplemented();
		// Intron intron = new SimpleIntron(startPos, endPos);
		// addIntron(intron);
		// return intron;
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
	public ThinSequenceBJ getSequence()
	{
		if (sequence == null)
		{
			sequence = new ThinSequenceBJ((RichSequence) getFeature().getSequence());
		}
		return sequence;
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
	public boolean isPositiveStrand()
	{
		return getStart() <= getEnd();
	}

	@Override
	public FeatureBJ createFeature(AnnotationMethodBJ method, String type)
	{
		RichLocation loc = getRichLocation();
		if (getFeature() != null)
		{
			loc = (RichLocation) loc.translate(0);
		}
	}

	@Override
	public CDSBJ createCDS(AnnotationMethodBJ method)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneBJ createGene(AnnotationMethodBJ method)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RNABJ createRNA(AnnotationMethodBJ method, String type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFeature(FeatureBJ feature)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public FeatureBJ getFeature(AnnotationMethodBJ method, String type, LocationBJ source)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FeatureBJ> getFeatures(FeatureFilter<FeatureBJ> featureFilter)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FeatureBJ> getFeatures(AnnotationMethodBJ method, String type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThinDBLinkBJ<LocationBJ> createDBLink(AnnotationMethodBJ method, DBRecordBJ target)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThinDBLinkBJ<LocationBJ> createDBLink(AnnotationMethodBJ method, String accession, String dbName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDBLink(ThinDBLinkBJ<LocationBJ> link)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ThinDBLinkBJ<LocationBJ> getDBLink(LocationBJ source, AnnotationMethodBJ method, DBRecordBJ target)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ThinDBLinkBJ<LocationBJ>> getDBLinks(AnnotationMethodBJ method, DBRecordBJ target)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ThinDBLinkBJ<LocationBJ>> getDBLinks(AnnotationMethodBJ method, String dbName, String accession)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ThinDBLinkBJ<LocationBJ>> getDBLinks(DBLinkFilter<ThinDBLinkBJ<LocationBJ>> filter)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

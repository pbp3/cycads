/*
 * Created on 28/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.dBLink.DBLinkFilter;
import org.cycads.entities.annotation.dBLink.DBLinkSource;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.feature.CDS;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureFilter;
import org.cycads.entities.annotation.feature.FeatureSource;
import org.cycads.entities.annotation.feature.Gene;
import org.cycads.entities.annotation.feature.RNA;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.NotesContainer;

public class SimpleLocation implements Location<>
{
	Collection<Intron>	introns;
	int					start;
	int					end;
	Sequence			sequence;

	public SimpleLocation(int start, int end, Sequence sequence, Collection<Intron> introns)
	{
		this.introns = introns;
		this.start = start;
		this.end = end;
		this.sequence = sequence;
	}

	@Override
	public boolean addIntron(Intron intron)
	{
		return introns.add(intron);
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
		return introns;
	}

	@Override
	public Sequence getSequence()
	{
		return sequence;
	}

	@Override
	public int getStart()
	{
		return start;
	}

	@Override
	public int getEnd()
	{
		return end;
	}

	@Override
	public boolean isPositiveStrand()
	{
		return getStart() <= getEnd();
	}

	@Override
	public CDS createCDS(AnnotationMethod method)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Feature createFeature(AnnotationMethod method, String type, Note<Feature> notes)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gene createGene(AnnotationMethod method)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RNA createRNA(AnnotationMethod method, String type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFeature(Feature feature)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Feature getFeature(AnnotationMethod method, String type, FeatureSource source)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Feature> getFeatures(FeatureFilter featureFilter)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Feature> getFeatures(AnnotationMethod method, String type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBLink createDBLink(AnnotationMethod method, DBRecord record, NotesContainer<Note<DBLink>> notes)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBLink createDBLink(AnnotationMethod method, String accession, String dbName,
			NotesContainer<Note<DBLink>> notes)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDBLink(DBLink link)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public DBLink getDBLink(AnnotationMethod method, DBRecord record, DBLinkSource source)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DBLink> getDBLinks(AnnotationMethod method, DBRecord record)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DBLink> getDBLinks(AnnotationMethod method, String accession, String dbName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<DBLink> getDBLinks(DBLinkFilter filter)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

/*
 * Created on 10/06/2008
 */
package org.cycads.entities;

import java.util.List;
import java.util.Set;

import org.biojavax.Note;
import org.biojavax.SimpleNote;
import org.biojavax.SimpleRichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.SimpleRichFeature;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.InvalidFeature;
import org.cycads.general.biojava.TermsAndOntologies;
import org.hibernate.Query;

public class CDSBJ implements CDS
{
	RichFeature	feature;

	public CDSBJ(RichFeature feature)
	{
		this.feature = feature;
		if (feature.getTypeTerm() != TermsAndOntologies.getTermTypeCDS())
		{
			throw new InvalidFeature(feature);
		}
	}

	public String getSequenceStr()
	{
		return feature.getSymbols().seqString();
	}

	public RichFeature getFeature()
	{
		return feature;
	}

	public String getName()
	{
		return feature.getName();
	}

	public void addAnnotation(Method method, String value)
	{
		MethodBJ meth = (MethodBJ) method;
		SimpleRichAnnotation annot = (SimpleRichAnnotation) feature.getAnnotation();
		Set<Note> notes = annot.getNoteSet();
		int rank = 0;
		for (Note note : notes)
		{
			if (note.getTerm().equals(meth.getTerm()))
			{
				if (note.getValue().equalsIgnoreCase(value))
				{
					return;
				}
				rank++;
			}
		}
		annot.addNote(new SimpleNote(meth.getTerm(), value, rank));
	}

	public static CDS getCDSByProtID(String protName, Organism organism)
	{
		Query query = BioJavaxSession.session.createQuery("select f from Feature as f join f.parent as b join f.noteSet as prop "
			+ "where f.typeTerm=:cdsTerm and b.taxon=:taxonId and "
			+ "prop.term=:termProteinID and prop.value=:protName");
		query.setString("protName", protName);
		query.setParameter("taxonId", ((NCBIOrganismBJ) organism).getTaxon());
		query.setParameter("cdsTerm", TermsAndOntologies.getTermTypeCDS());
		query.setParameter("termProteinID", TermsAndOntologies.getTermProteinID());
		List features = query.list();
		if (features.size() != 1)
		{
			return null;
		}
		SimpleRichFeature feature = (SimpleRichFeature) features.get(0);
		feature.toString(); // bug Biojava
		return new CDSBJ(feature);
	}

}

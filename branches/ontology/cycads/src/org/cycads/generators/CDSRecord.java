/*
 * Created on 16/09/2008
 */
package org.cycads.generators;

import java.util.Collection;

import org.biojavax.Note;
import org.biojavax.RichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.ontology.ComparableTerm;

import baobab.sequence.general.BioSql;
import baobab.sequence.general.Messages;
import baobab.sequence.general.TermsAndOntologies;

public class CDSRecord extends SimpleGeneRecord
{

	//	String					id, name, type;
	//	String					comment, productID;
	//	Collection<DBLink>		dbLinks		= new ArrayList<DBLink>();
	//	Collection<String>		ecs			= new ArrayList<String>(1);
	//	int						endBase, startBase;
	//	Collection<Function>	functions	= new ArrayList<Function>(1);
	//	Collection<String>		synonyms	= new ArrayList<String>();

	public CDSRecord(RichFeature cds) throws Exception {
		super(cds);
		addDBLink(Messages.getString("DBLink.DB.CDS.ProductId"), getProductID());
		RichFeature featureParent = BioSql.getParent(feature);
		RichAnnotation annotationParent = (RichAnnotation) featureParent.getAnnotation();
		Note[] notes;
		notes = annotationParent.getProperties("transcript_id");
		if (notes.length > 0) {
			addDBLink(Messages.getString("DBLink.DB.CDS.ParentProductId"), notes[0].getValue());
		}
	}

	@Override
	public String getType() {
		return "P";
	}

	@Override
	public String getProductIdTerm() {
		return "protein_id";
	}

	public Collection<ComparableTerm> getMethodsToLinkToKO() {
		return TermsAndOntologies.getOntologyToLinkCDSToKO().getTerms();
	}

}

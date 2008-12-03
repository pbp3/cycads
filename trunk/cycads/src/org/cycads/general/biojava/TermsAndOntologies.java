/*
 * Created on 11/06/2008
 */
package org.cycads.general.biojava;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.biojavax.RichObjectFactory;
import org.biojavax.ontology.ComparableOntology;
import org.biojavax.ontology.ComparableTerm;
import org.biojavax.ontology.SimpleComparableOntology;

public class TermsAndOntologies
{

	private static final String			BUNDLE_NAME		= "termsAndOntologies";					//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private TermsAndOntologies()
	{
	}

	private static String getString(String key)
	{
		try
		{
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static String getNameSpaceDefault()
	{
		return getString("nameSpaceDefault");
	}

	public static ComparableOntology getDefaultOntology()
	{
		return RichObjectFactory.getDefaultOntology();
	}

	private static SimpleComparableOntology getOntology(String name)
	{
		return (SimpleComparableOntology) RichObjectFactory.getObject(SimpleComparableOntology.class,
			new Object[] {name});
	}

	public static ComparableOntology getOntologyExternalDB(String ontName)
	{
		ComparableOntology ont = getOntology(ontName);
		ont.setDescription(getString("ontology.externalDB.description"));
		return ont;
	}

	public static ComparableOntology getOntologyEC()
	{
		return getOntologyExternalDB(getString("ontology.EC"));
	}

	public static ComparableOntology getOntologyGO()
	{
		return getOntologyExternalDB(getString("ontology.GO"));
	}

	public static ComparableOntology getOntologyKO()
	{
		return getOntologyExternalDB(getString("ontology.KO"));
	}

	public static ComparableOntology getOntologyCOG()
	{
		return getOntologyExternalDB(getString("ontology.COG"));
	}

	public static SimpleComparableOntology getOntologyToLinksKOToEC()
	{
		return getOntology(getString("ontology.Links.KOToEC"));
	}

	public static SimpleComparableOntology getOntologyToLinksKOToGO()
	{
		return getOntology(getString("ontology.Links.KOToGO"));
	}

	public static SimpleComparableOntology getOntologyToLinksKOToCOG()
	{
		return getOntology(getString("ontology.Links.KOToCOG"));
	}

	public static ComparableOntology getOntologyGeneral()
	{
		return getOntology(getString("ontology.general"));
	}

	public static ComparableOntology getOntologyNotes()
	{
		return getOntology(getString("ontology.notes"));
	}

	public static ComparableOntology getOntologyDBLinkMethods()
	{
		return getOntology(getString("ontology.DBLinkMethods"));
	}

	public static ComparableTerm getTermDBLinkMethodCrossReference()
	{
		return getOntology(getString("ontology.DBLinkMethods")).getOrCreateTerm(
			getString("term.DBLinkMethod.CrossReference"));
	}

	public static ComparableTerm getTermPredicateToLinkKOToEC()
	{
		return getOntology(getString("ontology.predicate.KOToEC")).getOrCreateTerm(getString("term.predicate.KOToEC"));
	}

	public static ComparableTerm getTermPredicateToLinkKOToGO()
	{
		return getOntology(getString("ontology.predicate.KOToGO")).getOrCreateTerm(getString("term.predicate.KOToGO"));
	}

	public static ComparableTerm getTermPredicateToLinkKOToCOG()
	{
		return getOntology(getString("ontology.predicate.KOToCOG")).getOrCreateTerm(getString("term.predicate.KOToCOG"));
	}

	public static ComparableTerm getTermTypeCDS()
	{
		return getDefaultOntology().getOrCreateTerm(getString("term.type.CDS"));
	}

	public static ComparableTerm getTermTypeEC()
	{
		return getDefaultOntology().getOrCreateTerm(getString("term.type.EC"));
	}

	public static ComparableTerm getTermTypeGene()
	{
		return getDefaultOntology().getOrCreateTerm(getString("term.type.Gene"));
	}

	public static ComparableTerm getTermProteinID()
	{
		return getDefaultOntology().getOrCreateTerm(getString("term.qualifier.ProteinID"));
	}

	public static ComparableOntology getOntologyMethodType(String methodTypeName)
	{
		return getOntology(methodTypeName);
	}

	public static ComparableTerm getTermNextBiocycId()
	{
		return getDefaultOntology().getOrCreateTerm(getString("term.nextBioCycID"));
	}

	// public static SimpleComparableOntology getOntologyFeatures() {
	// return (SimpleComparableOntology) RichObjectFactory.getObject(SimpleComparableOntology.class,
	// new Object[] {Messages.getString("ontologyFeatures")});
	// }
	//
	// public static SimpleComparableOntology getOntologyToLinkGeneToKO() {
	// return (SimpleComparableOntology) RichObjectFactory.getObject(SimpleComparableOntology.class,
	// new Object[] {Messages.getString("Gene.ontologyToKo")});
	// }
	//
	// public static SimpleComparableOntology getOntologyToLinkCDSToKO() {
	// return (SimpleComparableOntology) RichObjectFactory.getObject(SimpleComparableOntology.class,
	// new Object[] {Messages.getString("CDS.ontologyToKo")});
	// }
	//
	// public static SimpleComparableOntology getOntologyMRNAToCDS() {
	// return (SimpleComparableOntology) RichObjectFactory.getObject(SimpleComparableOntology.class,
	// new Object[] {Messages.getString("ontologyMRNAToCDS")});
	// }
	//
	// public static SimpleComparableOntology getOntologyToLinksKOToGO() {
	// return (SimpleComparableOntology) RichObjectFactory.getObject(SimpleComparableOntology.class,
	// new Object[] {Messages.getString("KO.ontologyToGo")});
	// }
	//
	// public static ComparableOntology getCompilationOnt(Organism org) {
	// return (SimpleComparableOntology) RichObjectFactory.getObject(SimpleComparableOntology.class,
	// new Object[] {Messages.getString("Compilation.ontologyPrefix") + org.getTaxon().getNCBITaxID()});
	// }
	//
	// public static ComparableTerm getTermGene() {
	// return RichObjectFactory.getDefaultOntology().getOrCreateTerm(Messages.getString("termGene"));
	// }
	//
	// public static ComparableTerm getTermEST() {
	// return RichObjectFactory.getDefaultOntology().getOrCreateTerm(Messages.getString("termEst"));
	// }
	//
	// public static ComparableTerm getTermMRNA() {
	// return RichObjectFactory.getDefaultOntology().getOrCreateTerm(Messages.getString("termMRNA"));
	// }
	//
	// public static ComparableTerm getTermCDS() {
	// return RichObjectFactory.getDefaultOntology().getOrCreateTerm(Messages.getString("termCDS"));
	// }
	//
	// public static ComparableTerm getTermTRNA() {
	// return RichObjectFactory.getDefaultOntology().getOrCreateTerm(Messages.getString("termTRNA"));
	// }
	//
	// public static ComparableTerm getTermMiscRNA() {
	// return RichObjectFactory.getDefaultOntology().getOrCreateTerm(Messages.getString("termMiscRNA"));
	// }
	//
	// public static ComparableTerm getTermVR() {
	// return getOntologyMRNAToCDS().getOrCreateTerm(Messages.getString("termVR"));
	// }
	//
	// public static ComparableTerm getTermMRNAID() {
	// return RichObjectFactory.getDefaultOntology().getOrCreateTerm(Messages.getString("termMRNAID"));
	// }
	//
	// public static ComparableTerm getTermCDSName() {
	// return RichObjectFactory.getDefaultOntology().getOrCreateTerm(Messages.getString("termCDSName"));
	// }
	//
	// public static ComparableTerm getNextBiocycId() {
	// return RichObjectFactory.getDefaultOntology().getOrCreateTerm(Messages.getString("termNextBiocycID"));
	// }
	//
	// // public static ComparableTerm getLastCompilationTerm(Organism org) {
	// // ComparableOntology ont = getCompilationOnt(org);
	// // Set<ComparableTerm> terms = ont.getTermSet();
	// // double version = Double.NEGATIVE_INFINITY;
	// // ComparableTerm res = null;
	// // for (ComparableTerm term : terms) {
	// // if (new Double(term.getDescription()) > version) {
	// // version = new Double(term.getDescription());
	// // res = term;
	// // }
	// // }
	// // return res;
	// // }
	// //
	// public static Set<ComparableTerm> getAllKosTerms() {
	// return ((SimpleComparableOntology) RichObjectFactory.getObject(SimpleComparableOntology.class,
	// new Object[] {Messages.getString("KO.ontology")})).getTerms();
	// }
	//
	// public static ComparableTerm getMethodCompTerm() {
	// return getOntologyGeneral().getOrCreateTerm(Messages.getString("termMethodComp"));
	// }
	//
}

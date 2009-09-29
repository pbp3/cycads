/*
 * Created on 09/06/2008
 */
package org.cycads.dbExternal;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.biojavax.ontology.ComparableTerm;
import org.biojavax.ontology.ComparableTriple;
import org.biojavax.ontology.SimpleComparableOntology;
import org.cycads.general.biojava.TermsAndOntologies;

public class KOBJ implements KO
{
	ComparableTerm	term;

	public KOBJ(String id) {
		term = TermsAndOntologies.getOntologyKO().getOrCreateTerm(id);
		setDefinition(DEFINITION_DEFAULT);
	}

	public KOBJ(ComparableTerm term) {
		this.term = term;
	}

	/* (non-Javadoc)
	 * @see org.cycads.dbExternal.KO#getId()
	 */
	public String getId() {
		return term.getName();
	}

	public ComparableTerm getTerm() {
		return term;
	}

	public String getDefinition() {
		return term.getDescription();
	}

	public void setDefinition(String definition) {
		term.setDescription(definition);
	}

	/* (non-Javadoc)
	 * @see org.cycads.dbExternal.KO#link2Ec(org.cycads.dbExternal.EC)
	 */
	public void link2EC(EC ec) {
		if (!(ec instanceof ECBJ)) {
			ec = new ECBJ(ec.getId());
		}
		TermsAndOntologies.getOntologyToLinksKOToEC().getOrCreateTriple(getTerm(), ((ECBJ) ec).getTerm(),
			TermsAndOntologies.getTermPredicateToLinkKOToEC());
	}

	/* (non-Javadoc)
	 * @see org.cycads.dbExternal.KO#getECs()
	 */
	public Collection<EC> getECs() {
		SimpleComparableOntology ont = TermsAndOntologies.getOntologyToLinksKOToEC();
		ComparableTerm term = TermsAndOntologies.getTermPredicateToLinkKOToEC();
		Set<ComparableTriple> triples = ont.getTriples(getTerm(), null, term);
		Set<EC> ecs = new TreeSet<EC>();
		for (ComparableTriple triple : triples) {
			ecs.add(new ECBJ((ComparableTerm) triple.getObject()));
		}
		return ecs;
	}

	/* (non-Javadoc)
	 * @see org.cycads.dbExternal.KO#link2Go(org.cycads.dbExternal.GO)
	 */
	public void link2GO(GO go) {
		if (!(go instanceof GOBJ)) {
			go = new GOBJ(go.getId());
		}
		TermsAndOntologies.getOntologyToLinksKOToGO().getOrCreateTriple(getTerm(), ((GOBJ) go).getTerm(),
			TermsAndOntologies.getTermPredicateToLinkKOToGO());
	}

	public Collection<GO> getGOs() {
		SimpleComparableOntology ont = TermsAndOntologies.getOntologyToLinksKOToGO();
		ComparableTerm term = TermsAndOntologies.getTermPredicateToLinkKOToGO();
		Set<ComparableTriple> triples = ont.getTriples(getTerm(), null, term);
		Set<GO> gos = new TreeSet<GO>();
		for (ComparableTriple triple : triples) {
			gos.add(new GOBJ((ComparableTerm) triple.getObject()));
		}
		return gos;
	}

	public int compareTo(KO ko) {
		if (ko instanceof KOBJ) {
			return term.compareTo(((KOBJ) ko).getTerm());
		}
		return getId().compareTo(ko.getId());
	}

	public void link2COG(COG cog) {
		if (!(cog instanceof COGBJ)) {
			cog = new COGBJ(cog.getId());
		}
		TermsAndOntologies.getOntologyToLinksKOToCOG().getOrCreateTriple(getTerm(), ((COGBJ) cog).getTerm(),
			TermsAndOntologies.getTermPredicateToLinkKOToCOG());
	}

	public Collection<COG> getCOGs() {
		SimpleComparableOntology ont = TermsAndOntologies.getOntologyToLinksKOToCOG();
		ComparableTerm term = TermsAndOntologies.getTermPredicateToLinkKOToCOG();
		Set<ComparableTriple> triples = ont.getTriples(getTerm(), null, term);
		Set<COG> cogs = new TreeSet<COG>();
		for (ComparableTriple triple : triples) {
			cogs.add(new COGBJ((ComparableTerm) triple.getObject()));
		}
		return cogs;
	}

}

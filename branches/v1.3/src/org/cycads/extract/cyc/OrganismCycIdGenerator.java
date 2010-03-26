package org.cycads.extract.cyc;

import org.cycads.entities.sequence.Organism;

public class OrganismCycIdGenerator implements CycIdGenerator
{
	Organism	organism;

	public OrganismCycIdGenerator(Organism organism) {
		super();
		this.organism = organism;
	}

	@Override
	public String getNewID() {
		return organism.getId() + "-" + organism.getNextCycId();
	}

}

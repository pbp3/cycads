/*
 * Created on 28/09/2009
 */
package org.cycads.entities.factory;

import org.cycads.entities.sequence.Organism;

public interface EntityOrganismFactory<O extends Organism< ? >>
{
	public O getOrganism(int orgId);

	public O createOrganism(int orgId, String name);

}

/*
 * Created on 07/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.sequence.feature.Gene;

public class Gene extends Feature implements Gene
{
	private Collection<RNA>	rNAs;

	public Collection<RNA> getRNAs()
	{
		return rNAs;
	}

}

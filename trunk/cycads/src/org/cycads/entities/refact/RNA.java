/*
 * Created on 07/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

public class RNA extends Feature
{
	private Collection<CDS>	cDSs;

	public Collection<CDS> getCDSs()
	{
		return cDSs;
	}
}

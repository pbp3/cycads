/*
 * Created on 07/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

import org.cycads.entities.IRNA;

public class RNA extends Feature implements IRNA
{
	private Collection<CDS>	cDSs;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.IRNA#getCDSs()
	 */
	public Collection<CDS> getCDSs()
	{
		return cDSs;
	}
}

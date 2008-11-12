/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface RNA extends Feature
{

	public Collection<CDS> getCDSs();

}
/*
 * Created on 31/10/2008
 */
package org.cycads.entities.biojava1;

import java.util.Collection;


public interface ECLinkCreator
{
	public Collection<ECLink> create(SequenceFeature feature);
}

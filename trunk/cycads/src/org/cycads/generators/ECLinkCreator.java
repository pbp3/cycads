/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import java.util.Collection;

import org.cycads.entities.SequenceFeature;

public interface ECLinkCreator
{
	public Collection<ECLink> create(SequenceFeature feature);
}

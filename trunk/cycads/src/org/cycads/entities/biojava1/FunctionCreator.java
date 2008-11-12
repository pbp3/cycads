/*
 * Created on 04/11/2008
 */
package org.cycads.entities.biojava1;

import java.util.Collection;

public interface FunctionCreator
{
	public Collection<Function> create(SequenceFeature feature);
}

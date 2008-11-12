/*
 * Created on 11/11/2008
 */
package org.cycads.entities.refact;

import java.util.Collection;

public interface ITypeTerm extends ITerm
{

	public Collection<ITerm> getTerms();

	public ITerm getOrCreateTerm(String value);

}
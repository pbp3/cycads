/*
 * Created on 25/02/2009
 */
package org.cycads.entities.synonym;

import java.util.Collection;

public interface Function
{
	public String getName();

	public String getDescription();

	public Collection<Function> getSynonyms();

	public Function getSynonym(String name);

	public Function addSynonym(String name, String description);

}

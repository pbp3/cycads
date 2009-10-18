/*
 * Created on 10/03/2009
 */
package org.cycads.entities.synonym;


public interface KO extends Dbxref
{
	public final static String	DBNAME	= "KO";

	public String getDefinition();

	public void setDefinition(String definition);

	public String getName();

	public void setName(String name);

}

/*
 * Created on 15/10/2008
 */
package org.cycads.dbExternal;

import java.util.Collection;

public interface KO extends Comparable<KO>
{
	public static final String	DEFINITION_DEFAULT	= "";

	public String getId();

	public String getDefinition();

	public void setDefinition(String definition);

	public void link2EC(EC ec);

	public Collection<EC> getECs();

	public void link2GO(GO go);

	public Collection<GO> getGOs();

	public void link2COG(COG cog);

	public Collection<COG> getCOGs();

}
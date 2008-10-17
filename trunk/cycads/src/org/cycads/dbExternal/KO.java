/*
 * Created on 15/10/2008
 */
package org.cycads.dbExternal;

import java.util.Collection;

public interface KO extends Comparable<KO>
{
	public String getId();

	public void link2Ec(EC ec);

	public Collection<EC> getECs();

	public void link2Go(GO go);

	public Collection<GO> getGOs();

}
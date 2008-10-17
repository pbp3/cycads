/*
 * Created on 15/10/2008
 */
package org.cycads.dbExternal;

public interface EC extends Comparable<EC>
{

	public String getId();

	public int compareTo(EC ec);

}
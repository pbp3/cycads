/*
 * Created on 15/10/2008
 */
package org.cycads.dbExternal;

public interface COG extends Comparable<COG>
{

	public String getId();

	public int compareTo(COG cog);

}
/*
 * Created on 15/10/2008
 */
package org.cycads.dbExternal;

public interface GO extends Comparable<GO>
{

	public String getId();

	public int compareTo(GO go);

}
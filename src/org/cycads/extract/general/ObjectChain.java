/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

public interface ObjectChain
{

	public Object getLast();

	public Object getFirst();

	public double getScore();

	public String getMethods();

	public void addObjectList(List<Object> list);

}

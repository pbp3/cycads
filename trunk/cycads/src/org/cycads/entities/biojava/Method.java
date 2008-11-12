/*
 * Created on 20/10/2008
 */
package org.cycads.entities.biojava;

public interface Method
{
	public String getName();

	public MethodType getType();

	//	public void setDescription(String methodDescription);

	public int getWeight();

	public void setWeight(int weight);
}

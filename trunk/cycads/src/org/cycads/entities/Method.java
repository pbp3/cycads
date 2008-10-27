/*
 * Created on 20/10/2008
 */
package org.cycads.entities;

public interface Method
{
	public String getName();

	public MethodType getType();

	public void setDescription(String methodDescription);
}

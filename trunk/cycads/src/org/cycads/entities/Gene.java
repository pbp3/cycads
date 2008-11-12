/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

public interface Gene extends Feature
{
	public Collection<RNA> getRNAs();
}
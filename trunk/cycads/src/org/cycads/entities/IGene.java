/*
 * Created on 12/11/2008
 */
package org.cycads.entities;

import java.util.Collection;

import org.cycads.entities.refact.RNA;

public interface IGene
{
	public Collection<RNA> getRNAs();
}
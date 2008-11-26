/*
 * Created on 25/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

public interface Gene extends Feature
{
	public Collection<RNA> getRNAProducts();

}

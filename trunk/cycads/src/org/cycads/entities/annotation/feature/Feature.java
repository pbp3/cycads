/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.feature;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Sequence;

public interface Feature extends Annotation<Location>
{
	public String getType();

	public Sequence getSequence();

	public Location getLocation();

}
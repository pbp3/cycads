/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Sequence;

public interface Feature extends Annotation<Feature>, ExternalAnnotationSource
{
	public String getType();

	public Sequence getSequence();

	public Location getLocation();

}
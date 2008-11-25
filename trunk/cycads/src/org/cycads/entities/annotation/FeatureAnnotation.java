/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Sequence;

public interface FeatureAnnotation extends Annotation, ExternalAnnotationSource
{

	public String getType();

	public Sequence getSequence();

	public Location getLocation();

}
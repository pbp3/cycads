/*
 * Created on 01/04/2009
 */
package org.cycads.extract.cyc;

import java.util.List;

import org.cycads.entities.annotation.Annotation;

public interface StringAndPath
{
	public String getValue();

	public void addAnnotation(Annotation annot);

	// annotations walked to arrive this value
	public List<Annotation> getAnnotations();
}

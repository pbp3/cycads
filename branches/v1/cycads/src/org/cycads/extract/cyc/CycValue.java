/*
 * Created on 01/04/2009
 */
package org.cycads.extract.cyc;

import java.util.List;

import org.cycads.entities.annotation.Annotation;

public interface CycValue {
	public String getValue();

	public void setValue(String value);

	// annotations walked to arrive this value
	public void addAnnotation(Annotation annot);

	public List<Annotation> getAnnotations();
}

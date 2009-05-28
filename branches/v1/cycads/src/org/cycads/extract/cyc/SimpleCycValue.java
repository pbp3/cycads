/*
 * Created on 01/04/2009
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.List;

import org.cycads.entities.annotation.Annotation;

public class SimpleCycValue implements CycValue
{
	String				value;
	List<Annotation>	annotations	= new ArrayList<Annotation>(3);

	public SimpleCycValue(String value, List<Annotation> annotations) {
		this.value = value;
		this.annotations = annotations;
	}

	public SimpleCycValue(String value) {
		this.value = value;
	}

	//	public SimpleCycValue() {
	//	}
	//
	@Override
	public void addAnnotation(Annotation annot) {
		annotations.add(annot);
	}

	@Override
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Override
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

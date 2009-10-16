/*
 * Created on 01/04/2009
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.List;

import org.cycads.entities.annotation.Annotation;

public class SimpleCycValueRet implements CycValueRet
{
	String				value;
	List<Annotation>	annotations	= new ArrayList<Annotation>(3);

	public SimpleCycValueRet(String value, List<Annotation> annotations) {
		this.value = value;
		this.annotations = annotations;
	}

	public SimpleCycValueRet(String value) {
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

package org.cycads.extract.general;

import java.util.ArrayList;
import java.util.List;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.general.ParametersDefault;

public class SimpleAnnotationWayListMethods extends ArrayList<List<AnnotationMethod>>
		implements AnnotationWayListMethods
{

	public SimpleAnnotationWayListMethods() {
		super();
	}

	public SimpleAnnotationWayListMethods(int initialCapacity) {
		super(initialCapacity);
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		boolean firstList = true;

		for (List<AnnotationMethod> methods : this) {
			boolean firstMethod = true;
			for (AnnotationMethod method : methods) {
				if (firstMethod) {
					if (!firstList) {
						strBuf.append(ParametersDefault.getMethodWaySeparator());
					}
				}
				else {
					strBuf.append(ParametersDefault.getMethodSeparator());
				}
				strBuf.append(method.getName());
			}
		}

		return strBuf.toString();
	}
}

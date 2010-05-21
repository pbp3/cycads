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

	public SimpleAnnotationWayListMethods(List<AnnotationMethod> methods) {
		super();
		add(methods);
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		boolean firstList = true;

		for (List<AnnotationMethod> methods : this) {
			boolean firstMethod = true;
			for (AnnotationMethod method : methods) {
				if (firstMethod) {
					firstMethod = false;
					if (!firstList) {
						strBuf.append(ParametersDefault.getMethodWaySeparator());
					}
					else {
						firstList = false;
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

	@Override
	public boolean addToAll(List<AnnotationMethod> methods) {
		boolean ret = true;
		if (methods == null) {
			return false;
		}
		if (this.isEmpty()) {
			ret = add(new ArrayList<AnnotationMethod>(methods));
		}
		else {
			for (List<AnnotationMethod> methodsThis : this) {
				ret = methodsThis.addAll(methods) & ret;
			}
		}
		return ret;
	}

	@Override
	public boolean addToAll(AnnotationMethod method) {
		boolean ret = true;
		if (method == null) {
			return false;
		}
		if (this.isEmpty()) {
			add(new ArrayList<AnnotationMethod>());
		}
		for (List<AnnotationMethod> methodsThis : this) {
			ret = methodsThis.add(method) & ret;
		}
		return ret;
	}

	@Override
	public boolean addToAll(AnnotationWayListMethods methodsList) {
		boolean ret = true;
		if (methodsList == null || methodsList.isEmpty()) {
			return false;
		}
		if (this.isEmpty()) {
			add(new ArrayList<AnnotationMethod>());
		}
		SimpleAnnotationWayListMethods cloneOldThis = this.clone();
		boolean first = true;
		for (List<AnnotationMethod> methodList : methodsList) {
			if (first) {
				first = false;
				ret = addToAll(methodList);
			}
			else {
				for (List<AnnotationMethod> methodsThis : cloneOldThis) {
					ArrayList<AnnotationMethod> newList = new ArrayList<AnnotationMethod>(methodsThis);
					ret = newList.addAll(methodList) & ret;
					add(newList);
				}
			}
		}
		return ret;
	}

	@Override
	public SimpleAnnotationWayListMethods clone() {
		SimpleAnnotationWayListMethods ret = new SimpleAnnotationWayListMethods();
		for (List<AnnotationMethod> methodsThis : this) {
			ArrayList<AnnotationMethod> newList = new ArrayList<AnnotationMethod>(methodsThis);
			ret.add(newList);
		}
		return ret;
	}
}

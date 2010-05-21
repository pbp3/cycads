package org.cycads.extract.general;

import java.util.ArrayList;

import org.cycads.entities.annotation.Annotation;
import org.cycads.general.ParametersDefault;

public class SimpleAnnotationWay extends ArrayList<Object> implements AnnotationWay
{

	public SimpleAnnotationWay() {
		super();
	}

	public SimpleAnnotationWay(AnnotationWay c) {
		super(c);
	}

	public SimpleAnnotationWay(Object o) {
		super();
		add(o);
	}

	public SimpleAnnotationWay(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public void addFirst(Object o) {
		if (o != null) {
			if (o instanceof AnnotationWay) {
				AnnotationWay way = (AnnotationWay) o;
				for (int i = way.size() - 1; i >= 0; i--) {
					addFirst(way.get(i));
				}
			}
			else if (!o.equals(get(0))) {
				add(0, o);
			}
		}
	}

	@Override
	public void addLast(Object o) {
		if (o != null) {
			if (o instanceof AnnotationWay) {
				AnnotationWay way = (AnnotationWay) o;
				for (int i = 0; i < way.size(); i++) {
					addLast(way.get(i));
				}
			}
			else if (!o.equals(get(size() - 1))) {
				add(o);
			}
		}
	}

	@Override
	public Object getSource() {
		return get(0);
	}

	@Override
	public Object getTarget() {
		Object ret = get(size() - 1);
		if (ret instanceof AnnotationCluster) {
			ret = ((AnnotationCluster) ret).getTarget();
		}
		return ret;
	}

	@Override
	public AnnotationWayListMethods getMethods() {
		AnnotationWayListMethods ret = new SimpleAnnotationWayListMethods();
		for (int i = 0; i <= size() - 1; i++) {
			//			for (int i = 1; i < size() - 1; i++) {
			Object obj = get(i);
			if (obj instanceof Annotation && ParametersDefault.isValidAnnotForMethods(((Annotation) obj))) {
				ret.addToAll(((Annotation) obj).getAnnotationMethod());
			}
			else if (obj instanceof AnnotationCluster) {
				ret.addToAll(((AnnotationCluster) obj).getMethods());
			}
		}
		return ret;
	}
}

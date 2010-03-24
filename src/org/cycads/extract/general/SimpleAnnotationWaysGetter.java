package org.cycads.extract.general;

import java.util.List;

import org.cycads.extract.objectsGetter.ObjectsGetter;

public class SimpleAnnotationWaysGetter implements AnnotationWaysGetter
{

	ObjectsGetter			objsGetter;
	AnnotationWaysGetter	next;

	public SimpleAnnotationWaysGetter(ObjectsGetter objsGetter, AnnotationWaysGetter next) {
		this.next = next;
		this.objsGetter = objsGetter;
	}

	public SimpleAnnotationWaysGetter(ObjectsGetter objsGetter) {
		this.next = EndAnnotationWaysGetter.getInstance();
		this.objsGetter = objsGetter;
	}

	@Override
	public AnnotationWayList getAnnotationWays(Object obj) throws GetterExpressionException {
		List< ? extends Object> objsGetted = objsGetter.getObjects(obj);
		if (objsGetted.isEmpty()) {
			return new SimpleAnnotationWayList();
		}
		AnnotationWayList ret = next.getAnnotationWays(objsGetted.get(0));
		ret.addAllFirst(obj);
		for (int i = 1; i < objsGetted.size(); i++) {
			AnnotationWayList retGetted = next.getAnnotationWays(objsGetted.get(i));
			retGetted.addAllFirst(obj);
			ret.addAll(retGetted);
		}
		return ret;
	}

	public void setNext(AnnotationWaysGetter next) {
		this.next = next;
	}

}

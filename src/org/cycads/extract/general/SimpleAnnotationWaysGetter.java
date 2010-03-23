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
		this.next = null;
		this.objsGetter = objsGetter;
	}

	@Override
	public AnnotationWayList getAnnotationWays(Object obj) throws GetterExpressionException {
		List<Object> objsGetted = objsGetter.getObjects(obj);
		AnnotationWayList ret = new SimpleAnnotationWayList();
		if (next == null) {
			for (Object oGetted : objsGetted) {
				ret.add(new SimpleAnnotationWay(oGetted));
			}
			return ret;
		}
		else {
			AnnotationWayList annotationWayListGettedNext;
			for (Object oGetted : objsGetted) {
				annotationWayListGettedNext = next.getAnnotationWays(oGetted);
				for (AnnotationWay annotationWay : annotationWayListGettedNext) {
					annotationWay.addFirst(oGetted);
					ret.add(annotationWay);
				}
			}
			return ret;
		}
	}

	public void setNext(AnnotationWaysGetter next) {
		this.next = next;
	}

}

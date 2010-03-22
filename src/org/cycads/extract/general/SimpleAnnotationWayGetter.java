package org.cycads.extract.general;

import java.util.List;

public class SimpleAnnotationWayGetter implements AnnotationWaysGetter
{

	ObjectsGetter			objsGetter;
	AnnotationWaysGetter	next;

	public SimpleAnnotationWayGetter(ObjectsGetter objsGetter, AnnotationWaysGetter next) {
		this.next = next;
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

}

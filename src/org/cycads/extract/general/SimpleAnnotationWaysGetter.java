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
		AnnotationWayList ret = new SimpleAnnotationWayList();
		AnnotationWayList annotationWayListGettedNext;
		List< ? extends Object> objsGetted = objsGetter.getObjects(obj);
		for (Object objGetted : objsGetted) {
			annotationWayListGettedNext = next.getAnnotationWays(objGetted);
			//			annotationWayListGettedNext.addSource(obj);
			ret.add(obj, annotationWayListGettedNext);
		}

		if (next == null) {
			for (Object oGetted : objsGetted) {
				if (oGetted instanceof AnnotationCluster) {
					ret.addAll(((AnnotationCluster) oGetted).getAnnotationWays());
				}
				else {
					AnnotationWay annotationWay = new SimpleAnnotationWay(oGetted);
					annotationWay.addFirst(obj);
					ret.add(annotationWay);
				}
			}
			return ret;
		}
		else {
			AnnotationWayList annotationWayListGettedNext;
			for (Object oGetted : objsGetted) {
				annotationWayListGettedNext = next.getAnnotationWays(oGetted);
				for (AnnotationWay annotationWay : annotationWayListGettedNext) {
					if (oGetted instanceof AnnotationCluster) {
						ret.addAll(((AnnotationCluster) oGetted).getAnnotationWays());
					}
					else {
						annotationWay.addFirst(oGetted);
						ret.add(annotationWay);
					}
				}
			}
			return ret;
		}
	}

	public void setNext(AnnotationWaysGetter next) {
		this.next = next;
	}

}

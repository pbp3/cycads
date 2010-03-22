package org.cycads.extract.general;

import java.util.List;

public abstract class AbstractAnnotationWayGetter implements AnnotationWayGetter
{

	AnnotationWayGetter	next;

	public AbstractAnnotationWayGetter(AnnotationWayGetter next) {
		this.next = next;
	}

	@Override
	public AnnotationWayList getAnnotationWays(Object obj) throws GetterExpressionException {
		List<Object> objsGetted = getObjects(obj);
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

	protected List<Object> getObjects(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}

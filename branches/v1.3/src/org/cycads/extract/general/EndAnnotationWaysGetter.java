package org.cycads.extract.general;

public class EndAnnotationWaysGetter implements AnnotationWaysGetter
{

	public EndAnnotationWaysGetter() {
	}

	@Override
	public AnnotationWayList getAnnotationWays(Object obj) {
		AnnotationWayList ret = new SimpleAnnotationWayList();
		if (obj instanceof AnnotationCluster) {
			ret.addAll(((AnnotationCluster) obj).getAnnotationWays());
		}
		else {
			AnnotationWay annotationWay = new SimpleAnnotationWay(obj);
			annotationWay.addFirst(obj);
			ret.add(annotationWay);
		}
		return ret;
	}

}

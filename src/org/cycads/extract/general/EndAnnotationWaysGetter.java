package org.cycads.extract.general;

public class EndAnnotationWaysGetter implements AnnotationWaysGetter
{

	static EndAnnotationWaysGetter	instance	= new EndAnnotationWaysGetter();

	private EndAnnotationWaysGetter() {
	}

	@Override
	public AnnotationWayList getAnnotationWays(Object obj) {
		//		if (obj instanceof AnnotationCluster) {
		//			AnnotationCluster cluster = (AnnotationCluster) obj;
		//			//			Object target
		//			return cluster;
		//		}
		//		else {
		return new SimpleAnnotationWayList(new SimpleAnnotationWay(obj));
		//		}
	}

	public static EndAnnotationWaysGetter getInstance() {
		return instance;
	}

}

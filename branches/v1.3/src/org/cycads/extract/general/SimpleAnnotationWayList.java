package org.cycads.extract.general;

import java.util.ArrayList;

public class SimpleAnnotationWayList extends ArrayList<AnnotationWay> implements AnnotationWayList
{

	public SimpleAnnotationWayList() {
		super();
	}

	public SimpleAnnotationWayList(AnnotationWayList c) {
		super(c);
	}

	public SimpleAnnotationWayList(AnnotationWay o) {
		super();
		add(o);
	}

	public SimpleAnnotationWayList(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public AnnotationWayListMethods getMethods() {
		AnnotationWayListMethods ret = new SimpleAnnotationWayListMethods(size());
		for (AnnotationWay way : this) {
			ret.add(way.getMethods());
		}
		return ret;
	}

	@Override
	public boolean add(AnnotationWay e) {
		return this.contains(e) ? false : super.add(e);
	}

	public void addAll(AnnotationWayList list) {
		if (list != null) {
			for (AnnotationWay annotationWay : list) {
				add(annotationWay);
			}
		}
	}

	@Override
	public void addAllFirst(Object obj) {
		if (this.isEmpty() || obj == null) {
			return;
		}
		if (obj instanceof AnnotationWayList) {
			AnnotationWayList annotationWayList = (AnnotationWayList) obj;
			if (!annotationWayList.isEmpty()) {
				ArrayList<AnnotationWay> waysThis = new ArrayList<AnnotationWay>(this);
				for (AnnotationWay way : waysThis) {
					for (int i = 0; i < annotationWayList.size() - 1; i++) {
						AnnotationWay wayClone = new SimpleAnnotationWay(way);
						wayClone.addFirst(annotationWayList.get(i));
						this.add(wayClone);
					}
					way.addFirst(annotationWayList.get(annotationWayList.size() - 1));
				}
			}
		}
		else {
			for (AnnotationWay way : this) {
				way.addFirst(obj);
			}
		}

	}

}

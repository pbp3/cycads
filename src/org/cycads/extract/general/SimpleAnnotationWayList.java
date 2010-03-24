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
	public void addAllFirst(Object obj) {
		for (AnnotationWay way : this) {
			way.addFirst(obj);
		}

	}

}

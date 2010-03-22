package org.cycads.extract.general;

import java.util.ArrayList;

public class SimpleObjectPathList extends ArrayList<ObjectPath> implements
		ObjectPathList {

	public SimpleObjectPathList() {
		super();
	}

	public SimpleObjectPathList(ObjectPathList c) {
		super(c);
	}

	public SimpleObjectPathList(ObjectPath o) {
		super();
		add(o);
	}

	public SimpleObjectPathList(int initialCapacity) {
		super(initialCapacity);
	}

}

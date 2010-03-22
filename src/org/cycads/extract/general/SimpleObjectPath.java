package org.cycads.extract.general;

import java.util.ArrayList;

public class SimpleObjectPath extends ArrayList<Object> implements ObjectPath {

	public SimpleObjectPath() {
		super();
	}

	public SimpleObjectPath(ObjectPath c) {
		super(c);
	}

	public SimpleObjectPath(Object o) {
		super();
		add(o);
	}

	public SimpleObjectPath(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public void addFirst(Object o) {
		add(0, o);
	}

}

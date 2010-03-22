package org.cycads.extract.general;

import java.util.List;

public abstract class AbstractObjectGetter implements ObjectGetter {

	ObjectGetter next;

	public AbstractObjectGetter(ObjectGetter next) {
		this.next = next;
	}

	protected List<Object> execute(Object obj) throws GetterExpressionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPathList getObjects(Object obj)
			throws GetterExpressionException {
		List<Object> objs = execute(obj);
		ObjectPathList ret = null;
		if (next == null) {
			ret = new SimpleObjectPathList();
			List<Object> retO;
			for (Object o : objs) {
				ret.add(new SimpleObjectPath(o));
			}
			return ret;
		} else {
			ObjectPathList retO;
			for (Object o : objs) {
				retO = next.getObjects(o);
				if (ret == null) {
					ret = retO;
				} else {
					for (ObjectPath objectPath : retO) {
						retList.add(0, o);
						ret.add(retList);
					}
				}
			}
			return ret;
		}
	}

}

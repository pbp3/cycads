package org.cycads.extract.cyc.walkerLoc;

import java.util.ArrayList;
import java.util.Stack;

public class Trace extends Stack<Object>
{
	private final ArrayList<StringAndTrace>	stringAndTraces	= new ArrayList<StringAndTrace>();

	public Trace() {
	}

	public Trace(Object object) {
		push(object);
	}

	public StringAndTrace arrive(String value) {
		StringAndTrace ret = new StringAndTrace(value, this);
		stringAndTraces.add(ret);
		return ret;
	}
}

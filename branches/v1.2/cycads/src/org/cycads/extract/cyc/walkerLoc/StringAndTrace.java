/*
 * Created on 01/04/2009
 */
package org.cycads.extract.cyc.walkerLoc;

import java.util.ArrayList;
import java.util.List;

import org.cycads.entities.EntityObject;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.Association;

public class StringAndTrace
{
	String	value;
	Trace	trace;

	public StringAndTrace(String value, Trace trace) {
		this.value = value;
		this.trace = trace;
	}

	public String getValue() {
		return value;
	}

	public Trace getTrace() {
		return trace;
	}

	// Associations walked to arrive this value
	public List<Association> getAssociations() {
		ArrayList<Association> ret = new ArrayList<Association>();
		for (EntityObject obj : trace) {
			if (obj instanceof Association) {
				ret.add((Association) obj);
			}
		}
		return ret;
	}

	// Annotations walked to arrive this value
	public List<Annotation> getAnnotations() {
		ArrayList<Annotation> ret = new ArrayList<Annotation>();
		for (EntityObject obj : trace) {
			if (obj instanceof Annotation) {
				ret.add((Annotation) obj);
			}
		}
		return ret;
	}

}

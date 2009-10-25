package org.cycads.extract.cyc.walkerLoc;

import org.cycads.entities.EntityFinder;
import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.AssociationFinder;
import org.cycads.extract.cyc.LocContainer;
import org.cycads.extract.cyc.ScoreSystemsContainer;

public class SimpleState
{
	static final char		LOC_FILTER_REGEX_CHAR	= '#';
	static final char		LOC_FILTER_STRING_CHAR	= '!';

	static final String		LOC_SYNONYM				= "SY";
	static final String		LOC_NOTE				= "NO";
	static final String		LOC_PARENT				= "PA";
	static final String		LOC_ANNOTATION			= "AN";
	static final String		LOC_ASSOCIATION			= "AS";
	static final String		LOC_SUBSEQUENCE			= "SS";
	static final String		LOC_SEQUENCE			= "SQ";
	static final String		LOC_VALUE				= "V";
	//	static final String		LOC_DBXREF			= "DX";
	//	static final String		LOC_DBXREF_ANNOTATION	= "XA";
	//	static final String		LOC_FUNCTION_ANNOTATION	= "FA";
	//	static final String		LOC_DBXREF_VALUE		= "XV";
	//	static final String		LOC_DBXREF_SET			= "XR";
	static final String		LOC_FUNCTION_TYPE		= "FU";
	static final String		LOC_FEATURE_TYPE		= "FE";
	static final String		LOC_DBXREF_TYPE			= "DX";

	ScoreSystemsContainer	scoreSystemsContainer;
	LocContainer			locContainer;
	EntityFinder			entityFinder;
	AnnotationFinder		annotFinder;
	AssociationFinder		assocFinder;

	public SimpleState(ScoreSystemsContainer scoreSystemsContainer, LocContainer locContainer) {
		this.scoreSystemsContainer = scoreSystemsContainer;
		this.locContainer = locContainer;
	}

	public void go(String loc, Trace trace) {
		int i = loc.indexOf('.');
		String subLoc;
		if (i == -1) {
			subLoc = loc;
			loc = "";
		}
		else {
			subLoc = loc.substring(0, i);
			loc = loc.substring(i + 1);
		}
		if (subLoc.equals(LOC_SYNONYM)) {
			analyseSynonym(loc, trace);
		}
		else if (subLoc.equals(LOC_NOTE)) {
			analyseSynonym(loc, trace);
		}
	}
}

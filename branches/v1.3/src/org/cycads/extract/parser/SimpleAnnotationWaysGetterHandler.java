/*
 * Created on 23/03/2010
 */
package org.cycads.extract.parser;

import java.util.List;
import java.util.Stack;

import org.cycads.extract.general.AnnotationClustersGetter;
import org.cycads.extract.general.AnnotationWaysGetter;
import org.cycads.extract.general.EndAnnotationWaysGetter;
import org.cycads.extract.general.SimpleAnnotationWaysGetter;
import org.cycads.extract.objectsGetter.ObjectsGetter;
import org.cycads.extract.objectsGetter.ObjectsGetterChangeObject;
import org.cycads.extract.objectsGetter.ObjectsGetterFilter;
import org.cycads.extract.objectsGetter.validator.CompAnnotWaysGetter;
import org.cycads.extract.objectsGetter.validator.CompNumber;
import org.cycads.extract.objectsGetter.validator.CompRegex;
import org.cycads.parser.ParserException;

public class SimpleAnnotationWaysGetterHandler implements AnnotationWaysGetterHandler
{
	List< ? extends ObjectsGetterChangeObject>	beforeEndGetters;
	Stack<ObjectsGetter>						getters;

	public SimpleAnnotationWaysGetterHandler(List< ? extends ObjectsGetterChangeObject> beforeEndGetters) {
		this.beforeEndGetters = beforeEndGetters;
	}

	public SimpleAnnotationWaysGetterHandler() {
		this.beforeEndGetters = null;
	}

	@Override
	public void endFilter() throws ParserException {
		if (getters.empty()) {
			throw new ParserException("Missing '('");
		}
		AnnotationWaysGetter ret = EndAnnotationWaysGetter.getInstance();
		ObjectsGetter getter = null;
		while (!getters.empty() && (getter = getters.pop()) != null) {
			ret = new SimpleAnnotationWaysGetter(getter, ret);
		}
		if (getter != null) {
			throw new ParserException("Missing '('");
		}
		getters.push(new ObjectsGetterFilter(new CompAnnotWaysGetter(ret)));
	}

	@Override
	public AnnotationWaysGetter endLoc() throws ParserException {
		if (beforeEndGetters != null && !beforeEndGetters.isEmpty()) {
			for (ObjectsGetterChangeObject beforeEndGetter : beforeEndGetters) {
				newChanger(beforeEndGetter);
			}
		}
		AnnotationWaysGetter ret = EndAnnotationWaysGetter.getInstance();
		ObjectsGetter getter;
		while (!getters.empty()) {
			getter = getters.pop();
			if (getter == null) {
				throw new ParserException("Missing ')'");
			}
			ret = new SimpleAnnotationWaysGetter(getter, ret);
		}
		return ret;
	}

	@Override
	public void newChanger(ObjectsGetterChangeObject changer) {
		getters.push(changer);
	}

	@Override
	public void newCompNumber(CompNumber compNumber) throws ParserException {
		if (getters.pop() != null) {
			throw new ParserException("The compNumber must be start with '('");
		}
		getters.push(new ObjectsGetterFilter(compNumber));
	}

	@Override
	public void newCompRegex(CompRegex compRegex) throws ParserException {
		if (getters.pop() != null) {
			throw new ParserException("The compRegex must be start with '('");
		}
		getters.push(new ObjectsGetterFilter(compRegex));
	}

	@Override
	public void startFilter() {
		getters.push(null);
	}

	@Override
	public void startLoc() {
		getters = new Stack<ObjectsGetter>();
	}

	@Override
	public void newAnnotClustersGetter(AnnotationClustersGetter annotClustersGetter) {
		getters.push(annotClustersGetter);
	}

}

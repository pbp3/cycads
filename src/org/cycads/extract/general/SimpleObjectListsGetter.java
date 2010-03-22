/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.ArrayList;
import java.util.List;

public class SimpleObjectListsGetter implements ObjectsGetter
{

	@Override
	public List<List<Object>> getObjectLists(Object obj, List<AnnotationWaysGetter> steps) throws GetterExpressionException {
		List<List<Object>> ret = new ArrayList<List<Object>>();
		if (steps.isEmpty()) {
			List<Object> retO;
			retO = new ArrayList<Object>();
			retO.add(obj);
			ret.add(retO);
		}
		else {
			AnnotationWaysGetter step = steps.get(0);
			List<Object> nexts = step.getObjects(obj);
			List<AnnotationWaysGetter> nextSteps = new ArrayList<AnnotationWaysGetter>(steps);
			nextSteps.remove(0);
			List<List<Object>> retLists;
			for (Object next : nexts) {
				retLists = getObjectLists(next, nextSteps);
				for (List<Object> retList : retLists) {
					if (next != obj) {
						retList.add(0, obj);
					}
					ret.add(retList);
				}
			}
		}
		return ret;
		//		if (loc.charAt(0)){
		//			case '.':
		//				{
		//					return walk(obj, loc.substring(1));
		//				}
		//			case '(':
		//				{
		//					String expression = getFilterExpression(loc);
		//					return filter(obj, loc.substring(1));
		//				}
		//			default:
		//				{
		//					throw new ParserException(loc);
		//				}
		//		}
	}

	//	private List<List<Object>> walk(Object obj, String loc) {
	//		String prefix = loc.substring(0, 2);
	//		String sufix = loc.substring(2);
	//		List<Object> objs;
	//		if (prefix.equals("AT")) {
	//			objs = getAT(obj);
	//		}
	//		else if (prefix.equals("AS")) {
	//			objs = getAS(obj);
	//		}
	//		else if (prefix.equals("SO")) {
	//			objs = getSO(obj);
	//		}
	//		else {
	//			throw new ParserException(loc);
	//		}
	//
	//		List<List<Object>> ret = new ArrayList<List<Object>>();
	//		if (sufix.isEmpty()) {
	//			List<Object> retO;
	//			for (Object o : objs) {
	//				retO = new ArrayList<Object>();
	//				retO.add(o);
	//				ret.add(retO);
	//			}
	//			return ret;
	//		}
	//		else {
	//			List<List<Object>> retLists;
	//			for (Object o : objs) {
	//				retLists = getObjectLists(o, sufix);
	//				for (List<Object> retList : retLists) {
	//					retList.add(0, o);
	//					ret.add(retList);
	//				}
	//			}
	//			return ret;
	//		}
	//	}
	//
	//	private List<List<Object>> filter(Object obj, String loc) {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}

}

/*
 * Created on 23/03/2010
 */
package org.cycads.extract.parser;

import org.cycads.extract.general.AnnotationWaysGetter;
import org.cycads.extract.objectsGetter.ObjectsGetterChangeObject;
import org.cycads.extract.objectsGetter.changeObject.ChangeToAnnotationsAsSource;
import org.cycads.extract.objectsGetter.changeObject.ChangeToSource;
import org.cycads.extract.objectsGetter.validator.CompNumber;
import org.cycads.extract.objectsGetter.validator.CompRegex;
import org.cycads.extract.objectsGetter.validator.CompRegexMatch;
import org.cycads.extract.objectsGetter.validator.CompRegexNotMatch;
import org.cycads.extract.objectsGetter.validator.NumberEqual;
import org.cycads.parser.ParserException;

public class SimpleObjectGetterReader implements ObjectGetterReader
{
	ObjectGetterHandler	handler;

	public SimpleObjectGetterReader(ObjectGetterHandler handler) {
		this.handler = handler;
	}

	@Override
	public AnnotationWaysGetter parse(String loc) throws ParserException {
		handler.startLoc();
		ObjectsGetterChangeObject changer;
		int posBeginNextChange;
		int posBeginNextFilter;
		int posEndFilter;
		while (loc != null && loc.length() != 0) {
			switch (loc.charAt(0)){
				case '.':
					{
						String changerStr = loc.substring(1, 3);
						if (changerStr.equalsIgnoreCase("AS")) {
							changer = new ChangeToAnnotationsAsSource();
						}
						else if (changerStr.equalsIgnoreCase("SO")) {
							changer = new ChangeToSource();
						}
						else {
							throw new ParserException(changerStr + " is not a valid changer String. Loc=" + loc);
						}
						handler.newChanger(changer);
						loc = loc.substring(3);
						break;
					}
				case '(':
					{
						handler.startFilter();
						loc = loc.substring(1);
						break;
					}
				case ')':
					{
						handler.endFilter();
						loc = loc.substring(1);
						break;
					}
				case '#':
					{
						posEndFilter = loc.indexOf(')');
						if (posEndFilter == -1) {
							throw new ParserException("CompRegex without close filter ')'. Loc=" + loc);
						}
						CompRegex compRegex;
						char compRegexChr = loc.charAt(1);
						if (compRegexChr == '=') {
							compRegex = new CompRegexMatch(loc.substring(2, posEndFilter));
						}
						else if (compRegexChr == '!') {
							compRegex = new CompRegexNotMatch(loc.substring(2, posEndFilter));
						}
						else {
							throw new ParserException(compRegexChr + " is not a valid compRegex comparator. Loc=" + loc);
						}
						handler.newCompRegex(compRegex);
						handler.endFilter();
						loc = loc.substring(posEndFilter + 1);
						break;
					}
				case '=':
					{
						posEndFilter = loc.indexOf(')');
						if (posEndFilter == -1) {
							throw new ParserException("CompNumber without close filter ')'. Loc=" + loc);
						}
						CompNumber compNumber;
						char compNumberChr = loc.charAt(1);
						switch (compNumberChr){
							case '=':{
								compNumber = new NumberEqual(loc.substring(2, posEndFilter));
								break;
							}
							case '!':{
								compNumber = new NumberNotEqual(loc.substring(2, posEndFilter));
								break;
							}
						}
							compNumber = new NumberNotEqual(loc.substring(2, posEndFilter));
						}
						else {
							throw new ParserException(compNumberChr + " is not a valid compNumber comparator. Loc="
								+ loc);
						}
						handler.newCompNumber(compNumber);
						handler.endFilter();
						loc = loc.substring(posEndFilter + 1);
						break;
					}
				default:
					throw new ParserException("Can't identify the next step:" + loc);

			}
		}
		// TODO Auto-generated method stub
		return null;
	}
}

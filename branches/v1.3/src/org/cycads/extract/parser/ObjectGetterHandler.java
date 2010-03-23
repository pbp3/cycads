package org.cycads.extract.parser;

import org.cycads.extract.general.AnnotationWaysGetter;
import org.cycads.extract.objectsGetter.ObjectsGetterChangeObject;
import org.cycads.extract.objectsGetter.validator.CompNumber;
import org.cycads.extract.objectsGetter.validator.CompRegex;
import org.cycads.parser.ParserException;

public interface ObjectGetterHandler
{

	public void newChanger(ObjectsGetterChangeObject changer) throws ParserException;

	public void startFilter() throws ParserException;

	public void endFilter() throws ParserException;

	public void newCompRegex(CompRegex compRegex) throws ParserException;

	public void newCompNumber(CompNumber compNumber) throws ParserException;

	public void startLoc() throws ParserException;

	public AnnotationWaysGetter endLoc() throws ParserException;
}

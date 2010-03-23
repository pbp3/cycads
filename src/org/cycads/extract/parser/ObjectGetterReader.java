package org.cycads.extract.parser;

import org.cycads.extract.general.AnnotationWaysGetter;
import org.cycads.parser.ParserException;

public interface ObjectGetterReader
{
	public AnnotationWaysGetter parse(String loc) throws ParserException;
}

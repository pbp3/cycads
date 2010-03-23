package org.cycads.extract.parser;

import org.cycads.extract.general.AnnotationWaysGetter;
import org.cycads.parser.ParserException;

public interface AnnotationWaysGetterReader
{
	public AnnotationWaysGetter parse(String loc) throws ParserException;
}

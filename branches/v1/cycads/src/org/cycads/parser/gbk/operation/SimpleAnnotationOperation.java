/*
 * Created on 15/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;

public abstract class SimpleAnnotationOperation<O> extends SimpleRelationshipOperation<Annotation, O>
		implements AnnotationOperation<O>
{

	protected SimpleAnnotationOperation(Pattern tagNameRegex, Pattern tagValueRegex) {
		super(tagNameRegex, tagValueRegex);
		// TODO Auto-generated constructor stub
	}

}

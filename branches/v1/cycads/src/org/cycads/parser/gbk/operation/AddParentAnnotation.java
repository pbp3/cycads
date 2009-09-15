/*
 * Created on 15/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;
import org.cycads.entities.annotation.Annotation;

public class AddParentAnnotation extends SimpleOperation implements AnnotationOperation<Annotation<?,?,?,?>>
{

	protected AddParentAnnotation(Pattern tagNameRegex, Pattern tagValueRegex) {
		super(tagNameRegex, tagValueRegex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Annotation< ? , ? , ? , ? > execute(Note note, Annotation< ? , ? , ? , ? > source,
			Collection<Annotation< ? , ? , ? , ? >> newRelatedObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}

/*
 * Created on 15/09/2009
 */
package org.cycads.parser.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.synonym.Dbxref;

public class AddDbxrefAnnotation<S extends Annotation< ? , Dbxref>>
		extends SimpleRelationshipOperation<S, Annotation< ? , Dbxref>>
{

	private EntityFactory		factory;
	private String				dbxrefDBName;
	private AnnotationMethod	method;

	public AddDbxrefAnnotation(Pattern tagNameRegex, Pattern tagValueRegex, String dbxrefDBName, String methodName,
			EntityFactory factory) {
		super(tagNameRegex, tagValueRegex);
		this.dbxrefDBName = dbxrefDBName;
		this.factory = factory;
		this.method = factory.getAnnotationMethod(methodName);
	}

	@Override
	protected Collection<Annotation< ? , Dbxref>> execute(S source, Note note) {
		Dbxref dbxref = factory.getDbxref(dbxrefDBName, note.getValue());
		Collection<Annotation< ? , Dbxref>> ret = new ArrayList<Annotation< ? , Dbxref>>();
		ret.add(source.addDbxrefAnnotation(method, dbxref));
		return ret;
	}
}

/*
 * Created on 15/09/2009
 */
package org.cycads.parser.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.DbxrefAnnotable;
import org.cycads.entities.annotation.DbxrefAnnotation;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.synonym.Dbxref;

public class AddDbxrefAnnotation<S extends DbxrefAnnotable> extends SimpleRelationshipOperation<S, DbxrefAnnotation>
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
	protected Collection<DbxrefAnnotation> execute(S source, Note note) {
		Dbxref dbxref = factory.getDbxref(dbxrefDBName, note.getValue());
		Collection<DbxrefAnnotation> ret = new ArrayList<DbxrefAnnotation>();
		ret.add(source.addDbxrefAnnotation(method, dbxref));
		return ret;
	}
}

/*
 * Created on 15/09/2009
 */
package org.cycads.parser.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.EntityObject;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.Dbxref;

public class AddDbxrefAnnotation<S extends EntityObject>
		extends SimpleRelationshipOperation<S, Annotation< ? , Dbxref>>
{

	private final EntityDbxrefFactory	dbxrefFactory;
	private final String				dbxrefDBName;
	private final AnnotationMethod		method;
	private final Collection<Type>		annotationTypes;

	public AddDbxrefAnnotation(Pattern tagNameRegex, Pattern tagValueRegex, String dbxrefDBName,
			AnnotationMethod method, EntityDbxrefFactory dbxrefFactory, Collection<Type> annotationTypes) {
		super(tagNameRegex, tagValueRegex);
		this.dbxrefDBName = dbxrefDBName;
		this.dbxrefFactory = dbxrefFactory;
		this.method = method;
		this.annotationTypes = annotationTypes;
	}

	@Override
	protected Collection<Annotation< ? , Dbxref>> execute(S source, Note note) {
		Dbxref dbxref = dbxrefFactory.getDbxref(dbxrefDBName, note.getValue());
		Collection<Annotation< ? , Dbxref>> ret = new ArrayList<Annotation< ? , Dbxref>>();
		ret.add(source.addAnnotation(dbxref, method, null, annotationTypes));
		return ret;
	}
}

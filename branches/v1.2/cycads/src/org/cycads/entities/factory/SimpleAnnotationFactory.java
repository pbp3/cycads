/*
 * Created on 23/10/2009
 */
package org.cycads.entities.factory;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.Association;
import org.cycads.entities.annotation.SimpleAnnotation;
import org.cycads.entities.annotation.SimpleAssociation;
import org.cycads.entities.note.Type;

public class SimpleAnnotationFactory implements EntityAnnotationFactory
{

	EntityTypeFactory	typeFactory;
	EntityDbxrefFactory	dbxrefFactory;

	public SimpleAnnotationFactory(EntityTypeFactory typeFactory, EntityDbxrefFactory dbxrefFactory) {
		this.typeFactory = typeFactory;
		this.dbxrefFactory = dbxrefFactory;
	}

	@Override
	public Annotation createAnnotation(EntityObject source, EntityObject target, Collection types,
			AnnotationMethod method, String score) {
		Annotation ret = new SimpleAnnotation(source, target, method, score, typeFactory, dbxrefFactory, this);
		for (Type type : (Collection<Type>) types) {
			ret.addType(type);
		}
		return ret;
	}

	@Override
	public Association createAssociation(EntityObject source, EntityObject target, Collection types) {
		Association ret = new SimpleAssociation(source, target, typeFactory, dbxrefFactory, this);
		for (Type type : (Collection<Type>) types) {
			ret.addType(type);
		}
		return ret;
	}

}

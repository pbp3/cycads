package org.cycads.entities.annotation;

import java.util.Collection;
import java.util.TreeSet;

import org.cycads.entities.EntityObject;
import org.cycads.entities.SimpleEntityObject;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;

public class SimpleAssociation<SO extends EntityObject, TA extends EntityObject> extends SimpleEntityObject
		implements Association<SO, TA>
{

	SO					source;
	TA					target;

	Collection<Type>	types	= new TreeSet<Type>();

	public SimpleAssociation(SO source, TA target, EntityTypeFactory< ? > typeFactory,
			EntityDbxrefFactory< ? > dbxrefFactory, EntityAnnotationFactory< ? > annotationFacotry) {
		super(typeFactory, dbxrefFactory, annotationFacotry);
		this.source = source;
		this.target = target;
	}

	@Override
	public Type addType(String typeName) {
		Type t = typeFactory.getType(typeName);
		types.add(t);
		return t;
	}

	@Override
	public Type addType(Type type) {
		types.add(type);
		return type;
	}

	@Override
	public Collection<Type> getTypes() {
		return types;
	}

	@Override
	public boolean isType(String typeName) {
		return isType(typeFactory.getType(typeName));
	}

	@Override
	public boolean isType(Type type) {
		return types.contains(type);
	}

	@Override
	public SO getSource() {
		return source;
	}

	@Override
	public TA getTarget() {
		return target;
	}

	@Override
	public Type getEntityType() {
		return typeFactory.getType("SimpleAnnotation");
	}

}

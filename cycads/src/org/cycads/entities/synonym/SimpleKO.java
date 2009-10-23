/*
 * Created on 23/10/2009
 */
package org.cycads.entities.synonym;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.SQL.AnnotationMethodSQL;
import org.cycads.entities.factory.EntityAnnotationFactory;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.SimpleType;
import org.cycads.entities.note.Type;
import org.cycads.general.ParametersDefault;

public class SimpleKO extends SimpleDbxref implements KO
{

	String	definition;
	String	name;

	public SimpleKO(String accession, EntityTypeFactory< ? > typeFactory, EntityDbxrefFactory< ? > factory,
			EntityAnnotationFactory annotationFactory) {
		super(accession, KO.DBNAME, typeFactory, factory, annotationFactory);
	}

	@Override
	public Type getEntityType() {
		return typeFactory.getType("Simple KO");
	}

	@Override
	public String getDefinition() {
		return definition;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Annotation< ? extends KO, ? extends EC> addEcAnnotation(AnnotationMethodSQL method, String ecNumber) {
		SimpleEC ec = new SimpleEC(ecNumber, typeFactory, dbxrefFactory, annotationFactory);
		Collection< ? extends Annotation< ? extends KO, ? extends EC>> annots = (Collection<Annotation< ? extends KO, ? extends EC>>) getAnnotations(
			ec, method, null);
		Collection<Type> types = new ArrayList<Type>();
		types.add(new SimpleType(ParametersDefault.getFunctionalAnnotationTypeName(), null));
		if (annots.isEmpty()) {
			return (Annotation< ? extends KO, ? extends EC>) addAnnotation(ec, method, null, types);
		}
		else {
			return annots.iterator().next();
		}
	}

}

/*
 * Created on 25/02/2009
 */
package org.cycads.entities;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationObject;

public interface Function extends AnnotationObject
{
	public String getName();

	public String getDescription();

	public Collection<Function> getSynonyms();

	public Function getSynonym(String name);

	public Function addSynonym(String name, String description);

}

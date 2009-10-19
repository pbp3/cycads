/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.EntityObject;
import org.cycads.entities.note.Noteble;
import org.cycads.entities.note.Type;
import org.cycads.entities.synonym.HasSynonyms;

public interface Association<SO, TA> extends Noteble, HasSynonyms, EntityObject
{
	public static final String	OBJECT_TYPE_NAME	= "Association";

	public Collection< ? extends Type> getTypes();

	public boolean isType(String type);

	public boolean isType(Type type);

	public Type addType(String type);

	public Type addType(Type type);

	public SO getSource();

	public TA getTarget();
}
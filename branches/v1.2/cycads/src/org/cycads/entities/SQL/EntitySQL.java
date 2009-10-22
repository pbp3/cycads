/*
 * Created on 16/10/2009
 */
package org.cycads.entities.SQL;

import java.sql.Connection;

import org.cycads.entities.EntityObject;

public interface EntitySQL extends EntityObject
{
	public int getId();

	public Connection getConnection();

	public int getTypeId();

}

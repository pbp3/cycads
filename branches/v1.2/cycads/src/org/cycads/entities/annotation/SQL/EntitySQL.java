/*
 * Created on 16/10/2009
 */
package org.cycads.entities.annotation.SQL;

import java.sql.Connection;

import org.cycads.entities.EntityObject;

public interface EntitySQL extends EntityObject
{
	public int getId();

	public Connection getConnection();

}

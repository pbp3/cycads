/*
 * Created on 16/10/2009
 */
package org.cycads.entities.SQL;

import java.sql.Connection;

import org.cycads.entities.BasicEntity;

public interface BasicEntitySQL extends BasicEntity
{
	public int getId();

	public Connection getConnection();

	public int getTypeId();

}

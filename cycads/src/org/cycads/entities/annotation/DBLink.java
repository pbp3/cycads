/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

public interface DBLink extends Annotation<DBLinkSource>, DBLinkSource, DBLinkCollection
{

	public DBRecord getDBRecord();

}
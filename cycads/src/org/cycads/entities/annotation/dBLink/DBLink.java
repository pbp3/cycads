/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.Annotation;

public interface DBLink<S extends DBLinkSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? >> extends Annotation<S>
{
	public R getDBRecord();

}
/*
 * Created on 04/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import org.biojavax.CrossRef;
import org.cycads.entities.annotation.dBLink.SynonymLink;
import org.cycads.entities.annotation.dBLink.SynonymSource;

// Without AnnotationMethod and notes. It has just source and target.
public class DBLinkBJ<S extends SynonymSource< ? , ? >> implements SynonymLink<S, DBRecordBJ>
{
	S			source;
	DBRecordBJ	target;

	public DBLinkBJ(S source, DBRecordBJ target)
	{
		this.source = source;
		this.target = target;
	}

	public DBLinkBJ(S source, CrossRef crossRef)
	{
		this.source = source;
		this.target = new DBRecordBJ(crossRef);
	}

	@Override
	public S getSource()
	{
		return source;
	}

	@Override
	public String toString()
	{
		return DBRecordDBRecordLinkBJ.joinTermName(getSource().toString(), "", getTarget().toString());
	}

	@Override
	public DBRecordBJ getTarget()
	{
		return target;
	}

}

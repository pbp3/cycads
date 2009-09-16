/*
 * Created on 15/09/2009
 */
package org.cycads.parser.operation;

import java.util.Collection;

public interface RelationshipOperation<S, O> extends Operation
{
	public Collection<O> relate(S source, Note note);

}

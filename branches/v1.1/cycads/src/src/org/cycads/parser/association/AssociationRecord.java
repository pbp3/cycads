/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

public interface AssociationRecord<S, T>
{
	public S getSource();

	public T getTarget();

	//	public String getNote(int index);

}

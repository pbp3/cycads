/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;


public class SimpleAssociationRecord<S, T> implements AssociationRecord<S, T>
{
	private S	source;
	private T	target;

	public SimpleAssociationRecord(S source, T target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public S getSource() {
		return source;
	}

	@Override
	public T getTarget() {
		return target;
	}

}

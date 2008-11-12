package org.cycads.entities.refact;

/*
 * Created on 07/11/2008
 */

/** 
 */
public class Term implements ITerm
{

	private String		value;

	private ITypeTerm	type;

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ITerm#getType()
	 */
	public ITypeTerm getType()
	{
		return type;
	}

	/* (non-Javadoc)
	 * @see org.cycads.entities.refact.ITerm#getValue()
	 */
	public String getValue()
	{
		return value;
	}

}

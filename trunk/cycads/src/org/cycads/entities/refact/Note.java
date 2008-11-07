package org.cycads.entities.refact;
/*
 * Created on 07/11/2008
 */

public class Note
{

	/*
	* (non-javadoc)
	*/
	private Term	type;

	/**
	 * Getter of the property <tt>type</tt>
	 * 
	 * @return Returns the type.
	 * 
	 */
	public Term getType() {
		return type;
	}

	/*
	* (non-javadoc)
	*/
	private String	value;

	/**
	 * Getter of the property <tt>value</tt>
	 * 
	 * @return Returns the value.
	 * 
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter of the property <tt>value</tt>
	 * 
	 * @param value The value to set.
	 * 
	 */
	public void setValue(String value) {
		this.value = value;
	}

}

///**
//	 * Getter of the property <tt>type</tt>
//	 * 
//	 * @return Returns the type.
//	 * 
//	 */
//	public String getType() {
//		return type;
//	}
/*
 * Created on 16/09/2008
 */
package org.cycads.entities.biojava1;

public class DBLink
{
	String	type, value;

	public DBLink(String type, String value) throws Exception {
		if (type == null || type.length() == 0) {
			throw new Exception("DBLink without type.");
		}
		if (value == null || value.length() == 0) {
			throw new Exception("DBLink without value.");
		}
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

}

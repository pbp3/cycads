/*
 * Created on 16/09/2009
 */
package org.cycads.parser.operation;

public class SimpleNote implements Note
{

	String	type, value;

	protected SimpleNote(String type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

}

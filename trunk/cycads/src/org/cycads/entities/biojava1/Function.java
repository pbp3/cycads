/*
 * Created on 16/09/2008
 */
package org.cycads.entities.biojava1;

public class Function
{
	String	name, comment, synonym;

	public Function(String name, String comment, String synonym) throws Exception {
		if (name == null || name.length() == 0) {
			throw new Exception("Function without name.");
		}
		this.name = name;
		this.comment = comment;
		this.synonym = synonym;
	}

	public void setName(String name) throws Exception {
		if (name == null || name.length() == 0) {
			throw new Exception("Function without name.");
		}
		this.name = name;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public String getSynonym() {
		return synonym;
	}

}

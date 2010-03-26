/*
 * Created on 16/09/2008
 */
package org.cycads.extract.pf;

import java.util.Collection;

public class SimpleCycFunction implements CycFunction
{
	String				name;
	Collection<String>	comments;
	Collection<String>	synonyms;

	public SimpleCycFunction(String name) {
		if (name == null || name.length() == 0) {
			throw new RuntimeException("Function without name.");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.length() == 0) {
			throw new RuntimeException("Function without name.");
		}
		this.name = name;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	public void setSynonyms(Collection<String> synonyms) {
		this.synonyms = synonyms;
	}

	public void addComment(String comment) {
		this.comments.add(comment);
	}

	public void addSynonym(String synonym) {
		this.synonyms.add(synonym);
	}

	public Collection<String> getComments() {
		return comments;
	}

	public Collection<String> getSynonyms() {
		return synonyms;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CycFunction)) {
			return false;
		}
		else {
			CycFunction o = (CycFunction) obj;
			return this.getName() == o.getName();
		}
	}

}

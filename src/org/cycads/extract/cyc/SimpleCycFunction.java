/*
 * Created on 16/09/2008
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleCycFunction implements CycFunction
{
	String				name;
	Collection<String>	comments;
	Collection<String>	synonyms;

	public SimpleCycFunction(String name) {
		setName(name);
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
		this.comments = new ArrayList<String>(comments);
	}

	public void setSynonyms(Collection<String> synonyms) {
		this.synonyms = new ArrayList<String>(synonyms.size());
		String name = getName();
		for (String synonym : synonyms) {
			if (!synonym.equals(name)) {
				this.synonyms.add(synonym);
			}
		}
	}

	public void addComment(String comment) {
		if (synonyms == null) {
			synonyms = new ArrayList<String>();
		}
		this.comments.add(comment);
	}

	public void addSynonym(String synonym) {
		if (!synonym.equals(getName())) {
			if (synonyms == null) {
				synonyms = new ArrayList<String>();
			}
			this.synonyms.add(synonym);
		}
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

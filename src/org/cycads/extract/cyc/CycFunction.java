/*
 * Created on 16/09/2008
 */
package org.cycads.extract.cyc;

import java.util.Collection;

public interface CycFunction
{
	public String getName();

	public void setName(String name);

	public void setComments(Collection<String> comments);

	public void setSynonyms(Collection<String> synonyms);

	public void addComment(String comment);

	public void addSynonym(String synonym);

	public Collection<String> getComments();

	public Collection<String> getSynonyms();
}

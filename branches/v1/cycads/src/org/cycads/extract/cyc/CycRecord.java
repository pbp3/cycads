/*
 * Created on 16/09/2008
 */
package org.cycads.extract.cyc;

import java.util.Collection;

public interface CycRecord
{
	public String getId();

	public void setID(String bioCycID);

	public String getName();

	public void setName(String name);

	public String getProductType();

	public void setProductType(String productType);

	public int getStartBase();

	public void setStartBase(int startBase);

	public int getEndBase();

	public void setEndtBase(int endBase);

	//	public void shiftLocation(int shiftQtty);

	public Collection<CycIntron> getIntrons();

	public void setIntrons(Collection<CycIntron> introns);

	public void addIntron(CycIntron intron);

	//	public String getProductId();
	//
	//	public void setProductId(String productId);
	//
	public Collection<CycDBLink> getDBLinks();

	public void setDBLinks(Collection<CycDBLink> dbLinks);

	public void addDBLink(CycDBLink dbLink);

	public Collection<CycFunction> getFunctions();

	public void setFunctions(Collection<CycFunction> functions);

	public void addFunction(CycFunction function);

	public Collection<String> getECs();

	public void setECs(Collection<String> eCs);

	public void addEC(String eC);

	public Collection<String> getComments();

	public void setComments(Collection<String> comments);

	public void addComment(String comment);

	public Collection<String> getSynonyms();

	public void setSynonyms(Collection<String> synonyms);

	public void addSynonym(String synonym);

	// public String getExternalId();
	//
}

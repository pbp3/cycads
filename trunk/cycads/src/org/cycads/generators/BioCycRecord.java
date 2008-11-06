/*
 * Created on 16/09/2008
 */
package org.cycads.generators;

import java.util.Collection;

import org.cycads.dbExternal.EC;
import org.cycads.entities.DBLink;
import org.cycads.entities.Function;
import org.cycads.entities.Location;

public interface BioCycRecord
{
	public String getType();

	public void setType(String type);

	public String getTypeDescription();

	public void setTypeDescription(String typeDescription);

	public String getId();

	public void setID(String bioCycID);

	public String getName();

	public void setName(String name);

	public Location getLocation();

	public void setLocation(Location location);

	public int getStartBase();

	public int getEndBase();

	public Collection<Intron> getIntrons();

	public void shiftLocation(int shiftQtty);

	public String getProductId();

	public void setProductId(String productId);

	public Collection<DBLink> getDBLinks();

	public void setDBLinks(Collection<DBLink> dbLinks);

	public void addDBLink(DBLink dbLink);

	public Collection<Function> getFunctions();

	public void setFunctions(Collection<Function> functions);

	public void addFunction(Function function);

	public Collection<EC> getECs();

	public void setECs(Collection<EC> eCs);

	public void addEC(EC eC);

	public Collection<String> getComments();

	public void setComments(Collection<String> comments);

	public void addComment(String comment);

	public Collection<String> getSynonyms();

	public void setSynonyms(Collection<String> synonyms);

	public void addSynonym(String synonym);

	// public String getExternalId();
	//
}

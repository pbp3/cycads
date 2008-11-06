/*
 * Created on 03/11/2008
 */
package org.cycads.generators;

import java.util.ArrayList;
import java.util.Collection;

import org.cycads.dbExternal.EC;
import org.cycads.entities.DBLink;
import org.cycads.entities.Function;
import org.cycads.entities.Location;

public class SimpleBioCycRecord implements BioCycRecord
{
	String					type;
	String					typeDescription;
	String					bioCycID;
	String					name;
	Location				location;
	String					productId;
	Collection<DBLink>		dBLinks;
	Collection<String>		comments;
	Collection<String>		synonyms;
	Collection<EC>			eCs;
	Collection<Function>	functions;

	public SimpleBioCycRecord(String type, String bioCycID)
	{
		this.type = type;
		this.bioCycID = bioCycID;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public String getTypeDescription()
	{
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription)
	{
		this.typeDescription = typeDescription;
	}

	public void setID(String bioCycID)
	{
		this.bioCycID = bioCycID;
	}

	public String getId()
	{
		return bioCycID;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public int getEndBase()
	{
		return location.getEnd();
	}

	public int getStartBase()
	{
		return location.getStart();
	}

	public void shiftLocation(int shiftQtty)
	{
		location.shift(shiftQtty);
	}

	public Collection<Intron> getIntrons()
	{
		return location.getIntrons();
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public void addDBLink(DBLink dbLink)
	{
		if (dBLinks == null)
		{
			dBLinks = new ArrayList<DBLink>();
		}
		dBLinks.add(dbLink);
	}

	public void setDBLinks(Collection<DBLink> dBLinks)
	{
		this.dBLinks = dBLinks;
	}

	public Collection<DBLink> getDBLinks()
	{
		return dBLinks;
	}

	public Collection<Function> getFunctions()
	{
		return functions;
	}

	public Collection<String> getSynonyms()
	{
		return synonyms;
	}

	public Collection<String> getComments()
	{
		return comments;
	}

	@Override
	public void addFunction(Function function)
	{
		if (functions == null)
		{
			functions = new ArrayList<Function>();
		}
		functions.add(function);
	}

	@Override
	public Collection<EC> getECs()
	{
		return eCs;
	}

	@Override
	public void setFunctions(Collection<Function> functions)
	{
		this.functions = functions;
	}

	@Override
	public void addComment(String comment)
	{
		if (comments == null)
		{
			comments = new ArrayList<String>();
		}
		comments.add(comment);
	}

	@Override
	public void addEC(EC ec)
	{
		if (eCs == null)
		{
			eCs = new ArrayList<EC>();
		}
		eCs.add(ec);
	}

	@Override
	public void addSynonym(String synonym)
	{
		if (synonyms == null)
		{
			synonyms = new ArrayList<String>();
		}
		synonyms.add(synonym);
	}

	@Override
	public void setComments(Collection<String> comments)
	{
		this.comments = comments;
	}

	@Override
	public void setECs(Collection<EC> eCs)
	{
		this.eCs = eCs;
	}

	@Override
	public void setSynonyms(Collection<String> synonyms)
	{
		this.synonyms = synonyms;
	}

	// @Override
	// public String getExternalId()
	// {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
}

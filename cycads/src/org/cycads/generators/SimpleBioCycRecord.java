/*
 * Created on 03/11/2008
 */
package org.cycads.generators;

import java.util.Collection;

import org.cycads.entities.DBLink;
import org.cycads.entities.Function;
import org.cycads.entities.Location;

public class SimpleBioCycRecord implements BioCycRecord
{
	String				type;
	String				bioCycID;
	String				name;
	Location			location;
	String				productId;
	Collection<DBLink>	dBLinks;

	public SimpleBioCycRecord(String type, String bioCycID) {
		this.type = type;
		this.bioCycID = bioCycID;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setID(String bioCycID) {
		this.bioCycID = bioCycID;
	}

	public String getId() {
		return bioCycID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getEndBase() {
		return location.getEnd();
	}

	public int getStartBase() {
		return location.getStart();
	}

	public void shiftLocation(int shiftQtty) {
		location.shift(shiftQtty);
	}

	public Collection<Intron> getIntrons() {
		return location.getIntrons();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void addDBLink(DBLink dbLink) {
		dBLinks.add(dbLink);
	}

	public void setDBLinks(Collection<DBLink> dBLinks) {
		this.dBLinks = dBLinks;
	}

	public Collection<DBLink> getDBLinks() {
		return dBLinks;
	}

	public Collection<Function> getFunctions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<String> getSynonyms() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTypeDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getExternalId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<String> getComments() {
		// TODO Auto-generated method stub
		return null;
	}

}

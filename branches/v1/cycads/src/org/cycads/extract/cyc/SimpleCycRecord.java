/*
 * Created on 03/11/2008
 */
package org.cycads.extract.cyc;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleCycRecord implements CycRecord
{
	private int						start, end;
	private String					productType;
	private String					bioCycID;
	private Collection<CycIntron>	introns;
	private String					name;
	private Collection<CycDBLink>	dBLinks;
	private Collection<String>		comments;
	private Collection<String>		synonyms;
	private Collection<String>		eCs;
	private Collection<CycFunction>	functions;

	public SimpleCycRecord(String productType, String bioCycID) {
		this.productType = productType;
		this.bioCycID = bioCycID;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductType() {
		return productType;
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

	public int getEndBase() {
		return end;
	}

	@Override
	public void setEndtBase(int endBase) {
		this.end = endBase;
	}

	public int getStartBase() {
		return start;
	}

	@Override
	public void setStartBase(int startBase) {
		this.start = startBase;
	}

	public void shiftLocation(int shiftQtty) {
		start += shiftQtty;
		end += shiftQtty;
	}

	public Collection<CycIntron> getIntrons() {
		return introns;
	}

	@Override
	public void addIntron(CycIntron intron) {
		introns.add(intron);
	}

	@Override
	public void setIntrons(Collection<CycIntron> introns) {
		this.introns = introns;
	}

	public void addDBLink(CycDBLink dbLink) {
		if (dBLinks == null) {
			dBLinks = new ArrayList<CycDBLink>();
		}
		dBLinks.add(dbLink);
	}

	public void setDBLinks(Collection<CycDBLink> dBLinks) {
		this.dBLinks = dBLinks;
	}

	public Collection<CycDBLink> getDBLinks() {
		return dBLinks;
	}

	public Collection<CycFunction> getFunctions() {
		return functions;
	}

	public Collection<String> getSynonyms() {
		return synonyms;
	}

	public Collection<String> getComments() {
		return comments;
	}

	@Override
	public void addFunction(CycFunction function) {
		if (functions == null) {
			functions = new ArrayList<CycFunction>();
		}
		functions.add(function);
	}

	@Override
	public Collection<String> getECs() {
		return eCs;
	}

	@Override
	public void setFunctions(Collection<CycFunction> functions) {
		this.functions = functions;
	}

	@Override
	public void addComment(String comment) {
		if (comments == null) {
			comments = new ArrayList<String>();
		}
		comments.add(comment);
	}

	@Override
	public void addEC(String ec) {
		if (eCs == null) {
			eCs = new ArrayList<String>();
		}
		eCs.add(ec);
	}

	@Override
	public void addSynonym(String synonym) {
		if (synonyms == null) {
			synonyms = new ArrayList<String>();
		}
		synonyms.add(synonym);
	}

	@Override
	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	@Override
	public void setECs(Collection<String> eCs) {
		this.eCs = eCs;
	}

	@Override
	public void setSynonyms(Collection<String> synonyms) {
		this.synonyms = synonyms;
	}

}

/*
 * Created on 03/11/2008
 */
package org.cycads.extract.cyc;

import java.util.Collection;
import java.util.HashSet;

public class SimpleCycRecord implements CycRecord
{
	private String					bioCycID;
	private String					productType;
	private String					name;
	private Collection<CycFunction>	functions	= new HashSet<CycFunction>();
	private Collection<String>		dBLinks		= new HashSet<String>();
	private Collection<String>		comments	= new HashSet<String>();
	private Collection<String>		synonyms	= new HashSet<String>();
	private Collection<String>		ecs			= new HashSet<String>();
	private Collection<String>		gos			= new HashSet<String>();
	private int						start, end;
	private Collection<CycIntron>	introns		= new HashSet<CycIntron>();

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
	public void setEndBase(int endBase) {
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

	public void addDBLink(String dbLink) {
		dBLinks.add(dbLink);
	}

	public void setDBLinks(Collection<String> dBLinks) {
		this.dBLinks = dBLinks;
	}

	public Collection<String> getDBLinks() {
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
		functions.add(function);
	}

	@Override
	public void setFunctions(Collection<CycFunction> functions) {
		this.functions = functions;
	}

	@Override
	public void addComment(String comment) {
		comments.add(comment);
	}

	@Override
	public Collection<String> getECs() {
		return ecs;
	}

	@Override
	public void addEC(String ec) {
		ecs.add(ec);
	}

	@Override
	public void setECs(Collection<String> eCs) {
		this.ecs = eCs;
	}

	@Override
	public Collection<String> getGOs() {
		return gos;
	}

	@Override
	public void addGO(String go) {
		gos.add(go);
	}

	@Override
	public void setGOs(Collection<String> gos) {
		this.gos = gos;
	}

	@Override
	public void addSynonym(String synonym) {
		synonyms.add(synonym);
	}

	@Override
	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	@Override
	public void setSynonyms(Collection<String> synonyms) {
		this.synonyms = synonyms;
	}

}

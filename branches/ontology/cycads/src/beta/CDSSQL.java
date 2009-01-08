/*
 * Created on 06/01/2009
 */
package beta;

import java.util.ArrayList;
import java.util.Collection;

public class CDSSQL
{
	String				acypi, xp, glean;
	Collection<KOAnnot>	kOAnnots	= new ArrayList<KOAnnot>();
	Collection<String>	functions	= new ArrayList<String>();
	String				name;
	String				geneId, locGene, geneComment, geneAphidDBId;

	public CDSSQL(String acypi, String xp, String glean) {
		this.acypi = acypi;
		this.xp = xp;
		this.glean = glean;
	}

	public void addKOAnnot(KOAnnot kOAnnot) {
		kOAnnots.add(kOAnnot);
	}

	public void addFunction(String function) {
		functions.add(function);
	}

	public String getName() {
		if (name == null || name.length() == 0) {
			return getAcypi();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcypi() {
		return acypi;
	}

	public String getXp() {
		return xp;
	}

	public String getGlean() {
		return glean;
	}

	public Collection<KOAnnot> getKOAnnots() {
		return kOAnnots;
	}

	public Collection<String> getFunctions() {
		return functions;
	}

	public String getGeneId() {
		return geneId;
	}

	public void setGeneId(String geneId) {
		this.geneId = geneId;
	}

	public String getLocGene() {
		return locGene;
	}

	public void setLocGene(String locGene) {
		this.locGene = locGene;
	}

	public String getGeneComment() {
		return geneComment;
	}

	public void setGeneComment(String geneComment) {
		this.geneComment = geneComment;
	}

	public String getGeneAphidDBId() {
		return geneAphidDBId;
	}

	public void setGeneAphidDBId(String geneAphidDBId) {
		this.geneAphidDBId = geneAphidDBId;
	}

}

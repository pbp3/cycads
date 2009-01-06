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

	protected CDSSQL(String acypi, String xp, String glean) {
		this.acypi = acypi;
		this.xp = xp;
		this.glean = glean;
	}

	public void addKOAnnot(KOAnnot kOAnnot) {
		kOAnnots.add(kOAnnot);
	}
}

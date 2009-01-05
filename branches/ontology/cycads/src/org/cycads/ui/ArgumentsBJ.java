/*
 * Created on 20/10/2008
 */
package org.cycads.ui;

import org.cycads.entities.biojava.MethodType;
import org.cycads.entities.biojava.MethodTypeBJ;
import org.cycads.entities.biojava.NCBIOrganismBJ;
import org.cycads.entities.biojava.Organism;
import org.cycads.exceptions.DBObjectNotFound;
import org.cycads.general.CacheCleanerListener;
import org.cycads.general.biojava.BioJavaxSession;

public class ArgumentsBJ extends Arguments {

	public ArgumentsBJ() {
		BioJavaxSession.init();
	}

	@Override
	public Organism createOrganismObject(int orgID) throws DBObjectNotFound {
		return new NCBIOrganismBJ(orgID);
	}

	@Override
	public MethodType createMethodTypeObject(String methodType) {
		return new MethodTypeBJ(methodType);
	}

	@Override
	public CacheCleanerListener getCacheCleanerSession() {
		return BioJavaxSession.getCacheCleanerListener();
	}
}

/*
 * Created on 20/10/2008
 */
package org.cycads.ui;

import org.cycads.entities.biojava.MethodType;
import org.cycads.entities.biojava.MethodTypeBJ;
import org.cycads.entities.biojava.NCBIOrganismBJ;
import org.cycads.entities.biojava.Organism;
import org.cycads.exceptions.DBObjectNotFound;

public class ArgumentsBJ extends Arguments
{

	@Override
	public Organism createOrganismObject(int orgID) throws DBObjectNotFound {
		return new NCBIOrganismBJ(orgID);
	}

	@Override
	public MethodType createMethodTypeObject(String methodType) {
		return new MethodTypeBJ(methodType);
	}
}

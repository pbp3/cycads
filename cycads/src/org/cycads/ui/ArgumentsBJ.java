/*
 * Created on 20/10/2008
 */
package org.cycads.ui;

import org.cycads.entities.biojava1.MethodType;
import org.cycads.entities.biojava1.MethodTypeBJ;
import org.cycads.entities.biojava1.NCBIOrganismBJ;
import org.cycads.entities.biojava1.Organism;
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

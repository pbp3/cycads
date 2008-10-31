/*
 * Created on 16/09/2008
 */
package org.cycads.generators;

public class GeneRecordInvalidException extends Exception
{

	public GeneRecordInvalidException(BioCycRecord geneRecord) {
		super("Gene record invalid: " + geneRecord.getId() + " " + geneRecord.getName());
	}

}

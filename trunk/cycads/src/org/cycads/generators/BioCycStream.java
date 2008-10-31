/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

public interface BioCycStream
{

	public void write(BioCycRecord record) throws GeneRecordInvalidException;

	public void flush();
}

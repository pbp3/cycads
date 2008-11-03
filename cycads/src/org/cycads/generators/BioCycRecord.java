/*
 * Created on 16/09/2008
 */
package org.cycads.generators;

import java.util.Collection;

import org.cycads.general.Config;

public interface BioCycRecord
{
	static final String	PROTEIN_TYPE	= Config.bioCycRecordProteinType();

	public boolean isValid();

	public String getId();

	public String getExternalId();

	public String getName();

	public int getStartBase();

	public int getEndBase();

	public String getType();

	public Collection<String> getComments();

	public String getProductID();

	public Collection<String> getSynonyms();

	public Collection<DBLink> getDBLinks();

	public Collection<Function> getFunctions();

	public Collection<String> getECs();

	public Collection<Intron> getIntrons();

	public void shiftLocation(int shiftQtty);

	public String getTypeDescriptor();
}

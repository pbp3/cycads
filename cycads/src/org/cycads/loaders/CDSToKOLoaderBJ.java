/*
 * Created on 21/10/2008
 */
package org.cycads.loaders;

import org.cycads.entities.biojava.CDS;
import org.cycads.entities.biojava.CDSBJ;
import org.cycads.entities.biojava.Method;
import org.cycads.entities.biojava.Organism;
import org.cycads.exceptions.DBObjectNotFound;
import org.cycads.general.CacheCleanerController;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class CDSToKOLoaderBJ extends FileLoaderLine
{
	Organism	org;
	Method		method;

	public CDSToKOLoaderBJ(Progress progress, CacheCleanerController cacheCleaner, Organism org, Method method) {
		super(progress, cacheCleaner);
		this.org = org;
		this.method = method;
	}

	public void loadLine(String line) {
		if (!line.startsWith(ParametersDefault.cdsToKOLoaderComment())) {
			String[] sep = line.split(ParametersDefault.cdsToKOLoaderSeparator());
			if (sep.length == 2) {
				try {
					getCDS(getCDSName(sep[0])).addAnnotation(method, sep[1]);
					progress.completeStep();
					cacheCleaner.incCache();
				}
				catch (DBObjectNotFound e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	public CDS getCDS(String cdsName) throws DBObjectNotFound {
		CDS cds = CDSBJ.getCDSByProtID(cdsName, org);
		if (cds == null) {
			throw new DBObjectNotFound(Messages.cdsToKOLoaderCDSNotFound(cdsName));
		}
		return cds;
	}

	public String getCDSName(String cdsPart) {
		return cdsPart.split(ParametersDefault.cdsToKOLoaderSeparatorCDSName())[ParametersDefault.cdsToKOLoaderPosCDSName()];
		//		return cdsPart.split("\\|")[3];
	}
}

/*
 * Created on 15/10/2008
 */
package org.cycads.loaders;

import java.io.BufferedReader;
import java.io.IOException;

import org.cycads.dbExternal.EC;
import org.cycads.dbExternal.ECBJ;
import org.cycads.dbExternal.KO;
import org.cycads.dbExternal.KOBJ;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.CacheCleaner;
import org.cycads.ui.progress.Progress;

public class KOToECLoaderBJ implements KOToECLoader
{
	Progress		progress;
	CacheCleaner	cacheCleaner;

	public KOToECLoaderBJ(Progress progress, CacheCleaner cacheCleaner) {
		this.progress = progress;
		this.cacheCleaner = cacheCleaner;
	}

	public void load(BufferedReader br) throws IOException {
		BioJavaxSession.init();
		progress.init();
		String line;
		KO ko;
		String[] sep;
		while ((line = br.readLine()) != null) {
			if (!line.startsWith(ParametersDefault.koToECLoaderComment())) {
				sep = line.split(ParametersDefault.koToECLoaderSeparator());
				if (sep.length != 2) {
					System.err.println(Messages.koToECLoaderParsingError(line));
				}
				else {
					ko = createKO(sep[0]);
					sep[1] = sep[1].replaceAll(ParametersDefault.koToECLoaderDeleteExpression(), "");
					sep = sep[1].split(" ");
					for (String ecId : sep) {
						ko.link2Ec(createEC(ecId));
					}
					progress.completeStep();
					cacheCleaner.incCache();
				}
			}
		}
		BioJavaxSession.finish();
		Object[] a1 = {progress.getStep()};
		progress.finish(a1);
	}

	protected KO createKO(String koId) {
		return new KOBJ(koId);
	}

	protected EC createEC(String ecId) {
		return new ECBJ(ecId);
	}

}

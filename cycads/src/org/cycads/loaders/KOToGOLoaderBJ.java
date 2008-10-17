/*
 * Created on 15/10/2008
 */
package org.cycads.loaders;

import java.io.BufferedReader;
import java.io.IOException;

import org.cycads.dbExternal.GO;
import org.cycads.dbExternal.GOBJ;
import org.cycads.dbExternal.KO;
import org.cycads.dbExternal.KOBJ;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.CacheCleaner;
import org.cycads.ui.progress.Progress;

public class KOToGOLoaderBJ implements KOToGOLoader
{
	Progress		progress;
	CacheCleaner	cacheCleaner;

	public KOToGOLoaderBJ(Progress progress, CacheCleaner cacheCleaner) {
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
			if (!line.startsWith(ParametersDefault.koToGOLoaderComment())) {
				sep = line.split(ParametersDefault.koToGOLoaderSeparator());
				if (sep.length != 2) {
					System.err.println(Messages.koToGOLoaderParsingError(line));
				}
				else {
					ko = createKO(sep[0]);
					sep[1] = sep[1].replaceAll(ParametersDefault.koToGOLoaderDeleteExpression(), "");
					sep = sep[1].split(" ");
					for (String goId : sep) {
						ko.link2Go(createGO(goId));
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

	protected GO createGO(String goId) {
		return new GOBJ(goId);
	}

}

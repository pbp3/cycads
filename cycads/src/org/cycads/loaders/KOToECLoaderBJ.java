/*
 * Created on 15/10/2008
 */
package org.cycads.loaders;

import java.text.ParseException;

import org.cycads.dbExternal.EC;
import org.cycads.dbExternal.ECBJ;
import org.cycads.dbExternal.KO;
import org.cycads.dbExternal.KOBJ;
import org.cycads.exceptions.LoadLineError;
import org.cycads.general.CacheCleaner;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class KOToECLoaderBJ extends FileLoaderLine
{
	public KOToECLoaderBJ(Progress progress, CacheCleaner cacheCleaner) {
		super(progress, cacheCleaner);
	}

	protected KO createKOObject(String koId) {
		return new KOBJ(koId);
	}

	protected EC createECObject(String ecId) {
		return new ECBJ(ecId);
	}

	@Override
	public void loadLine(String line) throws LoadLineError {
		KO ko;
		String[] sep;
		if (!line.startsWith(ParametersDefault.koToECLoaderComment())) {
			sep = line.split(ParametersDefault.koToECLoaderSeparator());
			if (sep.length != 2) {
				throw new LoadLineError(line, new ParseException(Messages.koToECLoaderParsingError(line), 0));
			}
			ko = createKOObject(sep[0]);
			sep[1] = sep[1].replaceAll(ParametersDefault.koToECLoaderDeleteExpression(), "");
			sep = sep[1].split(" ");
			for (String ecId : sep) {
				ko.link2EC(createECObject(ecId));
			}
			progress.completeStep();
			cacheCleaner.incCache();
		}
	}

}

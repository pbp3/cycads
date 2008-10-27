/*
 * Created on 15/10/2008
 */
package org.cycads.loaders;

import java.text.ParseException;

import org.cycads.dbExternal.GO;
import org.cycads.dbExternal.GOBJ;
import org.cycads.dbExternal.KO;
import org.cycads.dbExternal.KOBJ;
import org.cycads.exceptions.LoadLineError;
import org.cycads.general.CacheCleaner;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class KOToGOLoaderBJ extends FileLoaderLine
{
	public KOToGOLoaderBJ(Progress progress, CacheCleaner cacheCleaner) {
		super(progress, cacheCleaner);
	}

	protected KO createKOObject(String koId) {
		return new KOBJ(koId);
	}

	protected GO createGOObject(String goId) {
		return new GOBJ(goId);
	}

	@Override
	public void loadLine(String line) throws LoadLineError {
		KO ko;
		String[] sep;
		if (!line.startsWith(ParametersDefault.koToGOLoaderComment())) {
			sep = line.split(ParametersDefault.koToGOLoaderSeparator());
			if (sep.length != 2) {
				throw new LoadLineError(line, new ParseException(Messages.koToGOLoaderParsingError(line), 0));
			}
			ko = createKOObject(sep[0]);
			sep[1] = sep[1].replaceAll(ParametersDefault.koToGOLoaderDeleteExpression(), "");
			sep = sep[1].split(" ");
			for (String goId : sep) {
				ko.link2GO(createGOObject(goId));
			}
			progress.completeStep();
			cacheCleaner.incCache();
		}
	}

}

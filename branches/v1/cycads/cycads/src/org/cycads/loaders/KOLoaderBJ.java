/*
 * Created on 22/10/2008
 */
package org.cycads.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

import org.cycads.dbExternal.COG;
import org.cycads.dbExternal.COGBJ;
import org.cycads.dbExternal.EC;
import org.cycads.dbExternal.ECBJ;
import org.cycads.dbExternal.GO;
import org.cycads.dbExternal.GOBJ;
import org.cycads.dbExternal.KO;
import org.cycads.dbExternal.KOBJ;
import org.cycads.exceptions.LoadLineError;
import org.cycads.general.CacheCleanerController;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.progress.Progress;

public class KOLoaderBJ extends FileLoaderAbstract
{

	public static final String	EC_TAG	= ParametersDefault.koFileECTag();

	public KOLoaderBJ(Progress progress, CacheCleanerController cacheCleaner) {
		super(progress, cacheCleaner);
	}

	@Override
	public void load(BufferedReader br) throws IOException, LoadLineError {
		progress.init();
		String line;
		KO ko = null;
		String data;
		String def;
		String dbLink = null;
		String[] ecs;
		int ecPos;
		int defCounter = 0, ecCounter = 0, goCounter = 0, cogCounter = 0;
		String tag = "", tagLine;
		while ((line = br.readLine()) != null) {
			if (line.length() > 0 && !line.startsWith(ParametersDefault.koFileCommentStr())) {
				if (line.startsWith(ParametersDefault.koFileEndRecordStr())) {
					ko = null;
				}
				else {
					tagLine = line.substring(0, ParametersDefault.koFileDataPos()).trim();
					data = line.substring(ParametersDefault.koFileDataPos()).trim();
					if (tagLine.length() > 0) {
						tag = tagLine;
						//						if (tag.equals(ParametersDefault.koFileDefinitionTag())) {
						//							ko.setDefinition("");
						//						}
					}
					if (tag.equals(ParametersDefault.koFileEntryTag())) {
						if (ko != null) {
							throw new LoadLineError(line, new ParseException(Messages.koLoaderExceptionKONotNull(), 0));
						}
						ko = createKOObject(data.substring(0, 6));
						ko.setDefinition("");
						dbLink = "";
						progress.completeStep();
					}
					else if (tag.equals(ParametersDefault.koFileDBLinksTag())) {
						String[] strs = data.split(ParametersDefault.koFileDBLinkSubTagSeparator());
						if (strs.length == 2) {
							dbLink = strs[0].trim();
							data = strs[1];
						}
						else if (strs.length != 1 || (strs.length == 1 && dbLink.length() == 0)) {
							throw new LoadLineError(line, new ParseException(
								Messages.koLoaderExceptionDBLinkSeparator(), 0));
						}
						if (dbLink.equalsIgnoreCase(ParametersDefault.koFileDBLinkGOTag())) {
							goCounter++;
							String[] gos = data.split(ParametersDefault.koFileDBLinkGOSeparator());
							for (String go : gos) {
								if (go.length() > 0) {
									ko.link2GO(createGOObject(go));
								}
							}
						}
						else if (dbLink.equalsIgnoreCase(ParametersDefault.koFileDBLinkCOGTag())) {
							cogCounter++;
							String[] cogs = data.split(ParametersDefault.koFileDBLinkCOGSeparator());
							for (String cog : cogs) {
								if (cog.length() > 0) {
									ko.link2COG(createCOGObject(cog));
								}
							}
						}
					}
					else {
						if (tag.equals(ParametersDefault.koFileDefinitionTag())) {
							if (ko.getDefinition().length() == 0) {
								defCounter++;
							}
							//remove EC of data string
							ecPos = data.indexOf(EC_TAG);
							if (ecPos >= 0) {
								def = data.substring(0, ecPos);
								data = data.substring(ecPos);
								tag = EC_TAG;
								ecCounter++;
							}
							else {
								def = data;
							}
							if (def.length() > 0) {
								ko.setDefinition((ko.getDefinition() + " " + def).trim());
							}
						}
						if (tag.equals(EC_TAG)) {
							data = data.replaceAll(ParametersDefault.koFileECDeleteExpression(), "");
							ecs = data.split(ParametersDefault.koFileECSeparator());
							for (String ec : ecs) {
								if (ec.length() > 0) {
									ko.link2EC(createECObject(ec));
								}
							}
						}
					}
				}
			}
		}
		Object[] a1 = {progress.getStep(), defCounter, ecCounter, goCounter, cogCounter};
		progress.finish(a1);
	}

	private EC createECObject(String ec) {
		return new ECBJ(ec);
	}

	protected KO createKOObject(String koID) {
		return new KOBJ(koID);
	}

	protected GO createGOObject(String goID) {
		return new GOBJ(goID);
	}

	protected COG createCOGObject(String cogID) {
		return new COGBJ(cogID);
	}

}

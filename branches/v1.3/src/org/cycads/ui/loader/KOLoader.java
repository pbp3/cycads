/*
 * Created on 16/03/2009
 */
package org.cycads.ui.loader;

import java.io.File;
import java.io.IOException;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.parser.FileParserException;
import org.cycads.parser.KOFileParser;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class KOLoader
{

	public static void main(String[] args) {
		File file = Tools.getFileToOpen(args, 0, Config.koLoaderFileName(), Messages.koLoaderChooseFile());
		if (file == null) {
			return;
		}
		EntityFactory factory = EntityFactory.factoryDefault;
		AnnotationMethod methodDBLink = factory.getAnnotationMethod(Config.getKOLoaderMethodDBLinkName());
		AnnotationMethod methodEC = factory.getAnnotationMethod(Config.getKOLoaderECAnnotationMethodName());

		Progress progress = new ProgressPrintInterval(System.out, Messages.koLoaderStepShowInterval());
		KOFileParser parser = new KOFileParser(factory, progress, methodDBLink, methodEC);
		try {
			progress.init(Messages.koLoaderInitMsg(file.getPath()));
			parser.parse(file);
			progress.finish(Messages.koLoaderFinalMsg(progress.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (FileParserException e) {
			e.printStackTrace();
		}
		finally {
			//			((EntityFactorySQL) factory).finish();
		}
	}

}

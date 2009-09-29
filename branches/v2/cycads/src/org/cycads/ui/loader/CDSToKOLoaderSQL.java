/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loader;

import java.io.File;
import java.io.IOException;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.sequence.Organism;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.parser.CDSToKOFileParser;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressCount;
import org.cycads.ui.progress.ProgressPrintInterval;

public class CDSToKOLoaderSQL {
	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToOpen(args, 0, Config.cdsToKOLoaderFileName(), Messages.cdsToKOLoaderChooseFile());
		if (file == null) {
			return;
		}
		Organism< ? , ? , ? , ? , ? , ? > organism = Tools.getOrganism(args, 1, Config.cdsToKOLoaderOrganismNumber(),
			Messages.cdsToKOLoaderChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}

		String methodName = Tools.getString(args, 2, Config.cdsToKOMethodName(),
			Messages.cdsToKOLoaderChooseMethodName());
		if (methodName == null) {
			return;
		}
		AnnotationMethod method = factory.getAnnotationMethod(methodName);

		String cdsDBName = Tools.getString(args, 3, Config.cdsToKOCDSDBName(), Messages.cdsToKOLoaderChooseCDSDBName());
		if (cdsDBName == null) {
			return;
		}

		Progress progress = new ProgressPrintInterval(System.out, Messages.cdsToKOLoaderStepShowInterval());
		Progress errorCount = new ProgressCount();
		try {
			progress.init(Messages.cdsToKOLoaderInitMsg(file.getPath()));
			(new CDSToKOFileParser(factory, progress, method, organism, cdsDBName, errorCount)).parse(file);
			progress.finish(Messages.cdsToKOLoaderFinalMsg(progress.getStep(), errorCount.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			((EntityFactorySQL) factory).finish();
		}
	}

}

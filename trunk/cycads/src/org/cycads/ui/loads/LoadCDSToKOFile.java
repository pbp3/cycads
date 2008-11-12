/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loads;

import java.io.File;
import java.io.IOException;

import org.cycads.entities.biojava.Method;
import org.cycads.entities.biojava.MethodTypeBJ;
import org.cycads.entities.biojava.Organism;
import org.cycads.exceptions.LoadLineError;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.SimpleCacheCleanerController;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.loaders.CDSToKOLoaderBJ;
import org.cycads.ui.Arguments;
import org.cycads.ui.ArgumentsBJ;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class LoadCDSToKOFile
{
	public static void main(String[] args) {
		BioJavaxSession.init();
		Arguments tools = new ArgumentsBJ();
		File file = tools.getFileToOpen(args, 0, ParametersDefault.cdsToKOLoaderFileName(),
			Messages.cdsToKOChooseFile());
		if (file == null) {
			return;
		}

		Organism organism = tools.getOrganism(args, 1, ParametersDefault.cdsToKOLoaderOrganismNumber(),
			Messages.cdsToKOChooseOrganism());
		if (organism == null) {
			return;
		}

		Method method = tools.getMethod(MethodTypeBJ.CDS_TO_KO, args, 2, ParametersDefault.cdsToKOLoaderMethodName(),
			Messages.cdsToKOChooseMethod());
		if (method == null) {
			return;
		}

		Progress progress = new ProgressPrintInterval(System.out, ParametersDefault.cdsToKOLoaderStepShowInterval(),
			Messages.cdsToKOLoaderInitMsg(file.getPath()), Messages.cdsToKOLoaderFinalMsg());
		try {
			(new CDSToKOLoaderBJ(progress, new SimpleCacheCleanerController(ParametersDefault.cdsToKOLoaderStepCache(),
				BioJavaxSession.getCacheCleanerListener()), organism, method)).load(file);
		}
		catch (IOException e) {
			BioJavaxSession.finishWithRollback();
			e.printStackTrace();
		}
		catch (LoadLineError e) {
			BioJavaxSession.finishWithRollback();
			e.printStackTrace();
		}
		BioJavaxSession.finish();
	}

}

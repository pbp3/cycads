/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loads;

import java.io.File;
import java.io.IOException;

import org.cycads.entities.Method;
import org.cycads.entities.MethodTypeBJ;
import org.cycads.entities.Organism;
import org.cycads.exceptions.LoadLineError;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.CacheCleanerBJ;
import org.cycads.loaders.CDSToKOLoaderBJ;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class LoadCDSToKOFile
{
	public static void main(String[] args) {
		BioJavaxSession.init();
		LoadTools tools = new LoadToolsBJ();
		File file = tools.getFile(args, 0, ParametersDefault.cdsToKOLoaderFileName(), Messages.cdsToKOChooseFile());
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

		method.setDescription(ParametersDefault.cdsToKOMethodDescription(method.getName()));

		Progress progress = new ProgressPrintInterval(System.out, ParametersDefault.cdsToKOLoaderStepShowInterval(),
			Messages.cdsToKOLoaderInitMsg(file.getPath()), Messages.cdsToKOLoaderFinalMsg());
		try {
			(new CDSToKOLoaderBJ(progress, new CacheCleanerBJ(ParametersDefault.cdsToKOLoaderStepCache()), organism,
				method)).load(file);
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

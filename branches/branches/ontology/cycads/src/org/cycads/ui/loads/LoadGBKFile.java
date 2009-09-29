/*
 * Created on 09/06/2008
 */
package org.cycads.ui.loads;

import java.io.File;
import java.io.IOException;

import org.cycads.exceptions.LoadLineError;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.SimpleCacheCleanerController;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.loaders.GBKLoaderBJ;
import org.cycads.ui.Arguments;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class LoadGBKFile
{
	public static void main(String[] args) {
		BioJavaxSession.init();
		File file = Arguments.getFileToOpen(args, 0, ParametersDefault.gBKLoaderFileName(), Messages.gBKChooseFile());
		if (file == null) {
			return;
		}

		Progress progress = new ProgressPrintInterval(System.out, ParametersDefault.gBKLoaderStepShowInterval(),
			Messages.gBKLoaderInitMsg(file.getPath()), Messages.gBKLoaderFinalMsg());
		try {
			(new GBKLoaderBJ(progress, new SimpleCacheCleanerController(ParametersDefault.gBKLoaderStepCache(),
				BioJavaxSession.getCacheCleanerListener()))).load(file);
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

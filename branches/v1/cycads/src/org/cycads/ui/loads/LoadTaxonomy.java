/*
 * Created on 02/06/2008
 */
package org.cycads.ui.loads;

import java.io.File;
import java.io.IOException;

import org.cycads.general.CacheCleanerController;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.SimpleCacheCleanerController;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.loaders.TaxonomyLoaderBJ;
import org.cycads.ui.Arguments;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class LoadTaxonomy
{

	// command to run: java LoadTaxonomyBioSQL <nodes.dmp file> <names.dmp file>
	public static void main(String[] args) {
		BioJavaxSession.init();
		File fileNodes = Arguments.getFileToOpen(args, 0, ParametersDefault.taxonomyLoaderNodesFileName(),
			Messages.taxonomyLoaderChooseNodesFile());
		if (fileNodes == null) {
			return;
		}

		File fileNames = Arguments.getFileToOpen(args, 1, ParametersDefault.taxonomyLoaderNamesFileName(),
			Messages.taxonomyLoaderChooseNamesFile());
		if (fileNames == null) {
			return;
		}

		Progress progressNode = new ProgressPrintInterval(System.out,
			ParametersDefault.taxonomyLoaderStepShowNodeInterval(),
			Messages.taxonomyLoaderNodesInitMsg(fileNodes.getPath()), Messages.taxonomyLoaderNodesFinalMsg());
		Progress progressName = new ProgressPrintInterval(System.out,
			ParametersDefault.taxonomyLoaderStepShowNameInterval(),
			Messages.taxonomyLoaderNamesInitMsg(fileNames.getPath()), Messages.taxonomyLoaderNamesFinalMsg());
		CacheCleanerController cacheCleaner = new SimpleCacheCleanerController(
			ParametersDefault.taxonomyLoaderStepCache(), BioJavaxSession.getCacheCleanerListener());

		try {
			(new TaxonomyLoaderBJ(progressNode, progressName, cacheCleaner)).load(fileNodes, fileNames);
		}
		catch (IOException e) {
			BioJavaxSession.finishWithRollback();
			e.printStackTrace();
		}
		BioJavaxSession.finish();
	}
}

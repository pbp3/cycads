/*
 * Created on 02/06/2008
 */
package org.cycads.ui.loads;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.CacheCleaner;
import org.cycads.loaders.TaxonomyLoaderBJ;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class LoadTaxonomy
{

	// command to run: java LoadTaxonomyBioSQL <nodes.dmp file> <names.dmp file>
	public static void main(String[] args) {
		BufferedReader nodes;
		BufferedReader names;
		String fileNodesName = ParametersDefault.taxonomyLoaderNodesFileName();
		String fileNamesName = ParametersDefault.taxonomyLoaderNamesFileName();
		if (args.length > 1) {
			fileNodesName = args[0];
			fileNamesName = args[1];
		}

		try {
			nodes = new BufferedReader(new FileReader(fileNodesName));
			names = new BufferedReader(new FileReader(fileNamesName));
		}
		catch (FileNotFoundException e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(Messages.taxonomyLoaderChooseNodesFile());
			int returnVal = fc.showOpenDialog(null);
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				System.exit(0);
			}
			try {
				nodes = new BufferedReader(new FileReader(fc.getSelectedFile()));
				fileNodesName = fc.getSelectedFile().getPath();
				fc.setDialogTitle("Choose Names file");
				returnVal = fc.showOpenDialog(null);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					System.exit(0);
				}
				names = new BufferedReader(new FileReader(fc.getSelectedFile()));
				fileNamesName = fc.getSelectedFile().getPath();
			}
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return;
			}
		}

		Progress progressNode = new ProgressPrintInterval(System.out,
			ParametersDefault.taxonomyLoaderStepShowNodeInterval(),
			Messages.taxonomyLoaderNodesInitMsg(fileNodesName), Messages.taxonomyLoaderNodesFinalMsg());
		Progress progressName = new ProgressPrintInterval(System.out,
			ParametersDefault.taxonomyLoaderStepShowNameInterval(),
			Messages.taxonomyLoaderNamesInitMsg(fileNamesName), Messages.taxonomyLoaderNamesFinalMsg());
		CacheCleaner cacheCleaner = new CacheCleaner(ParametersDefault.taxonomyLoaderStepCache());

		try {
			(new TaxonomyLoaderBJ(progressNode, progressName, cacheCleaner)).load(nodes, names);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

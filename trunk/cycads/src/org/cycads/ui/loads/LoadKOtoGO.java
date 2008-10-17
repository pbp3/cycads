/*
 * Created on 09/06/2008
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
import org.cycads.loaders.KOToGOLoaderBJ;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class LoadKOtoGO
{
	public static void main(String[] args) {
		try {
			//file Fasta
			String fileName;
			BufferedReader br;
			if (args.length > 0) {
				fileName = args[0];
				br = new BufferedReader(new FileReader(fileName));
			}
			else {
				try {
					fileName = ParametersDefault.koToGOLoaderFileName();
					br = new BufferedReader(new FileReader(fileName));
				}
				catch (FileNotFoundException e) {
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle(Messages.koToGOChooseFile());
					int returnVal = fc.showOpenDialog(null);
					if (returnVal != JFileChooser.APPROVE_OPTION) {
						return;
					}
					fileName = fc.getSelectedFile().getPath();
					br = new BufferedReader(new FileReader(fileName));
				}
			}

			Progress progress = new ProgressPrintInterval(System.out, ParametersDefault.koToGOLoaderStepShowInterval(),
				Messages.koToGOLoaderInitMsg(fileName), Messages.koToGOLoaderFinalMsg());
			(new KOToGOLoaderBJ(progress, new CacheCleaner(ParametersDefault.koToGOLoaderStepCache()))).load(br);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}

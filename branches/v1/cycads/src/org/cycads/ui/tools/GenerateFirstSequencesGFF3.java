/*
 * Created on 05/01/2009
 */
package org.cycads.ui.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.sequence.Organism;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.parser.gff3.FirstSequencesGFF3Handler;
import org.cycads.parser.gff3.GFF3Parser;
import org.cycads.parser.gff3.GeneralGFF3Handler;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class GenerateFirstSequencesGFF3
{

	public static void main(String[] args) {
		File file = Tools.getFileToOpen(args, 0, Config.gff3LoaderFileName(), Messages.gff3LoaderChooseFile());
		if (file == null) {
			return;
		}
		int i = Tools.getInteger(args, 1, 10 , "Quantity of sequences");
		File fileOut = Tools.getFileToSave(args, 2, Config.gff3LoaderFileName()+"."+i, "Choose the file to save");
			try {
				(new GFF3Parser()).parse(file, new FirstSequencesGFF3Handler(new PrintStream(fileOut),i));
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}

}

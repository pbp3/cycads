/*
 * Created on 05/01/2009
 */
package org.cycads.ui.loader;

import java.io.File;
import java.io.IOException;

import org.cycads.entities.EntityFactorySQL;
import org.cycads.general.Messages;
import org.cycads.parser.gbk.GBKFileConfig;
import org.cycads.parser.gbk.GBKFileParser;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class GBKLoaderSQL {

	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToOpen(args, 0, GBKFileConfig.gbkLoaderFileName(), Messages.gbkLoaderChooseFile());
		if (file == null) {
			return;
		}
		String seqDBName = Tools.getString(args, 1, Messages.gbkLoaderChooseSeqDBName(),
			GBKFileConfig.gbkLoaderSeqDBName());
		if (seqDBName == null) {
			return;
		}
		Progress progress = new ProgressPrintInterval(System.out, Messages.gbkLoaderStepShowInterval());
		try {
			progress.init(Messages.gbkLoaderInitMsg(file.getPath()));
			(new GBKFileParser(factory, seqDBName, progress)).parse(file);
			progress.finish(Messages.gbkLoaderFinalMsg(progress.getStep()));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}

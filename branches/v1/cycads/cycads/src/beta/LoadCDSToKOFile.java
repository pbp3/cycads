/*
 * Created on 09/06/2008
 */
package beta;

import java.io.File;
import java.io.IOException;

import org.cycads.exceptions.LoadLineError;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.ui.Arguments;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class LoadCDSToKOFile
{
	public static void main(String[] args) {
		File file = Arguments.getFileToOpen(args, 0, "", Messages.cdsToKOChooseFile());
		if (file == null) {
			return;
		}

		int method = Arguments.getInteger(args, 1, "Method number:", 0);
		if (method == 0) {
			return;
		}

		Progress progress = new ProgressPrintInterval(System.out, ParametersDefault.cdsToKOLoaderStepShowInterval(),
			Messages.cdsToKOLoaderInitMsg(file.getPath()), Messages.cdsToKOLoaderFinalMsg());
		try {
			(new CDSToKOLoader(progress, method)).load(file);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (LoadLineError e) {
			e.printStackTrace();
		}
	}

}

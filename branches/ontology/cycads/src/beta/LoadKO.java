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

public class LoadKO
{
	public static void main(String[] args) {
		File file = Arguments.getFileToOpen(args, 0, ParametersDefault.koLoaderFileName(),
			Messages.koLoaderChooseFile());
		if (file == null) {
			return;
		}

		Progress progress = new ProgressPrintInterval(System.out, ParametersDefault.koLoaderStepShowInterval(),
			Messages.koLoaderInitMsg(file.getPath()), Messages.koLoaderFinalMsg());
		try {

			(new KOLoader(progress)).load(file);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (LoadLineError e) {
			e.printStackTrace();
		}
	}

}

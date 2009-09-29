/*
 * Created on 21/10/2008
 */
package beta;

import java.io.BufferedReader;
import java.io.IOException;

import org.cycads.exceptions.LoadLineError;
import org.cycads.ui.progress.Progress;

public abstract class FileLoaderLine extends FileLoaderAbstract {

	public FileLoaderLine(Progress progress) {
		super(progress);
	}

	@Override
	public void load(BufferedReader br) throws IOException, LoadLineError {
		progress.init();
		String line;
		while ((line = br.readLine()) != null) {
			try {
				loadLine(line);
			}
			catch (RuntimeException e) {
				throw new LoadLineError(line, e);
			}
		}
		Object[] a1 = {progress.getStep()};
		progress.finish(a1);
	}

	public abstract void loadLine(String line) throws LoadLineError;

}

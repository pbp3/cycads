/*
 * Created on 20/10/2008
 */
package beta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.cycads.exceptions.LoadLineError;
import org.cycads.ui.progress.Progress;

public abstract class FileLoaderAbstract implements FileLoader
{
	Progress	progress;

	public FileLoaderAbstract(Progress progress) {
		this.progress = progress;
	}

	public void load(File f) throws IOException, LoadLineError {
		load(new BufferedReader(new FileReader(f)));

	}

	public void load(String fileName) throws IOException, LoadLineError {
		load(new BufferedReader(new FileReader(fileName)));

	}

	public abstract void load(BufferedReader br) throws IOException, LoadLineError;

}

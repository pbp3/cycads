/*
 * Created on 15/10/2008
 */
package beta;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.cycads.exceptions.LoadLineError;

public interface FileLoader
{
	public void load(BufferedReader br) throws IOException, LoadLineError;

	public void load(File f) throws IOException, LoadLineError;

	public void load(String fileName) throws IOException, LoadLineError;
}

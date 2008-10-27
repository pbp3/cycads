/*
 * Created on 20/10/2008
 */
package org.cycads.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.cycads.exceptions.LoadLineError;
import org.cycads.general.CacheCleaner;
import org.cycads.ui.progress.Progress;

public abstract class FileLoaderAbstract implements FileLoader
{
	Progress		progress;
	CacheCleaner	cacheCleaner;

	public FileLoaderAbstract(Progress progress, CacheCleaner cacheCleaner) {
		this.progress = progress;
		this.cacheCleaner = cacheCleaner;
	}

	public void load(File f) throws IOException, LoadLineError {
		load(new BufferedReader(new FileReader(f)));

	}

	public void load(String fileName) throws IOException, LoadLineError {
		load(new BufferedReader(new FileReader(fileName)));

	}

	public abstract void load(BufferedReader br) throws IOException, LoadLineError;

}

/*
 * Created on 15/10/2008
 */
package org.cycads.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.biojava.bio.seq.io.ParseException;
import org.biojavax.bio.taxa.io.NCBITaxonomyLoader;
import org.biojavax.bio.taxa.io.SimpleNCBITaxonomyLoader;
import org.cycads.general.CacheCleanerController;
import org.cycads.ui.progress.Progress;

public class TaxonomyLoaderBJ implements TaxonomyLoader
{
	Progress		progressNode, progressName;
	CacheCleanerController	cacheCleaner;

	public TaxonomyLoaderBJ(Progress progressNode, Progress progressName, CacheCleanerController cacheCleaner) {
		this.progressNode = progressNode;
		this.progressName = progressName;
		this.cacheCleaner = cacheCleaner;
	}

	public void load(BufferedReader nodes, BufferedReader names) throws IOException {
		NCBITaxonomyLoader l = new SimpleNCBITaxonomyLoader();
		progressNode.init();
		try {
			while ((l.readNode(nodes)) != null) {
				progressNode.completeStep();
				cacheCleaner.incCache();
			}
			Object[] a = {progressNode.getStep()};
			progressNode.finish(a);

			progressName.init();
			while ((l.readName(names)) != null) {
				progressName.completeStep();
				cacheCleaner.incCache();
			}
		}
		catch (ParseException e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
		finally {
			Object[] a1 = {progressName.getStep()};
			progressName.finish(a1);
		}
	}

	public void load(File nodesFile, File namesFile) throws IOException {
		load(new BufferedReader(new FileReader(nodesFile)), new BufferedReader(new FileReader(nodesFile)));
	}
}

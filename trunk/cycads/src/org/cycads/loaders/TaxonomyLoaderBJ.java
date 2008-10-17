/*
 * Created on 15/10/2008
 */
package org.cycads.loaders;

import java.io.BufferedReader;
import java.io.IOException;

import org.biojava.bio.seq.io.ParseException;
import org.biojavax.bio.taxa.io.NCBITaxonomyLoader;
import org.biojavax.bio.taxa.io.SimpleNCBITaxonomyLoader;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.general.biojava.CacheCleaner;
import org.cycads.ui.progress.Progress;

public class TaxonomyLoaderBJ implements TaxonomyLoader
{
	Progress		progressNode, progressName;
	CacheCleaner	cacheCleaner;

	public TaxonomyLoaderBJ(Progress progressNode, Progress progressName, CacheCleaner cacheCleaner) {
		this.progressNode = progressNode;
		this.progressName = progressName;
		this.cacheCleaner = cacheCleaner;
	}

	public void load(BufferedReader nodes, BufferedReader names) throws IOException {
		BioJavaxSession.init();
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
			BioJavaxSession.finish();
		}
		catch (ParseException e) {
			e.printStackTrace();
			BioJavaxSession.finishWithRollback();
			throw new IOException(e.getMessage());
		}
		finally {
			Object[] a1 = {progressName.getStep()};
			progressName.finish(a1);
		}
	}
}

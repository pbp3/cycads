/*
 * Created on 31/10/2008
 */
package org.cycads.generators;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.cycads.entities.Organism;
import org.cycads.entities.Sequence;
import org.cycads.entities.SequenceFeature;
import org.cycads.general.CacheCleanerController;
import org.cycads.ui.progress.Progress;

public class SimpleBioCycExporter implements BioCycExporter
{

	Progress				progress;
	CacheCleanerController	cacheCleaner;
	BioCycRecordFactory		recordFactory;

	public SimpleBioCycExporter(Progress progress, CacheCleanerController cacheCleaner,
			BioCycRecordFactory recordFactory) {
		this.progress = progress;
		this.cacheCleaner = cacheCleaner;
		this.recordFactory = recordFactory;
	}

	/* (non-Javadoc)
	 * @see org.cycads.generators.BioCycExporter#export(org.cycads.entities.Organism, int, org.cycads.generators.FeatureFilter, org.cycads.generators.BioCycStream)
	 */
	public void export(Organism org, int version, FeatureFilter featureFilter, BioCycStream stream) {
		progress.init();
		Hashtable<String, Integer> counters = new Hashtable<String, Integer>();

		Collection<Sequence> seqs = org.getSequences(version);
		for (Sequence seq : seqs) {
			Collection<SequenceFeature> features = seq.getFeatures(featureFilter);
			for (SequenceFeature feature : features) {
				BioCycRecord record = recordFactory.createRecord(feature);
				if (record != null) {
					try {
						stream.write(record);
					}
					catch (GeneRecordInvalidException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
					progress.completeStep();
					incCounter(record, counters);
				}
			}
			cacheCleaner.incCache();
		}
		Iterator<Map.Entry<String, Integer>> it = counters.entrySet().iterator();
		String[] a = new String[counters.size() * 2 + 1];
		a[0] = new Integer(progress.getStep()).toString();
		int i = 1;
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = it.next();
			a[++i] = entry.getKey();
			a[++i] = entry.getValue().toString();
		}
		progress.finish(a);
	}

	private void incCounter(BioCycRecord record, Hashtable<String, Integer> counters) {
		String typeDescriptor = record.getTypeDescriptor();
		Integer counter = counters.get(record);
		if (counter == null) {
			counter = 1;
		}
		else {
			counter++;
		}
		counters.put(typeDescriptor, counter);
	}
}

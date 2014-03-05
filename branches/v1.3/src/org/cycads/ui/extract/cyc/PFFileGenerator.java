/*
 * Created on 05/01/2009
 */
package org.cycads.ui.extract.cyc;

import java.io.File;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.Feature;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.extract.cyc.CycIdGenerator;
import org.cycads.extract.cyc.CycRecord;
import org.cycads.extract.cyc.CycRecordGenerator;
import org.cycads.extract.cyc.OrganismCycIdGenerator;
import org.cycads.extract.cyc.PFFileCycRecordGenerator;
import org.cycads.extract.general.AnnotationClustersGetterRepository;
import org.cycads.extract.general.ConfigAnnotationClustersGetterRepository;
import org.cycads.extract.stream.PFFileStream;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class PFFileGenerator
{

	static List<Pattern>	typesPatterns	= Config.getAnnotationGeneratorFeaturesToGenerate();

	public static void main(String[] args) {
		EntityFactory factory = EntityFactory.factoryDefault;
		File file = Tools.getFileToSave(args, 0, Config.annotationGeneratorFileName(), Messages.pfGeneratorChooseFile());
		if (file == null) {
			return;
		}
		Organism organism = Tools.getOrganism(args, 1, Config.annotationGeneratorOrganismNumber(),
			Messages.pfGeneratorChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}
		String seqSynonym = Tools.getString(args, 2, Config.annotationGeneratorSeqSynonym(),
			Messages.pfGeneratorChooseSeqSynonym());
		String seqDbname = null, seqAccession = null;
		while (seqSynonym != null && !seqSynonym.equals("*") && seqDbname == null && seqAccession == null) {
			String[] strs = seqSynonym.split(":");
			if (strs.length == 2) {
				seqDbname = strs[0];
				seqAccession = strs[1];
			}
			else {
				if (args.length > 2) {
					throw new RuntimeException("Sequence synonym error: " + seqSynonym);
				}
				else {
					seqSynonym = Tools.getString(args, 2, seqSynonym, Messages.pfGeneratorChooseSeqSynonym());
				}
			}
		}
		if (seqSynonym == null) {
			return;
		}

		String seqVersion = Tools.getString(args, 3, Config.annotationGeneratorSeqVersion(),
			Messages.pfGeneratorChooseSeqVersion());
		if (seqVersion == null) {
			return;
		}

		boolean sequenceLocation = Tools.getBoolean(args, 4, Messages.pfGeneratorChooseSequenceLocation());

		Double ecThreshold = Tools.getDouble(args, 5, Config.annotationGeneratorEcThreshold(),
			Messages.pfGeneratorChooseEcThreshold());
		if (ecThreshold == null) {
			return;
		}

		Double goThreshold = Tools.getDouble(args, 6, Config.annotationGeneratorGoThreshold(),
			Messages.pfGeneratorChooseGoThreshold());
		if (goThreshold == null) {
			return;
		}
		Double phygoThreshold = Tools.getDouble(args, 7, Config.annotationGeneratorPhyGoThreshold(),
			Messages.pfGeneratorChooseGoThreshold());
		if (phygoThreshold == null) {
			return;
		}
			
		Progress progress = new ProgressPrintInterval(System.out, Messages.pfGeneratorStepShowInterval());
		try {
			progress.init(Messages.pfGeneratorInitMsg(file.getPath()));
			Collection<Sequence< ? , ? >> seqs;
			if (seqDbname == null) {
				seqs = organism.getSequences(seqVersion);
			}
			else {
				seqs = organism.getSequences(factory.getDbxref(seqDbname, seqAccession));
			}

			PFFileStream pfFile = new PFFileStream(file, Config.annotationGeneratorPfFileHeader(), sequenceLocation);
			CycIdGenerator cycIdGenerator = new OrganismCycIdGenerator(organism);

			AnnotationClustersGetterRepository repository = new ConfigAnnotationClustersGetterRepository();

			CycRecordGenerator cycRecordGenerator = new PFFileCycRecordGenerator(cycIdGenerator, repository,
				ecThreshold, goThreshold, phygoThreshold);
			for (Sequence seq : seqs) {
				Collection<Subsequence> subseqs = seq.getSubsequences();
				for (Subsequence subseq : subseqs) {
					Collection<Annotation<Subsequence, Feature>> annots = (Collection<Annotation<Subsequence, Feature>>) subseq.getAnnotationsByType(
						factory.getType(Feature.ENTITY_TYPE_NAME), null, null);
					for (Annotation<Subsequence, Feature> annot : annots) {
						String featureName = annot.getTarget().getName();
						if (featureIsValid(featureName)) {
							CycRecord record = cycRecordGenerator.generate(annot);
							if (record != null) {
								pfFile.print(record);
								progress.completeStep();
							}
						}
					}
				}
			}
			progress.finish(Messages.pfGeneratorFinalMsg(progress.getStep()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	static Hashtable<String, Boolean>	features	= new Hashtable<String, Boolean>();

	private static boolean featureIsValid(String featureName) {
		Boolean ret = features.get(featureName);
		if (ret == null) {
			for (Pattern typePattern : typesPatterns) {
				if (typePattern.matcher(featureName).matches()) {
					ret = true;
				}
			}
			if (ret == null) {
				ret = false;
			}
			features.put(featureName, ret);
		}
		return ret;
	}
}

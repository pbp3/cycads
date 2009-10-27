/*
 * Created on 05/01/2009
 */
package org.cycads.ui.extract.cyc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.factory.EntityFactorySQL;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.extract.cyc.CycDBLink;
import org.cycads.extract.cyc.CycDbxrefAnnotationPaths;
import org.cycads.extract.cyc.CycIdGenerator;
import org.cycads.extract.cyc.CycRecord;
import org.cycads.extract.cyc.CycRecordGenerator;
import org.cycads.extract.cyc.LocContainer;
import org.cycads.extract.cyc.OrganismCycIdGenerator;
import org.cycads.extract.cyc.PFFileConfig;
import org.cycads.extract.cyc.PFFileCycRecordGenerator;
import org.cycads.extract.cyc.ScoreSystemCollection;
import org.cycads.extract.cyc.ScoreSystemsContainer;
import org.cycads.extract.cyc.SimpleLocInterpreter;
import org.cycads.general.Config;
import org.cycads.general.Messages;
import org.cycads.ui.Tools;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class ECCDSFileGeneratorSQL
{
	static char					columnSeparator		= Config.ecCDSFileGeneratorColumnSeparator();
	static char					dbLinkSeparator		= Config.ecCDSFileGeneratorDBLinkSeparator();
	static String				dbLinkNotPresent	= Config.ecCDSFileGeneratorAtributeNotPresentStr();
	static Collection<String>	dbNames				= Config.ecCDSFileGeneratorDBNames();

	public static void main(String[] args) {
		EntityFactorySQL factory = new EntityFactorySQL();
		File file = Tools.getFileToSave(args, 0, Config.ecCDSFileGeneratorFileName(),
			Messages.ecCDSFileGeneratorChooseFile());
		if (file == null) {
			return;
		}
		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream(file, false));
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}
		printHeader(out);

		Organism organism = Tools.getOrganism(args, 1, Config.ecCDSFileGeneratorOrganismNumber(),
			Messages.ecCDSFileGeneratorChooseOrganismNumber(), factory);
		if (organism == null) {
			return;
		}
		String seqVersion = Tools.getString(args, 2, Config.ecCDSFileGeneratorSeqVersion(),
			Messages.ecCDSFileGeneratorChooseSeqVersion());
		if (seqVersion == null) {
			return;
		}

		Progress progress = new ProgressPrintInterval(System.out, Messages.ecCDSFileGeneratorStepShowInterval());
		progress.init(Messages.ecCDSFileGeneratorInitMsg(file.getPath()));
		Collection<Sequence< ? , ? , ? , ? , ? , ? >> seqs = organism.getSequences(seqVersion);
		Collection<Type> types = new ArrayList<Type>(1);
		types.add(factory.getAnnotationTypeCDS());
		CycIdGenerator cycIdGenerator = new OrganismCycIdGenerator(organism);

		LocAndScores locAndScores = new LocAndScores();
		ScoreSystemCollection ecScoreSystemCollection = locAndScores.getScoreSystems("ec");
		ScoreSystemCollection goScoreSystemCollection = locAndScores.getScoreSystems("go");

		SimpleLocInterpreter locInterpreter = new SimpleLocInterpreter(locAndScores, locAndScores);
		CycRecordGenerator cycRecordGenerator = new PFFileCycRecordGenerator(cycIdGenerator, locInterpreter, 0,
			ecScoreSystemCollection, 0, goScoreSystemCollection);
		for (Sequence seq : seqs) {
			Collection<SubseqAnnotation< ? , ? , ? , ? , ? >> cdss = seq.getAnnotationsByType(null, types, null);
			for (SubseqAnnotation< ? , ? , ? , ? , ? > cds : cdss) {
				CycRecord record = cycRecordGenerator.generate(cds);
				if (record != null) {
					Collection<CycDbxrefAnnotationPaths> ecs = locInterpreter.getCycDbxrefPathAnnots(cds,
						PFFileConfig.getPFFileECLocs(), ecScoreSystemCollection);
					for (CycDbxrefAnnotationPaths ec : ecs) {
						print(ec, record, out);
						progress.completeStep();
					}
				}
			}
		}
		progress.finish(Messages.ecCDSFileGeneratorFinalMsg(progress.getStep()));

	}

	private static void printHeader(PrintStream out) {
		out.print("EC");
		out.print(columnSeparator);
		out.print("EC Score");
		out.print(columnSeparator);
		out.print("EC Methods Annotation");
		for (String dbName : dbNames) {
			out.print(columnSeparator);
			out.print(dbName);
		}
		out.println();
	}

	protected static void print(CycDbxrefAnnotationPaths ec, CycRecord record, PrintStream out) {
		StringBuffer line = new StringBuffer(ec.getAccession());
		line.append(columnSeparator);
		line.append(ec.getScore());
		line.append(columnSeparator);
		line.append(Config.ecCDSFileGeneratorMethods(ec));
		for (String dbName : dbNames) {
			line.append(columnSeparator);
			boolean found = false;
			Collection<CycDBLink> dbLinks = record.getDBLinks();
			for (CycDBLink dbLink : dbLinks) {
				if (dbLink.getDbName().equals(dbName)) {
					if (found) {
						line.append(dbLinkSeparator);
					}
					found = true;
					line.append(dbLink.getAccession());
				}
			}
			if (!found) {
				line.append(dbLinkNotPresent);
			}
		}
		out.println(line);
	}

	public static class LocAndScores implements ScoreSystemsContainer, LocContainer
	{
		@Override
		public ScoreSystemCollection getScoreSystems(String scoreName) {
			return PFFileConfig.getScoreSystems(scoreName);
		}

		@Override
		public List<String> getLocs(String locName) {
			return PFFileConfig.getLocs(locName);
		}
	}

}

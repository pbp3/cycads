/*
 * Created on 12/09/2008
 */
package org.cycads.ui.generators;

import java.io.File;

import org.cycads.entities.MethodTypeBJ;
import org.cycads.entities.Organism;
import org.cycads.general.CacheCleanerController;
import org.cycads.general.CacheCleanerListener;
import org.cycads.general.Messages;
import org.cycads.general.ParametersDefault;
import org.cycads.general.SimpleCacheCleanerController;
import org.cycads.general.biojava.BioJavaxSession;
import org.cycads.generators.BioCycExporter;
import org.cycads.generators.BioCycRecordFactory;
import org.cycads.generators.BioCycStream;
import org.cycads.generators.ECLinkCreator;
import org.cycads.generators.FeatureFilter;
import org.cycads.generators.FeatureFilterByType;
import org.cycads.generators.MethodFilter;
import org.cycads.generators.PFFileStream;
import org.cycads.generators.SimpleBioCycExporter;
import org.cycads.generators.SimpleBioCycRecordFactory;
import org.cycads.ui.Arguments;
import org.cycads.ui.ArgumentsBJ;
import org.cycads.ui.progress.Progress;
import org.cycads.ui.progress.ProgressPrintInterval;

public class PFFile
{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BioJavaxSession.init();
		Arguments arguments = new ArgumentsBJ();

		Organism organism = arguments.getOrganism(args, 0, ParametersDefault.pfFileGeneratorOrganismNumber(),
			Messages.pfFileGeneratorChooseOrganism());
		if (organism == null) {
			return;
		}

		Integer version = arguments.getInteger(args, 1, Messages.pfFileGeneratorChooseVersion(),
			ParametersDefault.pfFileGeneratorVersionNumber());
		if (version == null) {
			return;
		}

		boolean pfForSequence = arguments.getBoolean(args, 2, Messages.pfFileGeneratorForEachSequence());

		boolean createFastaFile = arguments.getBoolean(args, 3, Messages.pfFileGeneratorGenerateFastaFile());

		File directory = arguments.getDirectoryToSave(args, 4, ParametersDefault.pfFileGeneratorPfDirectoryName(),
			Messages.pfFileGeneratorChooseDirectory());

		FeatureFilter featureFilter = new FeatureFilterByType(ParametersDefault.pfFileGeneratorFeatureFilter());

		MethodFilter koFilter = new ThresholdMethodFilter(MethodTypeBJ.CDS_TO_KO.getMethods(), 0);
		MethodFilter ecFilter = new ThresholdMethodFilter(MethodTypeBJ.CDS_TO_EC.getMethods(), 0);
		//		MethodFilter methodFilter = new UnionMethodFilter(koFilter, ecFilter);

		ECLinkCreator directECCreator = new DirectECLinkCreator(ecFilter);
		ECLinkCreator koECCreator = new ECLinkCreatorByKO(koFilter);
		ECLinkCreator[] ecCreators = {directECCreator, koECCreator};
		ECLinkCreator ecCreator = new MediaECLinkCreator(0, ecCreators);

		FunctionCreator productFunctionCreator = new FunctionCreatorByNote(
			ParametersDefault.pfFileGeneratorFunctionCreatorNote());
		FunctionCreator koFunctionCreator = new FunctionCreatorByKO(koFilter);
		FunctionCreator[] functionCreators = {productFunctionCreator, koFunctionCreator}; //verificar funções repetidas ou contidas
		FunctionCreator functionCreator = new UnionFunctionCreator(functionCreators);

		DBLinkCreator crossRefDBLinkCreator = new DBLinkCreatorCrossRef();
		DBLinkCreator sequenceDBLinkCreator = new DBLinkCreatorSeqAcession();
		DBLinkCreator koDBLinkCreator = new DBLinkCreatorKO(koFilter);
		DBLinkCreator[] dbLinkCreators = {crossRefDBLinkCreator, sequenceDBLinkCreator, koDBLinkCreator};
		DBLinkCreator dbLinkCreator = new UnionDBLinkCreator(dbLinkCreators);

		BioCycIdRepository bioCycIdFileRepository = new SimpleBioCycIdFileRepository(directory,
			ParametersDefault.pfFileGeneratorBioCycIdFileName(pfForSequence));
		BioCycIDGenerator bioCycIdGenerator = new BioCycIdGeneratorBJ(ParametersDefault.bioCycIdRegexGenerator(),
			ParametersDefault.bioCycIdTag());

		BioCycRecordFactory bioCycRecordFactory = new SimpleBioCycRecordFactory(ecCreator, functionCreator,
			dbLinkCreator, bioCycIdGenerator);

		boolean createBioCycIdFile = true;

		BioCycStream pfFileStream = new PFFileStream(directory, pfForSequence, createFastaFile, bioCycIdFileRepository,
			ParametersDefault.pfFileGeneratorPfName(pfForSequence));

		//		PFFileFactory pfFactory = new SimplePFFileFactory(directory,
		//			ParametersDefault.pfFileGeneratorPfDirectoryName(pfForSequence), pfForSequence);
		//
		//		FastaFileFactory fastaFactory;
		//
		//		if (arguments.getBoolean(args, 4, Messages.pfFileGeneratorGenerateFastaFile())) {
		//			fastaFactory = new SimpleFastaFileFactory(directory,
		//				ParametersDefault.pfFileGeneratorFastaFileName(pfForSequence),
		//				ParametersDefault.fastaFileForPFFileLineWidth(), pfForSequence);
		//		}
		//		else {
		//			fastaFactory = new DummyFastaFileFactory();
		//		}

		Progress progress = new ProgressPrintInterval(System.out, ParametersDefault.pfFileGeneratorStepShowInterval(),
			Messages.pfFileGeneratorInitMsg(directory.getPath()), Messages.pfFileGeneratorFinalMsg());

		//Biojava needs to clean the cache of BioCycGenerator also

		CacheCleanerListener[] listeners = {BioJavaxSession.getCacheCleanerListener(),
			BioJavaxSession.getBioCycIdGeneratorListener()};
		CacheCleanerController cacheControl = new SimpleCacheCleanerController(
			ParametersDefault.pfFileGeneratorStepCache(), listeners);

		BioCycExporter exporter = new SimpleBioCycExporter(progress, cacheControl, bioCycRecordFactory);
		exporter.export(organism, version, featureFilter, pfFileStream);

		BioJavaxSession.finish();
	}
	//		PFFileStream pfFile;
	//		FastaFileForPFFile fastaFile = null;
	//		BioCycIdsFileForPFFile bioCycIdsFile;
	//
	//		File file = arguments.getFileToSave(args, 2,
	//			ParametersDefault.pfFileGeneratorPfDirectoryName(organism.getNumberID()),
	//			Messages.pfFileGeneratorChoosePfFile());
	//		if (file == null) {
	//			return;
	//		}
	//		try {
	//			pfFile = new PFFileStream(file, Messages.PFFileHeader());
	//
	//			if (arguments.getBoolean(args, 3, Messages.pfFileGeneratorGenerateFastaFile())) {
	//				fastaFile = new FastaFileForPFFile(new File(ParametersDefault.pfFileGeneratorFastaFileName(file)),
	//					Messages.fastaFileForPFFileHeader(), ParametersDefault.fastaFileForPFFileLineWidth());
	//			}
	//
	//			bioCycIdsFile = new BioCycIdsFileForPFFile(new File(
	//				ParametersDefault.pfFileGeneratorBioCycIdsFileName(file)), Messages.bioCycIdsFileForPFFileHeader());
	//		}
	//		catch (FileNotFoundException e) {
	//			BioJavaxSession.finishWithRollback();
	//			e.printStackTrace();
	//			return;
	//		}
	//
	//		BioCycIdBJ bioCycId = new BioCycIdBJ();
	//
	//		Progress progress = new ProgressPrintInterval(System.out, ParametersDefault.pfFileGeneratorStepShowInterval(),
	//			Messages.pfFileGeneratorInitMsg(file.getPath()), Messages.pfFileGeneratorFinalMsg());
	//
	//		CacheCleanerListener[] listeners = {BioJavaxSession.getCacheCleanerListener(), bioCycId, fastaFile};
	//		CacheCleanerController cacheControl = new SimpleCacheCleanerController(
	//			ParametersDefault.pfFileGeneratorStepCache(), listeners);
	//
	//		(new PFFileGeneratorBJ(progress, cacheControl)).generate(organism, version, pfFile, fastaFile, bioCycIdsFile);
	//
	//		BioJavaxSession.finish();
	//	}
	//		Transaction tx = BioSql.beginTransaction();
	//		try {
	//			String fileName;
	//			if (args.length > 0) {
	//				fileName = args[0];
	//			}
	//			else {
	//				JFileChooser fc = new JFileChooser();
	//				fc.setDialogTitle("Choose file name to save");
	//				int returnVal = fc.showSaveDialog(null);
	//				if (returnVal != JFileChooser.APPROVE_OPTION) {
	//					return;
	//				}
	//				fileName = fc.getSelectedFile().getPath();
	//			}
	//
	//			// the ncbiTaxon of organism
	//			Organism organism;
	//			String respDialog;
	//			if (args.length < 2) {
	//				respDialog = JOptionPane.showInputDialog("Please input the NCBI_Taxon_ID:",
	//					Messages.getString("ncbiTaxonNumberDefault"));
	//				if (respDialog == null) {
	//					return;
	//				}
	//				int ncbiTaxonNumber = Integer.parseInt(respDialog);
	//				try {
	//					organism = new Organism(ncbiTaxonNumber);
	//				}
	//				catch (DBObjectNotFound ex) {
	//					organism = null;
	//				}
	//				while (organism == null) {
	//					respDialog = JOptionPane.showInputDialog("NCBITaxonID not found. Please input the NCBI_Taxon_ID:",
	//						ncbiTaxonNumber);
	//					if (respDialog == null) {
	//						return;
	//					}
	//					ncbiTaxonNumber = Integer.parseInt(respDialog);
	//					try {
	//						organism = new Organism(ncbiTaxonNumber);
	//					}
	//					catch (DBObjectNotFound ex) {
	//					}
	//				}
	//			}
	//			else {
	//				organism = new Organism(Integer.parseInt(args[1]));
	//			}
	//
	//			//Version
	//			int version;
	//			if (args.length < 3) {
	//				respDialog = JOptionPane.showInputDialog("Please input the version of sequences:");
	//				if (respDialog == null) {
	//					return;
	//				}
	//			}
	//			else {
	//				respDialog = args[2];
	//			}
	//			version = Integer.parseInt(respDialog);
	//
	//			int stepShow = Integer.parseInt(Messages.getString("PFFile.stepShow"));
	//			int stepRestart = Integer.parseInt(Messages.getString("PFFile.stepRestart"));
	//			//			Collection<Integer> seqs = BioSql.getSequencesIdCDStRNAmRNA(organism, version);
	//			PFFileStream pfFile = new PFFileStream(fileName);
	//			pfFile.println(Messages.getString("PFFile.heading"));
	//			String fileFastaName = fileName + ".fsa";
	//			FastaFileForPFFile fastaFile = new FastaFileForPFFile(fileFastaName,
	//				Messages.getString("FastaFileForPFFile.header"),
	//				Integer.parseInt(Messages.getString("FastaFileForPFFile.lineWidth")));
	//			String fileIdsName = fileName + ".BioCycIds";
	//			PrintStream ids;
	//			ids = new PrintStream(new File(fileIdsName));
	//
	//			System.out.print("Start time:");
	//			System.out.println(new Date());
	//			Progress progress = new ProgressPrintInterval(System.out, stepShow,
	//				Messages.getString("PFFile.initialMessage") + fileName);
	//			progress.init();
	//			int cdsCounter = 0, tRNACounter = 0, miscRNACounter = 0, allCounter = 0;
	//			int pos = 0;
	//
	//			Collection<Integer> featureIds;
	//			Collection<Integer> seqIds = BioSql.getSequencesIdCDStRNAmRNA(organism, version);
	//			GeneRecord record;
	//
	//			for (Integer seqId : seqIds) {
	//				featureIds = BioSql.getCDSMiscRNATRNABySeqId(seqId);
	//
	//				for (Integer featureId : featureIds) {
	//					try {
	//						RichFeature feature = BioSql.getFeature(featureId);
	//						Sequence seq = feature.getSequence();
	//						if (feature.getTypeTerm() == TermsAndOntologies.getTermCDS()) {
	//							record = new CDSRecord(feature);
	//							cdsCounter++;
	//						}
	//						else if (feature.getTypeTerm() == TermsAndOntologies.getTermMiscRNA()) {
	//							record = new MiscRNARecord(feature);
	//							miscRNACounter++;
	//						}
	//						else {
	//							record = new TRNARecord(feature);
	//							tRNACounter++;
	//						}
	//						allCounter++;
	//						record.shiftLocation(pos);
	//						pfFile.print(record);
	//
	//						if (record.getProductID() != null) {
	//							ids.println(record.getProductID() + "\t" + record.getId());
	//						}
	//						else {
	//							ids.println(record.getName() + ";" + ((RichSequence) feature.getSequence()).getAccession()
	//								+ "\t" + record.getId());
	//						}
	//						if (allCounter % stepRestart == 0) {
	//							BioSql.restartTransaction();
	//							SimpleGeneRecord.nextBiocycId = TermsAndOntologies.getNextBiocycId();
	//							fastaFile.restart();
	//						}
	//						progress.completeStep();
	//					}
	//					catch (GeneRecordInvalidException e) {
	//						e.printStackTrace();
	//					}
	//					catch (Exception e) {
	//						e.printStackTrace();
	//					}
	//				}
	//				pos += fastaFile.write(seqId);
	//			}
	//
	//			BioSql.endTransactionOK();
	//			fastaFile.flush();
	//			Object[] a = {allCounter, cdsCounter, tRNACounter, miscRNACounter};
	//			progress.finish(MessageFormat.format(Messages.getString("PFFile.finalMessage"), a));
	//			System.out.print("End time:");
	//			System.out.println(new Date());
	//		}
	//		catch (DBObjectNotFound e) {
	//			e.printStackTrace();
	//		}
	//		catch (FileNotFoundException e) {
	//			e.printStackTrace();
	//		}
	//		finally {
	//			BioSql.finish();
	//		}
	//	}
}

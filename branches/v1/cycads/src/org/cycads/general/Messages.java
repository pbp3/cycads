package org.cycads.general;

import java.text.MessageFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages
{
	private static final String			BUNDLE_NAME		= "messages";								//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	private static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getStringOptional(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			return null;
		}
	}

	private static String getStringMandatory(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	// Progress

	public static String getProgressStartTimeMsg() {
		return getProgressStartTimeMsg(new Date());
	}

	public static String getProgressStartTimeMsg(Date date) {
		Object[] a = {date};
		return MessageFormat.format(getString("Progress.startTime"), a);
	}

	public static String getProgressEndTimeMsg() {
		return getProgressEndTimeMsg(new Date());
	}

	public static String getProgressEndTimeMsg(Date date) {
		Object[] a = {date};
		return MessageFormat.format(getString("Progress.endTime"), a);
	}

	public static String getProgressTotalTimeMsg(long time) {
		Object[] a = {time};
		return MessageFormat.format(getString("Progress.totalTime"), a);
	}

	public static String getProgressSeparatorObjects() {
		return getString("Progress.separatorObject");
	}

	public static String getProgressTotalStepsMsg(int totalSteps) {
		Object[] a = {totalSteps};
		return MessageFormat.format(getString("Progress.totalSteps"), a);
	}

	// Exception

	public static String getExceptionFileParserLineMsg(int lineNumber, String line) {
		Object[] a = {lineNumber, line};
		return MessageFormat.format(getStringMandatory("Exception.fileParser.line"), a);
	}

	// KOLOader

	public static String koLoaderChooseFile() {
		return getString("KOLoader.chooseFile");
	}

	public static int koLoaderStepShowInterval() {
		return getInt("KOLoader.stepShowInterval");
	}

	public static String koLoaderFinalMsg(int step) {
		Object[] a = {step};
		return MessageFormat.format(getStringMandatory("KOLoader.final"), a);

	}

	public static String koLoaderInitMsg(String path) {
		Object[] a = {path};
		return MessageFormat.format(getStringMandatory("KOLoader.init"), a);
	}

	// GFF3 Loader

	public static String gff3LoaderChooseFile() {
		return getString("gff3Loader.chooseFile");
	}

	public static String gff3ChooseOrganismNumber() {
		return getString("gff3Loader.chooseOrganismNumber");
	}

	public static String gff3ChooseOrganismName() {
		return getString("gff3Loader.chooseOrganismName");
	}

	public static String gff3LoaderChooseSeqDBName() {
		return getString("gff3Loader.chooseSeqDBName");
	}

	public static String gff3LoaderChooseSeqVersion() {
		return getString("gff3Loader.chooseSeqVersion");
	}

	public static int gff3LoaderStepShowInterval() {
		return getInt("gff3Loader.stepShowInterval");
	}

	public static String gff3LoaderInitMsg(String path) {
		Object[] a = {path};
		return MessageFormat.format(getStringMandatory("gff3Loader.init"), a);
	}

	public static String gff3LoaderFinalMsg(int step) {
		Object[] a = {step};
		return MessageFormat.format(getStringMandatory("gff3Loader.final"), a);
	}

	// pf file generator

	public static String pfGeneratorChooseOrganismNumber() {
		return getString("pfFile.extract.chooseOrganismNumber");
	}

	public static String pfGeneratorChooseFile() {
		return getString("pfFile.extract.chooseFile");
	}

	public static String pfGeneratorChooseSeqSynonym() {
		return getString("pfFile.extract.chooseSeqSynonym");
	}

	public static String pfGeneratorChooseSeqVersion() {
		return getString("pfFile.extract.chooseSeqVersion");
	}

	public static int pfGeneratorStepShowInterval() {
		return getInt("pfFile.extract.stepShowInterval");
	}

	public static String pfGeneratorInitMsg(String path) {
		Object[] a = {path};
		return MessageFormat.format(getStringMandatory("pfFile.extract.init"), a);
	}

	public static String pfGeneratorFinalMsg(int step) {
		Object[] a = {step};
		return MessageFormat.format(getStringMandatory("pfFile.extract.final"), a);
	}

	public static String pfGeneratorChooseEcThreshold() {
		return getString("pfFile.extract.chooseEcThreshold");
	}

	public static String pfGeneratorChooseGoThreshold() {
		return getString("pfFile.extract.chooseGoThreshold");
	}

	//	public static String pfGeneratorChooseKoThreshold() {
	//		return getString("pfFile.extract.chooseKoThreshold");
	//	}
	//
	public static String pfGeneratorChooseSequenceLocation() {
		return getString("pfFile.extract.chooseSequenceLocation");
	}

	// CDS to KO loader

	public static String cdsToKOLoaderChooseFile() {
		return getString("cdsToKOLoader.chooseFile");
	}

	public static String cdsToKOLoaderChooseOrganismNumber() {
		return getString("cdsToKOLoader.chooseOrganismNumber");
	}

	public static String cdsToKOLoaderChooseCDSDBName() {
		return getString("cdsToKOLoader.chooseCDSDBName");
	}

	public static String cdsToKOLoaderChooseMethodName() {
		return getString("cdsToKOLoader.chooseMethodName");
	}

	public static int cdsToKOLoaderStepShowInterval() {
		return getInt("cdsToKOLoader.stepShowInterval");
	}

	public static String cdsToKOLoaderInitMsg(String path) {
		Object[] a = {path};
		return MessageFormat.format(getStringMandatory("cdsToKOLoader.init"), a);
	}

	public static String cdsToKOLoaderFinalMsg(int step, int errorCount) {
		Object[] a = {step, errorCount};
		return MessageFormat.format(getStringMandatory("cdsToKOLoader.final"), a);
	}

	// gbkLoader

	public static String gbkLoaderChooseFile() {
		return getString("gbkLoader.chooseFile");
	}

	public static int gbkLoaderStepShowInterval() {
		return getInt("gbkLoader.stepShowInterval");
	}

	public static String gbkLoaderInitMsg(String path) {
		Object[] a = {path};
		return MessageFormat.format(getStringMandatory("gbkLoader.init"), a);
	}

	public static String gbkLoaderFinalMsg(int step) {
		Object[] a = {step};
		return MessageFormat.format(getStringMandatory("gbkLoader.final"), a);
	}

	public static String gbkLoaderChooseSeqDBName() {
		return getStringMandatory("gbkLoader.chooseSeqDBName");
	}

	// subseqAnnotationLoader

	public static String subseqAnnotationLoaderChooseFile() {
		return getStringMandatory("subseqAnnotationLoader.chooseFile");
	}

	public static String subseqAnnotationLoaderChooseOrganismNumber() {
		return getStringMandatory("subseqAnnotationLoader.chooseOrganismNumber");
	}

	public static String subseqAnnotationLoaderChooseMethodName() {
		return getStringMandatory("subseqAnnotationLoader.chooseMethodName");
	}

	public static String subseqAnnotationLoaderChooseAnnotColumnIndex() {
		return getStringMandatory("subseqAnnotationLoader.chooseAnnotColumnIndex");
	}

	public static String subseqAnnotationLoaderChooseAnnotDBName() {
		return getStringMandatory("subseqAnnotationLoader.chooseAnnotDBName");
	}

	public static String subseqAnnotationLoaderChooseDbxrefColumnIndex() {
		return getStringMandatory("subseqAnnotationLoader.chooseDbxrefColumnIndex");
	}

	public static String subseqAnnotationLoaderChooseDbxrefDBName() {
		return getStringMandatory("subseqAnnotationLoader.chooseDbxrefDBName");
	}

	public static String subseqAnnotationLoaderChooseScoreColumnIndex() {
		return getStringMandatory("subseqAnnotationLoader.chooseScoreColumnIndex");
	}

	public static int subseqAnnotationLoaderStepShowInterval() {
		return getInt("subseqAnnotationLoader.stepShowInterval");
	}

	public static String subseqAnnotationLoaderInitMsg(String path) {
		Object[] a = {path};
		return MessageFormat.format(getStringMandatory("subseqAnnotationLoader.init"), a);
	}

	public static String subseqAnnotationLoaderFinalMsg(int step, int errorStep) {
		Object[] a = {step, errorStep};
		return MessageFormat.format(getStringMandatory("subseqAnnotationLoader.final"), a);
	}

	// synonymLoader

	public static String synonymLoaderChooseFile() {
		return getStringMandatory("synonymLoader.chooseFile");
	}

	public static String synonymLoaderChooseOrganismNumber() {
		return getStringMandatory("synonymLoader.chooseOrganismNumber");
	}

	public static String synonymLoaderChooseAnnotColumnIndex() {
		return getStringMandatory("synonymLoader.chooseAnnotColumnIndex");
	}

	public static String synonymLoaderChooseAnnotDBName() {
		return getStringMandatory("synonymLoader.chooseAnnotDBName");
	}

	public static String synonymLoaderChooseDbxrefColumnIndex() {
		return getStringMandatory("synonymLoader.chooseDbxrefColumnIndex");
	}

	public static String synonymLoaderChooseDbxrefDBName() {
		return getStringMandatory("synonymLoader.chooseDbxrefDBName");
	}

	public static int synonymLoaderStepShowInterval() {
		return getInt("synonymLoader.stepShowInterval");
	}

	public static String synonymLoaderInitMsg(String path) {
		Object[] a = {path};
		return MessageFormat.format(getStringMandatory("synonymLoader.init"), a);
	}

	public static String synonymLoaderFinalMsg(int step, int errorStep) {
		Object[] a = {step, errorStep};
		return MessageFormat.format(getStringMandatory("synonymLoader.final"), a);
	}

	//ecCDS file Generator

	public static String ecCDSFileGeneratorChooseFile() {
		return getString("ecCDSFile.extract.chooseFile");
	}

	public static String ecCDSFileGeneratorChooseOrganismNumber() {
		return getString("ecCDSFile.extract.chooseOrganismNumber");
	}

	public static String ecCDSFileGeneratorChooseSeqVersion() {
		return getString("ecCDSFile.extract.chooseSeqVersion");
	}

	public static int ecCDSFileGeneratorStepShowInterval() {
		return getInt("ecCDSFile.extract.stepShowInterval");
	}

	public static String ecCDSFileGeneratorInitMsg(String path) {
		Object[] a = {path};
		return MessageFormat.format(getStringMandatory("ecCDSFile.extract.init"), a);
	}

	public static String ecCDSFileGeneratorFinalMsg(int step) {
		Object[] a = {step};
		return MessageFormat.format(getStringMandatory("ecCDSFile.extract.final"), a);
	}

	public static String copyException() {
		return getString("operation.exception.copy");
	}

	public static String dbxrefWithoutDBNameException() {
		return getString("dbxref.exception.withoutDBNamee");
	}

	public static String dbxrefWithoutAccessionException() {
		return getString("dbxref.exception.withoutAccession");
	}

}

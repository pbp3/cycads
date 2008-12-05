package org.cycads.general;

import java.text.MessageFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages
{
	private static final String			BUNDLE_NAME		= "messages";								//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages()
	{
	}

	private static String getString(String key)
	{
		try
		{
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static String splitUsage()
	{
		return getString("Split.usage");
	}

	// Progress

	public static String getProgressStartTimeMsg()
	{
		return getProgressStartTimeMsg(new Date());
	}

	public static String getProgressStartTimeMsg(Date date)
	{
		Object[] a = {date};
		return MessageFormat.format(getString("Progress.startTime"), a);
	}

	public static String getProgressEndTimeMsg()
	{
		return getProgressEndTimeMsg(new Date());
	}

	public static String getProgressEndTimeMsg(Date date)
	{
		Object[] a = {date};
		return MessageFormat.format(getString("Progress.endTime"), a);
	}

	public static String getProgressTotalTimeMsg(long time)
	{
		Object[] a = {time};
		return MessageFormat.format(getString("Progress.totalTime"), a);
	}

	public static String getProgressSeparatorObjects()
	{
		return getString("Progress.separatorObject");
	}

	public static String getProgressTotalStepsMsg(int totalSteps)
	{
		Object[] a = {totalSteps};
		return MessageFormat.format(getString("Progress.totalSteps"), a);
	}

	// LoadTaxonomy

	public static String taxonomyLoaderNodesFinalMsg()
	{
		return getString("TaxonomyLoader.nodesFinal");
	}

	public static String taxonomyLoaderNodesFinalMsg(int totalNodes)
	{
		Object[] a = {totalNodes};
		return MessageFormat.format(taxonomyLoaderNodesFinalMsg(), a);
	}

	public static String taxonomyLoaderNamesFinalMsg()
	{
		return getString("TaxonomyLoader.namesFinal");
	}

	public static String taxonomyLoaderNamesFinalMsg(int totalNames)
	{
		Object[] a = {totalNames};
		return MessageFormat.format(taxonomyLoaderNamesFinalMsg(), a);
	}

	public static String taxonomyLoaderNodesInitMsg(String fileNodesName)
	{
		Object[] a = {fileNodesName};
		return MessageFormat.format(getString("TaxonomyLoader.nodesInit"), a);
	}

	public static String taxonomyLoaderNamesInitMsg(String fileNamesName)
	{
		Object[] a = {fileNamesName};
		return MessageFormat.format(getString("TaxonomyLoader.namesInit"), a);
	}

	public static String taxonomyLoaderChooseNodesFile()
	{
		return getString("TaxonomyLoader.chooseNodesFile");
	}

	public static String taxonomyLoaderChooseNamesFile()
	{
		return getString("TaxonomyLoader.chooseNamesFile");
	}

	// koToECLoader

	public static String koToECChooseFile()
	{
		return getString("KOToECLoader.chooseFile");
	}

	public static String koToECLoaderParsingError(String line)
	{
		Object[] a = {line};
		return MessageFormat.format(getString("KOToECLoader.error.Parsing"), a);
	}

	public static String koToECLoaderFinalMsg()
	{
		return getString("KOToECLoader.final");
	}

	public static String koToECLoaderInitMsg(String fileKo2EcName)
	{
		Object[] a = {fileKo2EcName};
		return MessageFormat.format(getString("KOToECLoader.init"), a);
	}

	// koToGOLoader

	public static String koToGOChooseFile()
	{
		return getString("KOToGOLoader.chooseFile");
	}

	public static String koToGOLoaderParsingError(String line)
	{
		Object[] a = {line};
		return MessageFormat.format(getString("KOToGOLoader.error.Parsing"), a);
	}

	public static String koToGOLoaderFinalMsg()
	{
		return getString("KOToGOLoader.final");
	}

	public static String koToGOLoaderInitMsg(String fileKo2GOName)
	{
		Object[] a = {fileKo2GOName};
		return MessageFormat.format(getString("KOToGOLoader.init"), a);
	}

	// GBKLoader

	public static String gBKChooseFile()
	{
		return getString("GBKLoader.chooseFile");
	}

	public static String gBKLoaderFinalMsg()
	{
		return getString("GBKLoader.final");
	}

	public static String gBKLoaderInitMsg(String fileGBKName)
	{
		Object[] a = {fileGBKName};
		return MessageFormat.format(getString("GBKLoader.init"), a);
	}

	// CDSToKOLoader

	public static String cdsToKOLoaderCDSNotFound(String cdsName)
	{
		Object[] a = {cdsName};
		return MessageFormat.format(getString("CDSToKOLoader.error.CDSNotFound"), a);
	}

	public static String cdsToKOChooseFile()
	{
		return getString("CDSToKOLoader.chooseFile");
	}

	public static String cdsToKOLoaderFinalMsg()
	{
		return getString("CDSToKOLoader.final");
	}

	public static String cdsToKOLoaderInitMsg(String fileGBKName)
	{
		Object[] a = {fileGBKName};
		return MessageFormat.format(getString("CDSToKOLoader.init"), a);
	}

	public static String cdsToKOChooseOrganism()
	{
		return getString("CDSToKOLoader.chooseOrganism");
	}

	public static String cdsToKOChooseMethod()
	{
		return getString("CDSToKOLoader.chooseMethod");
	}

	// KOLoader

	public static String koLoaderChooseFile()
	{
		return getString("KOLoader.chooseFile");
	}

	public static String koLoaderFinalMsg()
	{
		return getString("KOLoader.final");
	}

	public static String koLoaderInitMsg(String fileKOName)
	{
		Object[] a = {fileKOName};
		return MessageFormat.format(getString("KOLoader.init"), a);
	}

	public static String koLoaderExceptionKONotNull()
	{
		return getString("KOLoader.Exception.KONotNull");
	}

	public static String koLoaderExceptionDBLinkSeparator()
	{
		return getString("KOLoader.Exception.DBLinkSeparator");
	}

	// PFFileGenerator

	public static String pfFileGeneratorChoosePfFile()
	{
		return getString("PFFileGenerator.chooseFile");
	}

	public static String pfFileGeneratorFinalMsg()
	{
		return getString("PFFileGenerator.final");
	}

	public static String pfFileGeneratorInitMsg(String fileGBKName)
	{
		Object[] a = {fileGBKName};
		return MessageFormat.format(getString("PFFileGenerator.init"), a);
	}

	public static String pfFileGeneratorChooseOrganism()
	{
		return getString("PFFileGenerator.chooseOrganism");
	}

	public static String pfFileGeneratorChooseVersion()
	{
		return getString("PFFileGenerator.chooseVersion");
	}

	public static String pfFileGeneratorGenerateFastaFile()
	{
		return getString("PFFileGenerator.GenerateFastaFile");
	}

	// File headers

	public static String fastaFileForPFFileHeader()
	{
		return getString("FastaFileForPFFile.Header");
	}

	public static String bioCycIdsFileForPFFileHeader()
	{
		return getString("BioCycIdsFileForPFFile.Header");
	}

	public static String PFFileHeader()
	{
		return getString("PFFile.Header");
	}

	public static String exceptionDBRecordDbRecordLinkBJSourceOrTargetNull()
	{
		return getString("Exception.DBRecordDbRecordLinkBJ.SourceOrTargetNull");
	}

	public static String exceptionMethodNotImplemented()
	{
		return getString("Exception.MethodNotImplemented");
	}

	public static String exceptionDBRecordBJSplitDBNameAccession(String dbNameAndAccession)
	{
		Object[] a = {dbNameAndAccession};
		return MessageFormat.format(getString("Exception.DBRecordBJ.SplitDBNameAccession"), a);
	}

	public static String ExceptionDBRecordDbRecordLinkBJConstructorTerm()
	{
		return getString("Exception.DBRecordDbRecordLinkBJ.ConstructorTerm");
	}

	public static String ExceptionInvalidDBRecordDBRecordLinkName(String termName)
	{
		Object[] a = {termName};
		return MessageFormat.format(getString("Exception.DBRecordDBRecordBJ.InvalidTermName"), a);
	}

	public static String ExceptionAnnotationMethodBJConstructorTerm()
	{
		return getString("Exception.AnnotationMethodBJ.ConstructorTerm");
	}

	public static String exceptionInvalidMethod()
	{
		return getString("Exception.InvalidMethod");
	}

	public static String exceptionHandleDBLinkOtherSequence()
	{
		return getString("Exception.Sequence.DBLinkOtherSequence");
	}

}

package org.cycads.general;

import java.io.File;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ParametersDefault
{
	private static final String			BUNDLE_NAME		= "parametersDefault";						//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private ParametersDefault()
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

	// LoadTaxonomy

	public static String taxonomyLoaderNodesFileName()
	{
		return getString("TaxonomyLoader.nodesFileName");
	}

	public static String taxonomyLoaderNamesFileName()
	{
		return getString("TaxonomyLoader.namesFileName");
	}

	public static int taxonomyLoaderStepShowNodeInterval()
	{
		return Integer.parseInt(getString("TaxonomyLoader.stepShowNodeInterval"));
	}

	public static int taxonomyLoaderStepShowNameInterval()
	{
		return Integer.parseInt(getString("TaxonomyLoader.stepShowNameInterval"));
	}

	public static int taxonomyLoaderStepCache()
	{
		return Integer.parseInt(getString("TaxonomyLoader.stepCache"));
	}

	// KOToECLoader

	public static String koToECLoaderSeparator()
	{
		return getString("KOToECLoader.separator");
	}

	public static String koToECLoaderComment()
	{
		return getString("KOToECLoader.comment");
	}

	public static String koToECLoaderDeleteExpression()
	{
		return getString("KOToECLoader.deleteExpression");
	}

	public static int koToECLoaderStepCache()
	{
		return Integer.parseInt(getString("KOToECLoader.stepCache"));
	}

	public static int koToECLoaderStepShowInterval()
	{
		return Integer.parseInt(getString("KOToECLoader.stepShowInterval"));
	}

	public static String koToECLoaderFileName()
	{
		return getString("KOToECLoader.fileName");
	}

	// KOToGOLoader

	public static String koToGOLoaderSeparator()
	{
		return getString("KOToGOLoader.separator");
	}

	public static String koToGOLoaderComment()
	{
		return getString("KOToGOLoader.comment");
	}

	public static String koToGOLoaderDeleteExpression()
	{
		return getString("KOToGOLoader.deleteExpression");
	}

	public static int koToGOLoaderStepCache()
	{
		return Integer.parseInt(getString("KOToGOLoader.stepCache"));
	}

	public static int koToGOLoaderStepShowInterval()
	{
		return Integer.parseInt(getString("KOToGOLoader.stepShowInterval"));
	}

	public static String koToGOLoaderFileName()
	{
		return getString("KOToGOLoader.fileName");
	}

	// GBKLoader

	public static int gBKLoaderStepCache()
	{
		return Integer.parseInt(getString("GBKLoader.stepCache"));
	}

	public static int gBKLoaderStepShowInterval()
	{
		return Integer.parseInt(getString("GBKLoader.stepShowInterval"));
	}

	public static String gBKLoaderFileName()
	{
		return getString("GBKLoader.fileName");
	}

	public static String cdsToECMethodType()
	{
		return getString("CDSToEC.methodType");
	}

	public static String gBKLoaderMethodCDSToECName()
	{
		return getString("GBKLoader.methodCDSToECName");
	}

	public static String gBKLoaderMethodCDSToECDescription(String methodName)
	{
		Object[] a = {methodName};
		return MessageFormat.format(getString("GBKLoader.methodCDSToECDescription"), a);
	}

	// GBK file

	public static String gbkECTagExpression()
	{
		return getString("GBK.file.ECTagExpression");
	}

	public static String gbkGeneTagExpression()
	{
		return getString("GBK.file.GeneTagExpression");
	}

	public static String gbkRNATagExpression()
	{
		return getString("GBK.file.RNATagExpression");
	}

	public static String gbkCDSTagExpression()
	{
		return getString("GBK.file.CDSTagExpression");
	}

	// CDSToKOLoader

	public static String cdsToKOLoaderSeparator()
	{
		return getString("CDSToKOLoader.separator");
	}

	public static String cdsToKOLoaderComment()
	{
		return getString("CDSToKOLoader.comment");
	}

	public static String cdsToKOLoaderFileName()
	{
		return getString("CDSToKOLoader.fileName");
	}

	public static int cdsToKOLoaderStepCache()
	{
		return Integer.parseInt(getString("CDSToKOLoader.stepCache"));
	}

	public static int cdsToKOLoaderStepShowInterval()
	{
		return Integer.parseInt(getString("CDSToKOLoader.stepShowInterval"));
	}

	public static int cdsToKOLoaderOrganismNumber()
	{
		return Integer.parseInt(getString("CDSToKOLoader.organismNumber"));
	}

	public static String cdsToKOMethodType()
	{
		return getString("CDSToKO.methodType");
	}

	public static String cdsToKOLoaderMethodName()
	{
		return getString("CDSToKOLoader.methodName");
	}

	public static String cdsToKOMethodDescription(String methodName)
	{
		Object[] a = {methodName};
		return MessageFormat.format(getString("CDSToKOLoader.methodDescription"), a);
	}

	public static int cdsToKOLoaderPosCDSName()
	{
		return Integer.parseInt(getString("CDSToKOLoader.PosCDSName"));
	}

	public static String cdsToKOLoaderSeparatorCDSName()
	{
		return getString("CDSToKOLoader.SeparatorCDSName");
	}

	// KOLoader

	public static String koLoaderFileName()
	{
		return getString("KOLoader.fileName");
	}

	public static int koLoaderStepCache()
	{
		return Integer.parseInt(getString("KOLoader.stepCache"));
	}

	public static int koLoaderStepShowInterval()
	{
		return Integer.parseInt(getString("KOLoader.stepShowInterval"));
	}

	// KOFile

	public static String koFileEntryTag()
	{
		return getString("KOFile.entryTag");
	}

	public static String koFileDefinitionTag()
	{
		return getString("KOFile.definitionTag");
	}

	public static String koFileDBLinksTag()
	{
		return getString("KOFile.dbLinksTag");
	}

	public static String koFileECTag()
	{
		return getString("KOFile.ecTag");
	}

	public static int koFileDataPos()
	{
		return Integer.parseInt(getString("KOFile.dataPos"));
	}

	public static String koFileEndRecordStr()
	{
		return getString("KOFile.endRecord");
	}

	public static String koFileCommentStr()
	{
		return getString("KOFile.comment");
	}

	public static String koFileECDeleteExpression()
	{
		return getString("KOFile.EC.deleteExpression");
	}

	public static String koFileECSeparator()
	{
		return getString("KOFile.EC.SeparatorExpression");
	}

	public static String koFileDBLinkSubTagSeparator()
	{
		return getString("KOFile.DBLink.SubTagSeparator");
	}

	public static String koFileDBLinkGOTag()
	{
		return getString("KOFile.DBLink.GOTag");
	}

	public static String koFileDBLinkGOSeparator()
	{
		return getString("KOFile.DBLink.GOSeparator");
	}

	public static String koFileDBLinkCOGTag()
	{
		return getString("KOFile.DBLink.COGTag");
	}

	public static String koFileDBLinkCOGSeparator()
	{
		return getString("KOFile.DBLink.COGSeparator");
	}

	// PFFileGenerator

	public static String pfFileGeneratorPfFileName(int organismNumber)
	{
		Object[] a = {organismNumber};
		return MessageFormat.format(getString("PFFileGenerator.fileName"), a);
	}

	public static int pfFileGeneratorStepCache()
	{
		return Integer.parseInt(getString("PFFileGenerator.stepCache"));
	}

	public static int pfFileGeneratorStepShowInterval()
	{
		return Integer.parseInt(getString("PFFileGenerator.stepShowInterval"));
	}

	public static int pfFileGeneratorOrganismNumber()
	{
		return Integer.parseInt(getString("PFFileGenerator.organismNumber"));
	}

	public static int pfFileGeneratorVersionNumber()
	{
		return Integer.parseInt(getString("PFFileGenerator.versionNumber"));
	}

	public static String pfFileGeneratorFastaFileName(File file)
	{
		return file.getPath() + getString("PFFileGenerator.fastaFileExtension");
	}

	public static int fastaFileForPFFileLineWidth()
	{
		return Integer.parseInt(getString("FastaFileForPFFile.LineWidth"));
	}

	public static String pfFileGeneratorBioCycIdsFileName(File file)
	{
		return file.getPath() + getString("PFFileGenerator.BioCycIdsFileExtension");
	}

	public static String bioCycIdsSeparator()
	{
		return getString("BioCycIdsFileForPFFile.Separator");
	}

	public static String bioCycIdFormat()
	{
		return getString("BioCycId.Format");
	}

	// General

	public static String methodDescription()
	{
		return getString("General.Method.Description");
	}

	// Annotation
	public static String DBRecordSeparator()
	{
		return getString("Annotation.DBLink.DBRecordSeparator");
	}

	public static String DBRecordDBRecordSeparator()
	{
		return getString("Annotation.DBLink.DBRecordDBRecordSeparator");
	}

}

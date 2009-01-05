package org.cycads.general;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Config
{
	private static final String			BUNDLE_NAME						= "config";											//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE					= ResourceBundle.getBundle(BUNDLE_NAME);

	public static List<Pattern>			SEQUENCEFEATURE_TYPE_PATTERNS	= getPatterns("SequenceFeature.Tag.Type.regex");
	public static List<String>			BIOCYC_RECORD_TYPES				= getStrings("BioCycRecord.Type.value");
	public static List<String>			SEQUENCEFEATURE_TAG_PRODUCT_IDS	= getStrings("SequenceFeature.Tag.ProductId.value");

	// public static List<Pattern> GFF3_GENE_DBXREF_PATTERNS = getPatterns("gff3.file.gene.dbxref.regex");
	// public static List<String> GFF3_GENE_DBXREF_DBNAMES = getStrings("gff3.file.gene.dbxref.dbName");
	// public static List<Pattern> GFF3_CDS_DBXREF_PATTERNS = getPatterns("gff3.file.cds.dbxref.regex");
	// public static List<String> GFF3_CDS_DBXREF_DBNAMES = getStrings("gff3.file.cds.dbxref.dbName");
	// public static List<Pattern> GFF3_MRNA_DBXREF_PATTERNS = getPatterns("gff3.file.mrna.dbxref.regex");
	// public static List<String> GFF3_MRNA_DBXREF_DBNAMES = getStrings("gff3.file.mrna.dbxref.dbName");

	private Config() {
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

	// KOToECLoader

	public static String koToECLoaderSeparator() {
		return getString("KOToECLoader.separator");
	}

	public static String koToECLoaderComment() {
		return getString("KOToECLoader.comment");
	}

	public static String koToECLoaderDeleteExpression() {
		return getString("KOToECLoader.deleteExpression");
	}

	// KOToGOLoader

	public static String koToGOLoaderSeparator() {
		return getString("KOToGOLoader.separator");
	}

	public static String koToGOLoaderComment() {
		return getString("KOToGOLoader.comment");
	}

	public static String koToGOLoaderDeleteExpression() {
		return getString("KOToGOLoader.deleteExpression");
	}

	// GBKLoader

	public static String cdsToECMethodType() {
		return getString("CDSToEC.methodType");
	}

	public static String gBKLoaderMethodCDSToECDescription(String methodName) {
		Object[] a = {methodName};
		return MessageFormat.format(getString("GBKLoader.methodCDSToECDescription"), a);
	}

	// GBK file

	public static String gbkECTagExpression() {
		return getString("GBK.file.ECTagExpression");
	}

	public static String gbkGeneTagExpression() {
		return getString("GBK.file.GeneTagExpression");
	}

	public static String gbkRNATagExpression() {
		return getString("GBK.file.RNATagExpression");
	}

	public static String gbkCDSTagExpression() {
		return getString("GBK.file.CDSTagExpression");
	}

	// CDSToKOLoader

	public static String cdsToKOLoaderSeparator() {
		return getString("CDSToKOLoader.separator");
	}

	public static String cdsToKOLoaderComment() {
		return getString("CDSToKOLoader.comment");
	}

	public static String cdsToKOMethodType() {
		return getString("CDSToKO.methodType");
	}

	public static String cdsToKOMethodDescription(String methodName) {
		Object[] a = {methodName};
		return MessageFormat.format(getString("CDSToKOLoader.methodDescription"), a);
	}

	public static int cdsToKOLoaderPosCDSName() {
		return Integer.parseInt(getString("CDSToKOLoader.PosCDSName"));
	}

	public static String cdsToKOLoaderSeparatorCDSName() {
		return getString("CDSToKOLoader.SeparatorCDSName");
	}

	// KOFile

	public static String koFileEntryTag() {
		return getString("KOFile.entryTag");
	}

	public static String koFileDefinitionTag() {
		return getString("KOFile.definitionTag");
	}

	public static String koFileDBLinksTag() {
		return getString("KOFile.dbLinksTag");
	}

	public static String koFileECTag() {
		return getString("KOFile.ecTag");
	}

	public static int koFileDataPos() {
		return Integer.parseInt(getString("KOFile.dataPos"));
	}

	public static String koFileEndRecordStr() {
		return getString("KOFile.endRecord");
	}

	public static String koFileCommentStr() {
		return getString("KOFile.comment");
	}

	public static String koFileECDeleteExpression() {
		return getString("KOFile.EC.deleteExpression");
	}

	public static String koFileECSeparator() {
		return getString("KOFile.EC.SeparatorExpression");
	}

	public static String koFileDBLinkSubTagSeparator() {
		return getString("KOFile.DBLink.SubTagSeparator");
	}

	public static String koFileDBLinkGOTag() {
		return getString("KOFile.DBLink.GOTag");
	}

	public static String koFileDBLinkGOSeparator() {
		return getString("KOFile.DBLink.GOSeparator");
	}

	public static String koFileDBLinkCOGTag() {
		return getString("KOFile.DBLink.COGTag");
	}

	public static String koFileDBLinkCOGSeparator() {
		return getString("KOFile.DBLink.COGSeparator");
	}

	// PFFileGenerator

	public static String bioCycIdsSeparator() {
		return getString("BioCycIdsFileForPFFile.Separator");
	}

	public static String bioCycIdFormat() {
		return getString("BioCycId.Format");
	}

	// General

	public static String methodDescription() {
		return getString("General.Method.Description");
	}

	public static String bioCycRecordType(String featureType) {
		return transform(featureType, SEQUENCEFEATURE_TYPE_PATTERNS, BIOCYC_RECORD_TYPES);
	}

	public static String sequenceFeatureTagProductId(String featureType) {
		return transform(featureType, SEQUENCEFEATURE_TYPE_PATTERNS, SEQUENCEFEATURE_TAG_PRODUCT_IDS);
	}

	private static String transform(String key, List<Pattern> keyPatterns, List<String> values) {
		for (int i = 0; i < keyPatterns.size(); i++) {
			if (keyPatterns.get(i).matcher(key).matches()) {
				if (values.size() > i) {
					return values.get(i);
				}
				else {
					return null;
				}
			}
		}
		return null;
	}

	private static String[] transforms(String key, List<Pattern> keyPatterns, List<String> values) {
		ArrayList<String> rets = new ArrayList<String>();
		for (int i = 0; i < keyPatterns.size(); i++) {
			if (keyPatterns.get(i).matcher(key).matches()) {
				if (values.size() > i) {
					rets.add(values.get(i));
				}
			}
		}
		return rets.toArray(new String[0]);
	}

	private static List<Pattern> getPatterns(String tag) {
		List<String> patternsStr = getStrings(tag);
		List<Pattern> patterns = new ArrayList<Pattern>(patternsStr.size());
		for (String str : patternsStr) {
			patterns.add(Pattern.compile(str));
		}
		return patterns;
	}

	private static List<String> getStrings(String tag) {
		ArrayList<String> values = new ArrayList<String>(4);
		int i = 0;
		String str;
		while ((str = getStringOptional(tag + "." + i)) != null) {
			values.add(str);
			i++;
		}
		values.trimToSize();
		return values;
	}

	public static String sequenceFeatureNameTag() {
		return getString("SequenceFeature.Tag.Name");
	}

	// GFF3 loader

	public static String gff3ExonTagExpression() {
		return getString("gff3.file.exonTagExpression");
	}

	public static String gff3GeneTagExpression() {
		return getString("gff3.file.geneTagExpression");
	}

	public static String gff3MRNATagExpression() {
		return getString("gff3.file.MRNATagExpression");
	}

	public static String gff3CDSTagExpression() {
		return getString("gff3.file.CDSTagExpression");
	}

	//	public static String gff3NoteIdExpression() {
	//		return getString("gff3.file.note.id.expression");
	//	}
	//
	//	public static String gff3NoteParentExpression() {
	//		return getString("gff3.file.note.parent.expression");
	//	}
	//
	public static String gff3NoteDBXRefExpression() {
		return getString("gff3.file.note.dbxref.regex");
	}

	public static String[] gff3GeneDBXRefDBName(String tagNote, String method) {
		if (method != null && method.length() > 0) {
			return transforms(tagNote, getPatterns("gff3.file.gene.dbxref.regex." + method),
				getStrings("gff3.file.gene.dbxref.dbName." + method));
		}
		else {
			return transforms(tagNote, getPatterns("gff3.file.gene.dbxref.regex"),
				getStrings("gff3.file.gene.dbxref.dbName"));
		}
	}

	public static String[] gff3CDSDBXRefDBName(String tagNote, String method) {
		if (method != null && method.length() > 0) {
			return transforms(tagNote, getPatterns("gff3.file.cds.dbxref.regex." + method),
				getStrings("gff3.file.cds.dbxref.dbName." + method));
		}
		else {
			return transforms(tagNote, getPatterns("gff3.file.cds.dbxref.regex"),
				getStrings("gff3.file.cds.dbxref.dbName"));
		}
	}

	public static String[] gff3MRNADBXRefDBName(String tagNote, String method) {
		if (method != null && method.length() > 0) {
			return transforms(tagNote, getPatterns("gff3.file.mrna.dbxref.regex." + method),
				getStrings("gff3.file.mrna.dbxref.dbName." + method));
		}
		else {
			return transforms(tagNote, getPatterns("gff3.file.mrna.dbxref.regex"),
				getStrings("gff3.file.mrna.dbxref.dbName"));
		}
	}

	public static String gff3GeneFunctionExpression() {
		return getString("gff3.file.gene.note.function.regex");
	}

	public static String gff3GeneExcludeExpression() {
		return getString("gff3.file.gene.note.exclude.regex");
	}

	public static String gff3CDSFunctionExpression() {
		return getString("gff3.file.cds.note.function.regex");
	}

	public static String gff3CDSExcludeExpression() {
		return getString("gff3.file.cds.note.exclude.regex");
	}

	public static String gff3MRNAFunctionExpression() {
		return getString("gff3.file.mrna.note.function.regex");
	}

	public static String gff3MRNAExcludeExpression() {
		return getString("gff3.file.mrna.note.exclude.regex");
	}

	public static String gff3MRNAIdExpression() {
		return getString("gff3.file.mrna.note.id.regex");
	}

	public static String gff3ExonParentAccesionExpression() {
		return getString("gff3.file.exon.note.parent.regex");
	}

	public static String gff3MRNAParentAccesionExpression() {
		return getString("gff3.file.mrna.note.parent.regex");
	}

	public static String gff3CDSParentAccesionExpression() {
		return getString("gff3.file.cds.note.parent.regex");
	}

	public static String gff3MRNAParentDB() {
		return getString("gff3.file.mrna.note.parent.dbName");
	}

	public static String gff3CDSParentDB() {
		return getString("gff3.file.cds.note.parent.dbName");
	}

	public static String gff3LoaderFileName() {
		return getString("gff3.loader.fileName");
	}

	public static int gff3LoaderOrganismNumber() {
		return Integer.parseInt(getString("gff3.loader.organismNumber"));
	}

	public static String gff3LoaderSeqDBName() {
		return getString("gff3.loader.seqDBName");
	}

	// public static String gff3MethodForDBLink()
	// {
	// return getString("gff3Loader.dbLink.method.name");
	// }
	//
}

package org.cycads.parser.gbk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.MissingResourceException;
import java.util.regex.Pattern;

import org.cycads.entities.annotation.AnnotationFinder;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.factory.EntityFactory;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.general.Config;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.operation.AddDbxrefAnnotation;
import org.cycads.parser.operation.AddFunctionAnnotation;
import org.cycads.parser.operation.AddParentAnnotation;
import org.cycads.parser.operation.AddSynonym;
import org.cycads.parser.operation.ChangeTagNameNote;
import org.cycads.parser.operation.ChangeTagValueNote;
import org.cycads.parser.operation.CopyNote;
import org.cycads.parser.operation.NoteOperation;
import org.cycads.parser.operation.RelationshipOperation;
import org.cycads.parser.operation.RemoveNote;
import org.cycads.parser.operation.SplitNote;

/*
 * #the gff3 parameters: gff3.file[.comment].<parameter name>[.<type>][.<source>][.regex][.<i>] #type =
 * gene|mrna|cds|exon
 * 
 */
public class GBKFileConfig
{
	//	private static final String			BUNDLE_NAME		= "config";								//$NON-NLS-1$
	//
	//	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle.getBundle(BUNDLE_NAME);

	private GBKFileConfig() {
	}

	private static String getString(String key) {
		try {
			return Config.getString(key);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getStringOptional(String key) {
		try {
			return Config.getString(key);
		}
		catch (MissingResourceException e) {
			return null;
		}
	}

	private static String getStringMandatory(String key) {
		try {
			return Config.getString(key);
		}
		catch (MissingResourceException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	public static ArrayList<String> getStrings(String fullParameterName) {
		ArrayList<String> values;
		values = new ArrayList<String>();
		String str;
		if ((str = getStringOptional(fullParameterName)) != null) {
			values.add(str);
		}
		int i = 0;
		while ((str = getStringOptional(fullParameterName + "." + i)) != null) {
			values.add(str);
			i++;
		}
		values.trimToSize();
		return values;
	}

	public static ArrayList<String> getStrings(String tag, ArrayList<String> values) {
		if (values == null) {
			values = new ArrayList<String>(4);
		}
		String str;
		if ((str = getStringOptional(tag)) != null) {
			values.add(str);
		}
		int i = 0;
		while ((str = getStringOptional(tag + "." + i)) != null) {
			values.add(str);
			i++;
		}
		values.trimToSize();
		return values;
	}

	public static ArrayList<Pattern> getPatterns(String fullParameterName) {
		fullParameterName += ".regex";
		ArrayList<Pattern> patterns;
		List<String> patternsStr = getStrings(fullParameterName, null);
		patterns = new ArrayList<Pattern>(patternsStr.size());
		for (String str : patternsStr) {
			patterns.add(Pattern.compile(str));
		}
		return patterns;
	}

	public static ArrayList<Pattern> getPatterns(String fullParameterName, ArrayList<Pattern> patterns) {
		fullParameterName += ".regex";
		List<String> patternsStr = getStrings(fullParameterName, null);
		if (patterns == null) {
			patterns = new ArrayList<Pattern>(patternsStr.size());
		}
		Pattern pattern;
		for (String str : patternsStr) {
			//			if (str != null && str.length() != 0) {
			pattern = Pattern.compile(str);
			//			}
			//			else {
			//				pattern = null;
			//			}
			patterns.add(pattern);
		}
		return patterns;
	}

	public static ArrayList<Pattern> getPatternsByType(String parameterName, String type) {
		ArrayList<Pattern> patterns = getPatterns("gbk.file." + parameterName, null);
		patterns = getPatterns("gbk.file." + type + "." + parameterName, patterns);
		return patterns;
	}

	public static String getStringByType(String parameterName, String type) {
		ArrayList<String> values = getStringsByType(parameterName, type);
		if (values == null || values.isEmpty()) {
			return null;
		}
		return values.get(0);
	}

	public static ArrayList<String> getStringsByType(String parameterName, String type) {
		ArrayList<String> values = getStrings("gbk.file." + parameterName, null);
		values = getStrings("gbk.file." + type + "." + parameterName, values);
		return values;
	}

	public static int matches(String input, Collection<Pattern> patterns) {
		int i = 0;
		for (Pattern pattern : patterns) {
			if (pattern != null && pattern.matcher(input).matches()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	static ArrayList<Pattern>	removeTypePatterns	= getPatterns("gbk.file.removeType");
	static ArrayList<Pattern>	changeTypePatterns	= getPatterns("gbk.file.changeType");
	static ArrayList<String>	newValueTypes		= getStrings("gbk.file.changeType.newValue");

	public static String getSeqCommentNoteType() {
		return getStringMandatory("gbk.file.sequence.comment.noteType");
	}

	public static String getSeqDescriptionNoteType() {
		return getStringMandatory("gbk.file.sequence.description.noteType");
	}

	private static Hashtable<String, String>	typeHash	= new Hashtable<String, String>();

	public static String getType(String type) {
		String newType = typeHash.get(type);
		if (newType == null) {
			String oldType = type;
			if (matches(type, removeTypePatterns) >= 0) {
				newType = "";
			}
			else {
				int i = matches(type, changeTypePatterns);
				if (i >= 0) {
					newType = newValueTypes.get(i);
					type = newType;
				}
				else {
					newType = type;
				}
				if (isTypeValue(type, ParametersDefault.getGeneAnnotationTypeName())) {
					newType = ParametersDefault.getGeneAnnotationTypeName();
				}
				else if (isTypeValue(type, ParametersDefault.getMRNAAnnotationTypeName())) {
					newType = ParametersDefault.getMRNAAnnotationTypeName();
				}
				else if (isTypeValue(type, ParametersDefault.getCDSAnnotationTypeName())) {
					newType = ParametersDefault.getCDSAnnotationTypeName();
				}
			}
			typeHash.put(oldType, newType);
		}
		return newType;
	}

	// cdsAnnotationType = factory.getAnnotationType(ParametersDefault.getCDSAnnotationTypeName());
	// mrnaAnnotationType = factory.getAnnotationType(ParametersDefault.getMRNAAnnotationTypeName());
	// geneAnnotationType = factory.getAnnotationType(ParametersDefault.getGeneAnnotationTypeName());

	public static boolean isTypeValue(String input, String type) {
		ArrayList<Pattern> patterns = getPatternsByType("typeValue", type);
		return matches(input, patterns) >= 0;
	}

	private static Hashtable<String, String>	annotationMethodNameHash	= new Hashtable<String, String>();

	public static String getAnnotationMethodName(String type) {
		String methodName = annotationMethodNameHash.get(type);
		if (methodName == null) {
			methodName = getStringByType("methodName", type);
			if (methodName == null || methodName.length() == 0) {
				methodName = getStringMandatory("gbk.file.methodNameDefault");
			}
			annotationMethodNameHash.put(type, methodName);
		}
		return methodName;
	}

	//	private static ArrayList<Pattern> getPatterns(String type, Hashtable<String, ArrayList<Pattern>> patternsHash,
	//			String parameterName) {
	//		ArrayList<Pattern> ret = patternsHash.get(type);
	//		if (ret == null) {
	//			ret = getPatternsByType(parameterName, type);
	//			patternsHash.put(type, ret);
	//		}
	//		return ret;
	//	}
	//
	//	private static ArrayList<String> getStrings(String type, Hashtable<String, ArrayList<String>> stringsHash,
	//			String parameterName) {
	//		ArrayList<String> ret = stringsHash.get(type);
	//		if (ret == null) {
	//			ret = getStringsByType(parameterName, type);
	//			stringsHash.put(type, ret);
	//		}
	//		return ret;
	//	}
	//
	//	private static ArrayList<String> filter(ArrayList<String> results, ArrayList<Pattern> patterns, String expression) {
	//		ArrayList<String> ret = new ArrayList<String>();
	//		for (int i = 0; i < patterns.size(); i++) {
	//			if (patterns.get(i).matcher(expression).matches()) {
	//				ret.add(results.get(i));
	//			}
	//		}
	//		return ret;
	//	}
	//
	public static String gbkLoaderSeqDBName() {
		return getStringOptional("gbk.file.sequence.dbName");
	}

	public static String gbkLoaderFileName() {
		return getStringOptional("gbk.file.fileName");
	}

	public static String getOutputFile() {
		return getStringOptional("gbk.file.debug.outputGbkFile");
	}

	private static Hashtable<String, ArrayList<NoteOperation>>	noteOperationsHash	= new Hashtable<String, ArrayList<NoteOperation>>();

	public static List<NoteOperation> getNoteOperations(String type) {
		ArrayList<NoteOperation> ret = noteOperationsHash.get(type);
		if (ret == null) {
			ret = new ArrayList<NoteOperation>();
			ArrayList<String> operationNames = getStringsByType("tagOperation", type);
			String operationName;
			for (int i = 0; i < operationNames.size(); i++) {
				operationName = operationNames.get(i);
				if (operationName.equals("remove")) {
					ret.add(getRemoveNoteOperation(type, i));
				}
				else if (operationName.equals("split")) {
					ret.add(getSplitNoteOperation(type, i));
				}
				else if (operationName.equals("changeTagName")) {
					ret.add(getChangeTagNameNoteOperation(type, i));
				}
				else if (operationName.equals("changeTagValue")) {
					ret.add(getChangeTagValueNoteOperation(type, i));
				}
				else if (operationName.equals("copy")) {
					ret.add(getCopyNoteOperation(type, i));
				}
			}
			noteOperationsHash.put(type, ret);
		}
		return ret;
	}

	private static NoteOperation getCopyNoteOperation(String type, int i) {
		String tagName, tagValue, newTagName;
		tagName = getStringByType("tagOperation." + i + ".copy.tagName.regex", type);
		tagValue = getStringByType("tagOperation." + i + ".copy.tagValue.regex", type);
		newTagName = getStringByType("tagOperation." + i + ".copy.newTagName", type);
		return new CopyNote(Pattern.compile(tagName), Pattern.compile(tagValue), newTagName);
	}

	private static NoteOperation getChangeTagValueNoteOperation(String type, int i) {
		String tagName, tagValue;
		List<String> substSourceTagValue, substTargetTagValue;
		tagName = getStringByType("tagOperation." + i + ".changeTagValue.tagName.regex", type);
		tagValue = getStringByType("tagOperation." + i + ".changeTagValue.tagValue.regex", type);
		substSourceTagValue = getStringsByType("tagOperation." + i + ".changeTagValue.substSourceTagValue.regex", type);
		substTargetTagValue = getStringsByType("tagOperation." + i + ".changeTagValue.substTargetTagValue", type);
		return new ChangeTagValueNote(Pattern.compile(tagName), Pattern.compile(tagValue), substSourceTagValue,
			substTargetTagValue);
	}

	private static NoteOperation getChangeTagNameNoteOperation(String type, int i) {
		String tagName, tagValue, newTagName;
		tagName = getStringByType("tagOperation." + i + ".changeTagName.tagName.regex", type);
		tagValue = getStringByType("tagOperation." + i + ".changeTagName.tagValue.regex", type);
		newTagName = getStringByType("tagOperation." + i + ".changeTagName.newTagName", type);
		return new ChangeTagNameNote(Pattern.compile(tagName), Pattern.compile(tagValue), newTagName);
	}

	private static NoteOperation getSplitNoteOperation(String type, int i) {
		String tagName, tagValue, separator;
		tagName = getStringByType("tagOperation." + i + ".split.tagName.regex", type);
		tagValue = getStringByType("tagOperation." + i + ".split.tagValue.regex", type);
		separator = getStringByType("tagOperation." + i + ".split.separator.regex", type);
		return new SplitNote(Pattern.compile(tagName), Pattern.compile(tagValue), separator);
	}

	private static NoteOperation getRemoveNoteOperation(String type, int i) {
		String tagName, tagValue;
		tagName = getStringByType("tagOperation." + i + ".remove.tagName.regex", type);
		tagValue = getStringByType("tagOperation." + i + ".remove.tagValue.regex", type);
		return new RemoveNote(Pattern.compile(tagName), Pattern.compile(tagValue));
	}

	static Hashtable<String, ArrayList<RelationshipOperation<SubseqAnnotation, ? >>>	annotationOperationsHash	= new Hashtable<String, ArrayList<RelationshipOperation<SubseqAnnotation, ? >>>();

	public static List<RelationshipOperation<SubseqAnnotation, ? >> getSubseqAnnotationOperations(String type,
			EntityFactory factory, AnnotationFinder sequence) {
		ArrayList<RelationshipOperation<SubseqAnnotation, ? >> ret = annotationOperationsHash.get(type);
		if (ret == null) {
			ret = new ArrayList<RelationshipOperation<SubseqAnnotation, ? >>();
			ArrayList<AddSynonym<SubseqAnnotation>> addSynonyms = getFeatureSynonymOperations(type, factory);
			ArrayList<AddParentAnnotation<SubseqAnnotation>> addParents = getFeatureParentOperations(type, factory,
				sequence);
			ret.addAll(addSynonyms);
			ret.addAll(addParents);
			annotationOperationsHash.put(type, ret);
		}
		return ret;
	}

	private static ArrayList<AddSynonym<SubseqAnnotation>> getFeatureSynonymOperations(String type,
			EntityFactory factory) {
		ArrayList<AddSynonym<SubseqAnnotation>> ret = new ArrayList<AddSynonym<SubseqAnnotation>>();
		List<Pattern> tagNames = getPatternsByType("featureSynonym.tagName", type);
		List<Pattern> tagValues = getPatternsByType("featureSynonym.tagValue", type);
		List<String> dbNames = getStringsByType("featureSynonym.dbName", type);

		for (int i = 0; i < tagNames.size(); i++) {
			ret.add(new AddSynonym<SubseqAnnotation>(tagNames.get(i), tagValues.get(i), dbNames.get(i), factory));
		}

		return ret;
	}

	private static ArrayList<AddParentAnnotation<SubseqAnnotation>> getFeatureParentOperations(String type,
			EntityFactory factory, AnnotationFinder sequence) {
		ArrayList<AddParentAnnotation<SubseqAnnotation>> ret = new ArrayList<AddParentAnnotation<SubseqAnnotation>>();
		List<Pattern> tagNames = getPatternsByType("featureParent.tagName", type);
		List<Pattern> tagValues = getPatternsByType("featureParent.tagValue", type);
		List<String> dbNames = getStringsByType("featureParent.dbName", type);

		for (int i = 0; i < tagNames.size(); i++) {
			ret.add(new AddParentAnnotation<SubseqAnnotation>(tagNames.get(i), tagValues.get(i), dbNames.get(i),
				factory, sequence));
		}

		return ret;
	}

	static Hashtable<String, ArrayList<RelationshipOperation<Subsequence, ? >>>	subseqOperationsHash	= new Hashtable<String, ArrayList<RelationshipOperation<Subsequence, ? >>>();

	public static List<RelationshipOperation<Subsequence, ? >> getSubseqOperations(String type, EntityFactory factory) {
		ArrayList<RelationshipOperation<Subsequence, ? >> ret = subseqOperationsHash.get(type);
		if (ret == null) {
			ret = new ArrayList<RelationshipOperation<Subsequence, ? >>();
			ArrayList<AddSynonym<Subsequence>> addSynonyms = getSubseqSynonymOperations(type, factory);
			ArrayList<AddDbxrefAnnotation<Subsequence>> addDbxrefAnnots = getSubseqDbxrefAnnotOperations(type, factory);
			ArrayList<AddFunctionAnnotation<Subsequence>> addFunctionAnnots = getSubseqFunctionAnnotOperations(type,
				factory);
			ret.addAll(addSynonyms);
			ret.addAll(addDbxrefAnnots);
			ret.addAll(addFunctionAnnots);
			subseqOperationsHash.put(type, ret);
		}
		return ret;
	}

	private static ArrayList<AddSynonym<Subsequence>> getSubseqSynonymOperations(String type, EntityFactory factory) {
		ArrayList<AddSynonym<Subsequence>> ret = new ArrayList<AddSynonym<Subsequence>>();
		List<Pattern> tagNames = getPatternsByType("subseqSynonym.tagName", type);
		List<Pattern> tagValues = getPatternsByType("subseqSynonym.tagValue", type);
		List<String> dbNames = getStringsByType("subseqSynonym.dbName", type);

		for (int i = 0; i < tagNames.size(); i++) {
			ret.add(new AddSynonym<Subsequence>(tagNames.get(i), tagValues.get(i), dbNames.get(i), factory));
		}

		return ret;
	}

	private static ArrayList<AddDbxrefAnnotation<Subsequence>> getSubseqDbxrefAnnotOperations(String type,
			EntityFactory factory) {
		ArrayList<AddDbxrefAnnotation<Subsequence>> ret = new ArrayList<AddDbxrefAnnotation<Subsequence>>();
		List<Pattern> tagNames = getPatternsByType("subseqDbxRefAnnotation.tagName", type);
		List<Pattern> tagValues = getPatternsByType("subseqDbxRefAnnotation.tagValue", type);
		List<String> dbNames = getStringsByType("subseqDbxRefAnnotation.dbName", type);
		List<String> methods = getStringsByType("subseqDbxRefAnnotation.methodName", type);

		for (int i = 0; i < tagNames.size(); i++) {
			ret.add(new AddDbxrefAnnotation<Subsequence>(tagNames.get(i), tagValues.get(i), dbNames.get(i),
				methods.get(i), factory));
		}

		return ret;
	}

	private static ArrayList<AddFunctionAnnotation<Subsequence>> getSubseqFunctionAnnotOperations(String type,
			EntityFactory factory) {
		ArrayList<AddFunctionAnnotation<Subsequence>> ret = new ArrayList<AddFunctionAnnotation<Subsequence>>();
		List<Pattern> tagNames = getPatternsByType("subseqFunctionAnnotation.tagName", type);
		List<Pattern> tagValues = getPatternsByType("subseqFunctionAnnotation.tagValue", type);
		List<String> methods = getStringsByType("subseqFunctionAnnotation.methodName", type);

		for (int i = 0; i < tagNames.size(); i++) {
			ret.add(new AddFunctionAnnotation<Subsequence>(tagNames.get(i), tagValues.get(i), methods.get(i), factory));
		}

		return ret;
	}

}

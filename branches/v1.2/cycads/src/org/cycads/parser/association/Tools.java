/*
 * Created on 13/10/2009
 */
package org.cycads.parser.association;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.cycads.entities.Feature;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.factory.EntityFeatureFactory;
import org.cycads.entities.factory.EntityMethodFactory;
import org.cycads.entities.factory.EntityTypeFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Organism;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.general.ParametersDefault;
import org.cycads.parser.FileParserException;
import org.cycads.ui.progress.Progress;

public class Tools
{
	public static String cleanTextDelimiter(String text, String delimiter) {
		if (text == null || text.length() == 0 || delimiter == null || delimiter.length() == 0) {
			return text;
		}
		int start = 0, end = text.length();
		if (text.startsWith(delimiter)) {
			start = delimiter.length();
		}
		if (text.endsWith(delimiter)) {
			end = end - delimiter.length();
		}
		return text.substring(start, end);
	}

	public static String[] split(String value, String separator) {
		if (value == null) {
			return null;
		}
		if (separator == null || separator.length() == 0) {
			return new String[] {value};
		}
		else {
			return value.split(separator);
		}
	}

	public static <R> R getNextValidRecord(RecordFileReader<R> reader, Progress progressError) throws IOException {
		R record = null;
		boolean read = false;
		while (!read) {
			try {
				record = reader.read();
				read = true;
			}
			catch (FileParserException e) {
				if (ParametersDefault.isDebugging()) {
					e.printStackTrace();
				}
				progressError.completeStep();
			}
		}
		return record;
	}

	public static Annotation<Subsequence, Feature> createFakeSubseqAnnot(Dbxref annotSynonym, Organism organism,
			EntityMethodFactory methodFactory, EntityFeatureFactory featureFactory, EntityTypeFactory typeFactory) {
		System.out.println("Annotation not found: " + annotSynonym.toString());
		Sequence seq = organism.createNewSequence(ParametersDefault.getSeqVersionFake());
		Subsequence subseq = seq.createSubsequence(ParametersDefault.getSubseqStartFake(),
			ParametersDefault.getSubseqEndFake(), null);
		AnnotationMethod methodFake = methodFactory.getAnnotationMethod(ParametersDefault.getAnnotationMethodFake());
		Feature target = featureFactory.getFeature(ParametersDefault.getAnnotationFakeFeature());
		Collection<Type> annotTypesFake = new ArrayList<Type>(1);
		annotTypesFake.add(typeFactory.getType(ParametersDefault.getAnnotationFakeType()));
		Annotation<Subsequence, Feature> annot = (Annotation<Subsequence, Feature>) subseq.addAnnotation(target,
			methodFake, null, annotTypesFake);
		annot.addSynonym(annotSynonym);
		return annot;
	}

}

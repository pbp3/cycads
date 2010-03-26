package org.cycads.extract.pf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.cycads.entities.BasicEntity;
import org.cycads.entities.Feature;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.extract.general.AnnotationCluster;
import org.cycads.extract.general.AnnotationClustersGetter;
import org.cycads.extract.general.AnnotationClustersGetterRepository;
import org.cycads.extract.general.GetterExpressionException;
import org.cycads.extract.score.AnnotationScoreSystem;
import org.cycads.general.ParametersDefault;

public class PFFileCycRecordGenerator implements CycRecordGenerator
{

	CycIdGenerator						cycIdGenerator;
	AnnotationClustersGetterRepository	clusterRepository;
	double								ecThreshold;
	double								goThreshold;

	public static String				PREFIX_NAME			= ".pf";
	public static String				PRODUCT_TYPE		= PREFIX_NAME + ".productType";
	public static String				GENE_NAME			= PREFIX_NAME + ".geneName";
	public static String				GENE_SYNONYMS		= PREFIX_NAME + ".geneSynonyms";
	public static String				GENE_COMMENTS		= PREFIX_NAME + ".geneComments";
	public static String				GENE_DBLINKS		= PREFIX_NAME + ".geneDblinks";
	public static String				FUNCTION_NAME		= PREFIX_NAME + ".functionName";
	public static String				FUNCTION_SYNONYMS	= PREFIX_NAME + ".functionSynonyms";
	public static String				FUNCTION_COMMENTS	= PREFIX_NAME + ".functionComments";
	public static String				FUNCTION_SSEQUENCE	= PREFIX_NAME + ".functionSSequence";
	public static String				FUNCTION_ECS		= PREFIX_NAME + ".functionECs";
	public static String				FUNCTION_GOS		= PREFIX_NAME + ".functionGOs";

	public PFFileCycRecordGenerator(CycIdGenerator cycIdGenerator,
			AnnotationClustersGetterRepository clusterRepository, double ecThreshold, double goThreshold) {
		this.cycIdGenerator = cycIdGenerator;
		this.clusterRepository = clusterRepository;
		this.ecThreshold = ecThreshold;
		this.goThreshold = goThreshold;
	}

	@Override
	public SimpleCycRecord generate(Annotation< ? extends Subsequence, ? extends Feature> annot)
			throws GetterExpressionException {
		String id = getID(annot);
		String prodtype = clusterRepository.getFirstTargetStr(PRODUCT_TYPE, annot);
		SimpleCycRecord record = new SimpleCycRecord(prodtype, id);
		record.setName(clusterRepository.getFirstTargetStr(GENE_NAME, annot));
		record.setSynonyms(clusterRepository.getTargetsStr(GENE_SYNONYMS, annot));
		record.setComments(clusterRepository.getTargetsStr(GENE_COMMENTS, annot));
		record.setDBLinks(clusterRepository.getTargetsStr(GENE_DBLINKS, annot));

		String functionName = clusterRepository.getFirstTargetStr(FUNCTION_NAME, annot);
		CycFunction function = new SimpleCycFunction(functionName);
		function.setSynonyms(clusterRepository.getTargetsStr(FUNCTION_SYNONYMS, annot));
		function.setComments(clusterRepository.getTargetsStr(FUNCTION_COMMENTS, annot));
		record.addFunction(function);

		Object obj = clusterRepository.getFirstTarget(FUNCTION_SSEQUENCE, annot);
		if (!(obj instanceof Subsequence)) {
			throw new GetterExpressionException("Object is not a Subsequence. Object:" + obj);
		}
		Subsequence sseq = (Subsequence) obj;
		record.setStartBase(sseq.getStart());
		record.setEndBase(sseq.getEnd());
		record.setIntrons(sseq.getIntrons());

		List<AnnotationCluster> ecClusters = clusterRepository.getAnnotationClusterGetter(FUNCTION_ECS).getAnnotationClusters(
			annot);
		for (AnnotationCluster ecCluster : ecClusters) {
			if (ecCluster.getScore() >= ecThreshold) {
				record.addEC(ecCluster.getTarget().toString());
			}
		}

		List<AnnotationCluster> goClusters = clusterRepository.getAnnotationClusterGetter(FUNCTION_GOS).getAnnotationClusters(
			annot);
		for (AnnotationCluster goCluster : goClusters) {
			if (goCluster.getScore() >= goThreshold) {
				record.addGO(goCluster.getTarget().toString());
			}
		}

		return record;
	}

	private Type	cycIdNoteType	= null;

	private String getID(BasicEntity annot) {
		if (cycIdNoteType == null) {
			cycIdNoteType = annot.getNoteType(Config.getPFFileCycIdNoteType());
		}
		String id = annot.getNoteValue(cycIdNoteType);
		if (id == null) {
			id = cycIdGenerator.getNewID();
			annot.addNote(cycIdNoteType, id);
		}
		else if (id.length() == 0) {
			id = cycIdGenerator.getNewID();
			annot.setNoteValue(cycIdNoteType, id);
		}
		return id;
	}

}

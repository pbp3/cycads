/*
 * Created on 24/03/2010
 * Modified on 09/11/2011 PBP
 */
package org.cycads.extract.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cycads.extract.objectsGetter.changeObject.ChangeToStringReplaced;
import org.cycads.extract.parser.AnnotationWaysGetterReader;
import org.cycads.extract.parser.SimpleAnnotationWaysGetterHandler;
import org.cycads.extract.parser.SimpleAnnotationWaysGetterReader;
import org.cycads.extract.score.AnnotationWayListScoreSystem;
import org.cycads.extract.score.SimpleAnnotationWayListScoreSystem;
import org.cycads.general.Config;
import org.cycads.parser.ParserException;

public class ConfigAnnotationClustersGetterRepository implements AnnotationClustersGetterRepository
{

	HashMap<String, AnnotationClustersGetter>	clustersGetters	= new HashMap<String, AnnotationClustersGetter>();

	@Override
	public AnnotationClustersGetter getAnnotationClusterGetter(String clusterName) throws ParserException {
		AnnotationClustersGetter clustersGetter = null;
		if (clustersGetters.containsKey(clusterName)) {
			clustersGetter = clustersGetters.get(clusterName);
		}
		else {
			List<String> locations = Config.getAnnotationClusterLocs(clusterName);
			if (locations != null && !locations.isEmpty()) {
				AnnotationWayListScoreSystem scoreSystem = getScoreSystem(clusterName);
				List<String> replacesRegex = Config.getClusterReplaceRegex(clusterName);
				List<String> replacesReplacement = Config.getClusterReplaceReplacement(clusterName);
				List<ChangeToStringReplaced> modifiers = null;
				if (replacesRegex != null && !replacesRegex.isEmpty()) {
					modifiers = new ArrayList<ChangeToStringReplaced>(replacesRegex.size());
					for (int i = 0; i < replacesRegex.size(); i++) {
						if (replacesRegex.get(i) != null) {
							modifiers.add(new ChangeToStringReplaced(replacesRegex.get(i), replacesReplacement.get(i)));
						}
					}
				}
				String msgChangeTarget = Config.getClusterMsgChangeTarget(clusterName);
				AnnotationWaysGetterReader annotationWaysGetterReader = new SimpleAnnotationWaysGetterReader(
					new SimpleAnnotationWaysGetterHandler(modifiers));
				clustersGetter = new SimpleAnnotationClustersGetter(locations, annotationWaysGetterReader, scoreSystem);
				if (msgChangeTarget!=null && msgChangeTarget.length()>0) {
					clustersGetter.setMsgChangeTarget(msgChangeTarget);		
				}
			}
			clustersGetters.put(clusterName, clustersGetter);
		}
		return clustersGetter;
	}

	public AnnotationWayListScoreSystem getScoreSystem(String clusterName) {
		return new SimpleAnnotationWayListScoreSystem(Config.getAnnotScoreSystem(clusterName));
	}

	@Override
	public Object getFirstTarget(String clusterName, Object obj) throws GetterExpressionException, ParserException {
		AnnotationClustersGetter clusterGetter = getAnnotationClusterGetter(clusterName);
		if (clusterGetter != null) {
			return clusterGetter.getFirstTarget(obj);
		}
		else {
			throw new ParserException("Cluster getter doesn't exist:" + clusterName);
		}
	}

	@Override
	public String getFirstTargetStr(String clusterName, Object obj) throws GetterExpressionException, ParserException {
		AnnotationClustersGetter clusterGetter = getAnnotationClusterGetter(clusterName);
		if (clusterGetter != null) {
			return clusterGetter.getFirstTargetStr(obj);
		}
		else {
			throw new ParserException("Cluster getter doesn't exist:" + clusterName);
		}
	}

	@Override
	public List<Object> getTargets(String clusterName, Object obj) throws GetterExpressionException, ParserException {
		AnnotationClustersGetter clusterGetter = getAnnotationClusterGetter(clusterName);
		if (clusterGetter != null) {
			return clusterGetter.getTargets(obj);
		}
		else {
			throw new ParserException("Cluster getter doesn't exist:" + clusterName);
		}
	}

	@Override
	public List<String> getTargetsStr(String clusterName, Object obj) throws GetterExpressionException, ParserException {
		AnnotationClustersGetter clusterGetter = getAnnotationClusterGetter(clusterName);
		if (clusterGetter != null) {
			return clusterGetter.getTargetsStr(obj);
		}
		else {
			throw new ParserException("Cluster getter doesn't exist:" + clusterName);
		}
	}
}

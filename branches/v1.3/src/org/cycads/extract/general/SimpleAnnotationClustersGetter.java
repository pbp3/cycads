package org.cycads.extract.general;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.cycads.extract.parser.AnnotationWaysGetterReader;
import org.cycads.extract.parser.SimpleAnnotationWaysGetterReader;
import org.cycads.extract.score.AnnotationWayListScoreSystem;
import org.cycads.parser.ParserException;

public class SimpleAnnotationClustersGetter implements AnnotationClustersGetter
{

	List<AnnotationWaysGetter>		annotationWaysGetters;
	AnnotationWayListScoreSystem	scoreSystem;
	String							msgChangeTarget	= null;

	public SimpleAnnotationClustersGetter(AnnotationWayListScoreSystem scoreSystem,
			List<AnnotationWaysGetter> annotationWaysGetters) {
		this.annotationWaysGetters = annotationWaysGetters;
		this.scoreSystem = scoreSystem;
	}

	public SimpleAnnotationClustersGetter(List<String> locations,
			AnnotationWaysGetterReader annotationWaysGetterReader, AnnotationWayListScoreSystem scoreSystem)
			throws ParserException {
		this.scoreSystem = scoreSystem;
		annotationWaysGetters = new ArrayList<AnnotationWaysGetter>(locations.size());
		for (String location : locations) {
			annotationWaysGetters.add(annotationWaysGetterReader.parse(location));
		}
	}

	public SimpleAnnotationClustersGetter(List<String> locations, AnnotationWayListScoreSystem scoreSystem)
			throws ParserException {
		this(locations, new SimpleAnnotationWaysGetterReader(), scoreSystem);
	}

	@Override
	public List<AnnotationCluster> getAnnotationClusters(Object source) throws GetterExpressionException {
		List<AnnotationCluster> ret = new ArrayList<AnnotationCluster>();
		Hashtable<Object, AnnotationCluster> targets = new Hashtable<Object, AnnotationCluster>();
		AnnotationCluster cluster;
		for (AnnotationWaysGetter getter : annotationWaysGetters) {
			AnnotationWayList ways = getter.getAnnotationWays(source);
			for (AnnotationWay way : ways) {
				Object target = way.getTarget();
				cluster = targets.get(target);
				if (cluster == null) {
					cluster = new SimpleAnnotationCluster(source, target, scoreSystem, msgChangeTarget);
					targets.put(target, cluster);
					ret.add(cluster);
				}
				cluster.add(way);
			}
		}
		return ret;
	}

	@Override
	public List<AnnotationWaysGetter> getAnnotationWaysGetters() {
		return annotationWaysGetters;
	}

	@Override
	public AnnotationWayListScoreSystem getScoreSystem() {
		return scoreSystem;
	}

	@Override
	public List< ? extends Object> getObjects(Object obj) throws GetterExpressionException {
		return getAnnotationClusters(obj);
	}

	@Override
	public String getMsgChangeTarget() {
		return msgChangeTarget;
	}

	@Override
	public void setMsgChangeTarget(String msgChangeTarget) {
		this.msgChangeTarget = msgChangeTarget;
	}

	@Override
	public Object getFirstTarget(Object obj) throws GetterExpressionException {
		List<AnnotationCluster> clusters = getAnnotationClusters(obj);
		if (clusters != null && !clusters.isEmpty()) {
			return clusters.get(0).getTarget();
		}
		else {
			return null;
		}
	}

	@Override
	public String getFirstTargetStr(Object obj) throws GetterExpressionException {
		List<AnnotationCluster> clusters = getAnnotationClusters(obj);
		if (clusters != null && !clusters.isEmpty()) {
			return clusters.get(0).getTarget().toString();
		}
		else {
			return null;
		}
	}

	@Override
	public List<Object> getTargets(Object obj) throws GetterExpressionException {
		List<Object> ret = new ArrayList<Object>();
		List<AnnotationCluster> clusters = getAnnotationClusters(obj);
		if (clusters != null && !clusters.isEmpty()) {
			for (AnnotationCluster cluster : clusters) {
				ret.add(cluster.getTarget());
			}
		}
		return ret;
	}

	@Override
	public List<String> getTargetsStr(Object obj) throws GetterExpressionException {
		List<String> ret = new ArrayList<String>();
		List<AnnotationCluster> clusters = getAnnotationClusters(obj);
		if (clusters != null && !clusters.isEmpty()) {
			for (AnnotationCluster cluster : clusters) {
				ret.add(cluster.getTarget().toString());
			}
		}
		return ret;
	}

}

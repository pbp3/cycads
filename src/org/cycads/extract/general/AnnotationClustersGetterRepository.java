/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

public interface AnnotationClustersGetterRepository
{
	public AnnotationClustersGetter getAnnotationClusterGetter(String name);

	public List<Object> getTargets(String clusterName, Object obj) throws GetterExpressionException;

	public List<String> getTargetsStr(String clusterName, Object obj) throws GetterExpressionException;

	public String getFirstTargetStr(String clusterName, Object obj) throws GetterExpressionException;

	public Object getFirstTarget(String clusterName, Object obj) throws GetterExpressionException;

}

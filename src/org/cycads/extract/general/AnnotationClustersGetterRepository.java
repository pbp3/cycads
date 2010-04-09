/*
 * Created on 19/03/2010
 */
package org.cycads.extract.general;

import java.util.List;

import org.cycads.parser.ParserException;

public interface AnnotationClustersGetterRepository
{
	public AnnotationClustersGetter getAnnotationClusterGetter(String name) throws ParserException;

	public List<Object> getTargets(String clusterName, Object obj) throws GetterExpressionException, ParserException;

	public List<String> getTargetsStr(String clusterName, Object obj) throws GetterExpressionException, ParserException;

	public String getFirstTargetStr(String clusterName, Object obj) throws GetterExpressionException, ParserException;

	public Object getFirstTarget(String clusterName, Object obj) throws GetterExpressionException, ParserException;

}

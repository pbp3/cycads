/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.general.ParametersDefault;

public interface AnnotFeature<FEATURE_TYPE extends AnnotFeature< ? , ? , ? >, SS extends AnnotFeatureSource< ? , ? , ? , ? , ? >, M extends AnnotationMethod>
		extends Annotation<FEATURE_TYPE, SS, M>
{

	public static String	CDS_TYPE	= ParametersDefault.featureTypeCDS();
	public static String	MRNA_TYPE	= ParametersDefault.featureTypeMRNA();
	public static String	GENE_TYPE	= ParametersDefault.featureTypeGene();

	public String getType();

	public Collection<String> getFunctions();

	public void addFunction(String function);

}
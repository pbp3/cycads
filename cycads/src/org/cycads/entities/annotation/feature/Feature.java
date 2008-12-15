/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.feature;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Sequence;
import org.cycads.general.ParametersDefault;

public interface Feature<FEATURE_TYPE extends Feature< ? , ? , ? , ? >, L extends Location< ? , ? , ? , ? , ? , ? >, SEQ extends Sequence< ? , ? , ? , ? , ? , ? >, M extends AnnotationMethod>
		extends Annotation<FEATURE_TYPE, L, M>
{

	public static String	CDS_TYPE	= ParametersDefault.featureTypeCDS();
	public static String	MRNA_TYPE	= ParametersDefault.featureTypeMRNA();
	public static String	GENE_TYPE	= ParametersDefault.featureTypeGene();

	public String getType();

	public SEQ getSequence();

	public void addContainFeature(FEATURE_TYPE feature);

	public Collection<FEATURE_TYPE> getFeaturesContains();

	public Collection<FEATURE_TYPE> getFeaturesContainers();

	//	public L getLocation();

}
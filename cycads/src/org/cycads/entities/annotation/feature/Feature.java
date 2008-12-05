/*
 * Created on 12/11/2008
 */
package org.cycads.entities.annotation.feature;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.sequence.Location;
import org.cycads.entities.sequence.Sequence;
import org.cycads.general.ParametersDefault;
import org.cycads.general.biojava.TermsAndOntologies;

public interface Feature<L extends Location< ? , ? , ? , ? , ? >, SEQ extends Sequence< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends Annotation<L, M>
{

	public static String CDS_TYPE=ParametersDefault.;
	public String getType();

	public SEQ getSequence();

	//	public L getLocation();

}
/*
 * Created on 11/12/2008
 */
package org.cycads.entities.annotation.BJ;

import java.util.Collection;

import org.biojavax.RankedCrossRef;
import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotOntology;
import org.cycads.entities.sequence.BJ.SubsequenceBJ;
import org.cycads.general.biojava.TermsAndOntologies;

public class SubseqOntologyAnnotBJ
		extends
		AnnotationRichFeatureBJ<SubseqOntologyAnnotBJ, AnnotationRichFeatureBJ< ? , ? , ? >, AnnotationRichFeatureBJ< ? , ? , ? >>
		implements AnnotOntology<SubseqOntologyAnnotBJ, SubsequenceBJ, OntologyBJ, AnnotationMethodBJ>
{
	OntologyBJ	target;

	public SubseqOntologyAnnotBJ(RichFeature feature) {
		super(feature);
		//verify consistency of parameter
		if (!isOntologyAnnot(feature)) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public OntologyBJ getOntologyTarget() {
		if (target == null) {
			Collection<RankedCrossRef> crossRefs = getRichFeature().getRankedCrossRefs();
			for (RankedCrossRef crossRef : crossRefs) {
				target = new OntologyBJ(crossRef.getCrossRef());
			}
		}
		return target;
	}

	public static boolean isOntologyAnnot(RichFeature feature) {
		return (isAnnotation(feature) && feature.getTypeTerm().equals(TermsAndOntologies.getTermOntologyAnnotType()) && feature.getRankedCrossRefs().size() == 1);
	}

}

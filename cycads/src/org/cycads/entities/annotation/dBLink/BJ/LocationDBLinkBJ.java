/*
 * Created on 11/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.biojavax.RankedCrossRef;
import org.biojavax.bio.seq.RichFeature;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.AnnotationRichFeatureBJ;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.general.biojava.TermsAndOntologies;

public class LocationDBLinkBJ extends AnnotationRichFeatureBJ<LocationDBLinkBJ, LocationDBLinkBJ, LocationDBLinkBJ>
		implements DBLink<LocationDBLinkBJ, LocationBJ, DBRecordBJ, AnnotationMethodBJ>
{
	DBRecordBJ	target;

	public LocationDBLinkBJ(RichFeature feature) {
		super(feature);
		//verify consistency of parameter
		if (!feature.getTypeTerm().equals(TermsAndOntologies.getTermDBLinkType())
			|| feature.getRankedCrossRefs().size() != 1) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public DBRecordBJ getDBRecordTarget() {
		if (target == null) {
			Collection<RankedCrossRef> crossRefs = getRichFeature().getRankedCrossRefs();
			for (RankedCrossRef crossRef : crossRefs) {
				target = new DBRecordBJ(crossRef.getCrossRef());
			}
		}
		return target;
	}

}

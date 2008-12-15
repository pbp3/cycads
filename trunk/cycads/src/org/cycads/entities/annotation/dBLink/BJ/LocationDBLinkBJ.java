/*
 * Created on 11/12/2008
 */
package org.cycads.entities.annotation.dBLink.BJ;

import java.util.Collection;

import org.biojavax.RankedCrossRef;
import org.biojavax.bio.seq.RichLocation;
import org.cycads.entities.annotation.AnnotationMethodBJ;
import org.cycads.entities.annotation.dBLink.DBLink;
import org.cycads.entities.annotation.feature.FeatureBJ;
import org.cycads.entities.sequence.LocationBJ;
import org.cycads.general.biojava.TermsAndOntologies;

public class LocationDBLinkBJ extends FeatureBJ<LocationDBLinkBJ>
		implements DBLink<LocationDBLinkBJ, LocationBJ, DBRecordBJ, AnnotationMethodBJ>
{
	DBRecordBJ	target;

	public LocationDBLinkBJ(LocationBJ source) {
		super(source);
		//verify consistency of parameter
		if (!getTypeTerm().equals(TermsAndOntologies.getTermDBLinkType())
			|| getSource().getRichFeature().getRankedCrossRefs().size() != 1) {
			throw new IllegalArgumentException();
		}
	}

	public LocationDBLinkBJ(RichLocation location) {
		this(new LocationBJ(location));
	}

	@Override
	public DBRecordBJ getDBRecordTarget() {
		if (target == null) {
			Collection<RankedCrossRef> crossRefs = getSource().getRichFeature().getRankedCrossRefs();
			for (RankedCrossRef crossRef : crossRefs) {
				target = new DBRecordBJ(crossRef.getCrossRef());
			}
		}
		return target;
	}

}

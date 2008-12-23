/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotFeature;
import org.cycads.entities.annotation.AnnotFeatureSource;
import org.cycads.entities.annotation.AnnotOntology;
import org.cycads.entities.annotation.AnnotOntologySource;
import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.CDS;
import org.cycads.entities.annotation.DBRecord;
import org.cycads.entities.annotation.DBRecordsContainer;
import org.cycads.entities.annotation.Gene;
import org.cycads.entities.annotation.Ontology;
import org.cycads.entities.annotation.RNA;

public interface Subsequence<OA extends AnnotOntology<OA, ? extends Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? >, O, M>, O extends Ontology< ? , ? , ? , ? >, M extends AnnotationMethod, R extends DBRecord< ? >, SEQ extends Sequence< ? , ? , ? , ? >, F extends AnnotFeature< ? , ? , ? >, C extends CDS< ? , ? , ? , ? >, RN extends RNA< ? , ? , ? , ? , ? >, G extends Gene< ? , ? , ? , ? >>
		extends AnnotFeatureSource<F, C, RN, G, M>, AnnotOntologySource<OA, O, M>, DBRecordsContainer<R>
{

	public Collection<Intron> getIntrons();

	public boolean addIntron(Intron intron);

	public Intron addIntron(int startPos, int endPos);

	public boolean isPositiveStrand();

	public int getEnd();

	public int getStart();

	public int getMinPosition();

	public int getMaxPosition();

	public SEQ getSequence();

}
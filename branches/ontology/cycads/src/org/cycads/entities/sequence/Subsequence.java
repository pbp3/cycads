/*
 * Created on 11/11/2008
 */
package org.cycads.entities.sequence;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.annotation.dBLink.DBRecord;
import org.cycads.entities.annotation.dBLink.DBRecordsContainer;
import org.cycads.entities.annotation.dBLink.Ontology;
import org.cycads.entities.annotation.dBLink.OntologyAnnot;
import org.cycads.entities.annotation.dBLink.OntologyAnnotContainer;
import org.cycads.entities.annotation.dBLink.OntologyAnnotSource;
import org.cycads.entities.annotation.feature.CDS;
import org.cycads.entities.annotation.feature.Feature;
import org.cycads.entities.annotation.feature.FeatureCollection;
import org.cycads.entities.annotation.feature.FeatureSource;
import org.cycads.entities.annotation.feature.Gene;
import org.cycads.entities.annotation.feature.RNA;

public interface Subsequence<SSEQ extends Subsequence< ? , ? , ? , ? , ? , ? , ? , ? , ? , ? >, OA extends OntologyAnnot<OA, SSEQ, O, M>, O extends Ontology< ? , ? , ? >, M extends AnnotationMethod, R extends DBRecord, SEQ extends Sequence< ? , ? , ? , ? >, F extends Feature< ? , ? , ? , ? >, C extends CDS< ? , ? , ? , ? , ? >, RN extends RNA< ? , ? , ? , ? , ? , ? >, G extends Gene< ? , ? , ? , ? , ? >>
		extends FeatureSource<F, C, RN, G, M>, FeatureCollection<F, SSEQ, M, C, RN, G>, OntologyAnnotSource<OA, O, M>,
		OntologyAnnotContainer<OA, SSEQ, O, M>, DBRecordsContainer<R>
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
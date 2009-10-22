/*
 * Created on 22/10/2009
 */
package org.cycads.entities;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Sequence;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public class SubseqAnnotationFinderBySynonym implements EntityFinder<Annotation< ? extends Subsequence, ? >>
{

	EntityFinder< ? >										finder;
	Sequence												sequence;
	EntityFilter<Annotation< ? extends Subsequence, ? >>	filter;

	@Override
	public Collection<Annotation< ? extends Subsequence, ? >> getEntitiesBySynonym(String dbName, String accession,
			Type type, EntityFilter<Annotation< ? extends Subsequence, ? >> filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Annotation< ? extends Subsequence, ? >> getEntitiesBySynonym(Dbxref synonym, Type type,
			EntityFilter<Annotation< ? extends Subsequence, ? >> filter) {
		// TODO Auto-generated method stub
		return null;
	}

}

package org.cycads.entities.annotation;

import java.util.Collection;

import org.cycads.entities.EntityFinder;
import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public class SequenceAnnotationFinder implements EntityFinder< ? extends Annotation< ? extends Subsequence, ? >>
{

	EntityDbxrefFactory	dbxrefFactory;

	@Override
	public Collection<Annotation< ? extends Subsequence, ? >> getEntitiesBySynonym(Type type, String dbName,
			String accession) {
	}

	@Override
	public Collection<Annotation< ? extends Subsequence, ? >> getEntitiesBySynonym(Type type, Dbxref synonym) {
	}

}

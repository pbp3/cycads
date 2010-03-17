/*
 * Created on 15/09/2009
 */
package org.cycads.parser.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.cycads.entities.factory.EntityDbxrefFactory;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.HasSynonyms;

public class AddSynonym<S extends HasSynonyms> extends SimpleRelationshipOperation<S, Dbxref>
{

	private final EntityDbxrefFactory	factory;
	private final String				synonymDBName;

	public AddSynonym(Pattern tagNameRegex, Pattern tagValueRegex, String synonymDBName, EntityDbxrefFactory factory) {
		super(tagNameRegex, tagValueRegex);
		this.synonymDBName = synonymDBName;
		this.factory = factory;
	}

	@Override
	protected Collection<Dbxref> execute(S source, Note note) {

		Dbxref synonym = factory.getDbxref(synonymDBName, note.getValue());
		source.addSynonym(synonym);
		Collection<Dbxref> ret = new ArrayList<Dbxref>(1);
		ret.add(synonym);
		return ret;
	}

}

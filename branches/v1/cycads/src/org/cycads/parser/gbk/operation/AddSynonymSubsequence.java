/*
 * Created on 15/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;
import org.cycads.entities.EntityFactory;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;

public class AddSynonymSubsequence extends SimpleSubsequenceOperation<Dbxref>
{

	private EntityFactory	factory;
	private String			synonymDBName;

	protected AddSynonymSubsequence(Pattern tagNameRegex, Pattern tagValueRegex, String synonymDBName,
			EntityFactory factory) {
		super(tagNameRegex, tagValueRegex);
		this.synonymDBName = synonymDBName;
		this.factory = factory;
	}

	@Override
	protected Collection<Dbxref> execute(Subsequence source, Note note) {
		Dbxref synonym = factory.getDbxref(synonymDBName, note.getValue());
		source.addSynonym(synonym);
		Collection<Dbxref> ret = new ArrayList<Dbxref>();
		ret.add(synonym);
		return ret;
	}
}

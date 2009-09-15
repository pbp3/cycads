/*
 * Created on 15/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.Collection;
import java.util.regex.Pattern;

import org.biojavax.Note;

public abstract class SimpleRelationshipOperation<S, O> extends SimpleOperation implements RelationshipOperation<S, O>
{

	protected SimpleRelationshipOperation(Pattern tagNameRegex, Pattern tagValueRegex) {
		super(tagNameRegex, tagValueRegex);
	}

	@Override
	public Collection<O> relate(S source, Note note) {
		if (match(note)) {
			return execute(source, note);
		}
		return null;
	}

	protected abstract Collection<O> execute(S source, Note note);

}

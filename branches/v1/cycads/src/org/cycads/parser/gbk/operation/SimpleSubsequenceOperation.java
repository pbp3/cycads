/*
 * Created on 15/09/2009
 */
package org.cycads.parser.gbk.operation;

import java.util.regex.Pattern;

import org.cycads.entities.sequence.Subsequence;

public abstract class SimpleSubsequenceOperation<O> extends SimpleRelationshipOperation<Subsequence, O>
		implements SubsequenceOperation<O>
{

	protected SimpleSubsequenceOperation(Pattern tagNameRegex, Pattern tagValueRegex) {
		super(tagNameRegex, tagValueRegex);
		// TODO Auto-generated constructor stub
	}

}

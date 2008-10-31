/*
 * Created on 29/10/2008
 */
package org.cycads.entities;

import org.biojavax.bio.seq.RichSequence;

public class SequenceBJ implements Sequence
{
	RichSequence	seq;

	public SequenceBJ(RichSequence seq) {
		this.seq = seq;
	}

}

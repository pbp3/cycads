/*
 * Created on 14/09/2009
 */
package org.cycads.parser.gbk;

import java.util.Collection;

import org.biojavax.Note;

public interface Operation
{
	public boolean match(Note note);

	public Collection<Note> transform(Note note);
}

/*
 * Created on 25/09/2009
 */
package org.cycads.parser.association;

import java.util.Collection;

import org.cycads.entities.annotation.AnnotationMethod;
import org.cycads.entities.note.Note;

public class SimpleAnnotationRecord<S, T> extends SimpleAssociationRecord<S, T> implements AnnotationRecord<S, T>
{

	private String				score;
	private AnnotationMethod	method;
	private Collection<Note>	notes;

	public SimpleAnnotationRecord(S source, T target, String score, AnnotationMethod method, Collection<Note> notes) {
		super(source, target);
		this.score = score;
		this.method = method;
		this.notes = notes;
	}

	@Override
	public String getScore() {
		return score;
	}

	@Override
	public AnnotationMethod getMethod() {
		return method;
	}

	@Override
	public Collection<Note> getNotes() {
		return notes;
	}

}

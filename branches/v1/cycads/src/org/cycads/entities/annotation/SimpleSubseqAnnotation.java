package org.cycads.entities.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.cycads.entities.EntityFactory;
import org.cycads.entities.note.Note;
import org.cycads.entities.note.SimpleNote;
import org.cycads.entities.note.Type;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.entities.synonym.Dbxref;
import org.cycads.entities.synonym.SimpleHasSynonymsNoteble;

public class SimpleSubseqAnnotation<AParent extends Annotation<?, ?, ?, ?>, SS extends Subsequence<?, ?, ?, ?, ?, ?>, X extends Dbxref<?, ?, ?, ?>, T extends Type, M extends AnnotationMethod>
		extends SimpleAnnotation<AParent, X, T , M> implements SubseqAnnotation<AParent, SS, X, T, M> {

	EntityFactory<? extends X, ? extends M, ? extends T, ?> factory;

	SS subsequence;
	M method;

	public SimpleSubseqAnnotation(EntityFactory<? extends X, ? extends M, ? extends T, ?> factory, SS subsequence, M method) {
		super(factory, method);
		this.subsequence = subsequence;
	}

	@Override
	public SS getSubsequence() {
		return subsequence;
	}

	public void setSubsequence(SS subsequence) {
		this.subsequence = subsequence;
	}

}

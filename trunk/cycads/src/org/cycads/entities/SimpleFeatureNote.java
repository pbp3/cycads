/*
 * Created on 04/11/2008
 */
package org.cycads.entities;

public class SimpleFeatureNote implements FeatureNote
{
	SequenceFeature	sequenceFeature;
	FeatureNoteType	type;
	String			value;

	public SimpleFeatureNote(SequenceFeature sequenceFeature, FeatureNoteType type, String value) {
		this.sequenceFeature = sequenceFeature;
		this.type = type;
		this.value = value;
	}

	public SequenceFeature getSequenceFeature() {
		return sequenceFeature;
	}

	public FeatureNoteType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

}

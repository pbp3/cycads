package org.cycads.entities.refact;
import java.util.Collection;
import java.util.Iterator;

public class SeqFeatureDBLink extends DBLink
{

	/*
	* (non-javadoc)
	*/
	private SequenceFeature	sequenceFeature	= null;

	/**
	 * Getter of the property <tt>sequenceFeature</tt>
	 * 
	 * @return Returns the sequenceFeature.
	 * 
	 */
	public SequenceFeature getSequenceFeature() {
		return sequenceFeature;
	}

}

///**
//	 * Returns an iterator over the elements in this collection.
//	 * 
//	 * @return an <tt>Iterator</tt> over the elements in this collection
//	 * @see java.util.Collection#iterator()
//	 * 
//	 */
//	public Iterator<SequenceFeature> sequenceFeatureIterator() {
//		return sequenceFeature.iterator();
//	}
///**
//	 * Getter of the property <tt>sequenceFeature</tt>
//	 * 
//	 * @return Returns the sequenceFeature.
//	 * 
//	 */
//	public Collection<SequenceFeature> getSequenceFeature() {
//		return sequenceFeature;
//	}
///**
//	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
//	 * 
//	 * @param elements collection to be checked for containment in this collection.
//	 * @see java.util.Collection#containsAll(Collection)
//	 * 
//	 */
//	public boolean containsAllSequenceFeature(Collection<SequenceFeature> sequenceFeature) {
//		return this.sequenceFeature.containsAll(sequenceFeature);
//	}
///**
//	 * Returns all elements of this collection in an array.
//	 * 
//	 * @return an array containing all of the elements in this collection
//	 * @see java.util.Collection#toArray()
//	 * 
//	 */
//	public SequenceFeature[] sequenceFeatureToArray() {
//		return sequenceFeature.toArray(new SequenceFeature[sequenceFeature.size()]);
//	}
///**
//	 * Returns <tt>true</tt> if this collection contains the specified element.
//	 * 
//	 * @param element whose presence in this collection is to be tested.
//	 * @see java.util.Collection#contains(Object)
//	 * 
//	 */
//	public boolean containsSequenceFeature(SequenceFeature sequenceFeature) {
//		return this.sequenceFeature.contains(sequenceFeature);
//	}
///**
//	 * Returns the number of elements in this collection.
//	 * 
//	 * @return the number of elements in this collection
//	 * @see java.util.Collection#size()
//	 * 
//	 */
//	public int sequenceFeatureSize() {
//		return sequenceFeature.size();
//	}
///**
//	 * Returns <tt>true</tt> if this collection contains no elements.
//	 * 
//	 * @return <tt>true</tt> if this collection contains no elements
//	 * @see java.util.Collection#isEmpty()
//	 * 
//	 */
//	public boolean isSequenceFeatureEmpty() {
//		return sequenceFeature.isEmpty();
//	}
package org.cycads.entities.refact;

import java.util.Collection;

/**
 */
public class Feature
{
	private String						name;
	private Location					location;
	private FeatureAnnotationMethod		method;

	private Collection<FeatureNote>		notes;

	private Collection<FeatureDBLink>	seqFeatureDBLinks;

	private Sequence					sequence;

	/**
	 * Getter of the property <tt>sequence</tt>
	 * 
	 * @return Returns the sequence.
	 * 
	 */
	public Sequence getSequence()
	{
		return sequence;
	}

	public Location getLoaction()
	{
		return location;
	}

	public FeatureAnnotationMethod getMethod()
	{
		return method;
	}

	public Collection<FeatureNote> getNotes()
	{
		return notes;
	}

	public String getName()
	{
		return name;
	}

	public Collection<FeatureDBLink> getSeqFeatureDBLinks()
	{
		return seqFeatureDBLinks;
	}

}

// /**
// * Getter of the property <tt>externalLinks</tt>
// *
// * @return Returns the externalLinks.
// *
// */
// public Collection<DBLink> getExternalLinks()
// {
// return externalLinks;
// }
// /**
// * Returns <tt>true</tt> if this collection contains no elements.
// *
// * @return <tt>true</tt> if this collection contains no elements
// * @see java.util.Collection#isEmpty()
// *
// */
// public boolean isExternalLinksEmpty(){
// return externalLinks.isEmpty();
// }
// /**
// * Returns <tt>true</tt> if this collection contains all of the elements
// * in the specified collection.
// *
// * @param elements collection to be checked for containment in this collection.
// * @see java.util.Collection#containsAll(Collection)
// *
// */
// public boolean containsAllExternalLinks(Collection<DBLink> externalLinks){
// return this.externalLinks.containsAll(externalLinks);
// }
// /**
// * Returns <tt>true</tt> if this collection contains the specified element.
// *
// * @param element whose presence in this collection is to be tested.
// * @see java.util.Collection#contains(Object)
// *
// */
// public boolean containsExternalLinks(DBLink externalLinks){
// return this.externalLinks.contains(externalLinks);
// }
// /**
// * Returns the number of elements in this collection.
// *
// * @return the number of elements in this collection
// * @see java.util.Collection#size()
// *
// */
// public int externalLinksSize(){
// return externalLinks.size();
// }
// /**
// * Returns an iterator over the elements in this collection.
// *
// * @return an <tt>Iterator</tt> over the elements in this collection
// * @see java.util.Collection#iterator()
// *
// */
// public Iterator<DBLink> externalLinksIterator(){
// return externalLinks.iterator();
// }
// /**
// * Returns all elements of this collection in an array.
// *
// * @return an array containing all of the elements in this collection
// * @see java.util.Collection#toArray()
// *
// */
// public DBLink[] externalLinksToArray(){
// return externalLinks.toArray(new DBLink[externalLinks.size()]);
// }
// /**
// * Getter of the property <tt>sequence</tt>
// *
// * @return Returns the sequence.
// *
// */
// public Sequence getSequence()
// {
// return sequence;
// }
// /**
// * Getter of the property <tt>externalLinks</tt>
// *
// * @return Returns the externalLinks.
// *
// */
// public Collection<DBLink> getExternalLinks()
// {
// return externalLinks;
// }
// /**
// * Getter of the property <tt>externalLinks</tt>
// *
// * @return Returns the externalLinks.
// *
// */
// public Collection<DBLink> getExternalLinks()
// {
// return externalLinks;
// }
// /**
// * Returns <tt>true</tt> if this collection contains no elements.
// *
// * @return <tt>true</tt> if this collection contains no elements
// * @see java.util.Collection#isEmpty()
// *
// */
// public boolean isExternalLinksEmpty(){
// return externalLinks.isEmpty();
// }
// /**
// * Returns <tt>true</tt> if this collection contains all of the elements
// * in the specified collection.
// *
// * @param elements collection to be checked for containment in this collection.
// * @see java.util.Collection#containsAll(Collection)
// *
// */
// public boolean containsAllExternalLinks(Collection<DBLink> externalLinks){
// return this.externalLinks.containsAll(externalLinks);
// }
// /**
// * Returns <tt>true</tt> if this collection contains the specified element.
// *
// * @param element whose presence in this collection is to be tested.
// * @see java.util.Collection#contains(Object)
// *
// */
// public boolean containsExternalLinks(DBLink externalLinks){
// return this.externalLinks.contains(externalLinks);
// }
// /**
// * Returns the number of elements in this collection.
// *
// * @return the number of elements in this collection
// * @see java.util.Collection#size()
// *
// */
// public int externalLinksSize(){
// return externalLinks.size();
// }
// /**
// * Returns an iterator over the elements in this collection.
// *
// * @return an <tt>Iterator</tt> over the elements in this collection
// * @see java.util.Collection#iterator()
// *
// */
// public Iterator<DBLink> externalLinksIterator(){
// return externalLinks.iterator();
// }
// /**
// * Returns all elements of this collection in an array.
// *
// * @return an array containing all of the elements in this collection
// * @see java.util.Collection#toArray()
// *
// */
// public DBLink[] externalLinksToArray(){
// return externalLinks.toArray(new DBLink[externalLinks.size()]);
// }
// /**
// * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
// *
// * @param elements collection to be checked for containment in this collection.
// * @see java.util.Collection#containsAll(Collection)
// *
// */
// public boolean containsAllSeqFeatureDBLink(Collection<SeqFeatureDBLink> seqFeatureDBLink) {
// return this.seqFeatureDBLink.containsAll(seqFeatureDBLink);
// }
// /**
// * Getter of the property <tt>seqFeatureDBLink</tt>
// *
// * @return Returns the seqFeatureDBLink.
// *
// */
// public Collection<SeqFeatureDBLink> getSeqFeatureDBLink() {
// return seqFeatureDBLink;
// }

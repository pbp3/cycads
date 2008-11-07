package org.cycads.entities.refact;
import java.util.Collection;

/** 
 */
public class DBRecord
{

	ExternalDatabase					database;
	/**
	 * 
	 */
	private Collection<DBLinkDBLink>	dbLinks;

	/**
	 * Getter of the property <tt>dbLinks</tt>
	 * 
	 * @return Returns the dbLinks.
	 * 
	 */
	public Collection<DBLinkDBLink> getDbLinks() {
		return dbLinks;
	}

	/*
	 * (non-javadoc)
	 */
	private String	accession;

	/**
	 * Getter of the property <tt>accession</tt>
	 * 
	 * @return Returns the accession.
	 * 
	 */
	public String getAccession() {
		return accession;
	}

}

///**
// * Getter of the property <tt>seqFeatureDBRecord</tt>
// *
// * @return Returns the seqFeatureDBRecord.
// *
// */
//public DBLink getSeqFeatureDBRecord()
//{
//	return seqFeatureDBRecord;
//}
///**
// * Setter of the property <tt>seqFeatureDBRecord</tt>
// *
// * @param seqFeatureDBRecord The seqFeatureDBRecord to set.
// *
// */
//public void setSeqFeatureDBRecord(DBLink seqFeatureDBRecord ){
//	this.seqFeatureDBRecord = seqFeatureDBRecord;
//}
///**
// * Getter of the property <tt>seqFeatureDBRecords</tt>
// *
// * @return Returns the seqFeatureDBRecords.
// *
// */
//public Collection<DBLink> getSeqFeatureDBRecords()
//{
//	return seqFeatureDBRecords;
//}
///**
// * Returns <tt>true</tt> if this collection contains all of the elements
// * in the specified collection.
// *
// * @param elements collection to be checked for containment in this collection.
// * @see	java.util.Collection#containsAll(Collection)
// *
// */
//public boolean containsAllSeqFeatureDBRecords(Collection<DBLink> seqFeatureDBRecords){
//	return this.seqFeatureDBRecords.containsAll(seqFeatureDBRecords);
//}
///**
//	 * Getter of the property <tt>database</tt>
//	 *
//	 * @return Returns the database.
//	 *
//	 */
//	public Term getDatabase() {
//		return database;
//	}
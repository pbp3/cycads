/*
 * Created on 16/09/2008
 */
package org.cycads.generators;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.biojava.bio.symbol.Location;
import org.biojavax.Note;
import org.biojavax.RankedCrossRef;
import org.biojavax.RichAnnotation;
import org.biojavax.bio.seq.RichFeature;
import org.biojavax.bio.seq.RichSequence;
import org.biojavax.ontology.ComparableTerm;
import org.cycads.entities.biojava1.DBLink;
import org.cycads.entities.biojava1.Function;
import org.cycads.general.CacheCleanerController;
import org.cycads.general.CacheCleanerListener;

public abstract class SimpleGeneRecord implements BioCycRecord
{
	String					id;
	String					name;
	String					type;
	String					productID;
	Collection<String>		comments	= new ArrayList<String>();
	Collection<DBLink>		dbLinks		= new ArrayList<DBLink>();
	Collection<String>		ecs			= new ArrayList<String>(1);
	int						endBase, startBase;
	Collection<Intron>		introns		= new ArrayList<Intron>();
	Collection<Function>	functions	= new ArrayList<Function>(1);
	Collection<String>		synonyms	= new ArrayList<String>();
	RichFeature				feature;

	public SimpleGeneRecord(RichFeature geneProduct) throws Exception {
		this.feature = geneProduct;
		Location loc = geneProduct.getLocation();
		if (geneProduct.getStrand().getValue() > 0) {
			setStartBase(loc.getMin());
			setEndBase(loc.getMax());
		}
		else {
			setStartBase(loc.getMax());
			setEndBase(loc.getMin());
		}
		if (!loc.isContiguous()) {
			Iterator it = loc.blockIterator();
			Location loc1 = (Location) it.next();
			while (it.hasNext()) {
				Location loc2 = (Location) it.next();
				if (loc1.getMax() < loc2.getMin()) {
					addIntron(new Intron(loc1.getMax() + 1, loc2.getMin() - 1));
				}
				else if (loc2.getMax() < loc1.getMin()) {
					addIntron(new Intron(loc2.getMax() + 1, loc1.getMin() - 1));
				}
				loc1 = loc2;
			}
		}
		RichAnnotation annotation = (RichAnnotation) geneProduct.getAnnotation();
		RichFeature featureParent = BioSql.getParent(feature);
		RichAnnotation annotationParent = null;
		if (featureParent != null) {
			annotationParent = (RichAnnotation) featureParent.getAnnotation();
		}
		Note[] notes;
		notes = annotation.getProperties("BiocycID");
		if (notes.length == 0) {
			setId(getNextBiocycId());
			annotation.setProperty("BiocycID", getId());
		}
		else {
			setId(notes[0].getValue());
		}

		notes = annotation.getProperties("product");
		for (Note note : notes) {
			addFunction(new Function(note.getValue(), Messages.getString("Function.Comment.Product"), null));
			setName(note.getValue());
		}
		notes = annotation.getProperties(getProductIdTerm());
		for (Note note : notes) {
			setProductID(note.getValue());
		}
		notes = annotation.getProperties("note");
		if (notes.length == 0 && annotationParent != null) {
			notes = annotationParent.getProperties("note");
		}
		for (Note note : notes) {
			addComment(note.getValue());
		}
		notes = annotation.getProperties("anticodon");
		for (Note note : notes) {
			addComment("anticodon=" + note.getValue());
		}
		notes = annotation.getProperties("codon_start");
		for (Note note : notes) {
			addComment("codon_start=" + note.getValue());
		}
		notes = annotation.getProperties("exception");
		for (Note note : notes) {
			addComment("exception=" + note.getValue());
		}
		notes = annotation.getProperties("pseudo");
		if (notes.length > 0) {
			addComment("pseudo");
		}
		notes = annotation.getProperties("function");
		for (Note note : notes) {
			addComment("function=" + note.getValue());
		}
		notes = annotation.getProperties("transl_except");
		for (Note note : notes) {
			addComment("transl_except=" + note.getValue());
		}
		notes = annotation.getProperties("gene");
		for (Note note : notes) {
			addSynonym(note.getValue());
		}
		Set<RankedCrossRef> refs = geneProduct.getRankedCrossRefs();
		//		boolean canSetId = true;
		for (RankedCrossRef ref : refs) {
			addDBLink(ref.getCrossRef().getDbname(), ref.getCrossRef().getAccession());
			//			if (ref.getCrossRef().getDbname().equals("GI")) {
			//				setId(ref.getCrossRef().getAccession());
			//				canSetId = false;
			//			}
			//			if (ref.getCrossRef().getDbname().equals("GeneID") && canSetId) {
			//				setId(ref.getCrossRef().getAccession());
			//			}
		}
		addDBLink(Messages.getString("DBLink.DB.SequenceProductId"),
			((RichSequence) geneProduct.getSequence()).getAccession());

		Collection<ComparableTerm> methodsKO = getMethodsToLinkToKO();
		for (ComparableTerm methodKO : methodsKO) {
			//termKo need to be String because BIOJAVA
			notes = annotation.getProperties(methodKO.getName());
			for (Note note : notes) {
				KO ko = new KO(note.getValue());
				Collection<EC> ecs = ko.getECs();
				for (EC ec : ecs) {
					addEC(ec);
				}
				addDBLink(Messages.getString("DBLink.DB.KO"), ko.getId());
				String function = ko.getDefinition();
				if (function != null && function.length() > 0) {
					addFunction(new Function(function, methodKO.getDescription(), null));
				}
			}
		}
	}

	public abstract Collection<ComparableTerm> getMethodsToLinkToKO();

	public abstract String getProductIdTerm();

	public boolean isValid() {
		return (id != null && id.length() > 0 && name != null && name.length() > 0 && functions.size() > 0
			&& getType() != null && getType().length() > 0);
	}

	public void shiftLocation(int shiftQtty) {
		setStartBase(getStartBase() + shiftQtty);
		setEndBase(getEndBase() + shiftQtty);
		Collection<Intron> introns = getIntrons();
		for (Intron intron : introns) {
			intron.shift(shiftQtty);
		}
	}

	public boolean addDBLink(DBLink dbLink) {
		return dbLinks.add(dbLink);
	}

	public boolean addDBLink(String db, String acession) throws Exception {
		return addDBLink(new DBLink(db, acession));
	}

	public boolean addEC(String ec) {
		return ecs.add(ec);
	}

	public boolean addEC(EC ec) {
		return ecs.add(ec.getId());
	}

	public boolean addSynonym(String synonym) {
		return synonyms.add(synonym);
	}

	public boolean addFunction(Function function) {
		return functions.add(function);
	}

	public boolean addIntron(Intron intron) {
		return introns.add(intron);
	}

	public void addComment(String comment) {
		this.comments.add(comment);
	}

	public Collection<String> getComments() {
		return comments;
	}

	public Collection<DBLink> getDBLinks() {
		return dbLinks;
	}

	public Collection<String> getECs() {
		return ecs;
	}

	public int getEndBase() {
		return endBase;
	}

	public int getStartBase() {
		return startBase;
	}

	public Collection<Function> getFunctions() {
		return functions;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getProductID() {
		return productID;
	}

	public Collection<String> getSynonyms() {
		return synonyms;
	}

	public String getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public void setEndBase(int endBase) {
		this.endBase = endBase;
	}

	public void setStartBase(int startBase) {
		this.startBase = startBase;
	}

	public Collection<Intron> getIntrons() {
		return introns;
	}

	public static synchronized String getNextBiocycId() {
		long nextId = Long.parseLong(nextBiocycId.getDescription());
		Object[] a = {numberIdFormat.format(nextId)};
		String ret = MessageFormat.format(Messages.getString("BioCycId"), a);
		nextBiocycId.setDescription("" + (++nextId));
		return ret;
	}

	public String getExternalId() {
		if (getProductID() != null) {
			return getProductID();
		}
		else {
			return ((RichSequence) feature.getSequence()).getAccession() + "|" + getName();
		}
	}

}

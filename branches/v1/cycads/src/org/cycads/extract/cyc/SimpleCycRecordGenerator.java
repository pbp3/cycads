package org.cycads.extract.cyc;

import java.util.Collection;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.SubseqAnnotation;
import org.cycads.entities.note.Type;
import org.cycads.general.ParametersDefault;

public class SimpleCycRecordGenerator implements CycRecordGenerator
{
	double			threshold;
	CycIdGenerator	cycIdGenerator;

	public SimpleCycRecordGenerator(double threshold, CycIdGenerator cycIdGenerator) {
		this.threshold = threshold;
		this.cycIdGenerator = cycIdGenerator;
	}

	@Override
	public CycRecord generate(SubseqAnnotation< ? , ? , ? , ? , ? > annot) {
		String id = getID(annot);
		String type = getType(annot);
		String prodtype = PFFileConfig.getProductType(type);
		SimpleCycRecord record = new SimpleCycRecord(prodtype, id);
		record.setStartBase(annot.getSubsequence().getStart());
		record.setEndtBase(annot.getSubsequence().getEnd());
		record.setIntrons((Collection<CycIntron>) annot.getSubsequence().getIntrons());
		record.setName(name);
		// TODO Auto-generated method stub
		return null;
	}

	private String getType(Annotation< ? , ? , ? , ? > annot) {
		for (Type type : annot.getTypes()) {
			String typeStr = PFFileConfig.getType(type.getName());
			if (typeStr != null && typeStr.length() != 0) {
				return typeStr;
			}
		}
		return null;
	}

	private String getID(Annotation annot) {
		String id = annot.getNoteValue(ParametersDefault.getPFFileCycIdNoteType());
		if (id == null || id.length() == 0) {
			id = cycIdGenerator.getNewID();
		}
		return id;
	}

}

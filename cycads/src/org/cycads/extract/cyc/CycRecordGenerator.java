package org.cycads.extract.cyc;

import org.cycads.entities.annotation.SubseqAnnotation;

public interface CycRecordGenerator
{
	public CycRecord generate(SubseqAnnotation< ? , ? , ? , ? , ? > annot);

}

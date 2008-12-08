package org.cycads.entities.annotation.dBLink;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.AnnotationMethod;

public interface DBLink<S extends DBLinkSource< ? , ? , ? >, R extends DBRecord< ? , ? , ? , ? >, M extends AnnotationMethod>
		extends Annotation<S, M>
{
	public R getDBRecordTarget();

}
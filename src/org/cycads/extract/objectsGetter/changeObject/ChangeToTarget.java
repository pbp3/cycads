/*
 * Created on 22/03/2010
 */
package org.cycads.extract.objectsGetter.changeObject;

import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.annotation.Association;
import org.cycads.extract.general.AnnotationCluster;
import org.cycads.extract.general.GetterExpressionException;

// LOC "TA"
public class ChangeToTarget extends ChangeToOneObject
{

	@Override
	public Object executeMethod(Object obj) throws GetterExpressionException {
		if (obj instanceof Annotation) {
			return ((Association) obj).getTarget();
		}
		else if (obj instanceof AnnotationCluster) {
			return ((AnnotationCluster)obj).getTarget();
		}
		else {
			throw new GetterExpressionException("Object is neither an annotation nor an annotation cluster. Object:" + obj);
		}
	}

}

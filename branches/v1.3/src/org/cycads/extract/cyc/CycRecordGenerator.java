package org.cycads.extract.cyc;

import org.cycads.entities.Feature;
import org.cycads.entities.annotation.Annotation;
import org.cycads.entities.sequence.Subsequence;
import org.cycads.extract.general.GetterExpressionException;

public interface CycRecordGenerator
{
	public CycRecord generate(Annotation< ? extends Subsequence, ? extends Feature> annot)
			throws GetterExpressionException;

}

package org.cycads.extract.cyc;

import java.util.List;

import org.cycads.entities.annotation.Annotation;

public class SimpleCycDbxrefAnnotationPathsRet extends SimpleCycDbxrefRet implements CycDbxrefAnnotationPathsRet
{
	CycDbxrefAnnotationPaths	cycDbxrefAnnotationPaths;

	public SimpleCycDbxrefAnnotationPathsRet(CycDbxrefAnnotationPaths cycDbxrefAnnotationPaths,
			List<Annotation> annotations) {
		super(cycDbxrefAnnotationPaths.getDbxref(), annotations);
		this.cycDbxrefAnnotationPaths = cycDbxrefAnnotationPaths;
	}

	public SimpleCycDbxrefAnnotationPathsRet(CycDbxrefAnnotationPaths cycDbxrefAnnotationPaths) {
		super(cycDbxrefAnnotationPaths.getDbxref());
		this.cycDbxrefAnnotationPaths = cycDbxrefAnnotationPaths;
	}

	@Override
	public CycDbxrefAnnotationPaths getCycDbxrefAnnotationPaths() {
		return cycDbxrefAnnotationPaths;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CycDbxrefRet)) {
			return false;
		}
		CycDbxrefRet o = (CycDbxrefRet) obj;
		return getDbxref().equals(o.getDbxref());
	}

}

/*
 * Created on 27/04/2009
 */
package org.cycads.ui.progress;

public class ProgressCount implements Progress {

	int	count	= 0;

	@Override
	public void completeStep() {
		count++;
	}

	@Override
	public void finish() {

	}

	@Override
	public void finish(int step) {

	}

	@Override
	public void finish(Object[] a) {

	}

	@Override
	public void finish(String endMsg) {

	}

	@Override
	public int getStep() {
		return count;
	}

	@Override
	public void init() {
		count = 0;
	}

	@Override
	public void init(Object[] a) {
		count = 0;
	}

	@Override
	public void init(String beginMsg) {
		count = 0;
	}

	@Override
	public void setStep(int step) {
		count = step;
	}

	@Override
	public void setTotalSteps(int totalSteps) {

	}

}

/*
 * Created on 06/06/2008
 */
package org.cycads.ui.progress;

import javax.swing.JProgressBar;

import org.cycads.general.Messages;

public class ProgressBar implements Progress
{
	JProgressBar	progressBar;
	int				step	= 0;

	public void completeStep() {
		progressBar.setValue(++step);
	}

	public void setTotalSteps(int totalSteps) {
		progressBar.setMaximum(totalSteps);
	}

	public void setInitialStep(int initialStep) {
		progressBar.setMinimum(initialStep);
	}

	public void init() {
		progressBar.setVisible(true);
	}

	public void init(String beginMsg) {
		progressBar.setToolTipText(beginMsg);
		init();
	}

	public void finish() {
		progressBar.setValue(progressBar.getMaximum());
	}

	public void finish(String endMsg) {
		finish();
		progressBar.setToolTipText(endMsg);
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
		progressBar.setValue(step);
	}

	public void finish(Object[] a) {
		String str = "";
		for (Object o : a) {
			str += (Messages.getProgressSeparatorObjects() + o);
		}
		finish(str);
	}

	public void init(Object[] a) {
		String str = "";
		for (Object o : a) {
			str += (Messages.getProgressSeparatorObjects() + o);
		}
		init(str);
	}

}

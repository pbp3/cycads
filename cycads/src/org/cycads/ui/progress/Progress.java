/*
 * Created on 06/06/2008
 */
package org.cycads.ui.progress;

public interface Progress
{
	public void setTotalSteps(int totalSteps);

	public void completeStep();

	public void setStep(int step);

	public int getStep();

	public void init();

	public void init(Object[] a);

	public void init(String beginMsg);

	public void finish();

	public void finish(Object[] a);

	public void finish(String endMsg);

}

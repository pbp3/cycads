/*
 * Created on 06/06/2008
 */
package org.cycads.ui.progress;

import java.io.PrintStream;

public class ProgressPrintInterval extends ProgressPrintStream
{
	int	interval;

	public ProgressPrintInterval(PrintStream out, int interval) {
		super(out);
		this.interval = interval;
	}

	public ProgressPrintInterval(PrintStream out, int inicialStep, int interval) {
		super(out, inicialStep);
		this.interval = interval;
	}

	public ProgressPrintInterval(PrintStream out, int interval, String initialMessage) {
		super(out, initialMessage);
		this.interval = interval;
	}

	public ProgressPrintInterval(PrintStream out, int interval, String initialMessage, String finalMessage) {
		super(out, initialMessage);
		this.interval = interval;
		setFinishMessage(finalMessage);
	}

	public ProgressPrintInterval(PrintStream out, int inicialStep, int interval, String initialMessage) {
		super(out, inicialStep, initialMessage);
		this.interval = interval;
	}

	@Override
	public void completeStep() {
		step++;
		if (step % interval == 0) {
			printStep();
		}
	}

	@Override
	public void finish() {
		printStep();
		super.finish();
	}

	@Override
	public void finish(String msg) {
		printStep();
		super.finish(msg);
	}

}

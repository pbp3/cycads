/*
 * Created on 06/06/2008
 */
package org.cycads.ui.progress;

import java.io.PrintStream;
import java.text.MessageFormat;

import org.cycads.general.Messages;

public class ProgressPrintStream implements Progress
{
	PrintStream	out;
	int			step			= 0;
	String		initialMessage	= null;
	String		finalMessage	= null;
	long		timeStart		= 0;
	boolean		printTime		= true;

	public ProgressPrintStream(PrintStream out) {
		this.out = out;
	}

	public ProgressPrintStream(PrintStream out, int inicialStep) {
		this.out = out;
		this.step = inicialStep;
	}

	public ProgressPrintStream(PrintStream out, String initialMessage) {
		this(out);
		this.initialMessage = initialMessage;
	}

	public ProgressPrintStream(PrintStream out, int inicialStep, String initialMessage) {
		this(out, inicialStep);
		this.initialMessage = initialMessage;
	}

	public void completeStep() {
		step++;
		printStep();
	}

	protected void printStep() {
		out.println(step);
	}

	public void setTotalSteps(int totalSteps) {
		out.println(Messages.getProgressTotalStepsMsg(totalSteps));
	}

	public void setInitialStep(int initialStep) {
		step = initialStep;
		printStep();
	}

	public void init() {
		init(initialMessage);
	}

	public void init(String msg) {
		if (msg != null) {
			out.println(msg);
		}
		if (printTime) {
			out.println(Messages.getProgressStartTimeMsg());
		}
		timeStart = System.currentTimeMillis();
	}

	public void init(Object[] a) {
		if (initialMessage != null) {
			init(MessageFormat.format(initialMessage, a));
		}
		else {
			String str = "";
			for (Object o : a) {
				str += (Messages.getProgressSeparatorObjects() + o);
			}
			init(str);
		}
	}

	public void finish() {
		finish(finalMessage);
	}

	public void finish(String msg) {
		if (msg != null) {
			out.println(msg);
		}
		if (printTime) {
			out.println(Messages.getProgressEndTimeMsg());
			out.println(Messages.getProgressTotalTimeMsg(System.currentTimeMillis() - timeStart));
		}
		timeStart = 0;
	}

	public void finish(Object[] a) {
		if (finalMessage != null) {
			finish(MessageFormat.format(finalMessage, a));
		}
		else {
			String str = "";
			for (Object o : a) {
				str += (Messages.getProgressSeparatorObjects() + o);
			}
			finish(str);
		}
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

	public String getInitMessage() {
		return initialMessage;
	}

	public void setInitMessage(String initMsg) {
		this.initialMessage = initMsg;
	}

	public String getFinishMessage() {
		return finalMessage;
	}

	public void setFinishMessage(String finishMessage) {
		this.finalMessage = finishMessage;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	public boolean isPrintTime() {
		return printTime;
	}

	public void setPrintTime(boolean printTime) {
		this.printTime = printTime;
	}

}

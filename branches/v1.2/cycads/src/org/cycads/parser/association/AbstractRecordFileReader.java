package org.cycads.parser.association;

import java.io.IOException;

import org.cycads.ui.progress.Progress;

public abstract class AbstractRecordFileReader<R> implements RecordFileReader<R>
{

	@Override
	public void readAll(Progress progressOK, Progress progressError) throws IOException {
		while (Tools.getNextValidRecord(this, progressError) != null) {
			progressOK.completeStep();
		}

	}

}

package org.cycads.parser.association;

import java.io.IOException;

import org.cycads.general.ParametersDefault;
import org.cycads.parser.FileParserException;
import org.cycads.ui.progress.Progress;

public abstract class AbstractRecordFileReader<R> implements RecordFileReader<R>
{

	@Override
	public void readAll(Progress progressOK, Progress progressError) throws IOException, FileParserException {
		while (getNextValidRecord(this, progressError) != null) {
			progressOK.completeStep();
		}

	}

	public static <R> R getNextValidRecord(RecordFileReader<R> reader, Progress progressError) throws IOException {
		R record = null;
		boolean read = false;
		while (!read) {
			try {
				record = reader.read();
				read = true;
			}
			catch (FileParserException e) {
				if (ParametersDefault.isDebugging()) {
					e.printStackTrace();
				}
				progressError.completeStep();
			}
		}
		return record;
	}

}

package org.cycads.extract.cyc;

public interface CycStream
{
	public void print(CycRecord record);
	public void flush();
	public void close();
}

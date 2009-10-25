package org.cycads.extract.cyc.walkerLoc;


public interface Walker
{
	public boolean matches(String loc);

	public void walk(String loc, Trace basckpack);

}

/*
 * Created on 12/11/2008
 */
package org.cycads.entities.sequence;

public class SimpleIntron implements Intron
{
	int	start, end;

	public SimpleIntron(int start, int end)
	{
		this.start = start;
		this.end = end;
	}

	public int getStart()
	{
		return start;
	}

	public int getEnd()
	{
		return end;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Intron))
		{
			return false;
		}
		Intron intron = (Intron) obj;
		return getStart() == intron.getStart() && getEnd() == intron.getEnd();
	}

}

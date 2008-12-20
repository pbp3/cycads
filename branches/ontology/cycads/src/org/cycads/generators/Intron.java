/*
 * Created on 17/09/2008
 */
package org.cycads.generators;

public class Intron
{
	int	begin, end;

	public Intron(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void shift(int qtty) {
		begin += qtty;
		end += qtty;
	}

}

package org.cycads.extract.cyc.walkerLoc;

import java.util.List;

import org.cycads.entities.EntityObject;

public class StateMachine
{
	public Trace getTrace(EntityObject input, String loc) {
		State currentState = new InitialState();
		Trace ret = new Trace(input);
		while (loc != null && loc.length() > 0) {
			int i = loc.indexOf('.');
			String subLoc;
			if (i == -1) {
				subLoc = loc;
				loc = "";
			}
			else {
				subLoc = loc.substring(0, i);
				loc = loc.substring(i + 1);
			}
			List<State> nextStates = currentState.getNextStates();
			for (State state : nextStates) {
				if (state.matches(subloc)) {

				}
			}

		}
		return ret;

	}
}

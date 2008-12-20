/*
 * BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 * http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 * http://www.biojava.org
 */

package org.cycads.entities.change;

import java.util.EventListener;

/**
 * Interface for objects which listen to ChangeEvents.
 * 
 * @author Thomas Down
 * @author Matthew Pocock
 * @author Keith James
 * @since 1.1
 */

public interface ChangeListener<O> extends EventListener
{
	/**
	 * <p>
	 * Called before a change takes place.
	 * </p>
	 * 
	 * <p>
	 * This is your chance to stop the change by throwing a ChangeVetoException. This method does not indicate that the
	 * change will definitely take place, so it is not recomended that you take any positive action within this handler.
	 * </p>
	 * 
	 * @param cev An event encapsulating the change which is about to take place.
	 * @exception ChangeVetoException Description of Exception
	 * @throws ChangeVetoException if the receiver does not wish this change to occur at this time.
	 */
	void preChange(ChangeEvent<O> cev) throws ChangeVetoException;

	/**
	 * <p>
	 * Called when a change has just taken place.
	 * </p>
	 * 
	 * <p>
	 * This method is the place to perform any behavior in response to the change event.
	 * </p>
	 * 
	 * @param cev An event encapsulating the change which has occured.
	 */

	void postChange(ChangeEvent<O> cev);

}

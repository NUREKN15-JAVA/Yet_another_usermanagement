package ua.nure.baranov.gui;

import ua.nure.baranov.User;

/**
 * All implementations of this interface work with specific, already existing
 * user.
 * 
 * @author Yevhenii Baranov
 *
 */
public interface SpecificUserWorker {
	/**
	 * Sets the user to work with.
	 * 
	 * @param user
	 */
	void setUser(User user);
}

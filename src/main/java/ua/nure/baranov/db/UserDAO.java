package ua.nure.baranov.db;

import java.util.Collection;
import ua.nure.baranov.User;

/**
 * DAO for User class, defines basic CRUD operations and operation of getting all rows in table.
 * Needs a connection factory to work with database. 
 * 
 * @author Yevhenii Baranov
 *
 */
public interface UserDAO {
	/**
	 * Adds user to database.
	 * @param user with all not empty fields with exception of field id.
	 * @return Copy of user with filled field id.
	 * @throws DatabaseException	in case of occurence of any troubles with database.
	 */
	public User create(User user) throws DatabaseException;

	/**
	 * Updates current user in the database.
	 * @param user	Updated version of user.
	 * @throws DatabaseException	in case of occurence of any troubles with database.
	 */
	public void update(User user) throws DatabaseException;

	/**
	 * Deletes given user from database.
	 * @param user that has to be deleted. If there were no such user, function returns nothing.
	 * @throws DatabaseException	in case of occurence of any troubles with database.
	 */
	public void delete(User user) throws DatabaseException;

	/**
	 * Finds user with given id in the database and returns it.
	 * @param id	The identifier of user.
	 * @return user with given id or <code>null</code> if such user hasn't been found.
	 * @throws DatabaseException	in case of occurence of any troubles with database.
	 */
	public User find(Long id) throws DatabaseException;

	/**
	 * Gets all users from database into a collection and returns it.
	 * @return collection of users. If table is empty, collection will be also empty.
	 * @throws DatabaseException	in case of occurence of any troubles with database.
	 */
	
	public Collection<User> findAll() throws DatabaseException;

	/**
	 * Setter method for connection factory.
	 * @param connectionFactory
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory);

}

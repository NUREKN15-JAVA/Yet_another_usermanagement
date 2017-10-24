package ua.nure.baranov.db;

import java.util.Collection;
import ua.nure.baranov.User;

public interface UserDAO {
	/**
	 * Adds user to database
	 * @param user with all not empty fields with exception of field id
	 * @return Copy of user with filled field id
	 * @throws DatabaseException in case troubles with database occur
	 */
	public User create(User user) throws DatabaseException;
	/**
	 * 
	 * @param user
	 * @throws DatabaseException
	 */
	public void update(User user) throws DatabaseException;
	/**
	 * 
	 * @param user
	 * @throws DatabaseException
	 */
	public void delete(User user) throws DatabaseException;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws DatabaseException
	 */
	public User find(Long id) throws DatabaseException;
	/**
	 * 
	 * @return
	 * @throws DatabaseException
	 */
	public Collection<User> findAll() throws DatabaseException;
	/**
	 * 
	 * @param connectionFactory
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory);
	
}

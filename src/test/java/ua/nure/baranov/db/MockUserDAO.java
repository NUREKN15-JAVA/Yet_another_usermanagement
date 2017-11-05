package ua.nure.baranov.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.baranov.User;

/**
 * Mock version of UserDAO, does everything what implementations of UserDAO do,
 * but works with Map instead of database.
 * 
 * @author Yevhenii Baranov
 */
public class MockUserDAO implements UserDAO {
	private Map<Long, User> users = new HashMap<>();
	private Long id = 0L;

	@Override
	public User create(User user) throws DatabaseException {
		Long currentId = ++id;
		user.setId(currentId);
		users.put(id, user);
		return user;
	}

	@Override
	public void update(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.replace(currentId, user);
	}

	@Override
	public void delete(User user) throws DatabaseException {
		Long currentId = user.getId();
		users.remove(currentId);
	}

	@Override
	public User find(Long id) throws DatabaseException {
		return users.get(id);
	}

	@Override
	public Collection<User> findAll() throws DatabaseException {
		return users.values();
	}

	@Override
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
	}

}

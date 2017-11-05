package ua.nure.baranov.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.baranov.User;

/**
 * Implementation of UserDAO made to work with Hsqldb databases.
 * 
 * @author Yevhenii Baranov
 *
 */
class HsqldbUserDAO implements UserDAO {

	private static final String SELECT_QUERY = "SELECT * FROM Users WHERE Users.id=?";
	private static final String IDENTITY = "call IDENTITY()";
	private static final String CREATE_QUERY = "INSERT INTO Users (firstname, lastname, birthday) VALUES (?, ?, ?)";
	private static final String FIELD_BIRTHDAY = "birthday";
	private static final String FIELD_ID = "id";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_FIRSTNAME = "firstname";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM Users";
	private static final String UPDATE_USERS_SET_FIRSTNAME_LASTNAME_DATEOFBIRTH_WHERE_ID = "UPDATE users SET firstname = ?, lastname = ?, birthday = ? WHERE id = ?";
	private static final String DELETE_FROM_USERS_WHERE_ID = "DELETE FROM Users WHERE id=?";
	private ConnectionFactory connectionFactory;

	public HsqldbUserDAO(ConnectionFactory factory) {
		this.connectionFactory = factory;
	}

	public HsqldbUserDAO() {

	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	@Override
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public User create(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			User resultUser = null;
			PreparedStatement statement = connection.prepareStatement(CREATE_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getBirthDay().getTimeInMillis()));
			int n = statement.executeUpdate();
			if (n != 1) {
				throw new DatabaseException("Wrong inserted rows number:" + n);
			}
			CallableStatement callableStatement = connection.prepareCall(IDENTITY);
			ResultSet result = callableStatement.executeQuery();
			if (result.next()) {
				resultUser = new User(user);
				resultUser.setId(new Long(result.getLong(1)));
			}
			result.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return resultUser;
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}

	}

	@Override
	public void update(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection
					.prepareStatement(UPDATE_USERS_SET_FIRSTNAME_LASTNAME_DATEOFBIRTH_WHERE_ID);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getBirthDay().getTimeInMillis()));
			statement.setLong(4, user.getId());
			int n = statement.executeUpdate();
			if (n != 1)
				throw new DatabaseException("Update not executed");
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public void delete(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_FROM_USERS_WHERE_ID);
			statement.setLong(1, user.getId());
			int n = statement.executeUpdate();
			if (n != 1)
				throw new DatabaseException("Process has been executed unsuccessfully");
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public User find(Long id) throws DatabaseException {
		User resultUser = null;
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				resultUser = new User();
				resultUser.setFirstName(result.getString(FIELD_FIRSTNAME));
				resultUser.setLastName(result.getString(FIELD_LASTNAME));
				resultUser.setId(result.getLong(FIELD_ID));
				Calendar birthday = Calendar.getInstance();
				birthday.setTime(result.getDate(FIELD_BIRTHDAY));
				resultUser.setBirthDay(birthday);
			}
			result.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return resultUser;

	}

	@Override
	public Collection<User> findAll() throws DatabaseException {
		Collection<User> users = new LinkedList<>();
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(SELECT_ALL_QUERY);
			while (results.next()) {
				User user = new User();
				user.setFirstName(results.getString(FIELD_FIRSTNAME));
				user.setLastName(results.getString(FIELD_LASTNAME));
				user.setId(results.getLong(FIELD_ID));
				Calendar birthday = Calendar.getInstance();
				birthday.setTime(results.getDate(FIELD_BIRTHDAY));
				user.setBirthDay(birthday);
				users.add(user);
			}
			results.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return users;
	}

}

package ua.nure.baranov.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import ua.nure.baranov.User;

public class HsqldbUserDAO implements UserDAO {

	private ConnectionFactory connectionFactory;

	public HsqldbUserDAO(ConnectionFactory factory) {
		this.connectionFactory = factory;
	}

	@Override
	public User create(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			User resultUser = null;
			PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (firstname, lastname, birthday) VALUES (?, ?, ?)");
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getBirthDay().getTimeInMillis()));
			int n = statement.executeUpdate();
			if (n != 1) {
				throw new DatabaseException("Wrong inserted rows number:" + n);
			}
			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			ResultSet result = callableStatement.executeQuery();
			if(result.next()) {
				resultUser = new User(user);
				resultUser.setId(new Long(result.getLong(1)));
			}
			result.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return resultUser;
		}
		catch(DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
	}

	@Override
	public void update(User user) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User user) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public User find(Long id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> findAll() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}

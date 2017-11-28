package ua.nure.baranov.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ua.nure.baranov.User;
import ua.nure.baranov.gui.util.Messages;

@SuppressWarnings("serial")
public class UserTableModel extends AbstractTableModel {

	private List<User> users = null;
	private static final String[] COLUMN_NAMES = { Messages.getString("UserTableModel.id"), //$NON-NLS-1$
			Messages.getString("UserTableModel.firstName"), Messages.getString("UserTableModel.lastName") }; //$NON-NLS-1$ //$NON-NLS-2$
	private static final Class<?>[] COLUMN_CLASSES = { Long.class, String.class, String.class };

	public UserTableModel(Collection<User> users) {
		this.users = new ArrayList<>(users);
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = (User) users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return user.getId();
		case 1:
			return user.getFirstName();
		case 2:
			return user.getLastName();
		}
		return null;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return COLUMN_NAMES[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}

	public User getUser(int row) {
		return users.get(row);
	}

	public void addUsers(Collection<User> usersCollection) {
		this.users.addAll(usersCollection);
	}

	public void clearUsers() {
		this.users = new ArrayList<>();
		
	}
}

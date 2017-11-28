package ua.nure.baranov.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import ua.nure.baranov.User;
import ua.nure.baranov.db.DatabaseException;
import ua.nure.baranov.gui.util.Messages;

/**
 * Panel for browsing the contents of the database.
 * 
 * @author Yevhenii Baranov
 *
 */
@SuppressWarnings("serial")
public class BrowsePanel extends JPanel implements ActionListener {
	private MainFrame mainFrame;
	private JPanel buttonPanel;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton detailsButton;
	private JScrollPane tablePanel;
	private JTable userTable;
	private JButton searchButton;

	public BrowsePanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initialize();
		
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getTablePanel(), BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
		
	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getAddButton());
			buttonPanel.add(getEditButton());
			buttonPanel.add(getDeleteButton());
			buttonPanel.add(getDetailsButton());
			buttonPanel.add(getSearchButton());
		}
		return buttonPanel;
	}
	
	private JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = new JButton();
			searchButton.setText(Messages.getString("BrowsePanel.search")); //$NON-NLS-1$
			searchButton.setName("searchButton"); //$NON-NLS-1$
			searchButton.setActionCommand("search"); //$NON-NLS-1$
			searchButton.addActionListener(this);
		}
		return searchButton;
	}

	private JButton getDetailsButton() {
		if (detailsButton == null) {
			detailsButton = new JButton();
			detailsButton.setText(Messages.getString("BrowsePanel.details")); //$NON-NLS-1$
			detailsButton.setName("detailsButton"); //$NON-NLS-1$
			detailsButton.setActionCommand("details"); //$NON-NLS-1$
			detailsButton.addActionListener(this);
		}
		return detailsButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText(Messages.getString("BrowsePanel.delete")); //$NON-NLS-1$
			deleteButton.setName("deleteButton"); //$NON-NLS-1$
			deleteButton.setActionCommand("delete"); //$NON-NLS-1$
			deleteButton.addActionListener(this);
		}
		return deleteButton;
	}

	private JButton getEditButton() {
		if (editButton == null) {
			editButton = new JButton();
			editButton.setText(Messages.getString("BrowsePanel.edit")); //$NON-NLS-1$
			editButton.setName("editButton"); //$NON-NLS-1$
			editButton.setActionCommand("edit"); //$NON-NLS-1$
			editButton.addActionListener(this);
		}
		return editButton;
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText(Messages.getString("BrowsePanel.add")); //$NON-NLS-1$
			addButton.setName("addButton"); //$NON-NLS-1$
			addButton.setActionCommand("add"); //$NON-NLS-1$
			addButton.addActionListener(this);
		}
		return addButton;
	}

	private JScrollPane getTablePanel() {
		if (tablePanel == null) {
			tablePanel = new JScrollPane(getUserTable());
		}
		return tablePanel;
	}

	private JTable getUserTable() {
		if (userTable == null) {
			userTable = new JTable();
			userTable.setName("userTable"); //$NON-NLS-1$
			userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return userTable;
	}

	/**
	 * Initializes the table, making it able to show all changes in the database.
	 */
	public void initTable() {
		UserTableModel model = null;
		try {
			model = new UserTableModel(mainFrame.getDAO().findAll());
		} catch (DatabaseException e) {
			model = new UserTableModel(new ArrayList<User>());
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		getUserTable().setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("add".equals(e.getActionCommand())) { //$NON-NLS-1$
			mainFrame.showAddPanel();
		}
		if ("details".equals(e.getActionCommand())) {
			try {
				User selectedUser = getSelectedUser();
				if (selectedUser != null) {
					mainFrame.showDetailsPanel(mainFrame.getDAO().find(selectedUser.getId()),"browsePanel");
				}
			} catch (DatabaseException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if ("delete".equals(e.getActionCommand())) {
			try {
				User selectedUser = getSelectedUser();
				if (selectedUser != null) {
					processDeletion(selectedUser);
				}
			} catch (DatabaseException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if ("edit".equals(e.getActionCommand())) {
			User selectedUser = getSelectedUser();
			if (selectedUser != null) {
				mainFrame.showEditPanel(selectedUser);
			}
		}
		if("search".equals(e.getActionCommand())) {
			mainFrame.showSearchPanel();
		}
	}

	/**
	 * Prompts the confirmation window and deletes {@code selectedUser} from the
	 * database.
	 * 
	 * @param selectedUser
	 *            User to be deleted.
	 * @throws DatabaseException
	 */
	private void processDeletion(User selectedUser) throws DatabaseException {
		int result = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to delete this user?",
				"Delete user", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			mainFrame.getDAO().delete(selectedUser);
			getUserTable().setModel(new UserTableModel(mainFrame.getDAO().findAll()));
		}
	}

	/**
	 * Finds selected user in table and returns it.
	 * 
	 * @return User if it was successfully found, {@code null} otherwise.
	 */
	private User getSelectedUser() {
		int row = getUserTable().getSelectedRow();
		if (row == -1) {
			return null;
		}
		return ((UserTableModel) getUserTable().getModel()).getUser(row);
	}
}

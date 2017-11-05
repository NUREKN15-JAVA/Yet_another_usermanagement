package ua.nure.baranov.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import ua.nure.baranov.User;
import ua.nure.baranov.db.DatabaseException;
import ua.nure.baranov.gui.util.Messages;

@SuppressWarnings("serial")
public class EditPanel extends AbstractUserEditPanel implements SpecificUserWorker {
	User userToEdit;

	public EditPanel(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("ok".equals(e.getActionCommand())) {
			userToEdit.setFirstName(getFirstNameField().getText());
			userToEdit.setLastName(getLastNameField().getText());
			String birthday = getBirthdayField().getText();
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(Messages.format);
			try {
				calendar.setTime(format.parse(birthday));
			} catch (ParseException e1) {
				getBirthdayField().setBackground(Color.RED);
				return;
			}
			userToEdit.setBirthDay(calendar);
			try {
				getMainFrame().getDAO().update(userToEdit);
			} catch (DatabaseException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			}
		}
		clearFields();
		getMainFrame().showBrowsePanel();
	}

	public void setUser(User userToShow) {
		this.userToEdit = userToShow;
		resetLabels();
	}

	private void resetLabels() {
		this.getFirstNameField().setText(userToEdit.getFirstName());
		this.getLastNameField().setText(userToEdit.getLastName());
		SimpleDateFormat format = new SimpleDateFormat(Messages.format);
		this.getBirthdayField().setText(format.format(userToEdit.getBirthDay().getTime()));
	}

}

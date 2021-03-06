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

/**
 * Panel for adding new user to the database
 * 
 * @author Yevhenii Baranov
 *
 */
@SuppressWarnings("serial")
public class AddPanel extends AbstractUserEditPanel {

	public AddPanel(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("ok".equals(e.getActionCommand())) {
			User user = new User();
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			String birthday = getBirthdayField().getText();
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat(Messages.format);
			try {
				calendar.setTime(format.parse(birthday));
			} catch (ParseException e1) {
				getBirthdayField().setBackground(Color.RED);
				return;
			}
			user.setBirthDay(calendar);
			try {
				getMainFrame().getDAO().create(user);
			} catch (DatabaseException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			}
		}
		clearFields();
		getMainFrame().showBrowsePanel();
	}
}

package ua.nure.baranov.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.baranov.User;
import ua.nure.baranov.db.DatabaseException;
import ua.nure.baranov.gui.util.Messages;
@SuppressWarnings("serial")
public class AddPanel extends JPanel implements ActionListener {

	private MainFrame mainFrame;
	private JPanel buttonPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel fieldPanel;
	private JTextField firstNameField;
	private JTextField birthdayField;
	private JTextField lastNameField;

	public AddPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOKButton());
			buttonPanel.add(getCancelButton());
		}
		return buttonPanel;
	}

	private JButton getCancelButton() {
		if (cancelButton == null){
			cancelButton = new JButton();
			cancelButton.setText(Messages.getString("AddPanel.cancel")); //$NON-NLS-1$
			cancelButton.setName("cancelButton"); //$NON-NLS-1$
			cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOKButton() {
			if (okButton == null){
			okButton = new JButton();
			okButton.setText(Messages.getString("AddPanel.add")); //$NON-NLS-1$
			okButton.setName("okButton"); //$NON-NLS-1$
			okButton.setActionCommand("ok"); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JPanel getFieldPanel() {
		if(fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(3, 2));
			addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("AddPanel.birthday"), getBirthdayField()); //$NON-NLS-1$
		}
		return fieldPanel;
	}
	
	private JTextField getBirthdayField() {
		if(birthdayField == null) {
			birthdayField = new JTextField();
			birthdayField.setName("birthdayField"); //$NON-NLS-1$
		}
		return birthdayField;
	}

	private JTextField getLastNameField() {
		if(lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName("lastNameField"); //$NON-NLS-1$
		}
		return lastNameField;
	}

	private JTextField getFirstNameField() {
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName("firstNameField"); //$NON-NLS-1$
		}
		return firstNameField;
	}

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(textField);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("ok".equals(e.getActionCommand())) {
			User user = new User();
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			String birthday = getBirthdayField().getText();
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy.mm.dd");
			try {
				calendar.setTime(format.parse(birthday));
			} catch (ParseException e1) {
				getBirthdayField().setBackground(Color.RED);
				return;
			}
			user.setBirthDay(calendar);
			try {
				mainFrame.getDAO().create(user);
			} catch (DatabaseException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

			}
		}
		clearFields();
		this.setVisible(false);
		mainFrame.showBrowsePanel();
	}

	private void clearFields() {
		getFirstNameField().setText("");
		getFirstNameField().setBackground(Color.WHITE);
		getLastNameField().setText("");
		getLastNameField().setBackground(Color.WHITE);	
		getBirthdayField().setText("");
		getBirthdayField().setBackground(Color.WHITE);
	
	}
}

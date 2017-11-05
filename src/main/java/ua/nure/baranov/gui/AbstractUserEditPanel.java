package ua.nure.baranov.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.baranov.gui.util.Messages;

/**
 * JPanel designed specifically to enter the properties of {@link User}s.
 * 
 * @author Yevhenii Baranov
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractUserEditPanel extends JPanel implements ActionListener {
	private MainFrame mainFrame;

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	private JPanel buttonPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel fieldPanel;
	private JTextField firstNameField;
	private JTextField birthdayField;
	private JTextField lastNameField;

	public AbstractUserEditPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
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
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(Messages.getString("AbstactUserEditPanel.cancel")); //$NON-NLS-1$
			cancelButton.setName("cancelButton"); //$NON-NLS-1$
			cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOKButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(Messages.getString("AbstactUserEditPanel.add")); //$NON-NLS-1$
			okButton.setName("okButton"); //$NON-NLS-1$
			okButton.setActionCommand("ok"); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JPanel getFieldPanel() {
		if (fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(3, 2));
			addLabeledField(fieldPanel, Messages.getString("first_name"), getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("last_name"), getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("birthday"), getBirthdayField()); //$NON-NLS-1$
		}
		return fieldPanel;
	}

	protected JTextField getBirthdayField() {
		if (birthdayField == null) {
			birthdayField = new JTextField();
			birthdayField.setName("birthdayField"); //$NON-NLS-1$
		}
		return birthdayField;
	}

	protected JTextField getLastNameField() {
		if (lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName("lastNameField"); //$NON-NLS-1$
		}
		return lastNameField;
	}

	protected JTextField getFirstNameField() {
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName("firstNameField"); //$NON-NLS-1$
		}
		return firstNameField;
	}

	/**
	 * Adds field {@code textfield} with label {@code labelText} to panel
	 * {@code panel}.
	 * 
	 * @param panel
	 * @param labelText
	 * @param textField
	 */
	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);
		panel.add(label);
		panel.add(textField);
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

	/**
	 * Clears all input fields.
	 */
	protected void clearFields() {
		getFirstNameField().setText("");
		getFirstNameField().setBackground(Color.WHITE);
		getLastNameField().setText("");
		getLastNameField().setBackground(Color.WHITE);
		getBirthdayField().setText("");
		getBirthdayField().setBackground(Color.WHITE);

	}
}

package ua.nure.baranov.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ua.nure.baranov.User;
import ua.nure.baranov.gui.util.Messages;

/**
 * Panel that shows all properties of the specific user.
 * 
 * @author Yevhenii Baranov
 *
 */
@SuppressWarnings("serial")
public class DetailsPanel extends JPanel implements ActionListener, SpecificUserWorker {

	private MainFrame mainFrame;
	private JPanel textPanel;
	private JButton okButton;
	private JLabel idLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel birthdayLabel;
	private final JLabel firstNameDescriptionLabel = new JLabel(Messages.getString("first_name")); //$NON-NLS-1$
	private final JLabel lastNameDescriptionLabel = new JLabel(Messages.getString("last_name")); //$NON-NLS-1$
	private final JLabel birthdayDescriptionLabel = new JLabel(Messages.getString("birthday")); //$NON-NLS-1$
	private final JLabel idDescriptionLabel = new JLabel(Messages.getString("DetailsPanel.3")); //$NON-NLS-1$
	private User user;

	public DetailsPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setName("detailsPanel"); //$NON-NLS-1$
		this.add(getTextPanel(), BorderLayout.CENTER);
		this.add(getOKButton(), BorderLayout.SOUTH);
	}

	private JPanel getTextPanel() {
		if (textPanel == null) {
			textPanel = new JPanel();
			textPanel.setName("textPanel"); //$NON-NLS-1$
			textPanel.setLayout(new GridLayout(4, 2));
			addTextWithLabel(textPanel, idDescriptionLabel, getIdLabel());
			addTextWithLabel(textPanel, firstNameDescriptionLabel, getFirstNameLabel());
			addTextWithLabel(textPanel, lastNameDescriptionLabel, getLastNameLabel());
			addTextWithLabel(textPanel, birthdayDescriptionLabel, getBirthdayLabel());
		}
		return textPanel;
	}

	private void addTextWithLabel(JPanel panel, JLabel descriptionLabel, JLabel label) {
		panel.add(descriptionLabel);
		panel.add(label);

	}

	private JLabel getBirthdayLabel() {
		if (birthdayLabel == null) {
			birthdayLabel = new JLabel();
			birthdayLabel.setName("birthdayLabel"); //$NON-NLS-1$
			birthdayLabel.setText(""); //$NON-NLS-1$
		}
		return birthdayLabel;
	}

	private JLabel getLastNameLabel() {
		if (lastNameLabel == null) {
			lastNameLabel = new JLabel();
			lastNameLabel.setName("lastNameLabel"); //$NON-NLS-1$
			lastNameLabel.setText(""); //$NON-NLS-1$
		}
		return lastNameLabel;
	}

	private JLabel getFirstNameLabel() {
		if (firstNameLabel == null) {
			firstNameLabel = new JLabel();
			firstNameLabel.setName("firstNameLabel"); //$NON-NLS-1$
			firstNameLabel.setText(""); //$NON-NLS-1$
		}
		return firstNameLabel;
	}

	private JLabel getIdLabel() {
		if (idLabel == null) {
			idLabel = new JLabel();
			idLabel.setName("idLabel"); //$NON-NLS-1$
			idLabel.setText(""); //$NON-NLS-1$
		}
		return idLabel;
	}

	private JButton getOKButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setName("okButton"); //$NON-NLS-1$
			okButton.setText(Messages.getString("DetailsPanel.4")); //$NON-NLS-1$
			okButton.setActionCommand("ok"); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;

	}

	private void resetLabels() {
		this.getIdLabel().setText(user.getId().toString());
		this.getFirstNameLabel().setText(user.getFirstName());
		this.getLastNameLabel().setText(user.getLastName());
		SimpleDateFormat format = new SimpleDateFormat(Messages.format);
		this.getBirthdayLabel().setText(format.format(user.getBirthDay().getTime()));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if ("ok".equals(arg0.getActionCommand())) { //$NON-NLS-1$
			mainFrame.showBrowsePanel();
		}
	}

	@Override
	public void setUser(User userToShow) {
		this.user = userToShow;
		resetLabels();
	}

}

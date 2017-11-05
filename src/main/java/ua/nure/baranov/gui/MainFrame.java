package ua.nure.baranov.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.baranov.User;
import ua.nure.baranov.db.DAOFactory;
import ua.nure.baranov.db.UserDAO;
import ua.nure.baranov.gui.util.Messages;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 500;
	private JPanel contentPanel;
	private JPanel browsePanel;
	private JPanel addPanel;
	private UserDAO dao;
	private JPanel detailsPanel;
	private JPanel editPanel;

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

	public MainFrame() {
		super();
		dao = DAOFactory.getInstance().getUserDAO();
		initialize();
	}

	public UserDAO getDAO() {
		return dao;
	}

	private void initialize() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(Messages.getString("MainFrame.0")); //$NON-NLS-1$
		this.setContentPane(getContentPanel());

	}

	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
		}
		return contentPanel;
	}

	private JPanel getBrowsePanel() {
		if (browsePanel == null) {
			browsePanel = new BrowsePanel(this);
			browsePanel.setName("browsePanel"); //$NON-NLS-1$
		}
		((BrowsePanel) browsePanel).initTable();
		return browsePanel;
	}

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	private JPanel getAddPanel() {
		if (addPanel == null) {
			addPanel = new AddPanel(this);
			addPanel.setName("addPanel");
		}
		return addPanel;
	}

	public void disablePanels() {
		((BorderLayout) getContentPanel().getLayout()).getLayoutComponent(BorderLayout.CENTER).setVisible(false);
	}

	private JPanel getDetailsPanel() {
		if (detailsPanel == null) {
			detailsPanel = new DetailsPanel(this);
			detailsPanel.setName("detailsPanel");
		}
		return detailsPanel;
	}

	private JPanel getEditPanel() {
		if (editPanel == null) {
			editPanel = new EditPanel(this);
			editPanel.setName("editPanel");
		}
		return editPanel;
	}

	public void showAddPanel() {
		disablePanels();
		showPanel(getAddPanel());
	}

	public void showDetailsPanel(User userToShow) {
		disablePanels();
		((DetailsPanel) getDetailsPanel()).setUser(userToShow);
		showPanel(getDetailsPanel());
	}

	public void showBrowsePanel() {
		disablePanels();
		showPanel(getBrowsePanel());
	}

	public void showEditPanel(User userToShow) {
		disablePanels();
		((EditPanel) getEditPanel()).setUser(userToShow);
		showPanel(getEditPanel());
	}

}

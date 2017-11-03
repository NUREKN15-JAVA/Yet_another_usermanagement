package ua.nure.baranov.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
		if (contentPanel==null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
		}
		return contentPanel;
	}

	private JPanel getBrowsePanel() {
		if(browsePanel == null) {
			browsePanel = new BrowsePanel(this);
			browsePanel.setName("browsePanel"); //$NON-NLS-1$
		}
		((BrowsePanel) browsePanel).initTable();
		return browsePanel;
	}

	public void showAddPanel() {
		this.getBrowsePanel().setVisible(false);
		showPanel(getAddPanel());
	}

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	private JPanel getAddPanel() {
		if (addPanel == null) {
			addPanel = new AddPanel(this);
			addPanel.setName("addPanel"); //$NON-NLS-1$
		}
		return addPanel;
	}

	public void showBrowsePanel() {
		getAddPanel().setVisible(false);
		showPanel(getBrowsePanel());		
	}
}

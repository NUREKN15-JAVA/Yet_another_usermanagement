package ua.nure.baranov.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import ua.nure.baranov.User;
import ua.nure.baranov.db.DAOFactory;
import ua.nure.baranov.db.DatabaseException;
/**
 * Servlet that processes deletion of user
 * @author Yevhenii Baranov
 *
 */
public class DeleteServlet extends HttpServlet{
	
	private static final long serialVersionUID = -8886953122146505200L;


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("okButton") != null) {
			doOk(req, resp);
		} else if (req.getParameter("cancelButton") != null) {
			doCancel(req, resp);
		} else {
			showPage(req, resp);
		}
	}
	

	private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/browse.jsp").forward(req, resp);
	}

	private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User)req.getSession().getAttribute("user");
		try {
			processUser(user);
		} catch (DatabaseException e) {
			throw new ServletException(e.getMessage());
		}
		req.getRequestDispatcher("/browse").forward(req, resp);
	}
	
	private void processUser(User user) throws DatabaseException {
		DAOFactory.getInstance().getUserDAO().delete(user);
	}


	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/delete.jsp").forward(req, resp);
	}
}
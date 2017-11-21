package ua.nure.baranov.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.baranov.User;
import ua.nure.baranov.db.DAOFactory;
import ua.nure.baranov.db.DatabaseException;
/**
 * Servlet that shows all users.
 * @author Yevhenii Baranov
 *
 */
public class BrowseServlet extends HttpServlet {

	private static final long serialVersionUID = -1924238328344691837L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("addButton") != null) {
			add(req, resp);
		} else if (req.getParameter("editButton") != null) {
			edit(req, resp);
		} else if (req.getParameter("deleteButton") != null) {
			delete(req, resp);
		} else if (req.getParameter("detailsButton") != null) {
			details(req, resp);
		} else {
			browse(req, resp);
		}
	}

	private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("id");
		if (idString == null || idString.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		try {
			User user = DAOFactory.getInstance().getUserDAO().find(new Long(idString));
			req.getSession().setAttribute("user", user);
		} catch (NumberFormatException | DatabaseException e) {
			req.setAttribute("error", "Error: "+e.getMessage());
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/details.jsp").forward(req, resp);
	
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("id");
		if (idString == null || idString.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		try {
			User user = DAOFactory.getInstance().getUserDAO().find(new Long(idString));
			req.getSession().setAttribute("user", user);
		} catch (NumberFormatException | DatabaseException e) {
			req.setAttribute("error", "Error: "+e.getMessage());
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/delete.jsp").forward(req, resp);
	}

	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idString = req.getParameter("id");
		if (idString == null || idString.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		try {
			User user = DAOFactory.getInstance().getUserDAO().find(new Long(idString));
			req.getSession().setAttribute("user", user);
		} catch (NumberFormatException | DatabaseException e) {
			req.setAttribute("error", "Error: "+e.getMessage());
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/edit.jsp").forward(req, resp);
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/add.jsp").forward(req, resp);
	}

	private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Collection<User> users = DAOFactory.getInstance().getUserDAO().findAll();
			req.getSession().setAttribute("users", users);
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
		} catch (DatabaseException e) {
			throw new ServletException(e.getMessage());
		}
	}
}

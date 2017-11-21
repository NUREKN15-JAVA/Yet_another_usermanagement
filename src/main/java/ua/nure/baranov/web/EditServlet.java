package ua.nure.baranov.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import ua.nure.baranov.User;
import ua.nure.baranov.db.DAOFactory;
import ua.nure.baranov.db.DatabaseException;
/**
 * Servlet that edits properties of the user
 * @author Yevhenii Baranov
 *
 */
public class EditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2794858538693796149L;

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

	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/edit.jsp").forward(req, resp);
	}

	private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/browse.jsp").forward(req, resp);
	}

	private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = null;
		try {
			user = getUserFromRequest(req);
		} catch (ValidationException e1) {
			req.setAttribute("error", e1.getMessage());
			showPage(req, resp);
			return;
		}
		try {
			processUser(user);
		} catch (DatabaseException e) {
			throw new ServletException(e.getMessage());
		}
		req.getRequestDispatcher("/browse").forward(req, resp);
	}

	protected void processUser(User user) throws DatabaseException {
		DAOFactory.getInstance().getUserDAO().update(user);
	}

	private User getUserFromRequest(HttpServletRequest req) throws ValidationException {
		try {
			String firstName = req.getParameter("firstName");
			if (firstName == null || firstName.length() == 0) {
				throw new ValidationException("First name is missing!");
			}
			String lastName = req.getParameter("lastName");
			if (lastName == null || lastName.length() == 0) {
				throw new ValidationException("Last name is missing!");
			}
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(format.parse(req.getParameter("birthday")));
			User user = new User(firstName, lastName, calendar);
			String idString = req.getParameter("id");
			if(idString != null)
			{
				user.setId(Long.valueOf(idString));
			}
			return user;
		} catch (ParseException e) {
			throw new ValidationException(e.getMessage());
		}
	}

}

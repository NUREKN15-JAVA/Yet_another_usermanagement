package ua.nure.baranov.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.baranov.User;
import ua.nure.baranov.db.DAOFactory;
import ua.nure.baranov.db.DatabaseException;
/**
 * Servlet that shows interface for adding user
 * @author Yevhenii Baranov
 *
 */
public class AddServlet extends EditServlet {
	
	private static final long serialVersionUID = -3010170393245009577L;

	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/add.jsp").forward(req, resp);
	}
	
	@Override
	protected void processUser(User user) throws DatabaseException {
		DAOFactory.getInstance().getUserDAO().create(user);
	}

}

package ua.nure.baranov.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet that shows all information about user
 * @author Yevhenii Baranov
 *
 */
public class DetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 2092789362454538401L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.getRequestDispatcher("/details.jsp").forward(req, resp);
	}
}

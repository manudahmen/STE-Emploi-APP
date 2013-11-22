package com.myapp.struts.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableFormations;
import steemploi.persistance.TableUtilisateurs;

/**
 * Servlet implementation class DeleteUtilisateur
 */
public class DeleteUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUtilisateur() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String str_id = request.getParameter("id");
		int id = Integer.parseInt(str_id);
		try {
			new TableUtilisateurs().delete(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/error.jsp").forward(
					request, response);
			return;
		}
		getServletContext().getRequestDispatcher("/success.jsp")
				.forward(request, response);
		return;
	}
}

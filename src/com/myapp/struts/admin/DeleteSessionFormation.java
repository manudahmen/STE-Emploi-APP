package com.myapp.struts.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableFormations;
import steemploi.persistance.TableSessionsFormations;

/**
 * Servlet implementation class DeleteSessionFormation
 */
public class DeleteSessionFormation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteSessionFormation() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String str_id = request.getParameter("id");
		int id = Integer.parseInt(str_id);
		try {
			new TableSessionsFormations().delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/error.jsp");
			return;
		}
		getServletContext().getRequestDispatcher("/admin/usersmanager.jsp")
				.forward(request, response);
		return;
	}
}

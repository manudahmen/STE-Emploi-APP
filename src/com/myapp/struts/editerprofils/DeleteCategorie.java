package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableLogiciels;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class DeleteCategorie extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5940444384821381146L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utilisateur user = null;
		if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null
			    && request.getSession(false).getAttribute("user") instanceof Utilisateur) {
					user = (Utilisateur) request.getSession(false).getAttribute("user");

				} else {

					request.getRequestDispatcher("/login.jsp").forward(request,
					        response);
					return ;
				}

			if (!user.getType().equals(TypeUtilisateur.FORMATEUR)) {
					request.getRequestDispatcher("/login.jsp").forward(request,
					        response);
					return ;
				}
			int cat = Integer.parseInt(request.getParameter("categorieId"));
			try {
				new TableLogiciels().deleteCategorie(cat);
				 response.getWriter().print("Catégorie de logiciels supprimée de la base de données OK");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("index_agenda.jsp?page=logiciels").forward(request, response);
	}

}

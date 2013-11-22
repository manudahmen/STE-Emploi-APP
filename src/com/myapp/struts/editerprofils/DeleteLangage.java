package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableLangages;
import steemploi.persistance.TableLangues;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class DeleteLangage extends HttpServlet {

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
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				new TableLangages().deleteLangage(id);
				 //response.getWriter().print("Langue supprimée OK");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("action", "index_agenda.jsp?page=langages");
			request.getRequestDispatcher("/success.jsp").forward(request, response);

	}

}

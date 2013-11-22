package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableLogiciels;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class DeleteLogiciel extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1966506204351146994L;

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
			int log = Integer.parseInt(request.getParameter("id"));
			int catId = -1;
			try {
				Profil p =new Profil();
				TableLogiciels tl= new TableLogiciels();
				tl.loadLogiciels(p);
				Profil.Logiciels l = p.getLogiciel(log);
				catId = l.getCategorieLogiciel();
				tl.deleteLogiciel(log);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("index_agenda.jsp?page=logiciels&catId="+catId).forward(request, response);
			
	}

}

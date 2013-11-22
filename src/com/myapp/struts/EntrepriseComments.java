package com.myapp.struts;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableEntreprise;
import steemploi.service.Entreprise;
import steemploi.service.Etudiant;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EntrepriseComments extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Utilisateur user = null;

		if (request.getSession(false) != null
				&& request.getSession(false).getAttribute("user") != null
				&& request.getSession(false).getAttribute("user") instanceof Utilisateur) {
			user = (Utilisateur) request.getSession(false).getAttribute("user");

		} else {

			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}

		if (!user.getType().equals(TypeUtilisateur.ETUDIANT)) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}

		int id = Integer.parseInt(request.getParameter("id"));
		String comment = request.getParameter("commentaires");
		int owner = user.getEtudiant().getId();
		TableEntreprise te = new TableEntreprise();
		try {
			te.updateCommentaires(id, comment, owner);
		} catch (SQLException e) {
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("success.jsp").forward(request, response);
		return;
	}

}

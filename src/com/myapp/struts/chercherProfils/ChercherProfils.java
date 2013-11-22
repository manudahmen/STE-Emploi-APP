package com.myapp.struts.chercherProfils;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.CVRepos;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class ChercherProfils extends HttpServlet {

	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		Logger logger = Logger.getLogger("com.myapp.struts.PostCV");
		logger.entering("ChercherPofils", "execute()");
		logger.info("ChercherProfils.do méthode execute()");
		Utilisateur user = null;
		String query = request.getParameter("nom");
		if (request.getSession(false) != null
				&& request.getSession(false).getAttribute("user") != null
				&& request.getSession(false).getAttribute("user") instanceof Utilisateur) {
			user = (Utilisateur) request.getSession(false).getAttribute("user");

		} else {

			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}

		if (!user.getType().equals(TypeUtilisateur.FORMATEUR)) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}
		request.setAttribute("action", request.getParameter("action"));
		request.getRequestDispatcher("/success.jsp").forward(request,
				response);
		return;
	}
}

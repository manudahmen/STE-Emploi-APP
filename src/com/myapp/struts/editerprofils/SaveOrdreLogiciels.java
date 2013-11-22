package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableLangages;
import steemploi.persistance.TableLangues;
import steemploi.persistance.TableLogiciels;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;
import steemploi.service.Profil.Langue;

public class SaveOrdreLogiciels extends HttpServlet {

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

		if (!user.getType().equals(TypeUtilisateur.FORMATEUR)) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}
		try {
			String ids = request.getParameter("hiddenNodeIds");
			StringTokenizer tokenizer = new StringTokenizer(ids);
			TableLogiciels tl = new TableLogiciels();
			int i = 1;
			while (tokenizer.hasMoreTokens()) {
				String str = tokenizer.nextToken(",");
				if (!str.equals("trash")) {
					int id = Integer.parseInt(str);
					tl.updateOrdreLogiciel(id, i);
					i++;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request,
					response);
		}
		request.getRequestDispatcher("/success.jsp").forward(request, response);
	}
}

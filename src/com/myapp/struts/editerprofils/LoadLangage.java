package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class LoadLangage extends HttpServlet {

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
		Profil profil = new Profil();
		try {
			profil.loadLangages();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Profil.Langage langage = profil.new Langage();
		for(Profil.Langage l :  profil.getLangages())
		{
			if(l.getId()==id)
			{
				langage = l;
			}
		}
		response.getWriter().print("<table>" +
				"<tr><td>Description</td><td><input type='text' name='description' value='"+langage.getDescription()+"'/></td></tr>" +
				"<tr><td>Nom langage</td><td><input type='text' name='nom' value='"+langage.getNom()+"'/></td></tr>" +
				"<tr><td>N°</td><td><input type='text' name='noOrdreLangage' value='"+langage.getNoOrdreLangage()+"'/></td></tr>" +
				"<tr><td>Versions (sep:';')</td><td><input type='text' name='versions' value='"+langage.getVersion()+"' maxlength='200'/></td></tr>" +
				"<tr><td></td><td>" +
				"<input type='hidden' name='action' value='index_agenda.jsp?page=langages'/>" +
				"<input type='hidden' name='id' value='"+langage.getId()+"'/>" +
				"<p><a href='#' class='submitButton' onclick='document.forms[\"langageForm\"].submit()'>Enregistrer</a></p>" +
				"</td></tr>" +
				"<tr><td></td><td>" +
				"<p><a class='submitButton' href='#' onclick='deleteLangage("+langage.getId()+")'>Supprimer</a></p>" +
				"</td></tr>" +
				"</table>");

	}

}

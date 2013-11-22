package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;
import steemploi.service.Profil.Langue;

public class LoadLangue extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8802269725546254829L;
	
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


		
		Profil profil = new Profil();
		profil.loadLangues();
		
		int id = Integer.parseInt(request.getParameter("id"));
		List<Langue> langues = profil.getLangues();

		Langue l = profil.new Langue();
		for(Profil.Langue langue : langues)
		{
			if(langue.getId()==id)
				l=langue;
		}
		response.getWriter().write(""+
"<table>" +
"<tr><td>Nom</td><td><input type='text' name='nomLangue' value='"+l.getNom()+"'/></td></tr>" +
"<tr><td>N° ordre</td><td><input type='text' name='noOrdreLangue' value='"+l.getNoOrdreLangue()+"'/></td></tr>" +
"<tr><td>Nom court (identifiant)</td><td><input type='text' name='nomCourt' value='"+l.getNomCourt()+"'/></td></tr>" +
"<input type='hidden' name='idLangue' value='"+l.getId()+"'/>" +
"<tr><td></td><td><p><a href='#' onclick='document.forms[\"langueForm\"].submit()' class='submitButton'>Enregistrer</a></p></td></tr>" +
"</table>" +
"");
		return;
	}
}
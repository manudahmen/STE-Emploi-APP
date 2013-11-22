package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.service.CategoriesTache;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;
import steemploi.service.Profil.CategoriesLogiciels;

public class Logiciels extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7156137470783079363L;

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
		profil.loadCategories();
		
		int cat = Integer.parseInt(request.getParameter("categorie_id"));
		ArrayList<Profil.Logiciels> logiciels = profil.getLogicielsCategories(profil.getCategorie(cat));

		response.getWriter().write("<select id='selectLogiciels1' name='logiciels' muliple='true' size='8' style='width: 300px;' onchange='logicielsOnChange2(this, "+cat+")'>");
		response.getWriter().write("<option value='0'>Ajouter un logiciel</option>");
		for(Profil.Logiciels l : logiciels)
		{
			response.getWriter().write("<option value='"+l.getIdLogiciel()+"'>"+l.getNomLogiciel()+"</option>");
		}		
		response.getWriter().write("</select>");
		
	}

}

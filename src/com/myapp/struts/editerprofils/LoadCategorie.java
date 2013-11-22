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

public class LoadCategorie extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3173816235714203429L;

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
		Profil profil = new Profil();
		Profil.CategoriesLogiciels cl = profil.new CategoriesLogiciels();
		try {
			cl = new TableLogiciels().findCategorieById(profil, cat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = "<table>" +
		"<tr><th style='width: 80px;'></th><th style='width: 220px;'></th></tr>" +
		"<tr><td>Nom</td><td><input type='text' name='nomCategorieLogiciel' id='nomCategorieLogiciel' value='"+cl.getNomCategorie()+"'/></td></tr>"+
		"<tr><td>Description</td><td><input type='text' name='description' id='description' value='"+cl.getDescription()+"'/></td></tr>"+
		"<tr><td>Num&eacute;mero d'ordre</td><td><input type='text' name='noOrdreCategorie' id='noOrdreCategorie' value='"+cl.getNoOrdreCategorie()+"'/></td></tr>"+
		"<tr><td></td><td>"
		+"<input type='hidden' name='idCategorieLogiciel' id='idCategorieLogiciel' value='"+cl.getIdCategorie()+"'/>"
		+"<tr><td></td><td><p><a class='submitButton' href='#' onclick='document.forms[\"catInfo\"].submit()'>Modifier</a></p></td></tr>"
		+"<tr><td></td><td><p><a href='DeleteCategorie.servlet?categorieId="+cat+"' class='submitButton' >Supprimer</a></p></td></tr>"
		+"</table>";
		response.getWriter().print(output);
		response.getWriter().flush();
	}

}

package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableLogiciels;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class Logiciel extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7628226443993838229L;

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
		// TODO Load logiciel
		int id = Integer.parseInt(request.getParameter("id"));
		int categorieLogiciel = Integer.parseInt(request.getParameter("categorieId"));
		TableLogiciels tl = new TableLogiciels();
		Profil p = new Profil();
		Profil.Logiciels l = p.new Logiciels();
		if(id!=0)
		{
		try {
			l = tl.findLogicielById(p, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
		{
			l.setCategorieLogiciel(categorieLogiciel);
			l.setDescriptionLogiciel("");
			l.setEditeurLogiciel("");
			l.setNomLogiciel("");
			l.setNoOrdreLogiciel(0);
			l.setVersion("");
		}
		PrintWriter out= response.getWriter();
		out.println("<table>" +
				"<tr><th style='width: 80px;'></th><th style='width: 220px;'></th></tr>" +
				"<tr><td>Nom</td><td><input type='text' name='nomLogiciel' value='"+l.getNomLogiciel()+"'/></td></tr>" +
				"<tr><td>Description</td><td><input type='text' name='descriptionLogiciel' value='"+l.getDescriptionLogiciel()+"'/></td></tr>" +
				"<tr><td>Editeur</td><td><input ty/pe='text' name='editeurLogiciel' value='"+l.getEditeurLogiciel()+"'/></td></tr>" +
				"<tr><td>Version</td><td><input type='text' name='versionLogiciel' value='"+l.getVersion()+"'/></td></tr>" +
				"<tr><td>Num&eacute;ro d'ordre</td><td><input type='text' name='noOrdreLogiciel' value='"+l.getNoOrdreLogiciel()+"'/></td></tr>" +
				"<tr><td></td><td>" +
				"<input type='hidden' name='idLogiciel' value='"+l.getIdLogiciel()+"'/>" +
				"<input type='hidden' name='categorieLogiciel' value='"+l.getCategorieLogiciel()+"'/>" +
				"<tr><td></td><td><p><a href='#' class='submitButton' onclick='document.forms[\"logiciels\"].submit();'>Modifier</a></p></td></tr>" +
				"<tr><td></td><td><p><a href='DeleteLogiciel.servlet?id="+id+"' class='submitButton' >Supprimer</a></p></td></tr>" +
				"</table>");
		response.getWriter().flush();
	}

}

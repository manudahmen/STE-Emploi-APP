package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class LoadListeLogiciels extends HttpServlet {

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

		Profil profil = new Profil();
		profil.loadCategories();

		int cat = Integer.parseInt(request.getParameter("categorieId"));
		int logId =0; 
		String logIdStr = request.getParameter("logId");
		if(logIdStr!=null) 
			logId = Integer.parseInt(logIdStr);
		ArrayList<Profil.Logiciels> logiciels = profil
				.getLogicielsCategories(profil.getCategorie(cat));

		response.getWriter().write("<ul id=\"arrangableNodes2\">");
		for (Profil.Logiciels l : logiciels) {
			response.getWriter().write(
					"<li id='l" + l.getIdLogiciel() + "'>" + l.getNomLogiciel()
							+ "</li>");
		}
		response.getWriter().write("<li id='trash' style='text-decoration: underline; background-color: gray; color: black;'>Corbeille</li>");
		response.getWriter().write("</ul>");
		response.getWriter().write("<h3>S&eacute;lectionnez un logiciel</h3>");

		response.getWriter().write(
				"<form name='logicielSelect' action='index_agenda.jsp'>");
		response.getWriter().write(
				"<input type='hidden' name='page' value='logiciels' />");
		response.getWriter().write(
				"<input type='hidden' name='catId' value='" + cat + "' />");
		response.getWriter().write(
						"<select name='logId' onchange=\"document.forms['logicielSelect'].submit();\">"+
						"<option value='-1' "+(logId==-1?"selected='true'":"")+">---</option>"+
						"<option value='0' "+(logId==0?"selected='true'":"")+">Nouveau logiciel</option>");
		for (Profil.Logiciels l : logiciels) {
			response.getWriter().write(
					"<option value='" + l.getIdLogiciel() + "' "+((l.getIdLogiciel()==logId)?"selected='true'":"")+">"
							+ l.getNomLogiciel() + "</option>");
		}
		response.getWriter().write("</select>");
		response.getWriter().write("</form>");
		response.getWriter().flush();
	}
}

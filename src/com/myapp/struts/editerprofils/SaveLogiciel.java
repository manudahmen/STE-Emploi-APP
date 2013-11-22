package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import steemploi.persistance.TableLogiciels;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class SaveLogiciel extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 340068004678549098L;

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
		int idLogiciel = Integer.parseInt(request.getParameter("idLogiciel"));
		int noOrdreLogiciel = Integer.parseInt(request.getParameter("noOrdreLogiciel"));
		String nomLogiciel = request.getParameter("nomLogiciel");
		String descriptionLogiciel = request.getParameter("descriptionLogiciel");
		String versionLogiciel = request.getParameter("versionLogiciel");
		String editeurLogiciel = request.getParameter("editeurLogiciel");
		int categorieLogiciel = Integer.parseInt(request.getParameter("categorieLogiciel"));
		Profil p = new Profil();

		Profil.Logiciels l =   p.new Logiciels();
		l.setIdLogiciel(idLogiciel);
		l.setNoOrdreLogiciel(noOrdreLogiciel);
		l.setNomLogiciel(nomLogiciel);
		l.setDescriptionLogiciel(descriptionLogiciel);
		l.setEditeurLogiciel(editeurLogiciel);
		l.setCategorieLogiciel(categorieLogiciel);
		l.setVersion(versionLogiciel);
		TableLogiciels tl = new TableLogiciels();
		try {
			tl.save(l);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
		request.getRequestDispatcher("/success.jsp").forward(request, response);
	}

}

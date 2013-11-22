package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableLangues;
import steemploi.persistance.TableLogiciels;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;
import steemploi.service.Profil.Langue;
import steemploi.service.Profil.Logiciels;

public class SaveLangue extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703401140342722561L;

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
		int idLangue= Integer.parseInt(request.getParameter("idLangue"));
		int noOrdreLangue = Integer.parseInt(request.getParameter("noOrdreLangue"));
		String nomLangue= request.getParameter("nomLangue");
		String nomCourt=request.getParameter("nomCourt");
		Profil p = new Profil();
		Langue l = p.new Langue();
		l.setId(idLangue);
		l.setNom(nomLangue);
		l.setNoOrdreLangue(noOrdreLangue);
		l.setNomCourt(nomCourt);
		TableLangues tl = new TableLangues();
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

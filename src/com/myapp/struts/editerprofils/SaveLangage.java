package com.myapp.struts.editerprofils;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import steemploi.persistance.TableLangage;
import steemploi.persistance.TableLangages;
import steemploi.persistance.TableLangues;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;
import steemploi.service.Profil.Langue;

public class SaveLangage extends HttpServlet {
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
		int id= Integer.parseInt(request.getParameter("id"));
		int noOrdreLangage = Integer.parseInt(request.getParameter("noOrdreLangage"));
		String nom= request.getParameter("nom");
		String versions=request.getParameter("versions");
		String description=request.getParameter("description");
		Profil p = new Profil();

		Profil.Langage l = p.new Langage();
		l.setId(id);
		l.setNom(nom);
		l.setNoOrdreLangage(noOrdreLangage);
		l.setVersion(versions);
		l.setDescription(description);
		TableLangages tl = new TableLangages();
		try {
			tl.save(l);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("/success.jsp").forward(request, response);
	}

		
	

}

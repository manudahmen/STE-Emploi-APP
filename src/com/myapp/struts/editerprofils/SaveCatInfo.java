package com.myapp.struts.editerprofils;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableLogiciels;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class SaveCatInfo extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		Utilisateur user = null;

		if (request.getSession(false) != null
				&& request.getSession(false).getAttribute("user") != null
				&& request.getSession(false).getAttribute("user") instanceof Utilisateur) {
			user = (Utilisateur) request.getSession(false).getAttribute("user");

		} else {

			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return mapping.findForward("error");
		}

		if (!user.getType().equals(TypeUtilisateur.FORMATEUR)) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return mapping.findForward("error");
		}

		Profil profil = new Profil();
		profil.loadCategories();
		int cat = Integer.parseInt(request.getParameter("idCategorieLogiciel"));
		String nom = request.getParameter("nomCategorieLogiciel");
		String descriptionCategorie = request.getParameter("description");
		int noOrdreCategorie = Integer.parseInt(request
				.getParameter("noOrdreCategorie"));
		Profil.CategoriesLogiciels cl = profil.new CategoriesLogiciels();
		cl.setIdCategorie(cat);
		cl.setNomCategorie(nom);
		cl.setDescription(descriptionCategorie);
		cl.setNoOrdreCategorie(noOrdreCategorie);
		// Enregistrement
		try {
			new TableLogiciels().saveCatInfo(cl);
		} catch (SQLException e) {
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
	}

}

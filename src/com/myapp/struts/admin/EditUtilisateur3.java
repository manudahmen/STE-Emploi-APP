package com.myapp.struts.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import steemploi.persistance.TableEtudiants;
import steemploi.persistance.TableSessionsFormations;
import steemploi.persistance.TableUtilisateurs;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditUtilisateur3 extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Utilisateur user;
		if (request.getSession(false) != null
				&& request.getSession(false).getAttribute("user") != null
				&& request.getSession(false).getAttribute("user") instanceof Utilisateur) {
			user = (Utilisateur) request.getSession(false).getAttribute("user");

		} else {

			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return mapping.findForward("error");
		}

		if (!user.getType().equals(TypeUtilisateur.ADMIN)) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return mapping.findForward("error");
		}

		try {
			StudentManager sm = new StudentManager();
			Utilisateur u = (Utilisateur) form;
			u.setId(sm.updateUser(u));
			u.setType(u.getUsertype().equals("etudiant")?TypeUtilisateur.ETUDIANT:(u.getUsertype().equals("formateur")?TypeUtilisateur.FORMATEUR:TypeUtilisateur.ADMIN));
			if(u.getType().equals(TypeUtilisateur.ETUDIANT))
			{
				sm.updateEtudiant(u, u.getFormation_id());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
	}

}

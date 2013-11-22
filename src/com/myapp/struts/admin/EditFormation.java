package com.myapp.struts.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableFormations;
import steemploi.service.Formation;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditFormation extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Utilisateur user;
		if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null
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
			Formation f = (Formation) form;
			new TableFormations().save(f);
		} catch (Exception ex) {
			ex.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
	}

}

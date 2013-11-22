package com.myapp.struts;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;
import steemploi.persistance.*;
public class DeleteCV extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		Logger logger = Logger.getLogger("com.myapp.struts.PostCV");
		logger.entering("PostCV", "execute()");
		logger.info("PostCV.do méthode execute()");
		// TODO Auto-generated method stub
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

		if (!user.getType().equals(TypeUtilisateur.ETUDIANT)) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return mapping.findForward("error");
		}
		CVRepos repos = new CVRepos();
		repos.deleteCV(repos.findByEtudiantId(user.getEtudiant().getId()), user.getEtudiant().getId());
		return mapping.findForward("success");
	}

}

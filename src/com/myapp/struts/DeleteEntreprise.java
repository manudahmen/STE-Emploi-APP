package com.myapp.struts;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableEntreprise;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class DeleteEntreprise extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Utilisateur user = null;

		if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null
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
		String str_id = request.getParameter("id");
		int id = Integer.parseInt(str_id);
		try
		{
		TableEntreprise te =  new TableEntreprise();
		te.delete(id, user.getEtudiant().getId());
		return mapping.findForward("success");
		}
		catch(Exception ex)
		{
			Logger logger= Logger.getLogger("com.myapp.struts.DeleteEntreprise");
			logger.warning("Exception lors de la suppression de l'entreprise" + id + "\nMessage: " + ex.getMessage());
		}
		return mapping.findForward("error");
	}

}

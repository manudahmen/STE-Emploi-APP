package com.myapp.struts;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableCandidature;
import steemploi.service.Candidature;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;


/**
 *
 * @author manuel
 */

public class DeleteCandidature extends Action {

		@Override
		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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

				Candidature c = (Candidature) form;
				TableCandidature tc = new TableCandidature();
				tc.delete(c.getId());
				return mapping.findForward("success");
			}

	}

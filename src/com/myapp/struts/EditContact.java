package com.myapp.struts;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableContact;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditContact extends Action {

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

				try {
						steemploi.service.Contact c = (steemploi.service.Contact) form;
						TableContact tc = new TableContact();
						tc.update(c, c.getId());
						return mapping.findForward("success");

					} catch (Exception ex) {
						Logger logger = Logger.getLogger("com.struts.myapp.EditContact");
						logger.warning("Enregistrer contact - échec");
						ex.printStackTrace();
						return mapping.findForward("error");
					}
			}

	}

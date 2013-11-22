/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package com.myapp.struts;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.Validator;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.ValidatorForm;

import steemploi.persistance.TableEntreprise;
import steemploi.service.Entreprise;
import steemploi.service.Etudiant;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

/**
 * 
 * @author manuel
 */

public class EditEntreprise extends org.apache.struts.action.Action {

		/* forward name="success" path="" */

		/**
		 * This is the action called from the Struts framework.
		 * 
		 * @param mapping
		 *            The ActionMapping used to select this instance.
		 * @param form
		 *            The optional ActionForm bean for this request.
		 * @param request
		 *            The HTTP Request we are processing.
		 * @param response
		 *            The HTTP Response we are processing.
		 * @throws java.lang.Exception
		 * @return
		 */
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

				Entreprise e = (Entreprise) form;
				Etudiant owner = new Etudiant();
				TableEntreprise te = new TableEntreprise();
				te.update(e);
				int idnew = te.selectLastInserted();
				request.setAttribute("idnew", idnew);
				return mapping.findForward("success");
			}

	}

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.myapp.struts;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import steemploi.persistance.TableTaches;
import steemploi.service.Tache;
import steemploi.service.Utilisateur;

/**
 *
 * @author manuel
 */

public class ListTaskAction extends org.apache.struts.action.Action {

		/* forward name="success" path="" */
		private final static String SUCCESS = "success";

		/**
		 * This is the action called from the Struts framework.
		 * @param mapping The ActionMapping used to select this instance.
		 * @param form The optional ActionForm bean for this request.
		 * @param request The HTTP Request we are processing.
		 * @param response The HTTP Response we are processing.
		 * @throws java.lang.Exception
		 * @return
		 */
		public ActionForward execute(ActionMapping mapping, ActionForm form,
		                             HttpServletRequest request, HttpServletResponse response)
		throws Exception {
				try {
						if (((Utilisateur)( request.getSession(false).getAttribute("user"))).getId() == 0)
							return mapping.findForward("error");

					} catch (Exception ex) {
						ex.printStackTrace();
						return mapping.findForward("error");
					}

				Tache e = (Tache) form;
				TableTaches taches = new TableTaches();


				//BeanUtils.copyProperties(e, te);

				try {
						taches.update(e);

					} catch (Exception ex) {
						return mapping.findForward("ERROR");
					}

				List<Tache> te = taches.findByEtudiant(1);
				return mapping.findForward("SUCCESS");
			}
	}

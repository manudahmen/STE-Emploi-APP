/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package com.myapp.struts;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import steemploi.persistance.TableTaches;
import steemploi.service.Tache;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

/**
 *
 * @author manuel
 */

public class EditTask extends Action {

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

				Tache e = (Tache) form;
				Calendar cal = Calendar.getInstance();
				cal.setTime(DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).parse(e.getDateCompleted_()));
				e.setDateCompleted(cal);
				TableTaches te = new TableTaches();
				te.update(e);
				return mapping.findForward("success");
			}

	}

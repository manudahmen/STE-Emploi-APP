package com.myapp.struts;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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

public class EditCandidature extends Action {

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
						if (form == null)
							return mapping.findForward("error");

						Candidature c = (Candidature) form;

						Calendar cal = Calendar.getInstance();

						cal.setTime(DateFormat.getDateInstance(DateFormat.SHORT,
						                                       Locale.FRANCE).parse(c.getDate_()));

						c.setDate(cal);

						TableCandidature table = new TableCandidature();

						table.save(c);

						table.ExecuteUpdate();

						return mapping.findForward("success");

					} catch (Exception ex) {
						ex.printStackTrace();
						return mapping.findForward("error");
					}

			}
	}

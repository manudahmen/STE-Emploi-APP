package com.myapp.struts;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableUtilisateurs;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;


public class EditUtilisateur extends Action {

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

				if (!(user.getType().equals(TypeUtilisateur.ETUDIANT) || user.getType().equals(TypeUtilisateur.FORMATEUR))) {
						request.getRequestDispatcher("/login.jsp").forward(request,
						        response);
						return mapping.findForward("error");
					}

				if (form == null)
					return mapping.findForward("error");

				Utilisateur u = (Utilisateur) form;

				//request.getSession(false).setAttribute("user", u);
				TableUtilisateurs table = new TableUtilisateurs();

				table.save(u);

				table.ExecuteUpdate();

				return mapping.findForward("success");
			}

	}

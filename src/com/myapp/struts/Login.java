/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.struts;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMessages;

import steemploi.persistance.TableUtilisateurs;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

/**
 * 
 * @author manuel
 */

public class Login extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Logger logger = Logger.getLogger("com.myapp.struts.Login");
		try {
			// response.getWriter().append( request.getParameter("action"));
			steemploi.service.Login l = (steemploi.service.Login) form;
			// Vérifier l'utilisateur
			long sess_id = new TableUtilisateurs().login(l);
			// Stocker l'utilisateur dans la session en cours

			if (sess_id > 0) {
				HttpSession s = request.getSession(true);
				s.setAttribute("session_id", sess_id);
				s.setAttribute("duration", 3600);
				TableUtilisateurs tu = new TableUtilisateurs();
				if (tu != null) {
					Utilisateur user = tu.findByusername(l.getUsername());
					if (user != null) {
						s.setAttribute("user", user);
						logger.info("Utilisateur touvé: " + user.getId()
								+ " type: " + user.getTypeString());
						logger
								.info("Type id= "
										+ (user.getType().equals(
												TypeUtilisateur.ADMIN) ? user
												.getId()
												: ((user.getType()
														.equals(TypeUtilisateur.FORMATEUR)) ? user
														.getFormateur().getId()
														: user.getEtudiant()
																.getId())));
						if (user.getType().equals(TypeUtilisateur.ADMIN)) {
							return mapping.findForward("adminpage");
						}
						return mapping.findForward("success");
					} else {
						logger.warning("Utilisateur non touvé");
						ActionErrors errors = new ActionErrors();
						errors.add("match", new ActionMessage("errors.login")); 
						addErrors(request, errors);
						return mapping.findForward("error");

					}
				} else {
					logger.warning("problème de connexion db");
					ActionErrors errors = new ActionErrors();
					errors.add("dbconnection", new ActionMessage("errors.dbconnection")); 
					addErrors(request, errors);
					return mapping.findForward("error");
				}

			} else {
				logger
						.warning("utilisateur non trouvé ou problème de connexion db");
				ActionErrors errors = new ActionErrors();
				errors.add("dbconnection", new ActionMessage("errors.login")); 
				addErrors(request, errors);
				return mapping.findForward("error");
			}

			// Vérifier à chaque accès à une jsp le jeton

		} catch (NullPointerException ex) {
			logger.warning("NullPointerException dans Login");
			logger.log(Level.WARNING, "NullPointerException", (Throwable) ex);
			ex.printStackTrace();
			ActionErrors errors = new ActionErrors();
			errors.add("dbconnection", new ActionMessage("errors.dbconnection")); 
			addErrors(request, errors);
			return mapping.findForward("error");

		} catch (Exception ex) {
			logger.warning("Exception dans Login");
			logger.log(Level.WARNING, "Exception", (Throwable) ex);
			ActionErrors errors = new ActionErrors();
			errors.add("dbconnection", new ActionMessage("errors.dbconnection")); 
			addErrors(request, errors);
			return mapping.findForward("error");
		}
	}
}

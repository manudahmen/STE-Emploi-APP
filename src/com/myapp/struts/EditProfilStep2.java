package com.myapp.struts;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableProfils;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditProfilStep2 extends Action {

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

				if (form == null)
					return mapping.findForward("error");

				HttpSession s = request.getSession();

				Profil profil = (Profil) form;

				s.setAttribute("etudforma", profil);

				
				if (request.getParameter("reset") != null) {
						profil.clearDiplome();
						profil.setIdDiplome(0);
						return mapping.findForward("success");
					}

				if (request.getParameter("delete") != null) {
						String idStr = request.getParameter("id");

						if (idStr != null) {
								int id = Integer.parseInt(idStr);
								profil.deleteDiplome(id);
								new TableProfils().deleteDiplome(profil, id);
								profil.clearDiplome();
								s.setAttribute("etudforma", profil);
								return mapping.findForward("success");

							} else
							return mapping.findForward("error");
					}

				s.setAttribute("etudforma", profil);

				if (request.getParameter("obtenu") == null) {
						profil.setObtenu(false);

					} else {
						profil.setObtenu(true);
					}

				Calendar cal = Calendar.getInstance();
				cal.setTime(DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE)
				            .parse(profil.getDate_Debut_Formation()));
				profil.setDateDebutFormation(cal);

				cal = Calendar.getInstance();
				cal.setTime(DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE)
				            .parse(profil.getDate_Fin_Formation()));
				profil.setDateFinFormation(cal);

				
				
				TableProfils tp = new TableProfils();
				tp.setEtudiantId(user.getEtudiant().getId());
				int id = tp.saveDiplome(profil);
				if (profil.getIdDiplome() == 0) {
						profil.setIdDiplome(id);
						profil.addDiplome();

					} else {
						profil.updateDiplome();
					}

				profil.clearDiplome();
				s.setAttribute("etudforma", profil);
				return mapping.findForward("success");
			}

	}

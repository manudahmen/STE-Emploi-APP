package com.myapp.struts;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableCandidature;
import steemploi.persistance.TableProfils;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditProfilStep4 extends Action {

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

				s.removeAttribute("etudforma");

				if (request.getParameter("permisDeConduire") == null) {
						profil.setPermisDeConduire(false);

					} else {
						profil.setPermisDeConduire(true);
					}
				if (request.getParameter("licence") == null) {
					profil.setLicence(false);

				} else {
					profil.setLicence(true);
				}
				if (request.getParameter("voiture") == null) {
					profil.setVoiture(false);

				} else {
					profil.setVoiture(true);
				}
				
				TableProfils tp = new TableProfils();
				tp.setEtudiantId(user.getEtudiant().getId());
				tp.savePermis(profil);

				System.out.println("Checkbox permis de conduire : " + (profil.getPermisDeConduire() ? "true" : "false"));
				s.setAttribute("etudforma", profil);
				return mapping.findForward("success");
			}


	}

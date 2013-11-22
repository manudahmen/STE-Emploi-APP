package com.myapp.struts;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

public class EditProfilStep7 extends Action {

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

				profil.loadLangues();
				
				Map<String, String[]> parameters = request.getParameterMap();

				Set<String> keys = parameters.keySet();

				Iterator<String> it = keys.iterator();

				while (it.hasNext()) {
						String str = it.next();

						if (str.startsWith("langue_")) {
								String strLangue = str.substring("langue_".length());
								char c = strLangue.charAt(strLangue.indexOf('_')+1);
								String idLan = strLangue.substring(0, strLangue.indexOf('_'));
								int level = Integer.parseInt(request.getParameter(str));
								System.out.println(idLan+" "+c+" "+level);
								profil.setNiveauConnaissanceLangue(idLan, c, level);

							}

					}
				TableProfils tp = new TableProfils();
				tp.setEtudiantId(user.getEtudiant().getId());
				tp.saveLangues(profil);
				s.setAttribute("etudforma", profil);
				return mapping.findForward("success");
			}


	}

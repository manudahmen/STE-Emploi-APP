package com.myapp.struts;

import java.util.ArrayList;
import java.util.HashMap;
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

public class EditProfilStep6 extends Action {

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
				
				Map<String, String[]> parameters = request.getParameterMap();

				Set<String> keys = parameters.keySet();

				Iterator<String> it = keys.iterator();

				profil.loadLangages();
				profil.setNiveauconnaissanceLangage(new HashMap<Integer, Integer>());
				
				while (it.hasNext()) {
					String str = it.next();

					if (str.startsWith("langage_")) {
							String strLangue = str.substring("langage_".length());
							int id = Integer.parseInt(strLangue);
							int level = Integer.parseInt(request.getParameter(str));
							Set<String> versionsKeys = parameters.keySet();
							Iterator<String> versions = versionsKeys.iterator();
							ArrayList<String> versionsSaved = new ArrayList<String>();
							while(versions.hasNext())
							{
								String version = versions.next();
								if(version.startsWith("langageversion_"+id+"_"))
								{
									String versionName = version.substring(("langageversion_"+id+"_").length());
									if(request.getParameter("langageversion_"+id+"_"+versionName)!=null)
										versionsSaved.add(versionName);
								}
							}
							profil.setNiveauconnaissanceLangage(id, level, versionsSaved);
						}

				}
				
				
				TableProfils tp = new TableProfils();
				tp.setEtudiantId(user.getEtudiant().getId());
				tp.saveLangages(profil);

				s.setAttribute("etudforma", profil);

				return mapping.findForward("success");
			}


	}

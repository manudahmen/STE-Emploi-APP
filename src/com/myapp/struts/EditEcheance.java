package com.myapp.struts;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import steemploi.persistance.TableEcheance;
import steemploi.service.Echeance;
import steemploi.service.Etudiant;
import steemploi.service.SessionsFormations;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

/**
 * 
 * @author manuel
 */

public class EditEcheance extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Utilisateur user = null;
		if (request.getSession(false) != null
				&& request.getSession(false).getAttribute("user") != null
				&& request.getSession(false).getAttribute("user") instanceof Utilisateur) {
			user = (Utilisateur) request.getSession(false).getAttribute("user");

		} else {

			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return mapping.findForward("error");
		}

		if (!user.getType().equals(TypeUtilisateur.FORMATEUR)) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return mapping.findForward("error");
		}

		try {
			Echeance e = (Echeance) form;

			String etudforma = e.get_etudforma();
			String[] split = etudforma.split(";");
			e.setEtudiants(new ArrayList<Etudiant>());
			e.setFormations(new ArrayList<SessionsFormations>());

			for (int i = 0; i < split.length; i++) {
				String s = split[i];
				String t = s.substring(0, 1);
				int value = Integer.parseInt(s.substring(2));

				if (t.equals("e")) {
					Etudiant et = new Etudiant();
					et.setId(value);
					e.getEtudiants().add(et);

				} else if (t.equals("f")) {
					SessionsFormations f = new SessionsFormations();
					f.setId(value);
					e.getFormations().add(f);
				}
			}

			TableEcheance te = new TableEcheance();
			int id = te.save(e);
			request.setAttribute("idnew", id);
			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
			return mapping.findForward("error");
		}
	}
}

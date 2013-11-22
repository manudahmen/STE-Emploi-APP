package com.myapp.struts;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

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

public class EditProfilStep3 extends Action {

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
			profil.clearEmployeur();
			profil.setIdEmploi(0);
			return mapping.findForward("success");

		}

		if (request.getParameter("delete") != null) {
			String idStr = request.getParameter("id");

			if (idStr != null) {
				int id = Integer.parseInt(idStr);
				profil.deleteEmployeur(id);
				TableProfils tp = new TableProfils();
				tp.setEtudiantId(user.getEtudiant().getId());
				tp.deleteExperience(profil, id);
				profil.clearEmployeur();
				profil.setIdEmploi(0);
				s.setAttribute("etudforma", profil);
				return mapping.findForward("success");

			} else
				return mapping.findForward("error");
		}

		if (request.getParameter("finContrat") != null) {
			profil.setFinContrat(true);

		} else {
			profil.setFinContrat(false);
		}

		s.setAttribute("etudforma", profil);


		Calendar cal = Calendar.getInstance();
		cal.setTime(DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE)
				.parse(profil.getDate_Debut_Emploi()));
		profil.setDateDebutEmploi(cal);

		cal = Calendar.getInstance();
		cal.setTime(DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE)
				.parse(profil.getDate_Fin_Emploi()));
		profil.setDateFinEmploi(cal);

		
		TableProfils tp = new TableProfils();
		tp.setEtudiantId(user.getEtudiant().getId());
		int id = tp.saveExperience(profil);
		if (profil.getIdEmploi() == 0) {
			profil.setIdEmploi(id);
			profil.addEmployeur();

		} else {
			profil.updateEmployeur();
		}

		profil.clearEmployeur();
		s.setAttribute("etudforma", profil);

		return mapping.findForward("success");

	}
}

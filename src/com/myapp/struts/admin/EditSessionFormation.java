package com.myapp.struts.admin;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableSessionsFormations;
import steemploi.service.SessionsFormations;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditSessionFormation extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Utilisateur user;
		if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null
			    && request.getSession(false).getAttribute("user") instanceof Utilisateur) {
					user = (Utilisateur) request.getSession(false).getAttribute("user");

				} else {

					request.getRequestDispatcher("/login.jsp").forward(request,
					        response);
					return mapping.findForward("error");
				}

			if (!user.getType().equals(TypeUtilisateur.ADMIN)) {
					request.getRequestDispatcher("/login.jsp").forward(request,
					        response);
					return mapping.findForward("error");
				}
		SessionsFormations sf = (SessionsFormations) form;
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.FRANCE);
		Calendar cal =Calendar.getInstance(); 
		cal.setTime(df.parse(sf.getDate_Start()));
		sf.setDateStart(cal);
		cal =Calendar.getInstance(); 
		cal.setTime(df.parse(sf.getDate_End()));
		sf.setDateEnd(cal);
		try {
			new TableSessionsFormations().save(sf);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}
}

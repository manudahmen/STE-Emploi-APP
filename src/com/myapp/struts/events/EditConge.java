package com.myapp.struts.events;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableConge;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditConge extends Action {

		@Override
		public ActionForward execute(ActionMapping mapping, ActionForm form,
		                             HttpServletRequest request, HttpServletResponse response) throws Exception {
				Utilisateur user = null;

				if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null
				    && request.getSession(false).getAttribute("user") instanceof Utilisateur) {
						user = (Utilisateur) request.getSession(false).getAttribute("user");

					} else {

						request.getRequestDispatcher("/login.jsp").forward(request,
						        response);
						return mapping.findForward("error");
					}

				if (!(user.getType().equals(TypeUtilisateur.FORMATEUR))) {
						request.getRequestDispatcher("/login.jsp").forward(request,
						        response);
						return mapping.findForward("error");
					}

				try {
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"
						                                              );
						SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy"
						                                               );
						Date date = null;
						Date date2 = null;
						Calendar cal = Calendar.getInstance();
						Calendar cal2 = Calendar.getInstance();

						if (form == null)
							return mapping.findForward("error");

						Conge conge = (Conge) form;

						System.out.println(conge.getDate_Debut());

						System.out.println( conge.getDate_Fin());

						date = format.parse(conge.getDate_Debut());

						cal.setTime(date);

						conge.setDateDebut(cal);

						date2 = format.parse(conge.getDate_Fin());

						cal2.setTime(date2);

						conge.setDateFin(cal2);

						TableConge table = new TableConge();

						table.save(conge);

						table.ExecuteUpdate();

						return mapping.findForward("success");

					} catch (SQLException ex) {
						ex.printStackTrace(System.out);
						return mapping.findForward("error");
					}
			}

	}

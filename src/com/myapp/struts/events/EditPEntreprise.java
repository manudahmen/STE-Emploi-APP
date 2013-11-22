package com.myapp.struts.events;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TablePEntreprise;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditPEntreprise extends Action {

		@Override
		public ActionForward execute(ActionMapping mapping, ActionForm form,
		                             HttpServletRequest request, HttpServletResponse response) throws Exception {
				Logger logger = Logger.getLogger("com.myapp.struts.EditPEntreprise");
				Utilisateur user = null;

				if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null
				    && request.getSession(false).getAttribute("user") instanceof Utilisateur) {
						user = (Utilisateur) request.getSession(false).getAttribute("user");

					} else {
						logger.info("redirect1");
						request.getRequestDispatcher("/login.jsp").forward(request,
						        response);
						return mapping.findForward("error");
					}

				if (!(user.getType().equals(TypeUtilisateur.FORMATEUR))) {
						logger.info("redirect2");
						request.getRequestDispatcher("/login.jsp").forward(request,
						        response);
						return mapping.findForward("error");
					}

				try {
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"
						                                              );
						Date date = null;
						Calendar cal = Calendar.getInstance();

						if (form == null)
							return mapping.findForward("error");

						PEntreprise pe = (PEntreprise) form;

						System.out.println(pe.getDate_Debut());

						date = format.parse(pe.getDate_Debut());

						cal.setTime(date);

						pe.setDateDebut(cal);

						TablePEntreprise table = new TablePEntreprise();

						table.save(pe);

						table.ExecuteUpdate();

						return mapping.findForward("success");

					} catch (SQLException ex) {
						ex.printStackTrace(System.out);
						return mapping.findForward("error");
					}
			}

	}

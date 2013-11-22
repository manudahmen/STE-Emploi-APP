package com.myapp.struts.events;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableJPO;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditJPO extends Action {

		@Override
		public ActionForward execute(ActionMapping mapping, ActionForm form,
		                             HttpServletRequest request, HttpServletResponse response)
		throws Exception {
				try {
						if (((Utilisateur)( ((HttpServletRequest) request).getSession(false).getAttribute("user"))).getId() == 0)
							return mapping.findForward("error");

					} catch (Exception ex) {
						ex.printStackTrace();
						return mapping.findForward("error");
					}

				try {
						JPO jpo = (JPO) form;
						Calendar cal = Calendar.getInstance();
						System.out.println("Date_Debut: " + jpo.getDate_Debut());
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						Date date = format.parse(jpo.getDate_Debut());
						cal.setTime(date);
						System.out.println(cal.getTime().toString());
						jpo.setDateDebut(cal);
						TableJPO table = new TableJPO();
						int id = table.save(jpo);
						table.ExecuteUpdate();
						return mapping.findForward("success");

					} catch (Exception ex) {
						ex.printStackTrace(System.out);
						return mapping.findForward("error");
					}
			}

	}

package com.myapp.struts.events;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;

import steemploi.persistance.TableConge;
import steemploi.persistance.TableEcheance;
import steemploi.persistance.TableEvt;
import steemploi.persistance.TableJPO;
import steemploi.persistance.TablePEntreprise;
import steemploi.persistance.TablePresentationEntreprise;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class DeleteEvt extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utilisateur user = null;

		if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null
		    && request.getSession(false).getAttribute("user") instanceof Utilisateur) {
				user = (Utilisateur) request.getSession(false).getAttribute("user");

			} else {

				request.getRequestDispatcher("/login.jsp").forward(request,
				        response);
				return;
			}

		if (!(user.getType().equals(TypeUtilisateur.FORMATEUR))) {
				request.getRequestDispatcher("/login.jsp").forward(request,
				        response);
				return;
			}

		String str_eventid = request.getParameter("id");
		String str_eventtype = request.getParameter("eventtype");

		int id = Integer.parseInt(str_eventid);
		int eventtype = Integer.parseInt(str_eventtype);

		try {
			switch (eventtype) {
			case 1:
				new TableEcheance().delete(id);
				break;
			case 2:
				new TableJPO().delete(id);
				break;
			case 3:
				new TablePEntreprise().delete(id);
				break;
			case 4:
				new TableConge().delete(id);
				break;
			case 5:
				new TableEvt().delete(id);
				break;
			default:
				break;
			}
		} catch (Exception ex) {
			Logger logger = Logger.getLogger("com.myapp.struts.events");
			logger.error("Erreur SQL", ex);
			return;
		}
		request.setAttribute("action", "index_agenda.jsp?page=creerEvenement&eventtype="+eventtype);
		request.getRequestDispatcher("/success.jsp").forward(request,
		        response);
		return;
	}

}

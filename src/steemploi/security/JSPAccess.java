package steemploi.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class JSPAccess implements Filter {

	private Object fc;

	public void destroy() {
		this.fc = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		String url = ((HttpServletRequest) request).getRequestURI();
		Logger log = java.util.logging.Logger.getLogger("security.JSPAccess");
		boolean ok = false;
		Utilisateur user = null;
		url = url.substring(url.indexOf('/') + 1);
		url = url.substring(url.indexOf('/') + 1);
		log.info("URL: " + url);
		HttpSession session = (((HttpServletRequest) request).getSession(false));

		if (url == null || url.trim().equals("") || url.startsWith("login.jsp")
				|| url.startsWith("Login.do") || url.startsWith("logout.jsp")) {
			ok = true;

		} else
		if(url.startsWith("test/"))
		{
			ok = true;
		}
		else
		if (session != null && session.getAttribute("user") != null
				&& (session.getAttribute("user") instanceof Utilisateur)) {
			user = (Utilisateur) session.getAttribute("user");
			if (user == null
					|| user.getType() == null
					|| !(user.getType().equals(TypeUtilisateur.ETUDIANT) || 
							user.getType().equals(TypeUtilisateur.FORMATEUR) || 
							user.getType().equals(TypeUtilisateur.ADMIN))
					) {
				ok = false;
/*			} else if (user != null && user.getType() != null
					&& user.getType().equals(TypeUtilisateur.FORMATEUR)
					&& url.startsWith("addevent.jsp")) {
				ok = false;

			} else if (user.getType().equals(TypeUtilisateur.FORMATEUR)
					&& url.startsWith("agenda.jsp")) {
				ok = false;

			} else if (user.getType().equals(TypeUtilisateur.ETUDIANT)
					&& url.startsWith("candidatures.jsp")) {
				ok = false;

			} else if (user.getType().equals(TypeUtilisateur.ETUDIANT)
					&& url.startsWith("cvlettres.jsp")) {
				ok = false;

			} else if (user.getType().equals(TypeUtilisateur.FORMATEUR)
					&& url.startsWith("deleteEcheance.jsp")) {
				ok = false;

			} else if (user.getType().equals(TypeUtilisateur.FORMATEUR)
					&& url.startsWith("echeanceDetails.jsp")) {
				ok = false;

			} else if (user.getType().equals(TypeUtilisateur.FORMATEUR)
					&& url.startsWith("deleteEcheance.jsp")) {
				ok = false;

			} else if (user.getType().equals(TypeUtilisateur.FORMATEUR)
					&& url.startsWith("echeances.jsp")) {
				ok = false;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) || user
					.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("editUtilisateur.jsp")) {
				ok = false;
*/
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) || user
					.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("error.jsp")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) || user
					.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("success.jsp")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) || user
					.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("index_agenda.jsp")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT) )
					&& url.startsWith("downloadCV")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) )
					&& url.startsWith("saveLogiciel")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) )
					&& url.startsWith("Logiciel")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) )
					&& url.startsWith("Logiciels")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) )
					&& url.startsWith("LoadCategorie.servlet")) {
				ok = true;

/*
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("evtEditLinks.jsp")) {
				ok = false;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("formateur.jsp")) {
				ok = false;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("formationList1.jsp")) {
				ok = false;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) || user
					.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("listeFormations.jsp")) {
				ok = false;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("profil.jsp")) {
				ok = false;
*/
			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("task.jsp")) {
				ok = false;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("event/")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("liste/")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR) || user
					.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("eventview/")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("EditConge.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("EditEvt.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("EditJPO.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("EditPEntreprise.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("EditCandidature.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("DeleteEntreprise.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("EditContact.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("EditEcheance.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("Entreprise.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("EditProfilStep")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("Task.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT) || user
					.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("EditUtilisateur.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("DeleteTask.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("DeleteCandidature.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("DeleteCV.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("PostCV.do")) {
				ok = true;

			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("DeleteEcheance.do")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("Logiciels.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("SaveCatInfo")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.ADMIN))
					&& url.startsWith("admin/")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("DeleteLogiciel.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("DeleteCategorie.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("SaveLangue.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("DeleteLangue.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("LoadLangage.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("SaveLangage.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("DeleteLangage.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("SaveOrdreLangues.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("SaveOrdreLangages.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("SaveOrdreLogiciels.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("SaveOrdreLogiciels2.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("LoadLangue.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("LoadListeLogiciels.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("admin/login.jsp")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.ETUDIANT))
					&& url.startsWith("EntrepriseComments.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("ChercherProfils.servlet")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("charts/")) {
				ok = true;
			} else if ((user.getType().equals(TypeUtilisateur.FORMATEUR))
					&& url.startsWith("EtudiantTaches.servlet")) {
				ok = true;
				ok = true;
			} 

		} else {
			ok = false;
		}

		if (ok) {
			log.warning("OK");
			filterChain.doFilter(request, response);

		} else {
			log.warning("Redirection depuis : "
					+ url
					+ " | "
					+ ((user != null && user.getType() != null) ? user
							.getType().toString() : "user ou usertype : null"));

			if (session != null) {
				log.warning("Déconnexion de l'utilisateur : "
						+ (user != null ? user.getId() : -1));
				session.removeAttribute("user");
				session.removeAttribute("session_id");
				session.invalidate();
			}

			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		}
	}

	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.fc = filterConfig;

	}

}

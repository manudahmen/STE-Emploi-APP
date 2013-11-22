package steemploi.service;

import java.util.Calendar;
import org.apache.struts.action.ActionForm;


public class Candidature extends ActionForm {
		private int id;
		private int etudiant_id;
		private String tache;
		private Calendar date = Calendar.getInstance();
		private String date_;
		private String entreprise;
		private String fonction;
		private String reponse;
		private String suite;
		private String infos;
		private String contact;
		public int getId () {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getEtudiantId () {
			return etudiant_id;
		}

		public void setEtudiantId(int etudiant_id) {
			this.etudiant_id = etudiant_id;
		}

		public String getTache() {
			return tache;
		}

		public void setTache(String tache) {
			this.tache = tache;
		}

		public Calendar getDate() {
			return date;
		}

		public void setDate(Calendar date) {
			this.date = date;
		}

		public String getDate_() {
			return date_;
		}

		public void setDate_(String date_) {
			this.date_ = date_;
		}

		public String getEntreprise() {
			return entreprise;
		}

		public void setEntreprise(String entreprise) {
			this.entreprise = entreprise;
		}

		public String getFonction() {
			return fonction;
		}

		public void setFonction(String fonction) {
			this.fonction = fonction;
		}

		public String getReponse() {
			return reponse;
		}

		public void setReponse(String reponse) {
			this.reponse = reponse;
		}

		public String getSuite() {
			return suite;
		}

		public void setSuite(String suite) {
			this.suite = suite;
		}

		public String getInfos() {
			return infos;
		}

		public void setInfos(String infos) {
			this.infos = infos;
		}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

	}

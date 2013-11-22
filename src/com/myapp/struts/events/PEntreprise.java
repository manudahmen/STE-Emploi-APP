package com.myapp.struts.events;

import java.util.Calendar;

import org.apache.struts.action.ActionForm;

public class PEntreprise extends ActionForm {
		private int id;
		private String nom;
		private String telephone;
		private String url;
		private String email;
		private String description;
		private String infoscomplementaires;
		private Calendar dateDebut = Calendar.getInstance();
		private String date_Debut = "";
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public String getInfoscomplementaires() {
			return infoscomplementaires;
		}

		public void setInfoscomplementaires(String infoscomplementaires) {
			this.infoscomplementaires = infoscomplementaires;
		}

		public Calendar getDateDebut() {
			return dateDebut;
		}

		public void setDateDebut(Calendar dateDebut) {
			this.dateDebut = dateDebut;
		}

		public String getDate_Debut() {
			return date_Debut;
		}

		public void setDate_Debut(String date) {
			date_Debut = date;
		}
	}

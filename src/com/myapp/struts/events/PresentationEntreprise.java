package com.myapp.struts.events;

import java.util.Calendar;

import org.apache.struts.action.ActionForm;

public class PresentationEntreprise extends ActionForm {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		private String nom;
		private Calendar date;
		private String telephone;
		private String url;
		private String email;
		private String description;
		private String infoscomplementaires;
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

		public Calendar getDate() {
			return date;
		}

		public void setDate(Calendar date) {
			this.date = date;
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

	}

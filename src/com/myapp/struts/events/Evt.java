package com.myapp.struts.events;

import java.util.Calendar;

import org.apache.struts.action.ActionForm;

public class Evt extends ActionForm {
		private int id;
		private String name;
		private String location;
		private Calendar dateDebut = Calendar.getInstance();
		private Calendar dateFin = Calendar.getInstance();
		private String date_Debut = "";
		private String date_Fin = "";
		private String description;
		private String link;
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Calendar getDateDebut() {
			return dateDebut;
		}

		public void setDateDebut(Calendar dateDebut) {
			this.dateDebut = dateDebut;
		}

		public Calendar getDateFin() {
			return dateFin;
		}

		public void setDateFin(Calendar dateFin) {
			this.dateFin = dateFin;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getDate_Debut() {
			return date_Debut;
		}

		public void setDate_Debut(String date) {
			date_Debut = date;
		}

		public String getDate_Fin() {
			return date_Fin;
		}

		public void setDate_Fin(String date) {
			date_Fin = date;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

	}

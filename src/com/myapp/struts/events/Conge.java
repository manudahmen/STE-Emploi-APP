package com.myapp.struts.events;

import java.util.Calendar;

import org.apache.struts.action.ActionForm;

public class Conge extends ActionForm {
		private int id;
		private String name;
		private String location;
		private Calendar dateDebut = Calendar.getInstance();
		private Calendar dateFin = Calendar.getInstance();
		private String date_Debut = "";
		private String date_Fin = "";
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNom() {
			return name;
		}

		public void setNom(String name) {
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

	}

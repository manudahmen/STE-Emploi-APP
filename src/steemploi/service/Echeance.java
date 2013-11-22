/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package steemploi.service;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;
import java.util.Calendar;
/**
 *
 * @author manuel
 */

public class Echeance extends ActionForm implements Event {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;
		private int id;
		private String code;
		private String description;
		private String title;
		private ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
		private ArrayList<SessionsFormations> formations = new ArrayList<SessionsFormations>();
		private Calendar date = Calendar.getInstance();
		private int date_j;
		private int date_m;
		private int date_a;
		private String _etudforma;
		public Echeance() {
			setDate(Calendar.getInstance());
		}

		public int getDate_a() {
			return date_a;
		}

		public void setDate_a(int date_a) {
			this.date_a = date_a;
		}

		public int getDate_j() {
			return date_j;
		}

		public void setDate_j(int date_j) {
			this.date_j = date_j;
		}

		public int getDate_m() {
			return date_m;
		}

		public void setDate_m(int date_m) {
			this.date_m = date_m;
		}


		public Calendar getDate() {
			date.set(date_a, date_m, date_j);
			return date;
		}

		public void setDate(Calendar date) {
			setDate_j(date.get(Calendar.DATE));
			setDate_m(date.get(Calendar.MONTH));
			setDate_a(date.get(Calendar.YEAR));
			this.date = date;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public ArrayList<Etudiant> getEtudiants() {
			return etudiants;
		}

		public void setEtudiants(ArrayList<Etudiant> etudiants) {
			this.etudiants = etudiants;
		}

		public ArrayList<SessionsFormations> getFormations() {
			return formations;
		}

		public void setFormations(ArrayList<SessionsFormations> formations) {
			this.formations = formations;
		}

		public String get_etudforma() {
			return _etudforma;
		}

		public void set_etudforma(String _etudforma) {
			this._etudforma = _etudforma;
		}

		@Override
		public boolean equals(Object arg0) {
			// TODO Auto-generated method stub

			if (arg0 == null)
				return false;

			if (!(arg0 instanceof Echeance))
				return false;

			Echeance other = (Echeance) arg0;

			if (other.getId() != getId())
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return super.hashCode();
		}


	}

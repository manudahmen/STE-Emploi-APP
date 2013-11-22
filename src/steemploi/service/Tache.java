/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package steemploi.service;

import org.apache.struts.action.ActionForm;
import java.util.Calendar;
/**
 *
 * @author manuel
 */

public class Tache extends ActionForm implements Event {

		private int id;
		private int code;
		private java.util.Calendar dateCompleted = Calendar.getInstance();
		private String dateCompleted_ = "";
		private int percent = 100;
		private Calendar date;
		private String title;
		private String description;
		private int entreprise_id = 0;
		private int contact_id = 0;
		private int etudiant_id = 0;

		private Entreprise entreprise;
		private Contact contact_entreprise;
		private Etudiant etudiant;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getEtudiant_id() {
			return etudiant_id;
		}

		public void setEtudiant_id(int etudiant_id) {
			this.etudiant_id = etudiant_id;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public Calendar getDateCompleted() {
			return dateCompleted;
		}

		public void setDateCompleted(Calendar dateCompleted) {
			this.dateCompleted = dateCompleted;
		}

		public String getDateCompleted_() {
			return dateCompleted_;
		}

		public void setDateCompleted_(String dateCompleted_) {
			this.dateCompleted_ = dateCompleted_;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getPercent() {
			return percent;
		}

		public void setPercent(int percent) {
			this.percent = percent;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String Title) {
			this.title = Title;
		}

		public int getEntreprise_id() {
			return entreprise_id;
		}

		public void setEntreprise_id(int entreprise_id) {
			this.entreprise_id = entreprise_id;
		}

		public int getContact_id() {
			return contact_id;
		}

		public void setContact_id(int contact_id) {
			this.contact_id = contact_id;
		}

		public Calendar getDate() {
			return date;
		}

		public void setDate(Calendar date) {
			this.date = date;
		}

	}

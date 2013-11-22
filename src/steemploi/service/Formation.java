/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package steemploi.service;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author manuel
 */

public class Formation  extends ActionForm implements Comparable<Object>, Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 6980985017630236375L;
		private int id;
		private String nom;
		private int annee = Calendar.getInstance().get(Calendar.YEAR);

		public int getAnnee() {
			return annee;
		}

		public void setAnnee(int annee) {
			this.annee = annee;
		}

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

		public int compareTo(Object o) {
			if (o instanceof Formation && ((Formation)o).getId() == getId())
				return 0;
			else
				return -1;

		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
					return false;
				}

			if (getClass() != obj.getClass()) {
					return false;
				}

			final Formation other = (Formation) obj;

			if (id != other.getId())
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			int hash = 3;
			hash = 97 * hash + this.id;
			return hash;
		}

	}

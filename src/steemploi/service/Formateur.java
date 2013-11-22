/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package steemploi.service;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author manuel
 */

public class Formateur extends ActionForm implements Serializable
{
		/**
	 * 
	 */
	private static final long serialVersionUID = -1652464831656595785L;
		private int id;
		private int user_id;
		public int getUser_id() {
			return user_id;
		}

		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}



		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int compareTo(Object o) {
			if (o instanceof Etudiant && ((Etudiant)o).getId() == getId())
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

			final Formateur other = (Formateur) obj;

			if (id != other.getId())
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 31 * hash + this.id;
			return hash;
		}


	}

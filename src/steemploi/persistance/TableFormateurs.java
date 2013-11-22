/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package steemploi.persistance;

import steemploi.service.Formateur;

/**
 *
 * @author manuel
 */

public class TableFormateurs extends UpdateInsertIntoTable {

		public TableFormateurs() {
			super("formateurs");
		}

		public Formateur findByUserId(int id) {
			Formateur f = new Formateur();
			return f;
		}

		public Formateur findById(int id) {
			Formateur f = new Formateur();
			return f;
		}
	}

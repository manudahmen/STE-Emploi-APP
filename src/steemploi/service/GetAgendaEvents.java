/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package steemploi.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import steemploi.persistance.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import steemploi.service.Etudiant;
import steemploi.service.TypeUtilisateur;

/**
 *
 * @author manuel
 */

public class GetAgendaEvents {

		private Calendar dateMin;
		private Calendar dateMax;
		private Map < String, ArrayList < Echeance >> list;
		private Map<String, Integer> listCount;
		private int user_id;
		private int f_role_formateur_formateur_id;
		private int f_role_formateur_etudiant_id;

		public GetAgendaEvents(Calendar dateMin, Calendar dateMax) {
			this.dateMin = dateMin;
			this.dateMax = dateMax;
			list = new HashMap < String, ArrayList < Echeance >> ();
			listCount = new HashMap<String, Integer>();
		}

		public Map < String, ArrayList < Echeance >> getEtudiantRole(int etudiant_id, int formation_id) throws SQLException {
				Map < String, ArrayList < Echeance >> map = new TableEcheance().findByDate(dateMin, dateMax, TableEcheance.ViewEcheances.Formation, formation_id, etudiant_id);
				//map.putAll(new TableEcheance().findByDate(dateMin, dateMax, TableEcheance.ViewEcheances.Etudiant, formation_id, etudiant_id));
				return map;

			}

		public Calendar getDateMax() {
			return dateMax;
		}

		public void setDateMax(Calendar dateMax) {
			this.dateMax = dateMax;
		}

		public Calendar getDateMin() {
			return dateMin;
		}

		public void setDateMin(Calendar dateMin) {
			this.dateMin = dateMin;
		}

		public Map < String, ArrayList < Echeance >> getList() {
			return list;
		}

		public void setList(Map < String, ArrayList < Echeance >> list) {
			this.list = list;
		}

		public Map<String, Integer> getListCount() {
			return listCount;
		}

		public void setListCount(Map<String, Integer> listCount) {
			this.listCount = listCount;
		}
	}

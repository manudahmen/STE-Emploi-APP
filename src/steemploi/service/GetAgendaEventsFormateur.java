/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package steemploi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import steemploi.persistance.TableEcheance;
import steemploi.persistance.TableEtudiants;

/**
 *
 * @author manuel
 */

public class GetAgendaEventsFormateur {

		private Calendar dateMin;
		private Calendar dateMax;
		private Map < String, ArrayList < Echeance >> list;
		private Map<String, Integer> listCount;
		public GetAgendaEventsFormateur(Calendar dateMin, Calendar dateMax) {
			this.dateMin = dateMin;
			this.dateMax = dateMax;
			list = new HashMap < String, ArrayList < Echeance >> ();
			listCount = new HashMap<String, Integer>();
		}

		public Map < String, ArrayList < Echeance >> find(int type, int formation_id, int etudiant_id) throws SQLException {
				if (type == 0) // Formation
					{
						return new TableEcheance().findByDate(dateMin, dateMax, TableEcheance.ViewEcheances.Formation, formation_id, etudiant_id);
					} else if (type == 1) // Etudiant
					{
						return new TableEcheance().findByDate(dateMin, dateMax, TableEcheance.ViewEcheances.Etudiant, formation_id, etudiant_id);

					} else if (type == 2) // Etudiants
					{
						return new TableEcheance().findByDate(dateMin, dateMax, TableEcheance.ViewEcheances.TousEtudiants, formation_id, etudiant_id);

					}

				return new HashMap < String, ArrayList < Echeance >> ();
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

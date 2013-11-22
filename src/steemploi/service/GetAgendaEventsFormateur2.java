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

import steemploi.persistance.*;
import com.myapp.struts.events.*;

/**
 *
 * @author manuel
 */

public class GetAgendaEventsFormateur2 {

		private Calendar dateMin;
		private Calendar dateMax;

		List<JPO> jpo = null;
		List<Evt> evts = null;
		List<Conge> conges = null;
		List<PEntreprise> pentreprise = null;


		public GetAgendaEventsFormateur2(Calendar dateMin, Calendar dateMax) throws SQLException {
				this.dateMin = (Calendar) dateMin.clone();
				this.dateMax = (Calendar) dateMax.clone();
				findJPO();
				findPEntreprise();
				findConge();
				findEvt();
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

		public List<JPO> findJPO() throws SQLException {
				jpo = new TableJPO().findByDate(dateMin, dateMax);
				return jpo;
			}

		public List<PEntreprise> findPEntreprise() throws SQLException {
				pentreprise = new TablePEntreprise().findByDate(dateMin, dateMax);
				return pentreprise;
			}

		public List<Conge> findConge() throws SQLException {
				conges = new TableConge().findByDate(dateMin, dateMax);
				return conges;
			}

		public List<Evt> findEvt() throws SQLException {
				evts = new TableEvt().findByDate(dateMin, dateMax);
				return evts;
			}

		public List<JPO> getJPO(Calendar date) throws SQLException {
				jpo = new TableJPO().findByDate(dateMin, dateMax);
				return jpo;
			}

		public List<PEntreprise> getPEntreprise(Calendar date) throws SQLException {
				pentreprise = new TablePEntreprise().findByDate(dateMin, dateMax);
				return pentreprise;
			}

		public List<Conge> getConge(Calendar date) throws SQLException {
				conges = new TableConge().findByDate(dateMin, dateMax);
				return conges;
			}

		public List<Evt> getEvt(Calendar date) throws SQLException {
				evts = new TableEvt().findByDate(dateMin, dateMax);
				return evts;
			}

		public ArrayList<Object> getByDay(Calendar date) throws SQLException {
				ArrayList<Object> os = new ArrayList<Object>();

for (JPO j : jpo) {
						if (j.getDateDebut().get(Calendar.DATE) == date.get(Calendar.DATE) &&
						    j.getDateDebut().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
						    j.getDateDebut().get(Calendar.YEAR) == date.get(Calendar.YEAR)) {
								os.add(j);
							}
					}

for (PEntreprise j : pentreprise) {
						if (j.getDateDebut().get(Calendar.DATE) == date.get(Calendar.DATE) &&
						    j.getDateDebut().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
						    j.getDateDebut().get(Calendar.YEAR) == date.get(Calendar.YEAR)) {
								os.add(j);
							}
					}

for (Evt j : evts) {
						if (j.getDateDebut().get(Calendar.DATE) == date.get(Calendar.DATE) &&
						    j.getDateDebut().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
						    j.getDateDebut().get(Calendar.YEAR) == date.get(Calendar.YEAR)) {
								os.add(j);
							}
					}

for (Conge j : conges) {
						if (j.getDateDebut().compareTo(date) < 0 && j.getDateFin().compareTo(date) > 0) {
								os.add(j);
							}
					}

				return os;
			}

		public Calendar getDateMax() {
			return dateMax;
		}

		public Calendar getDateMin() {
			return dateMin;
		}

	}

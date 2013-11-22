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

public class GetAgendaEventsEtudiant {

	private Calendar dateMin;
	private Calendar dateMax;

	Map<String, ArrayList<Echeance>> echeances =new HashMap<String, ArrayList<Echeance>>();
	
	
	public GetAgendaEventsEtudiant(Calendar dateMin, Calendar dateMax, Etudiant etudiant) throws SQLException {
		this.dateMin = dateMin;
		this.dateMax = dateMax;
		findEcheances(etudiant.getId(), etudiant.getSf().getId());
	}

	private void findEcheances(int et_id, int sf_id) throws SQLException {
		GetAgendaEventsFormateur2 get;
			get = new GetAgendaEventsFormateur2(dateMin, dateMax);
			echeances = get.find(1, sf_id, et_id);
	}

	public Map<String, ArrayList<Echeance>> getEcheances() {
		return echeances;
	}

	public void setEcheances(Map<String, ArrayList<Echeance>> echeances) {
		this.echeances = echeances;
	}
	
}

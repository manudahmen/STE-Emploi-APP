package steemploi.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class SessionsFormations extends ActionForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6231207671138751599L;
	private String name;
	private int formation_id;
	private Calendar dateStart;
	private Calendar dateEnd;

	private String date_Start="";
	private String date_End="";
	private Formation formation = new Formation();
	private int id;
	private List<Etudiant> etudiants = new ArrayList<Etudiant>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getDateStart() {
		return dateStart;
	}

	public void setDateStart(Calendar dateStart) {
		this.dateStart = dateStart;
	}

	public Calendar getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Calendar dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	public void setId(int int1) {
		this.id = int1;

	}

	public int getId() {
		return id;
	}

	public List<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		SessionsFormations other = (SessionsFormations) obj;

		if (id != other.id)
			return false;

		return true;
	}

	public int getFormation_id() {
		return formation_id;
	}

	public void setFormation_id(int formation_id) {
		this.formation_id = formation_id;
	}

	public String getDate_Start() {
		return date_Start;
	}

	public void setDate_Start(String date_Start) {
		this.date_Start = date_Start;
	}

	public String getDate_End() {
		return date_End;
	}

	public void setDate_End(String date_End) {
		this.date_End = date_End;
	}

}

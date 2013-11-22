package steemploi.service;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class ResultatProfils extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2187767386507868147L;
	private int resultsCount=0;
	private ArrayList<Result> results = new ArrayList<Result>();
	
	
	public int getResultsCount() {
		return resultsCount;
	}



	public void setResultsCount(int resultsCount) {
		this.resultsCount = resultsCount;
	}



	public ArrayList<Result> getResults() {
		return results;
	}



	public void setResults(ArrayList<Result> results) {
		this.results = results;
	}



	public class Result
	{
		private int etudiantId=0;
		
		private Profil profil = null;
		private Etudiant etudiant = null;
		
		
		
		public int getEtudiantId() {
			return etudiantId;
		}
		public void setEtudiantId(int etudiantId) {
			this.etudiantId = etudiantId;
		}
		public void setProfil(Profil profil) {
			this.profil = profil;
		}
		public void setEtudiant(Etudiant etudiant) {
			this.etudiant = etudiant;
		}
		public Profil getProfil()
		{
			return null;
		}
		public Etudiant getEtudiant()
		{
			return null;
		}
	}
}

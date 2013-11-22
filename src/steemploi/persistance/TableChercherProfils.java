package steemploi.persistance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import steemploi.service.Etudiant;
import steemploi.service.Profil;
import steemploi.service.ResultatProfils;
import steemploi.service.Profil.Langage;
import steemploi.service.ResultatProfils.Result;

public class TableChercherProfils extends UpdateInsertIntoTable {

	public TableChercherProfils() {
		super("");
	}

	public ResultatProfils chercherProfils(String langages) throws SQLException {
		ResultatProfils profils = new ResultatProfils();
		String[] langagesArray = langages.split(" ");
		for(String str : langagesArray)
		{
			chercherChaine(str, profils);
		}
		return null;

	}

	public ResultatProfils chercherChaine(String result, ResultatProfils profils) 
		throws SQLException {
		TableEtudiants te = new TableEtudiants();
		ArrayList<Etudiant> etudiants = te.findAll();
		for (Etudiant e : etudiants) {
			Profil p = new Profil();
			p.loadLangages();
			p.loadCategories();
			HashMap<Integer, Integer> logiciels = p
					.getNiveauConnaissanceLogiciel();
			Set<Integer> logicielsKeys = logiciels.keySet();
			for (int i = 0; i < logicielsKeys.size(); i++) {
				int id = logiciels.get(i);
				Profil.Logiciels pl = p.getLogiciel(id);
				String name = pl.getNomLogiciel();
				if (result.contains(name)) {
					if (p.getNiveauConnaissanceLogiciel(id) > 0) {
						Result r = profils.new Result();
						r.setEtudiant(e);
						r.setEtudiantId(e.getId());
						r.setProfil(p);
						profils.getResults().add(r);
					}
				}

			}
			HashMap<Integer, Integer> langages = p
					.getNiveauxConnaissancesLangage();
			Set<Integer> langagesKeys = langages.keySet();
			ArrayList<Profil.Langage> langagesList = p.getLangages();
			HashMap<Integer, Profil.Langage> langageMap = new HashMap<Integer, Langage>();
			for (Profil.Langage pl : langagesList) {
				int id = pl.getId();
				langageMap.put(id, pl);
			}

			for (int i = 0; i < langagesKeys.size(); i++) {
				int id = langages.get(i);
				Profil.Langage pl = langageMap.get(id);
				String name = pl.getNom();
				if (result.contains(name)) {
					if (p.getNiveauconnaissanceLangage(id) > 0) {
						Result r = profils.new Result();
						r.setEtudiant(e);
						r.setEtudiantId(e.getId());
						r.setProfil(p);
						profils.getResults().add(r);
					}
				}
			}
		}
		return profils;
	}
}

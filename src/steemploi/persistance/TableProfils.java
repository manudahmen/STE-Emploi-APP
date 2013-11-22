package steemploi.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import steemploi.service.Profil;

public class TableProfils extends UpdateInsertIntoTable {

	private int etudiantId;

	public TableProfils() {
		super("");
	}

	public void saveCoordonnees(Profil profil) {
		setTableName("profil_coordonnees");
		setFieldsNames(new String[] { "nom", "prenom", "rue", "boite",
				"codepostal", "", "mobile", "etudiant_id" });

	}

	public int saveDiplome(Profil profil) throws SQLException {
		setTableName("profil_diplomes");
		setFieldsNames(new String[] { "intitule", "orientation", "ecole",
				"niveau", "commentaires", "obtenu", "date_debut", "date_fin",
				"etudiant_id" });
		if (profil.getIdDiplome() == 0)
			insertInto(new Object[] { profil.getIntitule(),
					profil.getOrientation(), profil.getEtablissement(),
					profil.getNiveau(), profil.getCommentaires(),
					profil.getObtenu() == true ? 1 : 0,
					setDate(profil.getDateDebutFormation()),
					setDate(profil.getDateFinFormation()), getEtudiantId() });
		else
			update(new Object[] { profil.getIntitule(),
					profil.getOrientation(), profil.getEtablissement(),
					profil.getNiveau(), profil.getCommentaires(),
					profil.getObtenu() == true ? 1 : 0,
					setDate(profil.getDateDebutFormation()),
					setDate(profil.getDateFinFormation()), getEtudiantId() },
					profil.getIdDiplome());
		ExecuteUpdate();
		return selectLastInserted();
	}

	public void deleteDiplome(Profil profil, int id) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_diplomes where id=?");
		pstmt.setInt(1, id);
		pstmt.execute();
	}

	public int saveExperience(Profil profil) throws SQLException {
		setTableName("profil_experience_professionnelle");
		setFieldsNames(new String[] { "employeur", "date_debut", "date_fin",
				"intitule_poste", "type_contrat", "emploi_actuel",
				"motif_fin_de_contrat", "etudiant_id" });
		if (profil.getIdEmploi() == 0)
			insertInto(new Object[] { profil.getEmployeur(),
					setDate(profil.getDateDebutEmploi()),
					setDate(profil.getDateFinEmploi()),
					profil.getIntitulePoste(), profil.getTypeContrat(),
					profil.getFinContrat() ? 0 : 1,
					profil.getMotifFinContrat(), etudiantId });
		else
			update(new Object[] { profil.getEmployeur(),
					setDate(profil.getDateDebutEmploi()),
					setDate(profil.getDateFinEmploi()),
					profil.getIntitulePoste(), profil.getTypeContrat(),
					profil.getFinContrat() ? 0 : 1,
					profil.getMotifFinContrat(), etudiantId }, profil
					.getIdEmploi());
		ExecuteUpdate();
		return selectLastInserted();
	}

	public void deleteExperience(Profil profil, int id) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_experience_professionnelle where id=? and etudiant_id=?");
		pstmt.setInt(1, id);
		pstmt.setInt(2, etudiantId);
		pstmt.execute();
	}

	public void savePermis(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_extra where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		pstmt.execute();
		setTableName("profil_extra");
		setFieldsNames(new String[] { "permisb", "licence", "voiture",
				"etudiant_id" });
		insertInto(new Object[] { profil.getPermisDeConduire() ? 1 : 0,
				profil.isLicence() ? 1 : 0, profil.isVoiture() ? 1 : 0,
				etudiantId });
		ExecuteUpdate();
	}

	public void saveLogiciels(Profil profil) throws SQLException {
		profil.loadCategories();
		setTableName("profil_logiciels");
		setFieldsNames(new String[] { "id_logiciel", "niveau", "etudiant_id" });
		for (Profil.Logiciels logiciel : profil.getLogiciels()) {
			int niveau = profil.getNiveauConnaissanceLogiciel(logiciel
					.getIdLogiciel());
			PreparedStatement pstmt = conn
					.prepareStatement("delete from profil_logiciels where etudiant_id=? and id_logiciel=?");
			pstmt.setInt(1, etudiantId);
			pstmt.setInt(2, logiciel.getIdLogiciel());
			pstmt.execute();
			insertInto(new Object[] { logiciel.getIdLogiciel(), niveau,
					etudiantId });
			ExecuteUpdate();
		}
	}

	public void saveLogiciels(Profil profil, int idLog, int level)
			throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_logiciels where etudiant_id=? and id_logiciel=?");
		pstmt.setInt(1, etudiantId);
		pstmt.setInt(2, idLog);
		pstmt.execute();
		setTableName("profil_logiciels");
		setFieldsNames(new String[] { "id_logiciel", "niveau", "etudiant_id" });
		insertInto(new Object[] { idLog, level, etudiantId });
		ExecuteUpdate();
	}

	public void saveLogiciels(Profil profil, int idLog, int level,
			ArrayList<String> versionsSaved) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_logiciels where etudiant_id=? and id_logiciel=?");
		pstmt.setInt(1, etudiantId);
		pstmt.setInt(2, idLog);
		pstmt.executeUpdate();
		setTableName("profil_logiciels");
		setFieldsNames(new String[] { "id_logiciel", "niveau", "etudiant_id",
				"versions" });
		String versions = "";
		int i = 0;
		for (String s : versionsSaved) {

			if (i > 0)
				versions += ";";
			versions += s;
			i++;
		}
		insertInto(new Object[] { idLog, level, etudiantId, versions });
		ExecuteUpdate();

	}

	public void loadNiveauxConnaissancesLogiciels(Profil profil)
			throws SQLException {
		profil.setNiveauConnaissanceLogiciel(new HashMap<Integer, Integer>());
		PreparedStatement pstmt = conn
				.prepareStatement("select * from profil_logiciels where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int id_logiciel = rs.getInt("id_logiciel");
			int niveau = rs.getInt("niveau");
			ArrayList<String> versions = new ArrayList<String>();
			String str = rs.getString("versions");
			for(String s : str.split(";"))
				versions.add(s);
			profil.setNiveauConnaissanceLogiciel(id_logiciel, niveau, versions);
		}
	}

	public void saveLangage(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_langages where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		pstmt.execute();
		setTableName("profil_langages");
		setFieldsNames(new String[] { "langage_id", "niveau", "etudiant_id" });
		for (Profil.Langage l : profil.getLangages()) {
			insertInto(new Object[] { l.getId(),
					profil.getNiveauconnaissanceLangage(l.getId()), etudiantId });
			ExecuteUpdate();
		}
	}

	// Penser aux niveaux ELEO
	public void saveLangues(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_langues where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		pstmt.execute();
		setTableName("profil_langues");
		setFieldsNames(new String[] { "langue_id", "lu", "ecrit", "parle",
				"etudiant_id" });
		for (Profil.Langue langue : profil.getLangues()) {
			insertInto(new Object[] {
					langue.getNomCourt(),
					profil.getNiveauConnaissanceLangue(langue.getNomCourt(),
							'l'),
					profil.getNiveauConnaissanceLangue(langue.getNomCourt(),
							'e'),
					profil.getNiveauConnaissanceLangue(langue.getNomCourt(),
							'p'), etudiantId });
			ExecuteUpdate();
		}
	}

	public void saveAtout(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_atouts where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		pstmt.execute();
		setTableName("profil_atouts");
		setFieldsNames(new String[] { "etudiant_id", "atouts" });
		insertInto(new Object[] { etudiantId, profil.getAtouts() });
		ExecuteUpdate();
	}

	public void loadAtouts(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("select * from profil_atouts where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			profil.setAtouts(rs.getString("atouts"));
		}
	}

	public void saveLoisirs(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_loisirs where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		pstmt.execute();
		setTableName("profil_loisirs");
		setFieldsNames(new String[] { "etudiant_id", "loisirs" });
		insertInto(new Object[] { etudiantId, profil.getLoisirs() });
		ExecuteUpdate();
	}

	public void loadLoisirs(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("select * from profil_loisirs where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			profil.setLoisirs(rs.getString("loisirs"));
		}
	}

	public void loadDiplomes(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("select * from profil_diplomes where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		ResultSet rs = pstmt.executeQuery();
		profil.setDiplomes(new ArrayList<Profil.ProfilDiplome>());
		while (rs.next()) {
			profil.setIntitule(rs.getString("intitule"));
			profil.setOrientation(rs.getString("orientation"));
			profil.setEtablissement(rs.getString("ecole"));
			profil.setNiveau(rs.getString("niveau"));
			profil.setCommentaires(rs.getString("commentaires"));
			profil.setObtenu(rs.getBoolean("obtenu"));
			profil.setDateDebutEmploi(getDate(rs.getDate("date_debut")));
			profil.setDateFinEmploi(getDate(rs.getDate("date_fin")));
			profil.setIdDiplome(rs.getInt("id"));
			profil.addDiplome();
		}
	}

	public void setEtudiantId(int id) {
		etudiantId = id;

	}

	private int getEtudiantId() {

		return etudiantId;
	}

	public void loadEmplois(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("select * from profil_experience_professionnelle where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		ResultSet rs = pstmt.executeQuery();
		profil.setEmployeurs(new ArrayList<Profil.ProfilEmployeur>());
		while (rs.next()) {
			profil.setEmployeur(rs.getString("employeur"));
			profil.setDateDebutEmploi(getDate(rs.getDate("date_debut")));
			profil.setDateFinEmploi(getDate(rs.getDate("date_fin")));
			profil.setIntitulePoste(rs.getString("intitule_poste"));
			profil.setTypeContrat(rs.getString("type_contrat"));
			profil.setFinContrat((rs.getInt("emploi_actuel") == 1) ? false
					: true);
			profil.setMotifFinContrat(rs.getString("motif_fin_de_contrat"));
			profil.setIdEmploi(rs.getInt("id"));
			profil.addEmployeur();
		}

	}

	public void saveLangages(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from profil_langages where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		pstmt.executeUpdate();
		setTableName("profil_langages");
		setFieldsNames(new String[] { "langage_id", "niveau", "versions", "etudiant_id" });
		for (Profil.Langage langage : profil.getLangages()) {
			int level = profil.getNiveauconnaissanceLangage(langage.getId());
			ArrayList<String> versionsAL = profil.getNiveauconnaissanceLangageVersions(langage.getId());
			String versions = "";
			int i=0;
			for(String s : versionsAL)
			{
				if(i>0) 
					versions += ";";
				versions+=s;
				i++;
			}
			insertInto(new Object[] { langage.getId(), level, versions, etudiantId });
			ExecuteUpdate();
		}
	}

	public void loadNiveauxConnaissancesLangages(Profil profil)
			throws SQLException {
		profil.setNiveauconnaissanceLangage(new HashMap<Integer, Integer>());
		PreparedStatement pstmt = conn
				.prepareStatement("select * from profil_langages where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			int langage_id = rs.getInt("langage_id");
			int level = rs.getInt("niveau");
			ArrayList<String> versions = new ArrayList<String>();
			String str = rs.getString("versions");
			for(String s : str.split(";"))
				versions.add(s);
			profil.setNiveauconnaissanceLangage(langage_id, level, versions);
		}

	}

	public void loadPermis(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("select * from profil_extra where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			profil
					.setPermisDeConduire(rs.getInt("permisb") == 1 ? true
							: false);
			profil.setLicence(rs.getInt("licence") == 1 ? true : false);
			profil.setVoiture(rs.getInt("voiture") == 1 ? true : false);
		}
	}

	public void loadNiveauxConnaissancesLangues(Profil profil)
			throws SQLException {
		profil.setNiveauconnaissanceLangage(new HashMap<Integer, Integer>());
		PreparedStatement pstmt = conn
				.prepareStatement("select * from profil_langues where etudiant_id=?");
		pstmt.setInt(1, etudiantId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			String langage_id = rs.getString("langue_id");
			int levelL = rs.getInt("lu");
			int levelP = rs.getInt("parle");
			int levelE = rs.getInt("ecrit");
			profil.setNiveauConnaissanceLangue(langage_id, 'l', levelL);
			profil.setNiveauConnaissanceLangue(langage_id, 'p', levelP);
			profil.setNiveauConnaissanceLangue(langage_id, 'e', levelE);
		}
	}

}

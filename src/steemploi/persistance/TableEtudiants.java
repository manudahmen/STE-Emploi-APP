/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;

import steemploi.service.Etudiant;
import steemploi.service.Formation;
import steemploi.service.Utilisateur;
import steemploi.service.SessionsFormations;

/**
 * 
 * @author manuel
 */

public class TableEtudiants extends UpdateInsertIntoTable {
	public TableEtudiants() {
		super("etudiants");
	}

	public Etudiant findByName(String username, String nom, String prenom,
			String email) {
		Etudiant e = new Etudiant();
		Utilisateur u = new Utilisateur();
		e.setUtilisateur(u);
		return e;
	}

	public List<Etudiant> findByFormation(int formation_id) {
		ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
		return etudiants;
	}

	public Etudiant findById(int etudiant_id) throws SQLException {
		Etudiant e = new Etudiant();
		Utilisateur u = new Utilisateur();
		e.setUtilisateur(u);
		String query = "select e.id as id, u.id as user_id, u.nom, u.prenom, e.sessions_formations_id as sfid, e.email, e.user_id from etudiants as e left outer join utilisateurs as u on u.id=e.user_id where e.id=?";
		ResultSet rs;
		try {
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(query);
			pstmt.setInt(1, etudiant_id);
			rs = pstmt.executeQuery();
		} catch (Exception ex) {
			Logger logger = Logger
					.getLogger("steemploi.persistance.TableEtudiants");
			logger.log(Level.SEVERE, this.getClass().getName() + " error", ex);
			return e;
		}

		if (rs.next()) {
			copyProp(rs, e);
			e.setUser_id(rs.getInt("user_id"));
			e.setNom(rs.getString("nom"));
			e.setPrenom(rs.getString("prenom"));
			e.setEmail(rs.getString("email"));
			u.setNom(rs.getString("nom"));
			u.setPrenom(rs.getString("prenom"));
			u.setEmail(rs.getString("email"));
		}

		Logger logger = Logger
				.getLogger("steemploi.persistance.TableEtudiants");
		logger.log(Level.INFO, this.getClass().getName() + " findById OK");
		return e;
	}

	public Etudiant findByUserId(int user_id) throws SQLException {
		Etudiant e = new Etudiant();
		String query = "select e.id as id, u.id as user_id, u.nom as nom, u.prenom as prenom, u.email as email, sf.id as sfid, sf.name as sfnom, sf.dateStart as sfdatestart, sf.dateend as sfdateend, f.id as fid, f.nom as fnom from etudiants as e "
				+ "inner join sessions_formations as sf on e.sessions_formations_id=sf.id inner join formations as f on sf.formation_id=f.id left outer join utilisateurs as u on u.id=e.user_id where u.id=?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(query);

			pstmt.setInt(1, user_id);
			rs = pstmt.executeQuery();
		} catch (Exception ex) {
			Logger logger = Logger
					.getLogger("steemploi.persistance.TableEtudiants");
			logger.log(Level.SEVERE, this.getClass().getName()
					+ " : findById()");
			return e;
		}
		if (rs.next()) {
			copyProp2(rs, e);
		}

		e.setUser_id(user_id);
		return e;
	}

	public void copyProp(ResultSet rs, Etudiant e) throws SQLException {
		e.setId(rs.getInt("id"));
		e.setUser_id(rs.getInt("user_id"));
		int sfid = rs.getInt("sfid");
		e.setFormation_id(sfid);
		SessionsFormations sf = new SessionsFormations();
		sf.setId(sfid);
		e.setSf(sf);
	}

	private void copyProp2(ResultSet rs, Etudiant e) throws SQLException {
		e.setId(rs.getInt("id"));
		e.setUser_id(rs.getInt("user_id"));
		e.setNom(rs.getString("prenom"));
		e.setPrenom(rs.getString("nom"));
		int sfid = rs.getInt("sfid");
		e.setFormation_id(sfid);
		SessionsFormations sf = new SessionsFormations();
		sf.setId(sfid);
		sf.setName(rs.getString("sfnom"));
		sf.setDateStart(getDate(rs.getDate("sfdatestart")));
		sf.setDateEnd(getDate(rs.getDate("sfdateend")));
		Formation f = new Formation();
		f.setId(rs.getInt("fid"));
		f.setNom(rs.getString("fnom"));
		sf.setFormation(f);
		e.setSf(sf);
	}

	public int save(Etudiant et) throws SQLException {
		setFieldsNames(new String[] { "nom", "prenom", "email", "user_id",
				"session_formation_id" });
		if (et.getId() == 0) {
			insertInto(new Object[] { et.getNom(), et.getPrenom(),
					et.getEmail(), et.getUser_id(), et.getFormation_id() });
			return selectLastInserted();
		} else {
			update(new Object[] { et.getNom(), et.getPrenom(), et.getEmail(),
					et.getUser_id(), et.getFormation_id() }, et.getId());
			return et.getId();
		}

	}

	public ArrayList<Etudiant> findAll() throws SQLException {
		ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
		String query = "select e.id as id, u.id as user_id, u.nom, u.prenom, e.sessions_formations_id as sfid, e.email, e.user_id from etudiants as e left outer join utilisateurs as u on u.id=e.user_id";
		ResultSet rs;
		try {
			PreparedStatement pstmt = (PreparedStatement) conn
					.prepareStatement(query);
			rs = pstmt.executeQuery();
		} catch (Exception ex) {
			Logger logger = Logger
					.getLogger("steemploi.persistance.TableEtudiants");
			logger.log(Level.SEVERE, this.getClass().getName() + " error", ex);
			return etudiants;
		}

		while (rs.next()) {
			Etudiant e = new Etudiant();
			Utilisateur u = new Utilisateur();
			e.setUtilisateur(u);
			copyProp(rs, e);
			e.setUser_id(rs.getInt("user_id"));
			e.setNom(rs.getString("nom"));
			e.setPrenom(rs.getString("prenom"));
			e.setEmail(rs.getString("email"));
			u.setNom(rs.getString("nom"));
			u.setPrenom(rs.getString("prenom"));
			u.setEmail(rs.getString("email"));
			etudiants.add(e);
		}

		Logger logger = Logger
				.getLogger("steemploi.persistance.TableEtudiants");
		logger.log(Level.INFO, this.getClass().getName() + " findById OK");
		return etudiants;
	}
}

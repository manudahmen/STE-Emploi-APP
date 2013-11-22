/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package steemploi.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.sql.PreparedStatement;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import steemploi.service.Etudiant;
import steemploi.service.Formateur;
import steemploi.service.Login;
import steemploi.service.Utilisateur;
import steemploi.service.TypeUtilisateur;

/**
 * 
 * @author manuel
 */

public class TableUtilisateurs extends UpdateInsertIntoTable {

	public TableUtilisateurs() {
		super("utilisateurs");
		setFieldsNames(new String[] { "email", "gsm", "tel", "rue", "numero",
				"boite", "codepostal", "ville", "pays", "nom", "prenom" });
		setTypes(new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR });
	}

	public int save(Utilisateur u) throws SQLException {
		setFieldsNames(new String[] { "email", "gsm", "tel", "rue", "numero",
				"boite", "codepostal", "ville", "pays", "nom", "prenom",
				"username","password","usertype"});
		setTypes(new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR,
				java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});

		if (u.getId() == 0) {
			insertInto(new Object[] { u.getEmail(), u.getGsm(), u.getTel(),
					u.getRue(), u.getNumero(), u.getBoite(), u.getCodepostal(),
					u.getVille(), u.getPays(), u.getNom(), u.getPrenom(), 
					u.getUsername(), u.getPassword(), u.getUsertype()});
			ExecuteUpdate();
			return selectLastInserted();

		} else {
			updateIfNotNull(new Object[] { u.getEmail(), u.getGsm(),
					u.getTel(), u.getRue(), u.getNumero(), u.getBoite(),
					u.getCodepostal(), u.getVille(), u.getPays(), u.getNom(),
					u.getPrenom(), 
					u.getUsername(), u.getPassword(), u.getUsertype()
					}, u.getId());
			ExecuteUpdate();
			return u.getId();
		}

	}

	public Utilisateur findByusername(String username) throws SQLException {
		String query = "select utilisateurs.* from utilisateurs where username=?";
		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(query);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		Utilisateur utilisateur = new Utilisateur();

		if (rs.next()) {
			copyProp(rs, utilisateur);

			if (utilisateur.getType() == TypeUtilisateur.ETUDIANT) {
				utilisateur.setEtudiant(new TableEtudiants()
						.findByUserId(utilisateur.getId()));

			} else if (utilisateur.getType() == TypeUtilisateur.FORMATEUR) {
				utilisateur.setFormateur(new TableFormateurs()
						.findByUserId(utilisateur.getId()));
			} else if (utilisateur.getType() == TypeUtilisateur.ADMIN) {
			}
		}

		return utilisateur;

	}

	public long login(Login l) throws SQLException {
		String query = "select utilisateurs.* from utilisateurs where username=? and password=PASSWORD(?)";
		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(query);
		stmt.setString(1, l.getUsername());
		stmt.setString(2, l.getPassword());
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			int active = rs.getInt("active");
			if (active == -1) {
				Logger logger = Logger
						.getLogger("steemploi.persistance.TableUtilisateurs");
				logger.warn("Login : utilisateur non actif");
				return -1;
			}
			int user_id = rs.getInt("id");
			Random r = new Random();
			long random = Math.abs(r.nextLong());
			long session_id = new TableSession().insert(random, user_id);
			return session_id;

		} else {
			return -1;
		}

	}

	public Utilisateur findBySessionId(long sess_id) throws SQLException {
		String query = "select utilisateurs.* from sessions inner join utilisateurs on sessions.user_id=utilisateurs.id where sessions.id="
				+ sess_id;
		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {

			Utilisateur u = new Utilisateur();
			copyProp(rs, u);
			u.setId(rs.getInt("id"));
			u
					.setType(rs.getString("usertype").equals("formateur") ? TypeUtilisateur.FORMATEUR
							: TypeUtilisateur.ETUDIANT);
			u.setUsername(rs.getString("username"));
			return u;
		}

		return null;

	}

	public static void copyProp(ResultSet rs, Utilisateur utilisateur)
			throws SQLException {
		utilisateur.setId(rs.getInt("id"));
		utilisateur.setActive(rs.getInt("active") == -1 ? false : true);
		utilisateur.setUsername(rs.getString("username"));
		utilisateur.setNom(rs.getString("nom"));
		utilisateur.setPassword(rs.getString("password"));
		utilisateur.setPrenom(rs.getString("prenom"));
		utilisateur.setEmail(rs.getString("email"));
		utilisateur.setRue(rs.getString("rue"));
		utilisateur.setNumero(rs.getString("numero"));
		utilisateur.setBoite(rs.getString("boite"));
		utilisateur.setCodepostal(rs.getString("codepostal"));
		utilisateur.setVille(rs.getString("ville"));
		utilisateur.setPays(rs.getString("pays"));
		// utilisateur.setDdn(rs.getDate("ddn"));
		utilisateur.setTel(rs.getString("tel"));
		utilisateur.setGsm(rs.getString("gsm"));
		String type = rs.getString("usertype");
		utilisateur.setType(type.equals("admin") ? TypeUtilisateur.ADMIN
				: (type.equals("formateur") ? TypeUtilisateur.FORMATEUR
						: TypeUtilisateur.ETUDIANT));
	}

	public Utilisateur findById(int id) throws SQLException {
		String query = "select utilisateurs.* from utilisateurs where id=?";
		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(query);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Utilisateur utilisateur = new Utilisateur();

		if (rs.next()) {
			copyProp(rs, utilisateur);

			if (utilisateur.getType() == TypeUtilisateur.ETUDIANT) {
				utilisateur.setEtudiant(new TableEtudiants()
						.findByUserId(utilisateur.getId()));

			} else if (utilisateur.getType() == TypeUtilisateur.FORMATEUR) {
				utilisateur.setFormateur(new TableFormateurs()
						.findByUserId(utilisateur.getId()));
			} else if (utilisateur.getType() == TypeUtilisateur.ADMIN) {
			}
		}

		return utilisateur;

	}

	public List<Utilisateur> getAutresEtudiants() throws SQLException {
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		String sql = "select * from utilisateurs as u inner join etudiants as e on e.user_id=u.id where usertype='etudiant' and e.sessions_formations_id=0";
		PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Utilisateur utilisateur = new Utilisateur();
			copyProp(rs, utilisateur);
			if (utilisateur.getType() == TypeUtilisateur.ETUDIANT) {
				utilisateur.setEtudiant(new TableEtudiants()
						.findByUserId(utilisateur.getId()));

			} else if (utilisateur.getType() == TypeUtilisateur.FORMATEUR) {
				utilisateur.setFormateur(new TableFormateurs()
						.findByUserId(utilisateur.getId()));
			} else if (utilisateur.getType() == TypeUtilisateur.ADMIN) {
			}
			utilisateurs.add(utilisateur);
		}
		return utilisateurs;
	}

	public List<Utilisateur> getFormateurs() throws SQLException {
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		String sql = "select * from utilisateurs as u where usertype='formateur'";
		PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Utilisateur utilisateur = new Utilisateur();
			copyProp(rs, utilisateur);
			if (utilisateur.getType() == TypeUtilisateur.ETUDIANT) {
				utilisateur.setEtudiant(new TableEtudiants()
						.findByUserId(utilisateur.getId()));

			} else if (utilisateur.getType() == TypeUtilisateur.FORMATEUR) {
				utilisateur.setFormateur(new TableFormateurs()
						.findByUserId(utilisateur.getId()));
			} else if (utilisateur.getType() == TypeUtilisateur.ADMIN) {
			}
			utilisateurs.add(utilisateur);
		}
		return utilisateurs;
	}

	public List<Utilisateur> getAdministrateurs() throws SQLException {
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		String sql = "select * from utilisateurs as u where usertype='admin'";
		PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Utilisateur utilisateur = new Utilisateur();
			copyProp(rs, utilisateur);
			if (utilisateur.getType() == TypeUtilisateur.ETUDIANT) {
				utilisateur.setEtudiant(new TableEtudiants()
						.findByUserId(utilisateur.getId()));

			} else if (utilisateur.getType() == TypeUtilisateur.FORMATEUR) {
				utilisateur.setFormateur(new TableFormateurs()
						.findByUserId(utilisateur.getId()));
			} else if (utilisateur.getType() == TypeUtilisateur.ADMIN) {
			}
			utilisateurs.add(utilisateur);
		}
		return utilisateurs;
	}

	public void delete(int id) throws SQLException {
		String sql = "delete from utilisateurs where id=?";
		PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		sql = "delete from etudiants where user_id=?";
		stmt = (PreparedStatement) conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		
		

		
	}

	public void saveEtudiantId(Utilisateur u, int etudiant_id) throws SQLException {
		update(new Object [] { etudiant_id }, u.getId());
		
	}
}

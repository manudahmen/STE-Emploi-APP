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

import steemploi.service.Entreprise;
import steemploi.service.TacheStats;

/**
 * 
 * @author manuel
 */

public class TableEntreprise extends UpdateInsertIntoTable {

	public TableEntreprise() {
		super("entreprise");
		setFieldsNames(new String[] { "tel", "mobile", "nom", "rue", "numero",
				"boite", "codepostal", "ville", "pays", "commentaires",
				"infocomplementaires", "email", "url", "owner", "secteur" });
	}

	public List<Entreprise> findByEtudiantId(int etudiant_id)
			throws SQLException {
		List<Entreprise> codes = new ArrayList<Entreprise>();
		String query = "select e.*, count(t.id) as count_taches from entreprise as e left outer join taches as t on t.entreprise_id=e.id where e.owner=? group by e.id order by e.nom ";
		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(query);
		stmt.setInt(1, etudiant_id);

		if (stmt.execute()) {
			ResultSet rs = stmt.getResultSet();
			Entreprise newcode;

			try {
				while (rs.next()) {
					newcode = new Entreprise();
					copyProp(rs, newcode);
					TacheStats stats = new TacheStats();
					stats.setCount(rs.getInt("count_taches"));
					newcode.setStats(stats);
					codes.add(newcode);
				}

			} catch (SQLException ex) {
				Logger.getLogger(TableEntreprise.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		return codes;
	}

	public void update(Entreprise e) throws SQLException {
		if (e.getId() == 0) {
			insertInto(new Object[] { e.getTel(), e.getGsm(), e.getNom(),
					e.getRue(), e.getNumero(), e.getBoite(), e.getCodepostal(),
					e.getVille(), e.getPays(), e.getCommentaires(),
					e.getInfocomplementaires(), e.getEmail(), e.getUrl(),
					e.getOwner(), e.getSecteur() });

		} else {
			update(new Object[] { e.getTel(), e.getGsm(), e.getNom(),
					e.getRue(), e.getNumero(), e.getBoite(), e.getCodepostal(),
					e.getVille(), e.getPays(), e.getCommentaires(),
					e.getInfocomplementaires(), e.getEmail(), e.getUrl(),
					e.getOwner(), e.getSecteur() }, e.getId());
		}

		ExecuteUpdate();
	}

	public Entreprise findById(int id) throws SQLException {
		String sql = "select * from entreprise where id = " + id
				+ " order by nom;";
		Entreprise e = new Entreprise();
		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			copyProp(rs, e);
			return e;

		} else {
			return null;
		}
	}

	public void delete(int id, int user_id) throws SQLException, Exception {
		String sql = "delete from entreprise where id = " + id + " and owner="
				+ user_id;
		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(sql);
		if (!stmt.execute()) {
			throw new Exception("Erreur lors de la suppression de l'entreprise"
					+ id + " par user_id" + user_id);
		}

	}

	public void copyProp(ResultSet rs, Entreprise e) throws SQLException {
		e.setId(rs.getInt("id"));
		e.setNom(rs.getString("nom"));
		e.setBoite(rs.getString("boite"));
		e.setCodepostal(rs.getString("codepostal"));
		e.setEmail(rs.getString("email"));
		e.setGsm(rs.getString("mobile"));
		e.setId(rs.getInt("id"));
		e.setNumero(rs.getString("numero"));
		e.setPays(rs.getString("pays"));
		e.setRue(rs.getString("rue"));
		e.setTel(rs.getString("tel"));
		e.setUrl(rs.getString("url"));
		e.setVille(rs.getString("ville"));
		e.setInfocomplementaires(rs.getString("infocomplementaires"));
		e.setCommentaires(rs.getString("commentaires"));
		e.setSecteur(rs.getString("secteur"));
		e.setOwner(rs.getInt("owner"));
	}

	public void updateCommentaires(int id, String comment, int owner) throws SQLException {
		String sql = "update entreprise set commentaires=? where id = " + id + " and owner="
		+ owner;
		PreparedStatement stmt = (PreparedStatement) conn
			.prepareStatement(sql);
		stmt.setString(1, comment);
		Logger logger= Logger.getLogger("TableEntreprise");
		logger.info(sql);
		
		stmt.executeUpdate();
	}
}

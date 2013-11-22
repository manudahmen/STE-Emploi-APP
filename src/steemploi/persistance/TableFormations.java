/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;

import steemploi.service.Etudiant;
import steemploi.service.Formation;

/**
 *
 * @author manuel
 */

public class TableFormations extends UpdateInsertIntoTable {

		public TableFormations() {
			super("formations");
		}

		public Formation findById(int id) throws SQLException {
				Formation f = new Formation();
				String query = "select f.id as id, f.nom as nom, f.annee as annee from formations as f where f.id=?";
				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
						f.setId(rs.getInt("id"));
						f.setNom(rs.getString("nom"));
						f.setAnnee(rs.getInt("annee"));

					}

				return f;

			}

		public List<Formation> findAll() throws SQLException {
				ArrayList<Formation> formations = new ArrayList<Formation>();
				Formation f = null;
				String query =
				    "select f.id as id, f.nom as f_nom, f.annee as annee from formations as f";
				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				int f_id = 0;

				while (rs.next()) {
						if (rs.getObject("id") != null && rs.getInt("id") != f_id) {
								f = new Formation();
								f.setId(rs.getInt("id"));
								f.setNom(rs.getString("f_nom"));
								f.setAnnee(rs.getInt("annee"));
								formations.add(f);
								f_id = f.getId();
							}
					}

				return formations;

			}

		public void delete(int id) throws SQLException {
			String sql = "delete from formations where id=?";
			PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
			
		}

		public void save(Formation f) throws SQLException {
			setFieldsNames(new String [] {"nom", "annee"});
			setTypes(new int [] {Types.VARCHAR, Types.INTEGER});
			if(f.getId()==0)
				insertInto(new Object [] {f.getNom(), f.getAnnee()});
			else
				update(new Object [] {f.getNom(), f.getAnnee()}, f.getId());
			ExecuteUpdate();
		}
	}

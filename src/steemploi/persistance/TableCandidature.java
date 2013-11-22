/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;

import steemploi.service.Candidature;
/**
 *
 * @author manuel
 */

public class TableCandidature extends UpdateInsertIntoTable {

		public TableCandidature() {
			super("candidature");
			setFieldsNames(new String [] {"etudiant_id", "tache", "date", "entreprise", "fonction", "reponse", "suite", "infos", "contact"});
			setTypes(new int[] { java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.DATE, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
			                     java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
		}

		public void delete(int id) throws SQLException {
				String query = "delete from candidature where id=?";

				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
				stmt.setInt(1, id);

				stmt.execute();

			}


		public int save(Candidature c) throws SQLException {
				if (c.getId() == 0) {
						insertInto(new Object[]
						           {
						               c.getEtudiantId(), c.getTache(),
						               setDate(c.getDate()), c.getEntreprise(), c.getFonction(), c.getReponse(), c.getSuite(), c.getInfos(), c.getContact()

						           });
						return selectLastInserted();

					} else {
						update(
						    new Object[]
						    {
						        c.getEtudiantId(), c.getTache(),
						        setDate(c.getDate()), c.getEntreprise(), c.getFonction(), c.getReponse(), c.getSuite(), c.getInfos(), c.getContact()
						    }, c.getId());
						return c.getId();
					}
			}


		public Candidature findById(int id, int etud_id) throws SQLException {
				String query = "select * from candidature where id=? and etudiant_id=?";
				Candidature newcode;
				newcode = new Candidature();

				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
				stmt.setInt(1, id);
				stmt.setInt(2, etud_id);

				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
						copyFields(newcode, rs);
					}

				return newcode;
			}

		private void copyFields(Candidature newcode, ResultSet rs) throws SQLException {
				newcode.setId(rs.getInt("id"));
				newcode.setEtudiantId(rs.getInt("etudiant_id"));
				newcode.setTache(rs.getString("tache"));
				newcode.setDate(getDate(rs.getDate("date")));
				newcode.setEntreprise(rs.getString("entreprise"));
				newcode.setFonction(rs.getString("fonction"));
				newcode.setReponse(rs.getString("reponse"));
				newcode.setSuite(rs.getString("suite"));
				newcode.setInfos(rs.getString("infos"));
				newcode.setContact(rs.getString("contact"));
			}

		public List<Candidature> findByEtudiant(int etud_id) throws SQLException {
				ArrayList<Candidature> candidatures = new ArrayList<Candidature>();

				String query = "select * from candidature where etudiant_id=?";

				Candidature newcode;

				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
				stmt.setInt(1, etud_id);

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
						newcode = new Candidature();
						copyFields(newcode, rs);
						candidatures.add(newcode);
					}

				return candidatures;
			}


	}

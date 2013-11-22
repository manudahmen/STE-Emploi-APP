/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;

import steemploi.service.Tache;

/**
 *
 * @author manuel
 */

public class TableTaches extends UpdateInsertIntoTable {

		private int id;
		private int code;
		private int percent;
		private String title;
		private String description;
		private int entreprise_id;
		private int contact_id;
		private int etudiant_id;
		private Calendar dateCompleted;
		public TableTaches() {
			super("taches");
		}

		public void delete(int id) {
			throw new UnsupportedOperationException("Not yet implemented");
		}



		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getEtudiant_id() {
			return etudiant_id;
		}

		public void setEtudiant_id(int etudiant_id) {
			this.etudiant_id = etudiant_id;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public Calendar getDateCompleted() {
			return dateCompleted;
		}

		public void setDateCompleted(Calendar completionDate) {
			this.dateCompleted = completionDate;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getPercent() {
			return percent;
		}

		public void setPercent(int percent) {
			this.percent = percent;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String Title) {
			this.title = Title;
		}

		public int getEntreprise_id() {
			return entreprise_id;
		}

		public void setEntreprise_id(int entreprise_id) {
			this.entreprise_id = entreprise_id;
		}

		public int getContact_id() {
			return contact_id;
		}

		public void setContact_id(int contact_id) {
			this.contact_id = contact_id;
		}

		public ArrayList<Tache> findByEtudiant(int i) throws SQLException {
				ArrayList<Tache> codes = new ArrayList<Tache>();
				String query = "select * from taches where etudiant_id=" + i;
				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);

				if (stmt.execute(query)) {
						ResultSet rs = stmt.getResultSet();
						Tache newcode;

						try {
								while (rs.next()) {
										newcode = new Tache();
										copyFields(newcode, rs);
										codes.add(newcode);
									}

							} catch (SQLException ex) {
								Logger.getLogger(TableEntreprise.class.getName()).log(Level.SEVERE, null, ex);
							}
					}

				return codes;
			}

		private void copyFields(Tache newcode, ResultSet rs) throws SQLException {
				newcode.setId(rs.getInt("id"));
				newcode.setCode(rs.getInt("code"));
				newcode.setDescription(rs.getString("description"));
				newcode.setPercent(rs.getInt("percent"));
				newcode.setTitle(rs.getString("title"));
				newcode.setDateCompleted(getDate(rs.getDate("dateCompleted")));
				newcode.setEtudiant_id(rs.getInt("etudiant_id"));
				newcode.setEntreprise_id(rs.getInt("entreprise_id"));
				newcode.setContact_id(rs.getInt("contact_id"));
			}

		public ArrayList<Tache> findByEtudiantAndEntreprise(int etud, int e_id) throws SQLException {
				ArrayList<Tache> codes = new ArrayList<Tache>();
				String query = "select * from taches where etudiant_id=" + etud + " and entreprise_id=" + e_id;
				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);

				if (stmt.execute(query)) {
						ResultSet rs = stmt.getResultSet();
						Tache newcode;

						try {
								while (rs.next()) {
										newcode = new Tache();
										copyFields(newcode, rs);
										codes.add(newcode);
									}

							} catch (SQLException ex) {
								Logger.getLogger(TableEntreprise.class.getName()).log(Level.SEVERE, null, ex);
							}
					}

				return codes;
			}

		public Tache findById(int id) throws SQLException {
				Tache newcode = new Tache();
				String query = "select * from taches where id=" + id;
				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);

				if (stmt.execute(query)) {
						ResultSet rs = stmt.getResultSet();

						while (rs.next()) {
								copyFields(newcode, rs);
							}
					}

				return newcode;
			}

		public void update(Tache tache) throws SQLException {
				// Vérifier donnéees

				if (tache.getId() == 0) {
						String sql = "insert into taches (title, code, description, dateCompleted, percent,"
						             + " etudiant_id, contact_id, entreprise_id) values (?,?,?,?,?,?,?,?);" ;

						PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);

						stmt.setString(1, tache.getTitle());
						stmt.setInt(2, tache.getCode());
						stmt.setString(3, tache.getDescription());
						stmt.setString(4, setDate(tache.getDateCompleted()));
						stmt.setInt(5, tache.getPercent());
						stmt.setInt(6, tache.getEtudiant_id());
						stmt.setInt(7, tache.getContact_id());
						stmt.setInt(8, tache.getEntreprise_id());
						System.out.println(sql);

						stmt.execute();

					} else {
						String sql = "update taches set title=?, code=?, description=?, dateCompleted=?, percent=?, etudiant_id=?, contact_id=?, entreprise_id=? WHERE id=?";
						PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
						stmt.setString(1, tache.getTitle());
						stmt.setInt(2, tache.getCode());
						stmt.setString(3, tache.getDescription());
						stmt.setString(4, setDate(tache.getDateCompleted()));
						stmt.setInt(5, tache.getPercent());
						stmt.setInt(6, tache.getEtudiant_id());
						stmt.setInt(7, tache.getContact_id());
						stmt.setInt(8, tache.getEntreprise_id());
						stmt.setInt(9, tache.getId());
						System.out.println(sql);
						stmt.execute();
					}
			}

		public void findByFormationDate(Map<String, Integer> counts, Map<String, ArrayList<Tache >> list, int formation_id, Calendar dateMin, Calendar dateMax) throws SQLException {
				String query = "select date_format('Y-m-d', t.dateCompleted) as dateStr , t.* from taches as t inner join etudiants as e on t.etudiant_id=e.id inner join formations as f on e.formation_id=f.id where t.dateCompleted>? and t.dateCompleted<? and f.id=?";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setString(1, setDate(dateMin));
				pstmt.setString(2, setDate(dateMax));
				pstmt.setInt(3, formation_id);
				String queryCount = "select date_format('Y-m-d', t.dateCompleted) as dateStr , count(t.id) from taches as t inner join etudiants as e on t.etudiant_id=e.id inner join formations as f on e.formation_id=f.id where t.dateCompleted>? and t.dateCompleted<? and f.id=? group by date(t.dateCompleted)";
				PreparedStatement pstmtCount = (PreparedStatement) conn.prepareStatement(queryCount);
				pstmtCount.setString(1, setDate(dateMin));
				pstmtCount.setString(2, setDate(dateMax));
				pstmtCount.setInt(3, formation_id);

				ResultSet rs = pstmt.executeQuery();
				ResultSet rsCount = pstmtCount.executeQuery();
				String dateStr = "";
				while (rs.next()) {
						Tache newcode = new Tache();
						copyFields(newcode, rs);
						dateStr = rs.getString("dateStr");

						if (list.get(dateStr) == null) {
								list.put(dateStr, new ArrayList<Tache>());
							}
					}

				while (rsCount.next()) {
						int i = rs.getInt("count");
						dateStr = rs.getString("dateStr");
						counts.put(dateStr, new Integer(counts.get(dateStr) + i));
					}
			}

		public void findByEtudiantDate(Map<String, Integer> counts, Map<String, ArrayList> list, int etudiant_id, Calendar dateMin, Calendar dateMax) throws SQLException {
				String query = "select date_format('Y-m-d', t.dateCompleted) as dateStr , t.* from taches as t innner join formations as f on t.id=f.id where t.dateCompleted<? and t.dateCompleted>? and t.etudiant_id=?";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setString(1, setDate(dateMin));
				pstmt.setString(2, setDate(dateMax));
				pstmt.setInt(3, etudiant_id);

				String dateStr = "";


				String queryCount = "select date_format('Y-m-d', t.dateCompleted) as dateStr , t.* from taches as t innner join formations as f on t.id=f.id  where t.dateCompleted<? and t.dateCompleted>? and t.etudiant_id=? group by date(t.dateCompleted)";
				PreparedStatement pstmtCount = (PreparedStatement) conn.prepareStatement(queryCount);
				pstmtCount.setString(1, setDate(dateMin));
				pstmtCount.setString(2, setDate(dateMax));
				pstmtCount.setInt(3, etudiant_id);

				ResultSet rs = pstmt.executeQuery();
				ResultSet rsCount = pstmtCount.executeQuery();

				while (rs.next()) {
						Tache newcode = new Tache();
						copyFields(newcode, rs);
						Tache t = new Tache();
						dateStr = rs.getString("dateStr");

						if (list.get(dateStr) == null) {
								list.put(dateStr, new ArrayList<Object>());
							}

						list.get(dateStr).add(newcode);
					}

				while (rsCount.next()) {
						dateStr = rs.getString("dateStr");
						int i = rs.getInt("count");
						counts.put(dateStr, new Integer(counts.get(dateStr) + i));
					}
			}

	}

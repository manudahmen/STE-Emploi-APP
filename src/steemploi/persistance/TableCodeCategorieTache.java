package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;

import steemploi.service.CategoriesTache;

/**
 *
 * @author Manuel Dahmen
 */

public class TableCodeCategorieTache extends Table {

		private int id;
		private String code;
		private String title;

		public TableCodeCategorieTache() {
			super();
		}




		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public List<CategoriesTache> findAll() throws SQLException {
				List<CategoriesTache> codes = new ArrayList<CategoriesTache>();
				String query = "select * from codes_categories_taches order by id";
				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);

				if (stmt.execute(query)) {
						ResultSet rs = stmt.getResultSet();
						CategoriesTache newcode;

						try {
								while (rs.next()) {
										newcode = new CategoriesTache();
										newcode.setId(rs.getInt("id"));
										newcode.setCode(rs.getString("code"));
										newcode.setTitle(rs.getString("title"));
										codes.add(newcode);
									}

							} catch (SQLException ex) {
								Logger.getLogger(TableCodeCategorieTache.class.getName()).log(Level.SEVERE, null, ex);
							}
					}

				return codes;
			}

		public CategoriesTache findById(int id) throws SQLException {
				String query = "select * from codes_categories_taches where id=" + id;
				PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
				CategoriesTache newcode = new CategoriesTache();

				if (stmt.execute(query)) {
						ResultSet rs = stmt.getResultSet();

						try {
								while (rs.next()) {
										newcode.setId(rs.getInt("id"));
										newcode.setCode(rs.getString("code"));
										newcode.setTitle(rs.getString("title"));
									}

							} catch (SQLException ex) {
								Logger.getLogger(TableCodeCategorieTache.class.getName()).log(Level.SEVERE, null, ex);
							}
					}

				return newcode;
			}
	}

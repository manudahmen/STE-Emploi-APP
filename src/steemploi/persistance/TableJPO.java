package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import com.myapp.struts.events.JPO;
import java.sql.PreparedStatement;

public class TableJPO extends UpdateInsertIntoTable {
		public TableJPO() {
			super("jpo");
			setFieldsNames(new String [] {"name", "date_debut", "date_fin", "description", "link" });
			setTypes(new int[] { java.sql.Types.VARCHAR, java.sql.Types.DATE, java.sql.Types.DATE, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
		}

		public int save(JPO jpo) throws SQLException {
				if (jpo.getId() == 0) {
						insertInto(new Object[] { jpo.getName(), setDate(jpo.getDateDebut()), setDate(jpo.getDateFin()), jpo.getDescription(), jpo.getLink()});
						return selectLastInserted();

					} else {
						update(new Object[] { jpo.getName(), setDate(jpo.getDateDebut()), setDate(jpo.getDateFin()), jpo.getDescription(), jpo.getLink()}, jpo.getId());
						return jpo.getId();
					}
			}

		public JPO findById(int id) throws SQLException {
				JPO jpo = new JPO();
				String query = "select * from jpo where id=?";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next())
					copyProps(rs, jpo);

				return jpo;
			}

		public List<JPO> findAll() throws SQLException {
				List<JPO> jpos = new ArrayList<JPO>();
				String query = "select * from jpo";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
						JPO jpo = new JPO();
						copyProps(rs, jpo);
						jpos.add(jpo);
					}

				return jpos;
			}


		public List<JPO> findByDate(Calendar dateMin, Calendar dateMax) throws SQLException {
				List<JPO> jpos = new ArrayList<JPO>();
				String query = "select * from jpo where date_debut>? and date_debut<?";

				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setString(1, setDate(dateMin));
				pstmt.setString(2, setDate(dateMax));

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
						JPO jpo = new JPO();
						copyProps(rs, jpo);
						jpos.add(jpo);
					}

				return jpos;
			}

		private void copyProps(ResultSet rs, JPO jpo) throws SQLException {
				jpo.setDateDebut(getDate(rs.getDate("date_debut")));
				jpo.setDateFin(getDate(rs.getDate("date_fin")));
				jpo.setId(rs.getInt("id"));
				jpo.setDescription(rs.getString("description"));
				jpo.setLink(rs.getString("link"));
				jpo.setName(rs.getString("name"));
			}

		public boolean delete(int id) throws SQLException {
			String sql = "delete from " + getTableName() + " where id=?";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			return pstmt.execute();
		}
	}

package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import java.sql.PreparedStatement;

import com.myapp.struts.events.Conge;


public class TableConge extends UpdateInsertIntoTable {
		public TableConge() {
			super("conge");
			setFieldsNames(new String [] {"nom", "date_debut", "date_fin"});
			setTypes(new int[] { java.sql.Types.VARCHAR, java.sql.Types.DATE, java.sql.Types.DATE});
		}

		public int save(Conge conge) throws SQLException {
				if (conge.getId() == 0) {
						insertInto(new Object[] { conge.getNom(), setDate(conge.getDateDebut()), setDate(conge.getDateFin())});
						return selectLastInserted();

					} else {
						update(new Object[] { conge.getNom(), setDate(conge.getDateDebut()), setDate(conge.getDateFin())}, conge.getId());
						return conge.getId();
					}
			}

		public Conge findById(int id) throws SQLException {
				Conge conge = new Conge();
				String query = "select * from conge where id=?";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next())
					copyProps(rs, conge);

				return conge;
			}

		public List<Conge> findAll() throws SQLException {
				List<Conge> conges = new ArrayList<Conge>();
				String query = "select * from conge";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
						Conge conge = new Conge();
						copyProps(rs, conge);
						conges.add(conge);
					}

				return conges;
			}

		public List<Conge> findByDate(Calendar dateMin, Calendar dateMax) throws SQLException {
				dateMin.set(Calendar.HOUR, 0);
				dateMin.set(Calendar.MINUTE, 0);
				dateMin.set(Calendar.SECOND, 0);
				dateMax.set(Calendar.HOUR, 23);
				dateMax.set(Calendar.MINUTE, 59);
				dateMax.set(Calendar.SECOND, 59);
				List<Conge> conges = new ArrayList<Conge>();
				String query = "select * from conge where (date_debut<=? and date_fin>=?) or (date_debut>=? and date_fin<=?) or (date_debut<=? and date_fin>=?)";
				PreparedStatement pstmt = (PreparedStatement)
				                                    conn.prepareStatement(query);
				pstmt.setString(1, setDate(dateMin));
				pstmt.setString(2, setDate(dateMax));
				pstmt.setString(3, setDate(dateMin));
				pstmt.setString(4, setDate(dateMax));
				pstmt.setString(5, setDate(dateMin));
				pstmt.setString(6, setDate(dateMax));
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
						Conge conge = new Conge();
						copyProps(rs, conge);
						conges.add(conge);
					}

				return conges;
			}


		private void copyProps(ResultSet rs, Conge conge) throws SQLException {
				Calendar cal = getDate(rs.getDate("date_debut"));
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				conge.setDateDebut(cal);
				cal = getDate(rs.getDate("date_fin"));
				cal.set(Calendar.HOUR, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				conge.setDateFin(cal);
				conge.setId(rs.getInt("id"));
				conge.setNom(rs.getString("nom"));
			}

		public boolean delete(int id) throws SQLException {
			String sql = "delete from " + getTableName() + " where id=?";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			return pstmt.execute();
			
		}
	}

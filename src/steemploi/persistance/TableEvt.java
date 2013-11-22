package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import java.sql.PreparedStatement;

import com.myapp.struts.events.Evt;

public class TableEvt extends UpdateInsertIntoTable {
		public TableEvt() {
			super("evenement");
			setFieldsNames(new String [] {"name", "date_debut", "date_fin", "description", "link", "location" });
			setTypes(new int[] { java.sql.Types.VARCHAR, java.sql.Types.DATE, java.sql.Types.DATE, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
		}

		public int save(Evt evt) throws SQLException {
				if (evt.getId() == 0) {
						insertInto(new Object[] { evt.getName(), setDate(evt.getDateDebut()), setDate(evt.getDateFin()), evt.getDescription(), evt.getLink(), evt.getLocation()});
						return selectLastInserted();

					} else {
						update(new Object[] { evt.getName(), setDate(evt.getDateDebut()), setDate(evt.getDateFin()), evt.getDescription(), evt.getLink(), evt.getLocation()}, evt.getId());
						return evt.getId();
					}
			}

		public Evt findById(int id) throws SQLException {
				Evt evt = new Evt();
				String query = "select * from evenement where id=?";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next())
					copyProps(rs, evt);

				return evt;
			}

		public List<Evt> findAll() throws SQLException {
				List<Evt> evts = new ArrayList<Evt>();
				String query = "select * from evenement";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
						Evt evt = new Evt();
						copyProps(rs, evt);
						evts.add(evt);
					}

				return evts;
			}

		public List<Evt> findByDate(Calendar dateMin, Calendar dateMax) throws SQLException {
				List<Evt> evts = new ArrayList<Evt>();
				String query = "select * from evenement where (date_debut<=? and date_fin>=?) or (date_debut>=? and date_fin<=?) or (date_debut<=? and date_fin>=?)";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setString(1, setDate(dateMin));
				pstmt.setString(2, setDate(dateMax));
				pstmt.setString(3, setDate(dateMin));
				pstmt.setString(4, setDate(dateMax));
				pstmt.setString(5, setDate(dateMin));
				pstmt.setString(6, setDate(dateMax));
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
						Evt evt = new Evt();
						copyProps(rs, evt);
						evts.add(evt);
					}

				return evts;
			}

		private void copyProps(ResultSet rs, Evt evt) throws SQLException {
				evt.setDateDebut(getDate(rs.getDate("date_debut")));
				evt.setDateFin(getDate(rs.getDate("date_fin")));
				evt.setId(rs.getInt("id"));
				evt.setDescription(rs.getString("description"));
				evt.setLink(rs.getString("link"));
				evt.setName(rs.getString("name"));
				evt.setLocation(rs.getString("location"));
			}

		public boolean delete(int id) throws SQLException {
			String sql = "delete from " + getTableName() + " where id=?";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			return pstmt.execute();
		}
	}

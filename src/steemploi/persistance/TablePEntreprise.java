package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import java.sql.PreparedStatement;

import com.myapp.struts.events.PEntreprise;

public class TablePEntreprise extends UpdateInsertIntoTable {
		public TablePEntreprise() {
			super("presentation_entreprise");
			setFieldsNames(new String [] {"nom", "telephone", "url", "email", "description", "infoscomplementaires", "date_debut"});
			setTypes(new int[] { java.sql.Types.VARCHAR,
			                     java.sql.Types.VARCHAR,
			                     java.sql.Types.VARCHAR,
			                     java.sql.Types.VARCHAR,
			                     java.sql.Types.VARCHAR,
			                     java.sql.Types.VARCHAR,
			                     java.sql.Types.DATE});
		}

		public int save(PEntreprise conge) throws SQLException {
				if (conge.getId() == 0) {
						insertInto(new Object[] { conge.getNom(),
						                          conge.getTelephone(),
						                          conge.getUrl(),
						                          conge.getEmail(),
						                          conge.getDescription(),
						                          conge.getInfoscomplementaires(),
						                          setDate(conge.getDateDebut())});
						return selectLastInserted();

					} else {
						update(new Object[] { conge.getNom(),
						                      conge.getTelephone(),
						                      conge.getUrl(),
						                      conge.getEmail(),
						                      conge.getDescription(),
						                      conge.getInfoscomplementaires(),
						                      setDate(conge.getDateDebut())}, conge.getId());
						return conge.getId();
					}
			}

		public PEntreprise findById(int id) throws SQLException {
				PEntreprise conge = new PEntreprise();
				String query = "select * from presentation_entreprise where id=?";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next())
					copyProps(rs, conge);

				return conge;
			}

		public List<PEntreprise> findAll() throws SQLException {
				List<PEntreprise> conges = new ArrayList<PEntreprise>();
				String query = "select * from presentation_entreprise";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
						PEntreprise conge = new PEntreprise();
						copyProps(rs, conge);
						conges.add(conge);
					}

				return conges;
			}

		public List<PEntreprise> findByDate(Calendar dateMin, Calendar dateMax) throws SQLException {
				List<PEntreprise> conges = new ArrayList<PEntreprise>();
				String query = "select * from presentation_entreprise where date_debut>? and date_debut<?";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setString(1, setDate(dateMin));
				pstmt.setString(2, setDate(dateMax));
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
						PEntreprise conge = new PEntreprise();
						copyProps(rs, conge);
						conges.add(conge);
					}

				return conges;
			}


		private void copyProps(ResultSet rs, PEntreprise conge) throws SQLException {
				conge.setId(rs.getInt("id"));
				conge.setNom(rs.getString("nom"));
				conge.setTelephone(rs.getString("telephone"));
				conge.setUrl(rs.getString("url"));
				conge.setEmail(rs.getString("email"));
				conge.setDescription(rs.getString("description"));
				conge.setInfoscomplementaires(rs.getString("infoscomplementaires"));
				conge.setDateDebut(getDate(rs.getDate("date_debut")));
			}

		public boolean delete(int id) throws SQLException {
			String sql = "delete from " + getTableName() + " where id=?";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			return pstmt.execute();
		}
	}

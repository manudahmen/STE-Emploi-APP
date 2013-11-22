package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myapp.struts.events.PresentationEntreprise;
import java.sql.PreparedStatement;

public class TablePresentationEntreprise extends UpdateInsertIntoTable {
		public TablePresentationEntreprise() {
			super("presentation_entreprise");
			setFieldsNames(new String[] {"nom", "date", "telephone", "email", "url", "description", "infoscomplementaires"});
			setTypes(new int [] {java.sql.Types.VARCHAR, java.sql.Types.DATE, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR});
		}

		public PresentationEntreprise findById(int id) throws SQLException {
				PresentationEntreprise e = new PresentationEntreprise();

				String query = "select * from presentation_entreprise where id=?";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setInt(1, id);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next())
					copyProps(rs, e);

				return e;
			}

		public void save(PresentationEntreprise pe) {

		}

		private void copyProps(ResultSet rs, PresentationEntreprise pe) throws SQLException {
				pe.setId(rs.getInt("id"));
				pe.setDate(getDate(rs.getDate("date")));
				pe.setDescription(rs.getString("description"));
				pe.setEmail(rs.getString("email"));
				pe.setInfoscomplementaires(rs.getString("infoscomplementaires"));
				pe.setNom(rs.getString("nom"));
				pe.setTelephone(rs.getString("telephone"));
				pe.setUrl(rs.getString("url"));
			}
	}

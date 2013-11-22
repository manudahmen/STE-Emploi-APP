package steemploi.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import steemploi.service.Profil;
import steemploi.service.Profil.Langage;

public class TableLangages extends UpdateInsertIntoTable {

	public TableLangages() {
		super("langages");
	}

	public void loadLangages(Profil profil) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement("select * from langages order by noOrdreLangage");
		ResultSet rs = pstmt.executeQuery();
		profil.setLangages(new ArrayList<Profil.Langage>());
		while (rs.next()) {
			Langage l = profil.new Langage();
			l.setId(rs.getInt("id"));
			l.setNom(rs.getString("nom_langage"));
			l.setDescription(rs.getString("description_langage"));
			l.setVersion(rs.getString("versions"));
			l.setNoOrdreLangage(rs.getInt("noOrdreLangage"));
			profil.getLangages().add(l);
		}
}
	public void deleteLangage(Profil.Langage l) throws SQLException {
		PreparedStatement pstmt = conn
				.prepareStatement("delete from langages where id=?");
		pstmt.setInt(1, l.getId());
		pstmt.execute();
	}

	public void save(Langage l) throws SQLException {
		setTableName("langages");
		setFieldsNames(new String[] { "nom_langage", "description_langage",
				"noOrdreLangage", "versions" });
		if (l.getId() == 0) {
			insertInto(new Object[] { l.getNom(), l.getDescription(),
					l.getNoOrdreLangage(), l.getVersion() });
		} else {
			update(new Object[] { l.getNom(), l.getDescription(),
					l.getNoOrdreLangage(), l.getVersion() }, l.getId());
		}
		ExecuteUpdate();
	}

	public void updateOrdreLangage(int idLangage, int noOrdreLangage) throws SQLException {
		setTableName("langages");
		setFieldsNames(new String[] { "noOrdreLangage" });
		setIdStr("id");
		update(new Object[] {noOrdreLangage}, idLangage);
		ExecuteUpdate();
	}

	public void deleteLangage(int id) throws SQLException {
		PreparedStatement pstmt = conn
		.prepareStatement("delete from langages where id=?");
		pstmt.setInt(1, id);
		pstmt.execute();
	}

}

package steemploi.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import steemploi.service.Profil;
import steemploi.service.Profil.Langue;

public class TableLangues extends UpdateInsertIntoTable {

	public TableLangues() {
		super("langues");
	}

	public void loadLangues(Profil profil) throws SQLException {
		String sql = "select nomLangue, nomCourt, noOrdreLangue, idLangue from langues order by noOrdreLangue";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Langue langue = profil.new Langue();
			langue.setId(rs.getInt("idLangue"));
			langue.setNom(rs.getString("nomLangue"));
			langue.setNoOrdreLangue(rs.getInt("noOrdreLangue"));
			langue.setNomCourt(rs.getString("nomCourt"));
			profil.getLangues().add(langue);
		}
	}

	public void save(Langue l) throws SQLException {
		setTableName("langues");
		setFieldsNames(new String[] { "noOrdreLangue", "nomLangue", "nomCourt" });
		setIdStr("idLangue");
		if (l.getId() == 0)
			insertInto(new Object[] { l.getNoOrdreLangue(), l.getNom(),
					l.getNomCourt() });
		else
			update(new Object[] { l.getNoOrdreLangue(), l.getNom(),
					l.getNomCourt() }, l.getId());
		ExecuteUpdate();
	}

	public void deleteLangue(int id) throws SQLException {
		PreparedStatement dstmt = (PreparedStatement) conn
				.prepareStatement("delete from langues where idLangue=?");
		dstmt.setInt(1, id);
		dstmt.execute();
	}

	public void updateOrdreLangue(int idLangue, int noOrdreLangue) throws SQLException {
		setTableName("langues");
		setFieldsNames(new String[] { "noOrdreLangue" });
		setIdStr("idLangue");
		update(new Object[] {noOrdreLangue}, idLangue);
		ExecuteUpdate();
	}

}

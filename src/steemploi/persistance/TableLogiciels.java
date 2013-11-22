package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import java.sql.PreparedStatement;


import steemploi.service.Profil;
import steemploi.service.Profil.CategoriesLogiciels;
import steemploi.service.Profil.Logiciels;

public class TableLogiciels extends UpdateInsertIntoTable {

	public TableLogiciels() {
		super("logiciels");
	}

	public ArrayList<Profil.CategoriesLogiciels> loadCategories(Profil p)
			throws SQLException {
		ArrayList<Profil.CategoriesLogiciels> categories = new ArrayList<Profil.CategoriesLogiciels>();
		PreparedStatement dstmt = (PreparedStatement) conn
				.prepareStatement("select * from categories_logiciels order by noOrdreCategorie");
		ResultSet rs = dstmt.executeQuery();

		while (rs.next()) {
			Profil.CategoriesLogiciels cat = p.new CategoriesLogiciels();
			cat.setIdCategorie(rs.getInt("idCategorie"));
			cat.setNomCategorie(rs.getString("nomCategorie"));
			cat.setNoOrdreCategorie(rs.getInt("noOrdreCategorie"));
			cat.setDescription(rs.getString("description"));
			p.getCategories().add(cat);
		}

		return categories;
	}

	public ArrayList<Profil.Logiciels> loadLogiciels(Profil p)
			throws SQLException {
		ArrayList<Profil.Logiciels> logiciels = new ArrayList<Profil.Logiciels>();
		PreparedStatement dstmt = (PreparedStatement) conn
				.prepareStatement("select * from logiciels order by categorieLogiciel, noOrdreLogiciel");
		ResultSet rs = dstmt.executeQuery();

		while (rs.next()) {
			Profil.Logiciels logiciel = p.new Logiciels();
			logiciel.setIdLogiciel(rs.getInt("idLogiciel"));
			logiciel.setNomLogiciel(rs.getString("nomLogiciel"));
			logiciel.setNoOrdreLogiciel(rs.getInt("noOrdreLogiciel"));
			logiciel
					.setDescriptionLogiciel(rs.getString("descriptionLogiciel"));
			logiciel.setEditeurLogiciel(rs.getString("editeurLogiciel"));
			logiciel.setCategorieLogiciel(rs.getInt("categorieLogiciel"));
			logiciel.setVersion(rs.getString("version"));
			p.getLogiciels().add(logiciel);
		}

		return logiciels;
	}

	public Profil.Logiciels findLogicielById(Profil profil, int id)
			throws SQLException {
		Profil.Logiciels l = profil.new Logiciels();
		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement("select * from logiciels where idLogiciel=?");
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			l.setIdLogiciel(rs.getInt("idLogiciel"));
			l.setNoOrdreLogiciel(rs.getInt("noOrdreLogiciel"));
			l.setNomLogiciel(rs.getString("nomLogiciel"));
			l.setEditeurLogiciel(rs.getString("editeurLogiciel"));
			l.setCategorieLogiciel(rs.getInt("categorieLogiciel"));
			l.setDescriptionLogiciel(rs.getString("descriptionLogiciel"));
			l.setVersion(rs.getString("version"));
		}
		return l;
	}

	public void save(Logiciels l) throws SQLException {
		setTableName("logiciels");
		setFieldsNames(new String[] { "noOrdreLogiciel", "nomLogiciel",
				"descriptionLogiciel", "editeurLogiciel", "categorieLogiciel",
				"version" });
		setIdStr("idLogiciel");
		setTypes(new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.INTEGER, Types.VARCHAR });
		if (l.getIdLogiciel() == 0)
			insertInto(new Object[] { l.getNoOrdreLogiciel(),
					l.getNomLogiciel(), l.getDescriptionLogiciel(),
					l.getEditeurLogiciel(), l.getCategorieLogiciel(), l.getVersion() });
		else
			update(new Object[] { l.getNoOrdreLogiciel(), l.getNomLogiciel(),
					l.getDescriptionLogiciel(), l.getEditeurLogiciel(),
					l.getCategorieLogiciel(), l.getVersion() }, l.getIdLogiciel());
		ExecuteUpdate();
	}

	public void saveCatInfo(CategoriesLogiciels cl) throws SQLException {
		setTableName("categories_logiciels");
		setFieldsNames(new String[] { "idCategorie", "nomCategorie",
				"noOrdreCategorie", "description" });
		setIdStr("idCategorie");
		if (cl.getIdCategorie() == 0)
			insertInto(new Object[] { cl.getIdCategorie(),
					cl.getNomCategorie(), cl.getNoOrdreCategorie(),
					cl.getDescription() });
		else
			update(new Object[] { cl.getIdCategorie(), cl.getNomCategorie(),
					cl.getNoOrdreCategorie(), cl.getDescription() }, cl
					.getIdCategorie());
		ExecuteUpdate();
	}

	public CategoriesLogiciels findCategorieById(Profil profil, int catid)
			throws SQLException {
		Profil.CategoriesLogiciels cat = profil.new CategoriesLogiciels();
		PreparedStatement dstmt = (PreparedStatement) conn
				.prepareStatement("select * from categories_logiciels where idCategorie=?");
		dstmt.setInt(1, catid);
		ResultSet rs = dstmt.executeQuery();

		while (rs.next()) {
			cat.setIdCategorie(rs.getInt("idCategorie"));
			cat.setNomCategorie(rs.getString("nomCategorie"));
			cat.setNoOrdreCategorie(rs.getInt("noOrdreCategorie"));
			cat.setDescription(rs.getString("description"));
		}

		return cat;
	}

	public void deleteCategorie(int cat) throws SQLException {
		PreparedStatement dstmt = (PreparedStatement) conn
				.prepareStatement("delete from categories_logiciels where idCategorie=?");
		dstmt.setInt(1, cat);
		dstmt.execute();

	}

	public void deleteLogiciel(int log) throws SQLException {
		PreparedStatement dstmt = (PreparedStatement) conn
				.prepareStatement("delete from logiciels where idLogiciel=?");
		dstmt.setInt(1, log);
		dstmt.execute();
	}

	public void updateOrdreLogiciel(int id, int index) throws SQLException {
		setTableName("categories_logiciels");
		setFieldsNames(new String[] { "noOrdreCategorie" });
		setIdStr("idCategorie");
		setTypes(new int[] { Types.INTEGER });
		update(new Object[] { index }, id);
		ExecuteUpdate();
	}

	public void updateOrdreLogiciel2(int id, int index) throws SQLException {
		setTableName("logiciels");
		setFieldsNames(new String[] { "noOrdreLogiciel" });
		setIdStr("idLogiciel");
		setTypes(new int[] { Types.INTEGER });
		update(new Object[] { index }, id);
		ExecuteUpdate();
		
	}

}

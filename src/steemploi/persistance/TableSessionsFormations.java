package steemploi.persistance;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;

import steemploi.service.Etudiant;
import steemploi.service.Formation;
import steemploi.service.SessionsFormations;
import java.sql.Types;

public class TableSessionsFormations extends UpdateInsertIntoTable implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7835942128849066862L;

	public TableSessionsFormations() {
		super("sessions_formations");
	}

	public Object findSessionByEtudiantId(int id) throws SQLException {
		SessionsFormations sf = new SessionsFormations();
		ResultSet rs = null;
		copyProp(rs, sf);
		return sf;

	}

	private void copyProp(ResultSet rs, SessionsFormations sf)
			throws SQLException {
		sf.setDateEnd(getDate(rs.getDate("dateEnd")));
		sf.setDateStart(getDate(rs.getDate("dateStart")));
		sf.setFormation(new TableFormations().findById(rs
				.getInt("formation_id")));
		sf.setName(rs.getString("name"));

	}

	/*
	 * public List<SessionsFormations> findAll() throws SQLException { return
	 * findAll(false); }
	 */
	public List<SessionsFormations> findAll(boolean current)
			throws SQLException {
		ArrayList<SessionsFormations> sfs = new ArrayList<SessionsFormations>();
		String query = "select sf.id as id, sf.name as name, sf.dateStart as dateStart, sf.dateEnd as dateEnd, f.id as fid, f.annee as annee, f.nom as nom, e.id as eid, e.user_id as user_id, u.nom as enom, u.prenom as eprenom, u.email as email from formations as f inner join sessions_formations as sf on sf.formation_id=f.id left outer join etudiants as e on e.sessions_formations_id=sf.id left outer join utilisateurs as u on e.user_id=u.id ";
		if (current) {
			java.util.Calendar date = java.util.Calendar.getInstance();
			query += " where dateEnd>'" + setDate(date) + "' ";
		}

		query += "order by nom, dateStart";
		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		Formation f = new Formation();
		Etudiant e;
		e = new Etudiant();
		int f_id = -1;
		int e_id = -1;
		int sf_id = -1;
		SessionsFormations sf = new SessionsFormations();

		while (rs.next()) {

			if (rs.getObject("id") != null && sf_id != rs.getInt("id")) {
				sf = new SessionsFormations();
				sf.setId(rs.getInt("id"));

				if (!sfs.contains(sf)) {
					sf.setDateStart(getDate(rs.getDate("dateStart")));
					sf.setDateEnd(getDate(rs.getDate("dateEnd")));
					sf.setFormation(f);
					sf.setName(rs.getString("name"));
					sf_id = rs.getInt("id");
					sfs.add(sf);
				}
			}

			if (rs.getObject("fid") != null && f_id != rs.getInt("fid")) {
				f = new Formation();
				f.setAnnee(rs.getInt("annee"));
				f.setNom(rs.getString("nom"));
				f.setId(rs.getInt("fid"));
				f_id = f.getId();
				sf.setFormation(f);
			}

			if (rs.getObject("eid") != null && e_id != rs.getInt("eid")) {
				e = new Etudiant();
				e.setNom(rs.getString("enom"));
				e.setPrenom(rs.getString("eprenom"));
				e.setEmail(rs.getString("email"));
				e.setId(rs.getInt("eid"));
				e.setUser_id(rs.getInt("user_id"));
				e_id = e.getId();
				sf.getEtudiants().add(e);
			}
		}

		return sfs;
	}

	public List<SessionsFormations> findEmpty(boolean current)
			throws SQLException {
		ArrayList<SessionsFormations> sfs = new ArrayList<SessionsFormations>();
		String query = "select sf.id as id, sf.name as name, sf.dateStart as dateStart, sf.dateEnd as dateEnd, f.id as fid, f.annee as annee, f.nom as nom, '' as eid, 0 as user_id, '' as enom, '' as eprenom, '' as email from formations  as f left outer join sessions_formations as sf on sf.formation_id=f.id";

		if (current) {
			java.util.Calendar date = java.util.Calendar.getInstance();
			query += " and sf.dateEnd>'" + setDate(date) + "'";
		}

		query += "order by fid, id ";
		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		Formation f = new Formation();
		Etudiant e;
		e = new Etudiant();
		int f_id = -1;
		int e_id = -1;
		int sf_id = -1;
		SessionsFormations sf = new SessionsFormations();

		while (rs.next()) {
			if (rs.getObject("id") != null && sf_id != rs.getInt("id")) {
				sf = new SessionsFormations();
				sf.setId(rs.getInt("id"));

				if (!sfs.contains(sf)) {
					sf.setDateStart(getDate(rs.getDate("dateStart")));
					sf.setDateEnd(getDate(rs.getDate("dateEnd")));
					sf.setFormation(f);
					sf.setName(rs.getString("name"));
					sf_id = rs.getInt("id");
					sfs.add(sf);
				}
			}

			if (rs.getObject("fid") != null && f_id != rs.getInt("fid")) {
				f = new Formation();
				f.setAnnee(rs.getInt("annee"));
				f.setNom(rs.getString("nom"));
				f.setId(rs.getInt("fid"));
				f_id = f.getId();
				sf.setFormation(f);
			}

			if (rs.getObject("eid") != null && e_id != rs.getInt("eid")) {
				e = new Etudiant();
				e.setNom(rs.getString("enom"));
				e.setPrenom(rs.getString("eprenom"));
				e.setEmail(rs.getString("email"));
				e.setId(rs.getInt("eid"));
				e.setUser_id(rs.getInt("user_id"));
				e_id = e.getId();
				sf.getEtudiants().add(e);
			}
		}

		return sfs;
	}

	public List<Formation> findFormationsSansSessions() throws SQLException {
		ArrayList<Formation> fs = new ArrayList<Formation>();
		String query = "select f.id as fid, f.annee as annee, f.nom as nom from formations as f where not exists (select * from sessions_formations where formation_id=f.id) ";
		query += "order by nom";
		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		Formation f = new Formation();
		int f_id = -1;
		while (rs.next()) {
			if (rs.getObject("fid") != null && f_id != rs.getInt("fid")) {
				f = new Formation();
				f.setAnnee(rs.getInt("annee"));
				f.setNom(rs.getString("nom"));
				f.setId(rs.getInt("fid"));
				f_id = f.getId();
				fs.add(f);
			}

		}

		return fs;
	}

	public SessionsFormations findById(int id) throws SQLException {
		String query = "select sf.id as id, sf.name as name, sf.dateStart as dateStart, sf.dateEnd as dateEnd, f.id as fid, f.annee as annee, f.nom as nom, e.id as eid, e.user_id as user_id, u.nom as enom, u.prenom as eprenom, u.email as email from sessions_formations as sf inner join formations as f on sf.formation_id=f.id left outer join etudiants as e on e.sessions_formations_id=sf.id left outer join utilisateurs as u on e.user_id=u.id where sf.id=? and (u.id is null or u.usertype='etudiant') order by e.nom";
		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		int fid = -1;
		Formation f = new Formation();
		Etudiant e;
		e = new Etudiant();
		int eid = -1;
		int sfid = -1;
		SessionsFormations sf = null;
		sf = new SessionsFormations();

		while (rs.next()) {
			int fid2 = rs.getInt("fid");
			if (fid != fid2) {
				f = new Formation();
				f.setId(fid2);
				f.setAnnee(rs.getInt("annee"));
				f.setNom(rs.getString("nom"));
				fid = fid2;
			}
			if (rs.getObject("eid") != null && rs.getInt("eid") != eid) {
				e = new Etudiant();
				e.setNom(rs.getString("enom"));
				e.setPrenom(rs.getString("eprenom"));
				e.setId(rs.getInt("eid"));
				e.setEmail(rs.getString("email"));
				eid = rs.getInt("eid");
				e.setUser_id(rs.getInt("user_id"));
				sf.getEtudiants().add(e);
			}
			int sfid1 = rs.getInt("id");
			if(sfid!=sfid1)
			{
				sf.setDateStart(getDate(rs.getDate("dateStart")));
				sf.setDateEnd(getDate(rs.getDate("dateEnd")));
				sf.setFormation(f);
				sf.setFormation_id(f.getId());
				sf.setName(rs.getString("name"));
				sf.setId(sfid1);
			}
		}
		Logger logger = Logger
				.getLogger("steemploi.persistance.TableSessionsFormations");
		logger.log(Level.INFO, this.getClass().getName() + " : findById()");
		return sf;
	}

	public void delete(int id) throws SQLException {
		String sql = "delete from sessions_formations where id=?";
		PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();

	}

	public void save(SessionsFormations sf) throws SQLException {
		setFieldsNames(new String[] { "dateStart", "dateEnd", "formation_id",
				"name" });
		setTypes(new int[] { Types.DATE, Types.DATE, Types.INTEGER,
				Types.VARCHAR });
		if (sf.getId() == 0)
			insertInto(new Object[] { setDate(sf.getDateStart()),
					setDate(sf.getDateEnd()), sf.getFormation_id(),
					sf.getName() });
		else
			update(new Object[] { setDate(sf.getDateStart()),
					setDate(sf.getDateEnd()), sf.getFormation_id(),
					sf.getName() }, sf.getId());
		ExecuteUpdate();
	}

	public void saveUtilisateur(int sf_id, int etudiantId) throws SQLException {
		setFieldsNames(new String[] { "formation_id" });
		setTypes(new int[] { Types.INTEGER, Types.INTEGER });
		update(new Object[] { sf_id }, etudiant_id);
		ExecuteUpdate();
	}
}

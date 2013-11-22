package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.PreparedStatement;
import com.myapp.struts.events.Conge;

import steemploi.service.Echeance;
import steemploi.service.Etudiant;
import steemploi.service.Formation;
import steemploi.service.SessionsFormations;

/**
 * 
 * @author manuel
 */

public class TableEcheance extends UpdateInsertIntoTable {

	public TableEcheance() {
		super("echeances");
	}

	private int id;
	private String code;
	private String description;
	private String title;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void copyProp(Echeance e, ResultSet rs) throws SQLException {

		e.setId(rs.getInt("id"));
		e.setCode(rs.getString("code"));
		e.setDescription(rs.getString("description"));
		e.setTitle(rs.getString("title"));
		e.setDate(getDate(rs.getDate("date")));
	}

	public Echeance findById2(int id) throws SQLException {
		Echeance e = new Echeance();
		String query = "select * from echeances where id=" + id;
		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			copyProp(e, rs);
		}

		return e;
	}

	public Echeance findById(int id) throws SQLException {
		Echeance e = new Echeance();
		String query = "select * from echeances where id=" + id;
		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			copyProp(e, rs);
		}

		String query2 = "select e.*, ee.etudiant_id as etid, ef.formation_id as sfid from echeances as e left outer join echeances_etudiants as ee on e.id=ee.echeance_id left outer join echeances_formations as ef on e.id=ef.echeance_id where e.id="
				+ id + " order by e.id";
		PreparedStatement stmt2 = (PreparedStatement) conn
				.prepareStatement(query2);
		ResultSet rs2 = stmt2.executeQuery();
		int fid = -1;
		int etid = -1;

		while (rs2.next()) {
			if (rs2.getObject("etid") != null && rs2.getInt("etid") != etid) {
				Etudiant et = new Etudiant();
				et.setId(rs2.getInt("etid"));
				e.getEtudiants().add(et);
				etid = et.getId();
			}

			if (rs2.getObject("sfid") != null && rs2.getInt("sfid") != fid) {
				SessionsFormations sf = new SessionsFormations();
				sf.setId(rs2.getInt("sfid"));
				e.getFormations().add(sf);
			}
		}

		return e;
	}

	public ArrayList<Echeance> findByEtudiantId(int id) throws SQLException {
		ArrayList<Echeance> e = new ArrayList<Echeance>();

		String query = "select echeances.* from echeances inner join echeances_attendee on echeance.id=echeances.echeance_attendee.echeance_id where etudiant_id=?";

		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(query);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Echeance ech = new Echeance();
			copyProp(ech, rs);
			e.add(ech);
		}

		return e;
	}

	public ArrayList<Echeance> findByDate(Calendar date) throws SQLException {
		ArrayList<Echeance> echeances = new ArrayList<Echeance>();
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		max.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date
				.get(Calendar.DATE), 23, 59);
		min.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date
				.get(Calendar.DATE), 0, 0);

		String query = "select echeances.* from echeances where date>'"
				+ String.format("%tY-%tm-%td %tT", min, min, min, min)
				+ "' and date<'"
				+ String.format("%tY-%tm-%td %tT", max, max, max, max) + "'";

		PreparedStatement stmt = (PreparedStatement) conn
				.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Echeance ech = new Echeance();

			copyProp(ech, rs);

			echeances.add(ech);
		}

		return echeances;
	}

	public void update(Echeance e) throws SQLException {
		// Vérifier donnéees

		if (e.getId() == 0) {
			String sql = "insert into echeances (code, description, title, date) values (?,?,?,'"
					+ String.format("%tY-%tm-%td %tT", e.getDate(),
							e.getDate(), e.getDate(), e.getDate()) + "')";
			PreparedStatement stmt = (PreparedStatement) conn
					.prepareStatement(sql);
			stmt.setString(1, "C");
			stmt.setString(2, e.getDescription());
			stmt.setString(3, e.getTitle());
			stmt.execute();

		} else {
			String sql = "update echeances set title=?,code=?,description=?,date='"
					+ String.format("%tY-%tm-%td %tT", e.getDate(),
							e.getDate(), e.getDate(), e.getDate())
					+ "' WHERE id=" + e.getId();
			PreparedStatement stmt = (PreparedStatement) conn
					.prepareStatement(sql);
			stmt.setString(1, e.getTitle());
			stmt.setString(2, "C");
			stmt.setString(3, e.getDescription());
			stmt.execute();
		}
	}

	public void findByFormationDate(Map<String, Integer> counts,
			Map<String, ArrayList<Echeance>> list, int formation_id,
			Calendar dateMin, Calendar dateMax) throws SQLException {

		String query = "select date_format(e.date, '%X-%m-%d') as dateStr , e.* from echeances as e inner join echeances_formations as ef on ef.echeance_id=e.id where e.date>? and e.date<? and ef.formation_id=?";
		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);
		pstmt.setString(1, setDate(dateMin));
		pstmt.setString(2, setDate(dateMax));
		pstmt.setInt(3, formation_id);

		String queryCount = "select date_format(e.date, '%X-%m-%d') as dateStr , count(e.id) as count from echeances as e inner join echeances_formations as ef on ef.echeance_id=e.id where e.date>? and e.date<? and ef.formation_id=? group by date_format(e.date, '%X-%m-%d')";
		PreparedStatement pstmtCount = (PreparedStatement) conn
				.prepareStatement(queryCount);
		pstmtCount.setString(1, setDate(dateMin));
		pstmtCount.setString(2, setDate(dateMax));
		pstmtCount.setInt(3, formation_id);

		ResultSet rs = pstmt.executeQuery();
		ResultSet rsCount = pstmtCount.executeQuery();

		// Requête 1
		String dateStr = null;
		while (rs.next()) {
			dateStr = rs.getString("dateStr");
			Echeance newcode = new Echeance();
			copyProp(newcode, rs);

			if (list.get(dateStr) == null) {
				list.put(dateStr, new ArrayList<Echeance>());
			}

			list.get(dateStr).add(newcode);
		}

		// Requête 2

		while (rsCount.next()) {
			int i = rsCount.getInt("count");
			dateStr = rsCount.getString("dateStr");

			if (counts.get(dateStr) == null)
				counts.put(dateStr, new Integer(0));

			counts.put(dateStr, new Integer(counts.get(dateStr) + i));
		}
	}

	public void findByEtudiantDate(Map<String, Integer> counts,
			Map<String, ArrayList<Echeance>> list, int etudiant_id,
			Calendar dateMin, Calendar dateMax) throws SQLException {
		String query = "select date_format(e.date, '%X-%m-%d') as dateStr, e.id, e.title, e.description, e.date, e.code from echeances as e inner join echeances_etudiants as ee on ee.echeance_id=e.id where e.date>? and e.date<? and ee.etudiant_id=?";
		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);
		pstmt.setString(1, setDate(dateMin));
		pstmt.setString(2, setDate(dateMax));
		pstmt.setInt(3, etudiant_id);

		String queryCount = "select date_format(e.date, '%X-%m-%d') as dateStr, e.* from echeances as e inner join echeances_etudiants as ee on ee.echeance_id= e.id where e.date>? and e.date<? and ee.etudiant_id=? group by date_format(e.date, '%X-%m-%d')";
		PreparedStatement pstmtCount = (PreparedStatement) conn
				.prepareStatement(queryCount);
		pstmtCount.setString(1, setDate(dateMin));
		pstmtCount.setString(2, setDate(dateMax));
		pstmtCount.setInt(3, etudiant_id);

		ResultSet rs = pstmt.executeQuery();
		ResultSet rsCount = pstmtCount.executeQuery();
		String dateStr = "";

		while (rs.next()) {
			Echeance newcode = new Echeance();
			copyProp(newcode, rs);
			dateStr = rs.getString("dateStr");

			if (list.get(dateStr) == null) {
				list.put(dateStr, new ArrayList<Echeance>());
			}

			list.get(dateStr).add(newcode);
		}

		// Requête 2

		while (rsCount.next()) {
			int i = rsCount.getInt("id");
			dateStr = rsCount.getString("dateStr");

			if (counts.get(dateStr) == null)
				counts.put(dateStr, new Integer(0));

			counts.put(dateStr, new Integer(+1));
		}
	}

	public Echeance findById(int echeance_id, boolean getEtudForma)
			throws SQLException {
		if (!getEtudForma)
			return findById(echeance_id);

		Echeance echeance = new Echeance();

		String query = "select ec.id as id, ec.title as title, ec.description as description, ec.code as code,  ec.date as date, ee.id as ee_id, ef.id as ef_id, ee.etudiant_id as e_id, ef.formation_id as f_id from echeances as ec "
				+ "left outer join echeances_etudiants as ee on ec.id=ee.echeance_id "
				+ "left outer join echeances_formations as ef on ec.id=ef.echeance_id where ec.id=?";

		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);

		pstmt.setInt(1, echeance_id);

		ResultSet rs = pstmt.executeQuery();

		echeance = new Echeance();

		while (rs.next()) {
			if (rs.getObject("id") != null && rs.getInt("id") > 0)
				copyProp(echeance, rs);

			if (rs.getObject("f_id") != null) {
				int f_id = rs.getInt("f_id");
				SessionsFormations f = new SessionsFormations();
				f.setId(f_id);
				echeance.getFormations().add(f);
			}

			if (rs.getObject("e_id") != null) {
				int e_id = rs.getInt("e_id");
				Etudiant et = new Etudiant();
				et.setId(e_id);
				echeance.getEtudiants().add(et);
			}
		}

		return echeance;

	}

	public List<Echeance> findByDate(Calendar dateMin, Calendar dateMax)
			throws SQLException {
		List<Echeance> echeances = new ArrayList<Echeance>();

		String query = "select ec.*, ec.id as id ee.id as ee_id, ef.id as ef_id, ee.etudiant_id as e_id, ef.foramtion_id as f_id from echeances as ec "
				+ "join echeances_etudiants as ee on ec.id=ee.echeance_id "
				+ "join echeances_formations as ef on ec.id=ef.echeance_id where ec.date>? and ec.date<? "
				+ "order by ec.id";

		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);

		pstmt.setString(1, setDate(dateMin));
		pstmt.setString(2, setDate(dateMax));

		ResultSet rs = pstmt.executeQuery();

		Echeance echeance = new Echeance();
		int id = -1;
		while (rs.next()) {
			echeance = new Echeance();

			if (rs.getObject("id") != null && rs.getInt("id") != id) {
				id = rs.getInt(id);

				if (echeance.getId() != 0) {
					echeances.add(echeance);

				}

				echeance = new Echeance();

			}

			copyProp(echeance, rs);

			if (rs.getObject("f_id") != null) {
				int f_id = rs.getInt("f_id");
				SessionsFormations f = new SessionsFormations();
				f.setId(f_id);
				echeance.getFormations().add(f);
			}

			if (rs.getObject("e_id") != null) {
				int e_id = rs.getInt("e_id");
				Etudiant e = new Etudiant();
				e.setId(e_id);
				echeance.getEtudiants().add(e);
			}
		}

		return echeances;

	}

	public int save(Echeance e) throws Exception {

		int id = e.getId();

		Echeance ePrim = findById(e.getId());
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2;

		if (ePrim == null || ePrim.getId() == 0) {
			e.setId(0);
			update(e);
			String query2 = "SELECT LAST_INSERT_ID() as id";
			pstmt2 = (PreparedStatement) conn.prepareStatement(query2);
			ResultSet rs = pstmt2.executeQuery();
			rs.next();
			id = rs.getInt("id");

			if (id == 0)
				throw new Exception("ID == 0");

		} else {
			update(e);
		}

		String queryIE = "insert into echeances_etudiants "
				+ "(echeance_id, etudiant_id) values(?,?);";
		String queryIF = "insert into echeances_formations(echeance_id, formation_id) "
				+ " values(?,?);";
		String queryDE = "insert into echeances_etudiants "
				+ " (echeance_id, etudiant_id) values(?,?);";
		String queryDF = "delete from echeances_formations where echeance_id=? and formation_id=?;";

		PreparedStatement pstmtIE = (PreparedStatement) conn
				.prepareStatement(queryIE);
		PreparedStatement pstmtIF = (PreparedStatement) conn
				.prepareStatement(queryIF);
		PreparedStatement pstmtDE = (PreparedStatement) conn
				.prepareStatement(queryDE);
		PreparedStatement pstmtDF = (PreparedStatement) conn
				.prepareStatement(queryDF);

		for (Etudiant et : e.getEtudiants()) {
			if (!ePrim.getEtudiants().contains(et)) {
				pstmtIE.setInt(1, id);
				pstmtIE.setInt(2, et.getId());
				pstmtIE.addBatch();

			}
		}

		for (Etudiant et : ePrim.getEtudiants()) {
			if (!e.getEtudiants().contains(et)) {
				pstmtDE.setInt(1, id);
				pstmtDE.setInt(2, et.getId());
				pstmtDE.addBatch();
			}

		}

		for (SessionsFormations f : e.getFormations()) {
			if (!ePrim.getFormations().contains(f)) {
				pstmtIF.setInt(1, id);
				pstmtIF.setInt(2, f.getId());
				pstmtIF.addBatch();

			}
		}

		for (SessionsFormations f : ePrim.getFormations()) {
			if (!e.getFormations().contains(f)) {
				pstmtDF.setInt(1, id);
				pstmtDF.setInt(2, f.getId());
				pstmtDF.addBatch();
			}

		}

		pstmtIE.executeBatch();
		pstmtIF.executeBatch();
		pstmtDE.executeBatch();
		pstmtDF.executeBatch();

		return id;
	}

	public void findByFormationEtudiantsDate(Map<String, Integer> counts,
			Map<String, ArrayList<Echeance>> list, int etudiant_id,
			Calendar dateMin, Calendar dateMax) throws SQLException {
		String query = "select date_format(e.date, '%x-%m-%d') as dateStr, e.* from echeances as e inner join echeances_formations as ef on ef.echeance_id=e.id inner join sessions_formations as sf on sf.id=ef.formation_id inner join etudiant"
				+ "s as et on et.sessions_formations_id=sf.id where e.date>? and e.date<? and et.id=?;";
		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);

		pstmt.setString(1, setDate(dateMin));
		pstmt.setString(2, setDate(dateMax));
		pstmt.setInt(3, etudiant_id);

		String queryCount = "select date_format(e.date, '%x-%m-%d') as dateStr, e.* from echeances as e inner join echeances_formations as ef on ef.echeance_id=e.id inner join sessions_formations as sf on sf.id=ef.formation_id inner join etudiant"
				+ "s as et on et.sessions_formations_id=sf.id where e.date>? and e.date<? and et.id=? group by date_format(e.date, '%X-%m-%d');";
		PreparedStatement pstmtCount = (PreparedStatement) conn
				.prepareStatement(queryCount);

		pstmtCount.setString(1, setDate(dateMin));
		pstmtCount.setString(2, setDate(dateMax));
		pstmtCount.setInt(3, etudiant_id);

		ResultSet rs = pstmt.executeQuery();
		ResultSet rsCount = pstmtCount.executeQuery();

		Echeance echeance = new Echeance();
		int id = -1;
		while (rs.next()) {
			echeance = new Echeance();
			copyProp(echeance, rs);
			String dateStr = rs.getString("dateStr");

			if (list.get(dateStr) == null)
				list.put(dateStr, new ArrayList<Echeance>());

			list.get(dateStr).add(echeance);
		}

		while (rsCount.next()) {
			int i = rsCount.getInt("id");
			String dateStr = rsCount.getString("dateStr");

			if (counts.get(dateStr) == null)
				counts.put(dateStr, new Integer(0));

			counts.put(dateStr, new Integer(counts.get(dateStr) + 1));
		}
	}

	public boolean delete(int id) throws SQLException {
		String query1 = "delete from echeances where id=" + id;
		String query2 = "delete from echeances_formations where echeance_id="
				+ id;
		String query3 = "delete from echeances_etudiants where echeance_id="
				+ id;
		PreparedStatement p = (PreparedStatement) conn.prepareStatement(query2);
		p.execute();
		p = (PreparedStatement) conn.prepareStatement(query2);
		p.execute();
		p = (PreparedStatement) conn.prepareStatement(query1);
		p.execute();
		return true;
	}

	public enum ViewEcheances {
		Formation, TousEtudiants, Etudiant
	};

	/***
	 * (A) -- Pour une formation
	 * 
	 * select e.id as echeance_id, e.title, e.description, e.code, e.date as
	 * echeance_date from echeances as e inner join echeances_formations as ef
	 * on ef.echeance_id=e.id where ef.formation_id=1
	 * 
	 * (A + B) -- Pour un étudiant d'une formation
	 * 
	 * select e.id as echeance_id, e.title, e.description, e.code, e.date as
	 * echeance_date from echeances as e inner join echeances_etudiants as ee on
	 * ee.echeance_id=e.id where ee.etudiant_id=1;
	 * 
	 * (A + C) C -- Pour tous les étudiants d'une formation
	 * 
	 * select e.id as echeance_id, e.title, e.description, e.code, e.date as
	 * echeance_date from echeances as e inner join echeances_etudiants as ee on
	 * e.id=ee.echeance_id where ee.etudiant_id in (select et.id from etudiants
	 * as et where et.sessions_formations_id=1);
	 * 
	 * 
	 * A view_formation A+C view_formation_all_etudiants A+C
	 * view_formation_etudiant
	 * 
	 * 
	 * @param dateMin
	 * @param dateMax
	 * @param viewEcheance
	 * @return List<Echeance>
	 * @throws SQLException
	 */
	public Map<String, ArrayList<Echeance>> findByDate(Calendar dateMin,
			Calendar dateMax, ViewEcheances viewEcheance, int formation_id,
			int etudiant_id) throws SQLException {
		dateMin.setTime(new Date(0, 0, 0));
		dateMax.setTime(new Date(23, 59, 59));
		// / (A) A -- Pour une formation
		String queryA = "select e.id, e.title, e.description, e.code, e.date, date_format(e.date, '%X-%m-%d') as dateStr from echeances as e  inner join echeances_formations as ef on ef.echeance_id=e.id where ef.formation_id=? AND e.date>? AND e.date>?";
		// / (A + B) B -- Pour un étudiant d'une formation
		String queryB = "select e.id, e.title, e.description, e.code, e.date, date_format(e.date, '%X-%m-%d') as dateStr from echeances as e inner join echeances_etudiants as ee on ee.echeance_id=e.id where ee.etudiant_id=? AND e.date>? AND e.date>?";
		// / (A + C) C -- Pour tous les étudiants d'une formation
		String queryC = "select e.id, e.title, e.description, e.code, e.date, date_format(e.date, '%X-%m-%d') as dateStr from echeances as e inner join echeances_etudiants as ee on e.id=ee.echeance_id where ee.etudiant_id in (select et.id from etudiants as et where et.sessions_formations_id=?) AND e.date>? AND e.date>?";
		Map<String, ArrayList<Echeance>> echeances = new HashMap<String, ArrayList<Echeance>>();

		String q;
		PreparedStatement pstmt = null;

		switch (viewEcheance) {
		case Formation:
			q = queryA;
			pstmt = (PreparedStatement) conn.prepareStatement(q);
			pstmt.setInt(1, formation_id);
			pstmt.setString(2, setDate(dateMin));
			pstmt.setString(3, setDate(dateMax));
			break;
		case Etudiant:
			q = queryA + " UNION " + queryB;
			pstmt = (PreparedStatement) conn.prepareStatement(q);
			pstmt.setInt(1, formation_id);
			pstmt.setString(2, setDate(dateMin));
			pstmt.setString(3, setDate(dateMax));
			pstmt.setInt(4, etudiant_id);
			pstmt.setString(5, setDate(dateMin));
			pstmt.setString(6, setDate(dateMax));

			break;
		case TousEtudiants:
			q = queryA + " UNION " + queryC;
			pstmt = (PreparedStatement) conn.prepareStatement(q);
			pstmt.setInt(1, formation_id);
			pstmt.setString(2, setDate(dateMin));
			pstmt.setString(3, setDate(dateMax));
			pstmt.setInt(4, formation_id);
			pstmt.setString(5, setDate(dateMin));
			pstmt.setString(6, setDate(dateMax));
			break;
		}

		ResultSet rs = pstmt.executeQuery();

		Echeance e = null;

		while (rs.next()) {
			String date = rs.getString("dateStr");

			if (echeances.get(date) == null)
				echeances.put(date, new ArrayList<Echeance>());

			ArrayList<Echeance> echAL = echeances.get(date);

			e = new Echeance();

			copyProp(e, rs);

			if (!echAL.contains(e))
				echAL.add(e);
		}

		rs.close();

		return echeances;
	}

	public List<Echeance> findAll() throws SQLException {
		List<Echeance> echeances = new ArrayList<Echeance>();
		String query = "select * from echeances order by date";
		PreparedStatement pstmt = (PreparedStatement) conn
				.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			Echeance echeance = new Echeance();
			copyProp(echeance, rs);
			echeances.add(echeance);
		}

		return echeances;
	}
	/*
	 * public void findByEtudiantDate(Map<String, ArrayList<Echeance>>
	 * echeances, Etudiant etudiant,Calendar dateMin, Calendar dateMax) {
	 * dateMin.setTime(new Date(0, 0, 0)); dateMax.setTime(new Date(23, 59,
	 * 59));
	 * 
	 * String q = select e.* from echeances inner join echeances_etudiants on
	 * e.id=ee.etudiant_id where ee.; PreparedStatement pstmt = null; ResultSet
	 * rs = pstmt.executeQuery();
	 * 
	 * Echeance e=null;
	 * 
	 * while(rs.next()) { String date = rs.getString("dateStr");
	 * if(echeances.get(date)==null) echeances.put(date, new
	 * ArrayList<Echeance>()); ArrayList<Echeance> echAL = echeances.get(date);
	 * e = new Echeance(); copyProp(e, rs); echAL.add(e); }
	 * 
	 * rs.close();
	 * 
	 * return echeances; }
	 */
}

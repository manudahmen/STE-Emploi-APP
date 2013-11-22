package steemploi.persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ArrayList;

import steemploi.service.Etudiant;
import steemploi.service.SessionsFormations;
import steemploi.service.TacheStatItem;
/***
 * Donne les statistiques quand au nombre de cv envoyés, les réponses,
 * positive ou négative, la relance téléphonique, les entretiens, les
 * entretien techniques, les contrats signés.
 * 
 * @author Manuel Dahmen
 *
 */
public class TableStat extends UpdateInsertIntoTable {

	public TableStat() {
		super("stats");
	}
	/***
	 * 
	 * @param sf Session de formation pour lesquelles on demande les statistiques
	 * @return Statistiques par étudiant
	 * @throws SQLException 
	 */
	public HashMap<Etudiant,ArrayList<TacheStatItem>> getStats(SessionsFormations sf, Calendar date) throws SQLException
	{
		
		HashMap<Etudiant,ArrayList<TacheStatItem>> stats = new HashMap<Etudiant,ArrayList<TacheStatItem>>();
		
//		String sql = "select e.id as id, e.user_id, u.nom, u.prenom, ct.code as ctcode, count(ct.code) as count " +
//				"from taches as t inner join codes_categories_taches as ct on ct.id=t.code " +
//				"inner join etudiants as e on t.etudiant_id=e.id " +
//				"inner join sessions_formations as sf on e.sessions_formations_id=sf.id and sf.id=? " +
//				"inner join utilisateurs as u on e.user_id=u.id " +
//				"group by id, ctcode;";
		String sql = "select u.prenom, u.nom, e.id as id, ct.code as ctcode, count(ct.code) as count " +
				"from taches as t inner join codes_categories_taches as ct on ct.id=t.code " +
				"inner join etudiants as e on t.etudiant_id=e.id "+ 
				"inner join utilisateurs as u on e.user_id=u.id " +
				"where e.sessions_formations_id=? and t.dateCompleted<? " +
				"group by id, ctcode;";
				System.out.println(sql);
				System.out.println(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, sf.getId());
		pstmt.setString(2, setDate(date));
		ResultSet rs = pstmt.executeQuery();
		
		int etid_ =-1;
		Etudiant et = null;
		TableEtudiants te = new TableEtudiants();
		while(rs.next())
		{
			int etid = rs.getInt("id");
			if(etid_!=etid)
			{
				etid_=etid;
				et = new Etudiant();
				et.setNom(rs.getString("nom"));
				et.setPrenom(rs.getString("prenom"));
				et.setId(etid);
				stats.put(et, new ArrayList<TacheStatItem>());
			}
			TacheStatItem tsi = new TacheStatItem(rs.getString("ctcode"), rs.getInt("count"));
			
			stats.get(et).add(tsi);
		}
		
		return stats;
		
	}
}

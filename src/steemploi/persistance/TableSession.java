/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;

/**
 *
 * @author manuel
 */

class TableSession extends Table {

		public TableSession() {
			super();
		}

		public long insert(long random, int user_id) {
			ResultSet rs = null;

			try {
					long millis = Calendar.getInstance().getTimeInMillis();
					String query = "insert into sessions(user_id, session, dateStart) values('" + user_id + "', " + random + ", " + millis + ")";
					PreparedStatement stmt;
					stmt = (PreparedStatement) conn.prepareStatement(query);
					stmt.execute();
					query = "select session from sessions where user_id=" + user_id + " and dateStart=" + millis;
					stmt = (PreparedStatement) conn.prepareStatement(query);
					rs = stmt.executeQuery();

					if (rs.next()) {
							return rs.getLong("session");
						}

				} catch (SQLException ex) {
					Logger.getLogger(TableSession.class.getName()).log(Level.SEVERE, null, ex);
					return -1;
				}

			return -1;
		}
	}

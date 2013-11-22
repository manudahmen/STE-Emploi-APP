/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package steemploi.persistance;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.sql.Connection;

import java.util.logging.*;
/**
 *
 * @author manuel
 */

public class Table {
		protected Connection conn = null;
		public Table() {
			try {
					DBConnection dbconn = DBConnection.getInstance();
					conn = dbconn.getConnection();

				} catch (Exception e) {
					Logger logger = Logger.getLogger("steemploi.persistance.Table");
					logger.log(Level.SEVERE, "Echec de la connection DB", e);
				}
		}

		public String setDate(Calendar cal) {
			//return String.format("%tY-%tm-%td %tT", cal,cal,cal,cal);
			//return String.format("%1$tY-%1$tm-%1$td", cal);

			if (cal == null) cal = Calendar.getInstance();

			return String.format("%1$TF", cal);

		}

		public Calendar getDate(java.sql.Date date) {
			Calendar c = Calendar.getInstance();

			if (date != null)
				c.setTime(date);

			return c;
		}

	}

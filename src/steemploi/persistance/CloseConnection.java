package steemploi.persistance;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class CloseConnection implements ServletRequestListener {

	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void requestInitialized(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		
	}
/*
	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		// 
		boolean test = false;
		try {
			DBConnection.getInstance();
			Statement stmt = (Statement) DBConnection.getConnection()
					.createStatement();
			stmt.execute("select 'test' from utilisateurs;");
			test = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!test) {
			DBConnection.dbconnection = null;
			DBConnection.ds = null;
			try {
				DBConnection.getInstance();
			} catch (Exception e) {
				// 
				e.printStackTrace();
			}
			try {
				DBConnection.getConnection();
			} catch (Exception e) {
				// 
				e.printStackTrace();
			}
		}
		try {
			event.getServletRequest().setAttribute("dbconnection",
					DBConnection.getInstance());
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
		try {
			event.getServletRequest().setAttribute("connection",
					DBConnection.getConnection());
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
	}
*/
}

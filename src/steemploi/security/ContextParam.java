package steemploi.security;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextParam implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute("Locale", "fr");
		Locale.setDefault(Locale.FRANCE);
		Logger logger = Logger.getLogger("security.ContextParam");
		logger.log(Level.INFO, "Locale = fr");
	}

}

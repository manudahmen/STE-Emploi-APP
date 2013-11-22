package steemploi.persistance;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

/**
 *
 * @author manuel dahmen
 */
public class DBConnection {

    public static DataSource ds; // la source de donn�es
    private static DBConnection dbconnection;
    protected static Connection conn;
    String connectionString;
    static String user;
    static String password;
    static String host;
    static String dbname;
    static String port;

    private DBConnection() {
    }

    public static DBConnection getInstance() throws Exception {
        if (DBConnection.dbconnection == null) {
            DBConnection.dbconnection = new DBConnection();
            Logger logger = Logger
                    .getLogger("steemploi.persistance.DBConnection");
            logger.log(Level.INFO,
                    "DBConnection.dbconnection = new DBConnection();");

            init();
        }

        return DBConnection.dbconnection;
    }

    public Connection getConnection() throws Exception {

        if (DBConnection.dbconnection == null) {
            throw new Exception("Singleton non initialis�");
        }

        if (ds != null) {
            if (conn == null || !conn.isValid(0)) {
                conn = ds.getConnection();
            }
            return conn;
        } else {
            init();
        }
        try {
        } catch (Exception ex) {
            Logger logger = Logger
                    .getLogger("steemploi.persistance.DBConnection");
            logger.log(Level.SEVERE,
                    "Erreur lors du chargement du dataSource\n"
                    + getMessage(ex));
            ex.printStackTrace();
            throw new Exception("Erreur lors du chargement du dataSource");
        }

        return conn;



    }

    public void close() throws Exception {
        if (conn != null) {
            try {
                conn.close();
                Logger logger = Logger
                        .getLogger("steemploi.persistance.DBConnection");
                logger.log(Level.INFO, "Déconnection DB réussie");

            } catch (Exception e) {
                Logger logger = Logger
                        .getLogger("steemploi.persistance.DBConnection");
                logger.log(Level.SEVERE, "Echec de la déconnection DB");
                throw new Exception("Echec lors de la déconection");
            }
        }
    }

    private static void init() throws Exception {
        Context env = null;
        DataSource pool1 = null;
        Logger logger = Logger
                .getLogger("steemploi.persistance.DBConnection");
        try {
            try {
                env = (Context) new InitialContext()
                        .lookup("java:comp/env");
                logger.log(Level.INFO, env.getNameInNamespace());
            } catch (NamingException ne) {
                logger.log(Level.SEVERE, ne.getExplanation()
                        + " : explanation at First lookup");
                logger.log(Level.SEVERE, "ResolvedName : "
                        + writeName(ne.getResolvedName()));
                logger.log(Level.SEVERE, "ResolvedObj Class : "
                        + (ne.getResolvedObj() != null ? ne.getResolvedObj()
                        .getClass().getName() : "null"));
                logger.log(Level.SEVERE, "RemainingName : "
                        + writeName(ne.getRemainingName()));
                throw new ServletException(ne.getMessage()
                        + " \n Cause:Naming Exception");
            }
            if (env == null) {
                throw new ServletException("env==null");
            }
            logger.log(Level.INFO, "env class: " + env.getClass().getName());
            try {
                NamingEnumeration<NameClassPair> ne = env.list("/jdbc");
                logger.log(Level.INFO, "Liste des jdbc:\n");
                while (ne.hasMoreElements()) {
                    NameClassPair ncp = ne.next();
                    if (ncp != null && ncp.getClassName() != null) {
                        logger.log(Level.INFO, "Name= \n" + ncp.getClassName());
                    }
                }
            } catch (NullPointerException ex1) {
                //throw new ServletException(ex1.getMessage()
                //		+ " \n Cause: NullPointerException");
            } catch (Exception ex1) {
            }
            try {





                //Context initContext = new InitialContext();
                //Context envContext = (Context) initContext.lookup("java:comp/env");
                //pool1 = (javax.sql.DataSource) envContext.lookup("jdbc/steemploi");

            pool1 = (javax.sql.DataSource) new InitialContext().lookup("jdbc/steemploi");


                if (pool1 == null) {
                    throw new ServletException("unknown DataSource");
                } else {
                    DBConnection.ds = pool1;
                    logger.log(Level.SEVERE, "Conn OK");
                }
            } catch (NamingException ne) {
                logger.log(Level.SEVERE, ne.getExplanation()
                        + " : explanation at Second lookup");
                logger.log(Level.SEVERE, "ResolvedName : "
                        + writeName(ne.getResolvedName()));
                logger.log(Level.SEVERE, "ResolvedObj Class : "
                        + (ne.getResolvedObj() != null ? ne.getResolvedObj()
                        .getClass().getName() : "null"));
                logger.log(Level.SEVERE, "RemainingName : "
                        + writeName(ne.getRemainingName()));
                logger.log(Level.SEVERE, "\n\n\n\n----\nINFO\n----" + getMessage(ne));
                throw new ServletException(ne.getMessage()
                        + " \n Cause:Naming Exception");
            }

        } catch (Exception ex1) {
            throw new ServletException(ex1.getMessage()
                    + " \n Cause: NullPointerException");
        }
    }

    protected static String writeName(Name name) {
        if (name == null) {
            return "NAME://NULL";
        }
        String str = "NAME://";
        for (int i = 0; i < name.size(); i++) {
            str += "/" + name.get(i);
        }
        return str;
    }

    public static String getMessage(Exception e) {

        String st = "ERREUR DANS INITIALISATION DU CONNECTION POOL\n\n";
        if (e != null) {
            st = "Exception: " + e.getMessage() + "\n\n";
            if (e.getCause() != null) {
                st += "Caused: " + e.getCause().getMessage();
            }
            if (e.getStackTrace() != null) {
                for (StackTraceElement el : e.getStackTrace()) {
                    st += "\n" + e.getMessage() + "\nClass: "
                            + el.getClassName() + "::" + el.getMethodName()
                            + "::" + el.getLineNumber() + "\n";
                }
            }
        }
        return st;
    }
}

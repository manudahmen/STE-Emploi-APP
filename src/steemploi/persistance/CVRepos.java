package steemploi.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import java.util.logging.*;

import javax.naming.InitialContext;

import steemploi.service.CV;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

public class CVRepos extends UpdateInsertIntoTable
{
	private static final String reposPath;
	private static final String repospathseparator;
	public CVRepos() {
		super("cvs");
		setFieldsNames(new String [] {"filename","filenameOrig", "name", "version", "etudiant_id", "ext", "type"});
	}
	static {
/*		Properties props = new Properties();
		Logger logger = Logger.getLogger("steemploi.persistance.CVRepos");
		try {
			props.load(new FileReader("cvrepos.properties"));
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Impossible de retrouver le repository de cvs", e);
			e.printStackTrace();
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Impossible de retrouver le repository de cvs", e);
			e.printStackTrace();
		}
		reposPath = props.getProperty("reposPath"); 
		repospathseparator = props.getProperty("repospathseparator");
*/
		reposPath = "c:\\cvrepos"; 
		repospathseparator = "\\";
	}
	public CV findByEtudiantId(int etudiantId) throws SQLException {
		CV cv = null;
		String sql = "select * from cvs where etudiant_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, etudiantId);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			cv = new CV();
			copyProps(rs, cv);
		}
		return cv;
	}
	private void copyProps(ResultSet rs, CV cv) throws SQLException {
		cv.setId(rs.getInt("id"));
		cv.setFilenameFS(rs.getString("filename"));
		cv.setFilenameOriginal(rs.getString("filenameOrig"));
		cv.setName(rs.getString("name"));
		cv.setVersion(rs.getString("version"));
		cv.setEtudiantId(rs.getInt("etudiant_id"));
		cv.setExt(rs.getString("ext"));
		//cv.setType(cv.getExt());
		
	}
	public void addCV(CV cv, int etudiant_id) throws SQLException 
	{
		/// 1 ) Copie du nouveau fichier
		Logger logger = Logger.getLogger("steemploi.persistance.CVRepos");
		int i=1;
		cv.setFilenameFS(""+etudiant_id+"_"+i+"."+cv.getExt());
		File file=new File(reposPath+repospathseparator+cv.getFilenameFS());
		while(file.exists())
		{
			cv.setFilenameFS(""+etudiant_id+"_"+i+"."+cv.getExt());
			file=new File(reposPath+repospathseparator+cv.getFilenameFS());
			i++;
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
				fos.write(cv.getFileData());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Exception FileOutputStream", e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Exception FileOutputStream", e);
			e.printStackTrace();
		}
		finally
		{
			try {
				if(fos!=null)
					fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.log(Level.SEVERE, "Exception FileOutputStream", e);
				e.printStackTrace();
			}
		}
		CV cvOld=null;
		try {
			cvOld = findByEtudiantId(etudiant_id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Exception findbyEtudiantId", e1);
			e1.printStackTrace();
		}
		/// 2 Enregistrement en db
		if(cv.getId()==0)
		{
				insertInto(new Object[] {cv.getFilenameFS(), cv.getFilenameOriginal(), cv.getName(), "1.0", etudiant_id, cv.getExt(), cv.getExt()});
				ExecuteUpdate();
		}
		/// 3 Effacement de l'ancien et du record en db
		deleteCV(cvOld, etudiant_id);
	}
	public void deleteCV(CV cvOld, int etudiant_id) throws SQLException
	{
		if(cvOld!=null)
		{
			File f = new File(reposPath+repospathseparator+ cvOld.getFilenameFS());
			if(f.exists() && f.canWrite()) f.delete();
			delete(cvOld.getId(), etudiant_id);
		}
	}
	private void delete(int id, int etudiant_id) throws SQLException {
		String sql = "delete from cvs where id=? and etudiant_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.setInt(2, etudiant_id);
		pstmt.executeUpdate();

	
	}
	public static String getReposPath() {
		return reposPath;
	}
	public static String getRepospathseparator() {
		return repospathseparator;
	}
	
	
}

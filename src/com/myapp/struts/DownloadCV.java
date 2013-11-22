package com.myapp.struts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.CVRepos;
import steemploi.service.CV;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class DownloadCV extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2390353707973026413L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Logger logger = Logger.getLogger("com.myapp.struts.DownloadCV");
		logger.entering("DownloadCV.do", "execute()");
		logger.info("PostCV.do méthode execute()");
		// TODO Auto-generated method stub
		Utilisateur user = null;

		if (request.getSession(false) != null
				&& request.getSession(false).getAttribute("user") != null
				&& request.getSession(false).getAttribute("user") instanceof Utilisateur) {
			user = (Utilisateur) request.getSession(false).getAttribute("user");

		} else {

			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		}

		if (!user.getType().equals(TypeUtilisateur.ETUDIANT)) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
		try {
		CVRepos cvrepos = new CVRepos();
		String path = CVRepos.getReposPath()+CVRepos.getRepospathseparator();
		CV cv = cvrepos.findByEtudiantId(user.getEtudiant().getId());
		
		path += cv.getFilenameFS();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachement; filename="+cv.getFilenameOriginal());
        ServletOutputStream os = response.getOutputStream();
        
        FileInputStream fis = new FileInputStream(new File(path));
        byte[] b = new byte[1024];
        int nbRead = 0;
        int sum = 0;
        nbRead = fis.read(b);
        while (nbRead > 0) {
            os.write(b, 0, nbRead);
        	if (nbRead > 0) {
             
                sum += nbRead;
            }
            nbRead = fis.read(b);
            
        }
        response.setContentLength(sum);
        os.flush();
        fis.close();
		os.close();
		}
		catch(Exception e)
		{
			logger.log(Level.WARNING, "Erreur lors du transfert du fichier",e );
			e.printStackTrace();
			//request.getRequestDispatcher("/error.jsp").forward(request, response);
			//response.getWriter().print("Erreur lors du transfert de fichier. <a href='/ste-emploi/index_agenda?page=cvlettres'>Retour à la page CV");
		}
		//request.getRequestDispatcher("/index_agenda.jsp?page=cvlettres").forward(request, response);
	}
}

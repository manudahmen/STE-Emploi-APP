package com.myapp.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.logging.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import steemploi.persistance.CVRepos;
import steemploi.service.CV;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class PostCV extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Logger logger = Logger.getLogger("com.myapp.struts.PostCV");
		logger.entering("PostCV", "execute()");
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
			return mapping.findForward("error");
		}

		if (!user.getType().equals(TypeUtilisateur.ETUDIANT)) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return mapping.findForward("error");
		}
		try {
			request.getParameter("cv");
			CV cv = (CV) form;

			// Process the FormFile
			FormFile myFile = cv.getCvFile();
			String contentType = myFile.getContentType();
			String fileName = myFile.getFileName();
			int fileSize = myFile.getFileSize();
			byte[] fileData = myFile.getFileData();
			cv.setFilenameOriginal(fileName);
			cv.setOriginalPath("");
			cv.setExt(cv.getFilenameOriginal().substring(cv.getFilenameOriginal().lastIndexOf(".")+1));
			cv.setFileData(fileData);
			logger.info("Data CV Post: fn orig: " + cv.getFilenameOriginal()+" | orig path: " + cv.getOriginalPath()+ " ext: "+cv.getExt());
			cv.setId(0);
			new CVRepos().addCV(cv, user.getEtudiant().getId());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans PostCV", e);
			return mapping.findForward("error");

		}
		return mapping.findForward("success");
	}

}

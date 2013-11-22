package steemploi.persistance;

import javax.servlet.http.HttpServletRequest;

import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class TableWithHttpRequest extends Table {

		private HttpServletRequest request = null;
		private String userField;
		protected int user_id = 0;
		protected int etudiant_id = 0;
		protected int formateur_id = 0;
		protected TypeUtilisateur userType;

		public TableWithHttpRequest() {
			super();
		}

		public int getEtudiant_id() {
			return etudiant_id;
		}

		public int getFormateur_id() {
			return formateur_id;
		}

		public HttpServletRequest getRequest() {
			return request;
		}

		public int getUser_id() {
			return user_id;
		}

		public String getUserField() {
			return userField;
		}

		public TypeUtilisateur getUserType() {
			return userType;
		}

		public void setEtudiant_id(int etudiant_id) {
			this.etudiant_id = etudiant_id;
		}

		public void setFormateur_id(int formateur_id) {
			this.formateur_id = formateur_id;
		}

		public void setRequest(HttpServletRequest request) {
			this.request = request;
			Utilisateur user = (Utilisateur) request.getSession(false).getAttribute("user");
			user_id = user.getId();
			userType = user.getType();
			formateur_id = user.getType().equals(TypeUtilisateur.FORMATEUR) ? user.getFormateur().getId() : 0;
			etudiant_id = user.getType().equals(TypeUtilisateur.ETUDIANT) ? user.getEtudiant().getId() : 0;
		}

		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}

		public void setUserField(String userField) {
			this.userField = userField;
		}

		public void setUserType(TypeUtilisateur userType) {
			this.userType = userType;
		}


	}

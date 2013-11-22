package steemploi.service;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.myapp.struts.EditEntreprise;

/**
 * 
 * @author Manuel Dahmen
 */

public class Entreprise extends ValidatorForm {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		private String nom;
		private String rue;
		private String numero;
		private String boite;
		private String codepostal;
		private String ville;
		private String pays;
		private String tel;
		private String gsm;
		private String email;
		private String url;
		private int owner;
		private String secteur;
		private String infocomplementaires;
		private String commentaires;
		private TacheStats stats;
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getRue() {
			return rue;
		}

		public void setRue(String rue) {
			this.rue = rue;
		}

		public String getNumero() {
			return numero;
		}

		public void setNumero(String numero) {
			this.numero = numero;
		}

		public String getBoite() {
			return boite;
		}

		public void setBoite(String boite) {
			this.boite = boite;
		}

		public String getCodepostal() {
			return codepostal;
		}

		public void setCodepostal(String codepostal) {
			this.codepostal = codepostal;
		}

		public String getVille() {
			return ville;
		}

		public void setVille(String ville) {
			this.ville = ville;
		}

		public String getPays() {
			return pays;
		}

		public void setPays(String pays) {
			this.pays = pays;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getGsm() {
			return gsm;
		}

		public void setGsm(String gsm) {
			this.gsm = gsm;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getOwner() {
			// TODO Auto-generated method stub
			return owner;
		}

		public void setOwner(int owner) {
			this.owner = owner;
		}

		public String getSecteur() {
			return secteur;
		}

		public void setSecteur(String secteur) {
			this.secteur = secteur;
		}

		public String getCommentaires() {
			return commentaires;
		}

		public void setCommentaires(String commentaires) {
			this.commentaires = commentaires;
		}

		public String getInfocomplementaires() {
			return infocomplementaires;
		}

		public void setInfocomplementaires(String infocomplementaires) {
			this.infocomplementaires = infocomplementaires;
		}

		public TacheStats getStats() {
			return stats;
		}

		public void setStats(TacheStats stats) {
			this.stats = stats;
		}

		@Override
		public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
			// TODO Auto-generated method stub
			ActionErrors errors = super.validate(arg0, arg1);
			return errors;

		}

	}

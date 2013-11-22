/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package steemploi.service;

import java.util.Date;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author manuel
 */

public class Utilisateur extends ActionForm {
		/**
	 * 
	 */
	private static final long serialVersionUID = -7027449497623379960L;
		private int id;
		private boolean active;
		private String nom;
		private String prenom;
		private String rue;
		private String numero;
		private String boite;
		private String codepostal;
		private String ville;
		private String pays;
		private String tel;
		private String gsm;
		private String email;
		private Date ddn;
		private int ddn_j;
		private int ddn_m;
		private int ddn_a;
		private int formation_id;
		private int user_id;
		private SessionsFormations sf = new SessionsFormations();
		private TypeUtilisateur type;
		private String username;
		private String password;
		private String usertype;
		private Etudiant etudiant;
		private Formateur formateur;


		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getUser_id() {
			return user_id;
		}

		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}




		public Date getDdn() {
			return ddn;
		}

		public void setDdn(Date ddn) {
			this.ddn = ddn;
		}

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

		public String getPrenom() {
			return prenom;
		}

		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}

		public int getFormation_id() {
			return formation_id;
		}

		public void setFormation_id(int formation_id) {
			this.formation_id = formation_id;
		}

		public int compareTo(Object o) {
			if (o instanceof Etudiant && ((Etudiant)o).getId() == getId())
				return 0;
			else
				return -1;

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

		public int getDdn_j() {
			return ddn_j;
		}

		public void setDdn_j(int ddn_j) {
			this.ddn_j = ddn_j;
		}

		public int getDdn_m() {
			return ddn_m;
		}

		public void setDdn_m(int ddn_m) {
			this.ddn_m = ddn_m;
		}

		public int getDdn_a() {
			return ddn_a;
		}

		public void setDdn_a(int ddn_a) {
			this.ddn_a = ddn_a;
		}

		public TypeUtilisateur getType() {
			return type;
		}

		public void setType(TypeUtilisateur type) {
			this.type = type;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUsertype() {
			return usertype;
		}

		public void setUsertype(String usertype) {
			this.usertype = usertype;
		}

		public Formateur getFormateur() {
			return formateur;
		}

		public void setFormateur(Formateur formateur) {
			this.formateur = formateur;
		}

		public Etudiant getEtudiant() {
			return etudiant;
		}

		public void setEtudiant(Etudiant etudiant) {
			this.etudiant = etudiant;
		}

		public String getTypeString() {
			return type == TypeUtilisateur.FORMATEUR ? "formateur/formatrice" : "utilisateur/utilisatrice";
		}

		public boolean getActive() {
			return active;
		}

		public void setActive(boolean b) {
			this.active = b;
		}

	}

package steemploi.service;

/**
 *  Classe Attendee
 * SessionsFormations, utilisateur, etudiant, formateur ou personne
 */

public class Attendee implements Bean {
		public enum Type {
		    STE, FORMATION, SESSIONFORMATION, UTILISATEUR, ETUDIANT, FORMATEUR, PERSONNE
		};
		private int id;
		private Utilisateur utilisateur;
		private SessionsFormations sessionFormation;
		private Etudiant etudiant;
		private Formateur formateur;
		private Personne personne;


		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Utilisateur getUtilisateur() {
			return utilisateur;
		}

		public void setUtilisateur(Utilisateur utilisateur) {
			this.utilisateur = utilisateur;
		}


	}

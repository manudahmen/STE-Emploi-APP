package steemploi.service;

import org.apache.struts.action.ActionForm;

import steemploi.persistance.TableLangages;
import steemploi.persistance.TableLangues;
import steemploi.persistance.TableLogiciels;
import steemploi.persistance.TableProfils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Profil extends ActionForm {
	public class Langage implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1103641875318176622L;
		private int id = 0;
		private String nom = "";
		private String description = "";
		private int noOrdreLangage = 0;
		private String version = "";

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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getNoOrdreLangage() {
			return noOrdreLangage;
		}

		public void setNoOrdreLangage(int noOrdreLangage) {
			this.noOrdreLangage = noOrdreLangage;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

	}

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public class ProfilDiplome implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 606928341878215178L;
		private int id;
		private String intitule;
		private int annee;
		private String etablissement;
		private String orientation;
		private String commentaires;
		private String niveau;
		private boolean obtenu;
		private Calendar dateDebutFormation = Calendar.getInstance();
		private Calendar dateFinFormation = Calendar.getInstance();

		public void setId(int id) {
			this.id = id;

		}

		public int getId() {
			return id;
		}

		public void setIntitule(String intitule) {
			this.intitule = intitule;

		}

		public String getIntitule() {
			return intitule;
		}

		public int getAnnee() {
			return annee;
		}

		public void setAnnee(int annee) {
			this.annee = annee;
		}

		public String getEtablissement() {
			return etablissement;
		}

		public void setEtablissement(String etablissement) {
			this.etablissement = etablissement;
		}

		public String getOrientation() {
			return orientation;
		}

		public void setOrientation(String orientation) {
			this.orientation = orientation;
		}

		public String getCommentaires() {
			return commentaires;
		}

		public void setCommentaires(String commentaires) {
			this.commentaires = commentaires;
		}

		public String getNiveau() {
			return niveau;
		}

		public void setNiveau(String niveau) {
			this.niveau = niveau;
		}

		public boolean getObtenu() {
			return obtenu;
		}

		public void setObtenu(boolean obtenu) {
			this.obtenu = obtenu;
		}

		public Calendar getDateDebutFormation() {
			return dateDebutFormation;
		}

		public void setDateDebutFormation(Calendar dateDebutFormation) {
			this.dateDebutFormation = dateDebutFormation;
		}

		public Calendar getDateFinFormation() {
			return dateFinFormation;
		}

		public void setDateFinFormation(Calendar dateFinFormation) {
			this.dateFinFormation = dateFinFormation;
		}

	}

	public static String[] niveaux = new String[] { "Enseignement secondaire",
			"Enseignement sup�rieur court", "Enseignement sup�rieur long",
			"Formation compl�mentaire" };

	public static String[] domaines = new String[] { "a", "b" };

	// Step 1
	private String nom;
	private String prenom;
	private String rue;
	private String numero;
	private String boite;
	private String codepostal;
	private String ville;
	private String telfixe;
	private String mobile;
	private String pays;
	private String email;

	// Step 2
	public static String[] domaine;

	private ArrayList<ProfilDiplome> diplomes = new ArrayList<ProfilDiplome>();
	public int countDiplomes = 0;
	private int idDiplome;
	private String intitule;
	private String niveau;
	private int annee;
	private String etablissement;
	private String orientation;
	private String commentaires;
	private boolean obtenu;
	private Calendar dateDebutFormation = Calendar.getInstance();
	private Calendar dateFinFormation = Calendar.getInstance();
	private String date_Debut_Formation;
	private String date_Fin_Formation;

	// Step 3

	private int idPermis;
	private boolean permisDeConduire;
	private boolean licence;
	private boolean voiture;

	// Step 4
	private int idEmploi;
	public int countEmploi = 0;
	private String employeur;
	private String intitulePoste;
	private String typeContrat;
	private Calendar dateDebutEmploi = Calendar.getInstance();
	private Calendar dateFinEmploi = Calendar.getInstance();
	private String date_Debut_Emploi;
	private String date_Fin_Emploi;
	private boolean finContrat;
	private String motifFinContrat;
	private String taches;

	// Step 5

	private ArrayList<CategoriesLogiciels> categories = new ArrayList<CategoriesLogiciels>();
	private ArrayList<Logiciels> logiciels = new ArrayList<Logiciels>();
	private int idLogicielConnu;
	private HashMap<Integer, Integer> niveauConnaissanceLogiciel = new HashMap<Integer, Integer>(); // 0
	private HashMap<Integer, ArrayList<String>> niveauConnaissanceLogicielVersions = new HashMap<Integer, ArrayList<String>>(); // 0
	// -
	// 1
	// -
	// 2
	// -
	// 3
	private ArrayList<LogicielConnu> logicielsConnus = new ArrayList<LogicielConnu>();

	// Step 6

	// A mettre dans une classe langage
	private ArrayList<Langage> langages = new ArrayList<Langage>();
	private HashMap<Integer, Integer> niveauxConnaissancesLangage = new HashMap<Integer, Integer>();
	private HashMap<Integer, ArrayList<String>> niveauxConnaissancesLangageVersions = new HashMap<Integer, ArrayList<String>>();

	private int idLangage;
	private int noOrdreLangage;
	private String nomLangage;
	// A mettre dans une classe langage

	private int idLangageConnu;

	// Step 7
	// A mettre dans une classe langue
	private List<Langue> langues = new ArrayList<Langue>();
	private int idLangue;
	private int noOrdreLangue;
	private String nomLangue;
	// A mettre dans une classe langue

	private int idLangueConnue;
	private HashMap<String, Integer> niveauConnaissanceLangue = new HashMap<String, Integer>(); 
	// non

	// Step 8
	private String atouts;

	// Step 9
	private String loisirs;

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

	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}

	public String getCodepostal() {
		return codepostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelfixe(String telfixe) {
		this.telfixe = telfixe;
	}

	public String getTelfixe() {
		return telfixe;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void addDiplome() {
		ProfilDiplome d = new ProfilDiplome();
		d.setId(idDiplome);
		d.setIntitule(intitule);
		d.setNiveau(niveau);
		d.setAnnee(annee);
		d.setEtablissement(etablissement);
		d.setOrientation(orientation);
		d.setCommentaires(commentaires);
		d.setObtenu(obtenu);
		d.setDateDebutFormation(dateDebutFormation);
		d.setDateFinFormation(dateFinFormation);
		diplomes.add(d);
		clearDiplome();

	}

	public void updateDiplome() {
		ProfilDiplome d = null;

		for (ProfilDiplome di : diplomes) {
			if (di.getId() == idDiplome) {
				d = di;
			}
		}
		if(d==null) return;
		d.setId(idDiplome);
		d.setIntitule(intitule);
		d.setNiveau(niveau);
		d.setAnnee(annee);
		d.setEtablissement(etablissement);
		d.setOrientation(orientation);
		d.setCommentaires(commentaires);
		d.setObtenu(obtenu);
		d.setDateDebutFormation(dateDebutFormation);
		d.setDateFinFormation(dateFinFormation);

	}

	public void deleteDiplome(int id) {
		ProfilDiplome d = null;

		for (ProfilDiplome di : diplomes) {
			if (di.getId() == id) {
				d = di;
			}
		}

		diplomes.remove(d);
	}

	public void clearDiplome() {
		setIdDiplome(0);
		setIntitule(null);
		setNiveau(null);
		setAnnee(Calendar.getInstance().get(Calendar.YEAR));
		setEtablissement(null);
		setOrientation(null);
		setCommentaires(null);
		setObtenu(false);
		setDateDebutFormation(Calendar.getInstance());
		setDateFinFormation(Calendar.getInstance());

	}

	public void copyDiplome(ProfilDiplome d) {
		setIdDiplome(d.getId());
		setIntitule(d.getIntitule());
		setNiveau(d.getNiveau());
		setAnnee(d.getAnnee());
		setEtablissement(d.getEtablissement());
		setOrientation(d.getOrientation());
		setCommentaires(d.getCommentaires());
		setObtenu(d.getObtenu());
		setDateDebutFormation(d.getDateDebutFormation());
		setDateFinFormation(d.getDateFinFormation());

	}

	public void setDiplomes(ArrayList<ProfilDiplome> diplomes) {
		this.diplomes = diplomes;
	}

	public ArrayList<ProfilDiplome> getDiplomes() {
		return diplomes;
	}

	public void setIdDiplome(int idDiplome) {
		this.idDiplome = idDiplome;
	}

	public int getIdDiplome() {
		return idDiplome;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;

	}

	public String getIntitule() {
		return intitule;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getEtablissement() {
		return etablissement;
	}

	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public boolean getObtenu() {
		return obtenu;
	}

	public void setObtenu(boolean obtenu) {
		this.obtenu = obtenu;
	}

	public Calendar getDateDebutFormation() {
		return dateDebutFormation;
	}

	public void setDateDebutFormation(Calendar dateDebutFormation) {
		this.dateDebutFormation = dateDebutFormation;
	}

	public Calendar getDateFinFormation() {
		return dateFinFormation;
	}

	public void setDateFinFormation(Calendar dateFinFormation) {
		this.dateFinFormation = dateFinFormation;
	}

	public String getDate_Debut_Formation() {
		return date_Debut_Formation;
	}

	public void setDate_Debut_Formation(String date_Debut_Formation) {
		this.date_Debut_Formation = date_Debut_Formation;
	}

	public String getDate_Fin_Formation() {
		return date_Fin_Formation;
	}

	public void setDate_Fin_Formation(String date_Fin_Formation) {
		this.date_Fin_Formation = date_Fin_Formation;
	}

	// Step 3
	public boolean getPermisDeConduire() {
		return permisDeConduire;
	}

	public void setPermisDeConduire(boolean permisDeConduire) {
		this.permisDeConduire = permisDeConduire;
	}

	// Step 4
	private ArrayList<ProfilEmployeur> employeurs = new ArrayList<ProfilEmployeur>();

	private int etudiantId;

	public int getIdEmploi() {
		return idEmploi;
	}

	public void setIdEmploi(int idEmploi) {
		this.idEmploi = idEmploi;
	}

	public int getCountEmploi() {
		return countEmploi;
	}

	public void setCountEmploi(int countEmploi) {
		this.countEmploi = countEmploi;
	}

	public class ProfilEmployeur implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -183430759760039451L;
		private int id;
		private String employeur;
		private String intitulePoste;
		private String typeContrat;
		private Calendar dateDebutEmploi = Calendar.getInstance();
		private Calendar dateFinEmploi = Calendar.getInstance();
		private String date_Debut_Emploi;
		private String date_Fin_Emploi;
		private boolean finContrat;
		private String motifFinContrat;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getEmployeur() {
			return employeur;
		}

		public void setEmployeur(String employeur) {
			this.employeur = employeur;
		}

		public String getIntitulePoste() {
			return intitulePoste;
		}

		public void setIntitulePoste(String intitulePoste) {
			this.intitulePoste = intitulePoste;
		}

		public String getTypeContrat() {
			return typeContrat;
		}

		public void setTypeContrat(String typeContrat) {
			this.typeContrat = typeContrat;
		}

		public Calendar getDateDebutEmploi() {
			return dateDebutEmploi;
		}

		public void setDateDebutEmploi(Calendar dateDebutEmploi) {
			this.dateDebutEmploi = dateDebutEmploi;
		}

		public Calendar getDateFinEmploi() {
			return dateFinEmploi;
		}

		public void setDateFinEmploi(Calendar dateFinEmploi) {
			this.dateFinEmploi = dateFinEmploi;
		}

		public String getDate_Debut_Emploi() {
			return date_Debut_Emploi;
		}

		public void setDate_Debut_Emploi(String date_Debut_Emploi) {
			this.date_Debut_Emploi = date_Debut_Emploi;
		}

		public String getDate_Fin_Emploi() {
			return date_Fin_Emploi;
		}

		public void setDate_Fin_Emploi(String date_Fin_Emploi) {
			this.date_Fin_Emploi = date_Fin_Emploi;
		}

		public boolean getFinContrat() {
			return finContrat;
		}

		public void setFinContrat(boolean finContrat) {
			this.finContrat = finContrat;
		}

		public String getMotifFinContrat() {
			return motifFinContrat;
		}

		public void setMotifFinContrat(String motifFinContrat) {
			this.motifFinContrat = motifFinContrat;
		}
	}

	public void addEmployeur() {
		ProfilEmployeur d = new ProfilEmployeur();
		d.setId(idEmploi);
		d.setEmployeur(employeur);
		d.setIntitulePoste(intitulePoste);
		d.setMotifFinContrat(motifFinContrat);
		d.setFinContrat(finContrat);
		d.setTypeContrat(typeContrat);
		d.setDateDebutEmploi(dateDebutEmploi);
		d.setDateFinEmploi(dateFinEmploi);
		employeurs.add(d);
		clearEmployeur();

	}

	public void updateEmployeur() {
		ProfilEmployeur d = null;

		for (ProfilEmployeur di : employeurs) {
			if (di.getId() == idEmploi) {
				d = di;
				d.setEmployeur(employeur);
				d.setIntitulePoste(intitulePoste);
				d.setFinContrat(finContrat);
				d.setMotifFinContrat(motifFinContrat);
				d.setTypeContrat(typeContrat);
				d.setDateDebutEmploi(dateDebutEmploi);
				d.setDateFinEmploi(dateFinEmploi);
			}
		}

	}

	public void deleteEmployeur(int id) {
		ProfilEmployeur d = null;

		for (ProfilEmployeur di : employeurs) {
			if (di.getId() == id) {
				d = di;
			}
		}

		employeurs.remove(d);
	}

	public void clearEmployeur() {
		setIdEmploi(0);
		setEmployeur("");
		setIntitulePoste("");
		setFinContrat(false);
		setMotifFinContrat("");
		setTypeContrat("");
		setDateDebutEmploi(Calendar.getInstance());
		setDateFinEmploi(Calendar.getInstance());

	}

	public void copyEmployeur(ProfilEmployeur d) {
		setIdEmploi(d.getId());
		setEmployeur(d.getEmployeur());
		setIntitulePoste(d.getIntitulePoste());
		setMotifFinContrat(d.getMotifFinContrat());
		setFinContrat(d.getFinContrat());
		setTypeContrat(d.getTypeContrat());
		setDateDebutEmploi(d.getDateDebutEmploi());
		setDateFinEmploi(d.getDateFinEmploi());

	}

	public void setEmployeurs(ArrayList<ProfilEmployeur> employeurs) {
		this.employeurs = employeurs;
	}

	public ArrayList<ProfilEmployeur> getEmployeurs() {
		return employeurs;
	}

	public String getEmployeur() {
		return employeur;
	}

	public void setEmployeur(String employeur) {
		this.employeur = employeur;
	}

	public String getIntitulePoste() {
		return intitulePoste;
	}

	public void setIntitulePoste(String intitulePoste) {
		this.intitulePoste = intitulePoste;
	}

	public String getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public Calendar getDateDebutEmploi() {
		return dateDebutEmploi;
	}

	public void setDateDebutEmploi(Calendar dateDebutEmploi) {
		this.dateDebutEmploi = dateDebutEmploi;
	}

	public Calendar getDateFinEmploi() {
		return dateFinEmploi;
	}

	public void setDateFinEmploi(Calendar dateFinEmploi) {
		this.dateFinEmploi = dateFinEmploi;
	}

	public String getDate_Debut_Emploi() {
		return date_Debut_Emploi;
	}

	public void setDate_Debut_Emploi(String date_Debut_Emploi) {
		this.date_Debut_Emploi = date_Debut_Emploi;
	}

	public String getDate_Fin_Emploi() {
		return date_Fin_Emploi;
	}

	public void setDate_Fin_Emploi(String date_Fin_Emploi) {
		this.date_Fin_Emploi = date_Fin_Emploi;
	}

	public boolean getFinContrat() {
		return finContrat;
	}

	public void setFinContrat(boolean finContrat) {
		this.finContrat = finContrat;
	}

	public String getMotifFinContrat() {
		return motifFinContrat;
	}

	public void setMotifFinContrat(String motifFinContrat) {
		this.motifFinContrat = motifFinContrat;
	}

	public String getTaches() {
		return taches;
	}

	public void setTaches(String taches) {
		this.taches = taches;
	}

	// Step 5

	public class CategoriesLogiciels implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2566769091731417325L;
		private int idCategorie = 0;
		private int noOrdreCategorie = 0;
		private String nomCategorie = "";
		private String description = "";

		public int getIdCategorie() {
			return idCategorie;
		}

		public void setIdCategorie(int idCategorie) {
			this.idCategorie = idCategorie;
		}

		public int getNoOrdreCategorie() {
			return noOrdreCategorie;
		}

		public void setNoOrdreCategorie(int noOrdreCategorie) {
			this.noOrdreCategorie = noOrdreCategorie;
		}

		public String getNomCategorie() {
			return nomCategorie;
		}

		public void setNomCategorie(String nomCategorie) {
			this.nomCategorie = nomCategorie;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}

	public class Logiciels implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2210452578149632609L;
		private int idLogiciel;
		private int noOrdreLogiciel;
		private String nomLogiciel;
		private String editeurLogiciel;
		private String descriptionLogiciel;
		private int categorieLogiciel;
		private String version;

		public int getIdLogiciel() {
			return idLogiciel;
		}

		public void setIdLogiciel(int idLogiciel) {
			this.idLogiciel = idLogiciel;
		}

		public int getNoOrdreLogiciel() {
			return noOrdreLogiciel;
		}

		public void setNoOrdreLogiciel(int noOrdreLogiciel) {
			this.noOrdreLogiciel = noOrdreLogiciel;
		}

		public String getNomLogiciel() {
			return nomLogiciel;
		}

		public void setNomLogiciel(String nomLogiciel) {
			this.nomLogiciel = nomLogiciel;
		}

		public String getEditeurLogiciel() {
			return editeurLogiciel;
		}

		public void setEditeurLogiciel(String editeurLogiciel) {
			this.editeurLogiciel = editeurLogiciel;
		}

		public String getDescriptionLogiciel() {
			return descriptionLogiciel;
		}

		public void setDescriptionLogiciel(String descriptionLogiciel) {
			this.descriptionLogiciel = descriptionLogiciel;
		}

		public int getCategorieLogiciel() {
			return categorieLogiciel;
		}

		public void setCategorieLogiciel(int categorieLogiciel) {
			this.categorieLogiciel = categorieLogiciel;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

	}

	public class LogicielConnu {
		private int idLogiciel;
		private int niveauConnaissance;

		public int getIdLogiciel() {
			return idLogiciel;
		}

		public void setIdLogiciel(int idLogiciel) {
			this.idLogiciel = idLogiciel;
		}

		public int getNiveauConnaissance() {
			return niveauConnaissance;
		}

		public void setNiveauConnaissance(int niveauConnaissance) {
			this.niveauConnaissance = niveauConnaissance;
		}

	}

	public ArrayList<CategoriesLogiciels> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<CategoriesLogiciels> categories) {
		this.categories = categories;
	}

	public ArrayList<Logiciels> getLogiciels() {
		return logiciels;
	}

	public void setLogiciels(ArrayList<Logiciels> logiciels) {
		this.logiciels = logiciels;
	}

	public Logiciels getLogiciel(int idLogiciel) {
		for (Logiciels l : logiciels) {
			if (l.getIdLogiciel() == idLogiciel)
				return l;
		}

		return null;
	}

	public CategoriesLogiciels getCategorie(int idCategorie) {
		for (CategoriesLogiciels l : categories) {
			if (l.getIdCategorie() == idCategorie)
				return l;
		}

		return null;
	}

	public ArrayList<Logiciels> getLogicielsCategories(CategoriesLogiciels c) {
		ArrayList<Logiciels> logcat = new ArrayList<Logiciels>();

		for (Logiciels l : logiciels) {
			if (l.getCategorieLogiciel() == c.getIdCategorie())
				logcat.add(l);
		}

		return logcat;
	}

	public int getNiveauconnaissanceLangage(int langage_id) {

		Integer level = niveauxConnaissancesLangage.get(langage_id);
		return level == null ? 0 : level.intValue();
	}
	public ArrayList<String> getNiveauconnaissanceLangageVersions(int langage_id) {

		ArrayList<String> level = niveauxConnaissancesLangageVersions.get(langage_id);
		return level == null ? new ArrayList<String>() : level;
	}

	public void setNiveauconnaissanceLangage(int langage_id, int niveau) {

		niveauxConnaissancesLangage.put(langage_id, niveau);
	}
	public void setNiveauconnaissanceLangage(int langage_id, int level,
			ArrayList<String> versionsSaved) {
		niveauxConnaissancesLangage.put(langage_id, level);
		niveauxConnaissancesLangageVersions.put(langage_id, versionsSaved);
		
	}

	public int getIdLogicielConnu() {
		return idLogicielConnu;
	}

	public void setIdLogicielConnu(int idLogicielConnu) {
		this.idLogicielConnu = idLogicielConnu;
	}

	public int getNiveauConnaissanceLogiciel(int idLogiciel) {
		Integer i = niveauConnaissanceLogiciel.get(idLogiciel);
		return (i == null) ? 0 : i;
	}

	public void setNiveauConnaissanceLogiciel(int idLogiciel,
			int niveauConnaissanceLogiciel) {
		if (niveauConnaissanceLogiciel >= 0 && niveauConnaissanceLogiciel < 4)
			this.niveauConnaissanceLogiciel.put(idLogiciel,
					niveauConnaissanceLogiciel);
	}
	public void setNiveauConnaissanceLogiciel(int id_logiciel, int niveau2,
			ArrayList<String> versions) {
		if (niveau2 >= 0 && niveau2 < 4)
			this.niveauConnaissanceLogiciel.put(id_logiciel,
					niveau2);
		this.niveauConnaissanceLogicielVersions.put(id_logiciel, versions);
	}

	public ArrayList<String> getNiveauConnaissanceLogicielVersions(int idLogiciel) {
		ArrayList<String> i = niveauConnaissanceLogicielVersions.get(idLogiciel);
		return (i == null) ? null : i;
	}

	public void setNiveauConnaissanceLogicielVersions(int idLogiciel,
			ArrayList<String> niveauConnaissanceLogiciel) {
			this.niveauConnaissanceLogicielVersions.put(idLogiciel,
					niveauConnaissanceLogiciel);
	}
	public void loadCategories() {
		TableLogiciels t = new TableLogiciels();

		try {
			if (categories.size() == 0)
				t.loadCategories(this);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (logiciels.size() == 0)
				t.loadLogiciels(this);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void loadPermis(int etId) throws SQLException {
		TableProfils tp = new TableProfils();
		tp.setEtudiantId(etId);
		tp.loadPermis(this);
	}

	public void loadLangages() throws SQLException {

		TableLangages tp = new TableLangages();
		tp.loadLangages(this);
	}

	public void loadNiveauxConnaissancesLangages(int etudiant_id)
			throws SQLException {
		TableProfils tp = new TableProfils();
		tp.setEtudiantId(etudiant_id);
		tp.loadNiveauxConnaissancesLangages(this);
	}

	public void loadNiveauxConnaissancesLogiciels(int etudiant_id)
			throws SQLException {
		TableProfils tp = new TableProfils();
		tp.setEtudiantId(etudiant_id);
		tp.loadNiveauxConnaissancesLogiciels(this);
	}

	public void loadNiveauxConnaissancesLangues(int etudiant_id)
			throws SQLException {
		TableProfils tp = new TableProfils();
		tp.setEtudiantId(etudiant_id);
		tp.loadNiveauxConnaissancesLangues(this);
	}

	public void loadDiplomes(int id) throws SQLException {
		TableProfils tp = new TableProfils();
		tp.setEtudiantId(id);
		tp.loadDiplomes(this);
	}

	public void loadEmplois(int id) throws SQLException {
		TableProfils tp = new TableProfils();
		tp.setEtudiantId(id);
		tp.loadEmplois(this);
	}

	public void loadLangues() {
		TableLangues tl = new TableLangues();
		try {
			if (langues.size() == 0)
				tl.loadLangues(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadAtouts(int etudiant_id)
	{
		TableProfils tp = new TableProfils();
		tp.setEtudiantId(etudiant_id);
		try {
			tp.loadAtouts(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadLoisirs(int etudiant_id)
	{
		TableProfils tp = new TableProfils();
		tp.setEtudiantId(etudiant_id);
		try {
			tp.loadLoisirs(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getNiveauConnaissanceLangue(String idLangue, char c) {

		return niveauConnaissanceLangue.get(idLangue + c) == null ? 0
				: niveauConnaissanceLangue.get(idLangue + c);
	}

	public void setNiveauConnaissanceLangue(String langage_id, char c, int level) {
		this.niveauConnaissanceLangue.put(langage_id + c, level);
	}

	// Step 6: langages connus

	// Step 7
	public class Langue {
		private int id = 0;
		private String nom = "";
		private int noOrdreLangue = 0;
		private String nomCourt = "";

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

		public int getNoOrdreLangue() {
			return noOrdreLangue;
		}

		public void setNoOrdreLangue(int noOrdreLangue) {
			this.noOrdreLangue = noOrdreLangue;
		}

		public String getNomCourt() {
			return nomCourt;
		}

		public void setNomCourt(String nomCourt) {
			this.nomCourt = nomCourt;
		}

	}

	public List<Langue> getLangues() {
		return langues;
	}

	public void setLangues(List<Langue> langues) {
		this.langues = langues;
	}

	public void setEtudiantId(int id) {
		this.etudiantId = id;
	}

	public int getEtudiantId() {
		return etudiantId;
	}

	public int getIdPermis() {
		return idPermis;
	}

	public void setIdPermis(int idPermis) {
		this.idPermis = idPermis;
	}

	public boolean isLicence() {
		return licence;
	}

	public void setLicence(boolean licence) {
		this.licence = licence;
	}

	public boolean isVoiture() {
		return voiture;
	}

	public void setVoiture(boolean voiture) {
		this.voiture = voiture;
	}


	public HashMap<Integer, Integer> getNiveauConnaissanceLogiciel() {
		return niveauConnaissanceLogiciel;
	}
	public HashMap<Integer, ArrayList<String>> getNiveauConnaissanceLogicielVersions() {
		return niveauConnaissanceLogicielVersions;
	}

	public void setNiveauConnaissanceLogiciel(
			HashMap<Integer, Integer> niveauConnaissanceLogiciel) {
		this.niveauConnaissanceLogiciel = niveauConnaissanceLogiciel;
	}
	public void setNiveauConnaissanceLogicielVersions(
			HashMap<Integer, ArrayList<String>> niveauConnaissanceLogicielVersions) {
		this.niveauConnaissanceLogicielVersions = niveauConnaissanceLogicielVersions;
	}

	public HashMap<String, Integer> getNiveauConnaissanceLangue() {
		return niveauConnaissanceLangue;
	}

	public void setNiveauConnaissanceLangue(
			HashMap<String, Integer> niveauConnaissanceLangue) {
		this.niveauConnaissanceLangue = niveauConnaissanceLangue;
	}

	public ArrayList<Langage> getLangages() {
		return langages;
	}

	public void setLangages(ArrayList<Langage> langages) {
		this.langages = langages;
	}

	public void setNiveauconnaissanceLangage(HashMap<Integer, Integer> hashMap) {
		niveauxConnaissancesLangage = hashMap;

	}
	public void setAtouts(String atouts)
	{
		this.atouts = atouts;
	}
	public String getAtouts()
	{
		return atouts;
	}
	public String getLoisirs() {
		return loisirs;
	}

	public void setLoisirs(String loisirs) {
		this.loisirs = loisirs;
	}

	public HashMap<Integer, Integer> getNiveauxConnaissancesLangage() {
		return niveauxConnaissancesLangage;
	}

	public void setNiveauxConnaissancesLangage(
			HashMap<Integer, Integer> niveauxConnaissancesLangage) {
		this.niveauxConnaissancesLangage = niveauxConnaissancesLangage;
	}

	public HashMap<Integer, ArrayList<String>> getNiveauxConnaissancesLangageVersions() {
		return niveauxConnaissancesLangageVersions;
	}

	public void setNiveauxConnaissancesLangageVersions(
			HashMap<Integer, ArrayList<String>> niveauxConnaissancesLangageVersions) {
		this.niveauxConnaissancesLangageVersions = niveauxConnaissancesLangageVersions;
	}


}

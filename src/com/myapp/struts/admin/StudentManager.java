package com.myapp.struts.admin;

import java.sql.SQLException;

import java.util.*;

import java.sql.Types;

import steemploi.persistance.*;
import steemploi.service.*;
import java.util.logging.*;

public class StudentManager extends TableUtilisateurs {

	public int updateUser(Utilisateur utilisateur) throws SQLException {
		Utilisateur u = utilisateur;
		boolean active = u.getActive();
		if (u.getId() == 0) {
			active = false;
		}
		setFieldsNames(new String[] { "active", /*"ddn",*/ "nom", "prenom", "rue",
				"numero", "boite", "codepostal", "ville", "pays", "tel", "gsm",
				"email", "usertype", "username", "password" });
		setTypes(new int[] { Types.INTEGER, Types.DATE, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
		if (u.getId() == 0)
			insertInto(new Object[] { active, /*u.getDdn(),*/ u.getNom(),
					u.getPrenom(), u.getRue(), u.getNumero(), u.getBoite(),
					u.getCodepostal(), u.getVille(), u.getPays(), u.getTel(),
					u.getGsm(), u.getEmail(), u.getUsertype(), u.getUsername(), u.getPassword()});
		else
			update(new Object[] { active, /*u.getDdn(),*/ u.getNom(),
					u.getPrenom(), u.getRue(), u.getNumero(), u.getBoite(),
					u.getCodepostal(), u.getVille(), u.getPays(), u.getTel(),
					u.getGsm(), u.getEmail(), u.getUsertype(), u.getUsername(), u.getPassword() }, u.getId());
		ExecuteUpdate();
		return selectLastInserted();

	}

	public boolean updateEtudiant(Utilisateur u, int sf_id) throws SQLException
	{
		Logger logger = Logger.getLogger("com.myapp.struts.admin.StudentManager");
		if(u.getType().equals(TypeUtilisateur.ETUDIANT))
		{
			TableEtudiants te = new TableEtudiants();
			Etudiant et = te.findByUserId(user_id);
			if(et==null)
			{
				logger.warning("Erreur lors de la recherche de l'étudiant");
			}
			te.setFieldsNames(new String[]{"user_id", "sessions_formations_id", "nom" , "prenom", "email"});
			te.setTypes(new int [] {Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR} );
			et.setNom(u.getNom());
			et.setPrenom(u.getPrenom());
			et.setEmail(u.getEmail());
			if(sf_id!=0)
			{
				logger.warning("et.setFormation_id");
				et.setFormation_id(sf_id);
			}
			if(et.getId()==0)
			{
				logger.warning("te.insertInto");
				et.setUser_id(u.getId());
				te.insertInto(new Object[]{et.getUser_id(), et.getFormation_id(), et.getNom(), et.getPrenom(), et.getEmail()});
				logger.warning(te.getQ());
				te.ExecuteUpdate();
			}
			else
			{
				logger.warning("te.update");
				te.update(new Object[]{et.getUser_id(), et.getFormation_id(), et.getNom(), et.getPrenom(), et.getEmail()}, et.getId());
				te.ExecuteUpdate();
			}
			TableUtilisateurs tu = new TableUtilisateurs();
			u = tu.findById(u.getId());
			u.setEtudiant(et);
			
			logger.warning("tu.update (setEtudiant_id");
			tu.setFieldsNames(new String[]{"etudiant"});
			tu.setTypes(new int [] {Types.INTEGER} );
			tu.update(new Object[] {et.getId()}, u.getId());
			logger.warning(tu.getQ());
			tu.ExecuteUpdate();
		}
		else if(u.getType().equals(TypeUtilisateur.FORMATEUR))
		{
			TableFormateurs tf = new TableFormateurs();
			Formateur form = tf.findByUserId(user_id);
			tf.setFieldsNames(new String[]{"user_id"});
			if(form.getId()==0)
			{
				form.setUser_id(u.getId());
			}
			tf.update(new Object[]{form.getUser_id()}, form.getId());
		}
		else
			logger.info("TypeUtilisateur non défini");
		/*setFieldsNames(new String[]{"usertype"});
		setTypes(new int [] {Types.VARCHAR});
		update(new Object[]{u.getType().equals(TypeUtilisateur.ETUDIANT)? "etudiant" : "formateur" }, u.getId());
		*/
		return true;
	}

	public boolean setPassword(int user_id, String username, String password)
			throws SQLException {
		setFieldsNames(new String[] { "username", "password" });
		setTypes(new int[] { Types.VARCHAR, Types.VARCHAR });
		update(new Object[] { username, password }, user_id);
		return true;
	}

	public boolean activateAccount(int user_id, boolean activate)
			throws SQLException {
		setFieldsNames(new String[] { "active" });
		setTypes(new int[] { Types.VARCHAR, Types.VARCHAR });
		update(new Object[] { activate ? 1 : -1 }, user_id);
		return true;
	}

	public SessionsFormations getList(int sf_id) throws SQLException {
		SessionsFormations sf = null;

		sf = new TableSessionsFormations().findById(sf_id);

		return sf;

	}

	public List<SessionsFormations> getList() throws SQLException {
		List<SessionsFormations> sfs = new ArrayList<SessionsFormations>();

		sfs = new TableSessionsFormations().findAll(false);

		return sfs;
	}
}
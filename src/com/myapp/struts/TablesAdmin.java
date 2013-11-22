package com.myapp.struts;

import java.sql.SQLException;

import steemploi.persistance.TableCodeCategorieTache;
import steemploi.persistance.TableEtudiants;
import steemploi.persistance.TableFormations;
import steemploi.persistance.TableSessionsFormations;
import steemploi.persistance.TableUtilisateurs;

public class TablesAdmin {
		public Object findById(String objectType, int id) throws SQLException {
				if (objectType.equals("Formation")) {
						return new TableFormations().findById(id);

					} else if (objectType.equals("SessionsFormations")) {
						return new TableSessionsFormations().findById(id);

					} else if (objectType.equals("Etudiant")) {
						return new TableEtudiants().findById(id);

					} else if (objectType.equals("Utilisateur")) {
						return new TableUtilisateurs().findById(id);

					} else if (objectType.equals("Formateur")) {
						//return new TableFormateur().findById(id);

					} else if (objectType.equals("CategoriesTache")) {
						return new TableCodeCategorieTache().findById(id);
					}

				return null;
			}
	}

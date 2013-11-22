// Not used

package com.myapp.struts.events;

import org.apache.struts.action.ActionForm;

import steemploi.service.events.Evenement;

public abstract class Event {


		/***
		 * Initialise l'objet à sauvegarder avec les
		 * données du formulaire.
		 * @param data données issues du formulaire. 
		 * @return Objet de type Evenement correspondant
		 * aux données du formulaire
		 */
		public abstract Evenement initObject(ActionForm data);


		/***
		 * Sauvegarde l'objet en DB (insertion ou mise à jour
		 * des données).
		 * @param e : Evenement
		 * 	 
		 */
		public abstract void saveObject(Evenement e);


		/***
		 * Retire l'objet de la base de données .
		 * @return Evenement avec sous-type éventuel.
		 */
		public abstract Evenement getObject(int evt_id);


		/***
		 * Retire les données du formulaire avec l'objet
		 * Evenement
		 * @param e Evenement à convertir en données de 
		 * formulaire.
		 * @return Données du formulaire.
		 */
		public abstract ActionForm getData(Evenement e);
	}

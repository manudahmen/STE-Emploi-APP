package steemploi.persistance;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.struts.action.ActionForm;

import steemploi.service.Profil;
import steemploi.service.Profil.Langage;

public class TableLangage extends UpdateInsertIntoTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1086440069234217352L;

	public TableLangage() {
		super("langages");
	}

}

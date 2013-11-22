package steemploi.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;

import java.util.logging.*;


import steemploi.service.Contact;

public class TableContact extends UpdateInsertIntoTable {

		public TableContact() {
			super("contacts_entreprises");
			setFieldsNames(new String [] {"tel", "gsm", "nom", "prenom", "rue", "numero", "boite", "codepostal", "ville", "pays", "commentaires", "infocomplementaires", "email" , "url", "owner", "entreprise_id"} );
		}

		public Contact findById(int id) throws SQLException {
				Contact c = new Contact();
				String query = "select * from contacts_entreprises where id=" + id;
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
						copyProps(rs, c);
					}

				return c;
			}

		private void copyProps(ResultSet rs, Contact c) throws SQLException {
				c.setId(rs.getInt("id"));
				c.setEntreprise_id(rs.getInt("entreprise_id"));
				c.setOwner(rs.getInt("owner"));
				c.setNom ( rs.getString("nom"));
				c.setPrenom(rs.getString("prenom"));
				c.setBoite(rs.getString("boite"));
				c.setCodepostal(rs.getString("codepostal"));
				c.setEmail(rs.getString("email"));
				c.setGsm(rs.getString("gsm"));
				c.setId(rs.getInt("id"));
				c.setNumero(rs.getString("numero"));
				c.setPays(rs.getString("pays"));
				c.setRue(rs.getString("rue"));
				c.setTel(rs.getString("tel"));
				c.setUrl(rs.getString("url"));
				c.setVille(rs.getString("ville"));
				c.setInfocomplementaires(rs.getString("infocomplementaires"));
				c.setCommentaires(rs.getString("commentaires"));


			}

		public List<Contact> findByEntreprise(int etudiant_id, int entreprise_id) throws SQLException {
				List<Contact> contacts = new ArrayList<Contact>();
				String query = "select * from contacts_entreprises where owner=? and entreprise_id=? order by id";
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);

				pstmt.setInt(1, etudiant_id);
				pstmt.setInt(2, entreprise_id);
				ResultSet rs = pstmt.executeQuery();


				Contact c = new Contact();

				while (rs.next()) {
						c = new Contact();
						copyProps(rs, c);
						contacts.add(c);
					}

				return contacts;
			}

		public void update(Contact c, int id) throws SQLException {
				if (id == 0) {
						insertInto(new Object[] {
						               c.getTel(), c.getGsm(), c.getNom(), c.getPrenom(), c.getRue(), c.getNumero(), c.getBoite(), c.getCodepostal(), c.getVille(), c.getPays(), c.getCommentaires(), c.getInfocomplementaires(), c.getEmail(), c.getUrl(), c.getOwner(), c.getEntreprise_id()});

					} else {
						update(new Object[] {
						           c.getTel(), c.getGsm(), c.getNom(), c.getPrenom(), c.getRue(), c.getNumero(), c.getBoite(), c.getCodepostal(), c.getVille(), c.getPays(), c.getCommentaires(), c.getInfocomplementaires(), c.getEmail(), c.getUrl(), c.getOwner(), c.getEntreprise_id()}, id);
					}
				Logger logger = Logger.getLogger("com.struts.myapp.EditContact");
				logger.warning("Enregistrer contact");
				ExecuteUpdate();
			}
	}

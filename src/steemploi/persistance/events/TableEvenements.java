// Not used
package steemploi.persistance.events;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import steemploi.persistance.TableEntreprise;
import steemploi.persistance.TableEtudiants;
import steemploi.persistance.TableFormateurs;
import steemploi.persistance.UpdateInsertIntoTable;
import steemploi.service.Entreprise;
import steemploi.service.Etudiant;
import steemploi.service.events.Attendee;
import steemploi.service.events.AttendeeRole;
import steemploi.service.events.AttendeeType;
import steemploi.service.events.CalendarDates;
import steemploi.service.events.Evenement;
import steemploi.service.events.EvenementAttendee;
import steemploi.service.events.JPO;

public class TableEvenements extends UpdateInsertIntoTable {
		public TableEvenements() {
			super("evts");
			// TODO Auto-generated constructor stub
		}

		public Evenement findById(int id) throws SQLException, MalformedURLException {
				Evenement evt = new Evenement();
				String query = "select e.id as id, e.name as name, e.description as description, e.table_name, d.id as did, d.date1 as date1, d.has2values as has2values, d.date2 as date2, d.name as, d.parent as parent_id dname, aid, a.role as role, a.type as type, a.value as value from evts as e left outer join evt_dates as ed on e.id=ed.evt_id inner join dates as d on e.id=d.evt_id left outer join attendee as a on e.id=a.evt_id where e.id=?;";

				PreparedStatement pstmt = (PreparedStatement) conn
				                          .prepareStatement(query);

				String table_name = "";
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
						if (rs.getObject("id") != null) {
								evt.setId(rs.getInt("id"));
								evt.setName(rs.getString("name"));
								evt.setDescription(rs.getString("description"));

								table_name = rs.getString("table_name");
								// Init propriétés de l'évènement
							}

						if (rs.getObject("did") != null) {
								CalendarDates caldates = new CalendarDates();
								// Init propriétés de la plage de dates ou de la date
								caldates.setId(rs.getInt("did"));

								if (!evt.getDates().contains(caldates)) {
										caldates.setDate1(getDate(rs.getDate("date2")));

										if (rs.getBoolean("has2values")) {
												caldates.setDate2(getDate(rs.getDate("date2")));
											}

										caldates.setName(rs.getString("dname"));
										caldates.setParentId(rs.getInt("parent_id"));

										evt.getDates().add(caldates);

									}
							}

						if (rs.getObject("aid") != null) {
								EvenementAttendee attendee = new EvenementAttendee();
								attendee.setId(rs.getInt("aid"));
								// Init propriétés de l'attendee

								if (!evt.getPersonnes().contains(attendee)) {
										Attendee a = new Attendee();
										/**
										 * instancier d'objet de (Attendee) a 
										 */
										a.setId(rs.getInt("aid"));
										int value = rs.getInt("value");

										switch (rs.getInt("type")) {
													case 1:
													attendee.setType(AttendeeType.TousLesEtudiants);
													break;
													case 2:
													attendee.setType(AttendeeType.Formateurs);
													break;
													case 3:
													attendee.setType(AttendeeType.Entreprise);
													a.setObject(new TableEntreprise().findByEtudiantId(value));
													a.setClassofObject(Entreprise.class);
													break;
													case 4:
													attendee.setType(AttendeeType.Etudiant);
													a.setObject(new TableEtudiants().findById(value));
													a.setClassofObject(Etudiant.class);
													break;
													case 5:
													attendee.setType(AttendeeType.Formateur);
													a.setObject(new TableFormateurs().findById(value));
													a.setClassofObject(Etudiant.class);
													break;
													case 6:
													attendee.setType(AttendeeType.Coordinatrice);
													a.setObject(new TableFormateurs().findById(value));
													a.setClassofObject(Etudiant.class);
													break;
											}

										switch (rs.getInt("role")) {
													case 1:
													attendee.setRole(AttendeeRole.Createur);
													break;
													case 2:
													attendee.setRole(AttendeeRole.Attendee);
													break;
													case 3:
													attendee.setRole(AttendeeRole.Externe);
													break;
											}

										/*
										 * initialiser type et role
										 */
										attendee.setAttendee(a);

										evt.getPersonnes().add(attendee);
									}
							}
					}

				if (evt.getId() != 0 && table_name != null && !table_name.equals("")) {
						String query2 = "select * from " + table_name + " where evt_id=?";
						PreparedStatement pstmt1 = (PreparedStatement) conn.prepareStatement(query2);
						pstmt1.setInt(1, evt.getId());

						ResultSet rs2 = pstmt1.executeQuery();

						while (rs2.next()) {
								if (table_name.equals("jpo")) {
										JPO jpo = new JPO();
										evt.setSub(jpo);
										jpo.setUrl(new URL(rs2.getString("link")));
										jpo.setEvt_id(evt.getId());
										evt.setTableName(table_name);
									}

							}

					} else if (evt != null) {
						evt.setTableName("");
					}


				// hiérarchiser les dates
for (CalendarDates cd1 : evt.getDates()) {
						if (cd1.getParentId() != 0) {
								CalendarDates cd = new CalendarDates();
								cd.setParentId(cd1.getParentId());

								if (evt.getDates().contains(cd)) {
										cd1.setParent(evt.getDates().get(evt.getDates().indexOf(cd)));
									}
							}
					}



				return evt;
			}

		public boolean save(Evenement evt) throws SQLException {
				return true;
			}

		public boolean insert(Evenement evt) throws SQLException {
				String query = "insert into evts (name, description, table_name) values(?,?,?)";

				String query2 = "insert into attendee(evt_id, role, type, value) values(?,?,?,?)";
				PreparedStatement pstmtIA = (PreparedStatement) conn.prepareStatement(query2);
				query2 = "delete from attendee where id=?";
				PreparedStatement pstmtDA = (PreparedStatement) conn.prepareStatement(query2);

				query2 = "insert into dates(evt_id, name, has2values, date1, date2, parent) values(?,?,?,?,?,?)";
				PreparedStatement pstmtID = (PreparedStatement) conn.prepareStatement(query2);
				query2 = "delete from dates where id=?";
				PreparedStatement pstmtDD = (PreparedStatement) conn.prepareStatement(query2);

				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.setString(1, evt.getName());
				pstmt.setString(2, evt.getDescription());
				pstmt.setString(3, evt.getTableName());

				pstmt.execute();
				int evt_id = selectLastInserted();

for (EvenementAttendee ea : evt.getPersonnes()) {
						// insérer tous les attendee en tenant compte
						// du type et du role


					}

for (CalendarDates cd : evt.getDates()) {
						// insérer toutes les dates avec noms et
						// hiérarchie

					}

				return true;
			}

		public boolean update(Evenement evt) {
			return true;
		}

		public boolean delete(Evenement evt) {
			return true;
		}
	}

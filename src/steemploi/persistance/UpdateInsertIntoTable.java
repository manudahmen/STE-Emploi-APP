package steemploi.persistance;

import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import java.sql.PreparedStatement;

import steemploi.service.Etudiant;



public class UpdateInsertIntoTable extends TableWithHttpRequest {
		private String tableName;
		private String[] fieldsNames;
		private int[] types;
		private int count;
		private String idStr = "id";
		PreparedStatement pstmt;
		private String q;

		public UpdateInsertIntoTable(String tablename) {
			super();
			this.tableName = tablename;
		}

		public void insertInto(Object[] values) throws SQLException {
				if (count != values.length)
					throw new IndexOutOfBoundsException();

				String q = "insert into " + tableName + " ( ";

				q += fieldsNames[0];

				for (int i = 1; i < fieldsNames.length; i++) {
						q += " ,  " + fieldsNames[i];
					}

				q += " )  values  (  ?  ";

				for (int i = 1; i < fieldsNames.length; i++) {
						q += " ,  ?";
					}

				q += ")";
				this.q=q;
				pstmt = (PreparedStatement) conn.prepareStatement(q);
				setValues(pstmt, values);
			}

		private void setValues(PreparedStatement pstmt, Object[] objects)
		throws SQLException {

				for (int i = 1; i <= objects.length; i++) {
						Object o = objects[i - 1];

						if (o == null)
							pstmt.setNull(i, types[i - 1]);
						else if (o instanceof Integer)
							pstmt.setInt(i, (Integer) o);
						else if (o instanceof String)
							pstmt.setString(i, (String) o);
						else if (o instanceof Calendar)
							pstmt.setDate(i, (Date) ((Calendar) o).getTime());
						else if (o instanceof Long)
							pstmt.setLong(i, (Long) o);
						else if(o instanceof Boolean)
							pstmt.setBoolean(i, (Boolean) o);
							

					}
			}

		private void setValuesIfNotNull(PreparedStatement pstmt,
		                                Object[] objects) throws SQLException {

				int j = 1;

				for (int i = 1; i <= objects.length; i++) {
						Object o = objects[i - 1];
						// Ne pas tenir compte de l'argument s'il est null ou si
						// c'est une chaîne vide.

						if ((o == null)
					    || ((o instanceof String) && ((String) o).trim().equals(""))) {}
						else if (o instanceof Integer) {
								pstmt.setInt(j, (Integer) o);
								j++;

							} else if (o instanceof String) {
								pstmt.setString(j, (String) o);
								j++;

							} else if (o instanceof Calendar) {
								pstmt.setDate(j, (Date) ((Calendar) o).getTime());
								j++;

							} else if (o instanceof Long) {
								pstmt.setLong(j, (Long) o);
								j++;
							}
					}
			}

		public void update(Object[] values, int id) throws SQLException {
				if (count != values.length)
					throw new IndexOutOfBoundsException();

				String q = "update " + tableName + " set ";

				q += fieldsNames[0] + " = ? ";

				for (int i = 1; i < fieldsNames.length; i++) {
						q += " ,  " + fieldsNames[i] + " = ? ";
					}

				q += " where "+idStr+"= ?";
				this.q=q;
				pstmt = (PreparedStatement) conn.prepareStatement(q);

				setValues(pstmt, values);
				pstmt.setInt(values.length + 1, id);

			}

		public void updateIfNotNull(Object[] values, int id) throws SQLException {
				Object[] values2 = values.clone();
				int j = 0;

				if (count != values.length)
					throw new IndexOutOfBoundsException();

				String q = "update " + tableName + " set ";

				boolean first = true;

				for (int i = 0; i < fieldsNames.length; i++) {
						if (values[i] != null
						    && !((values[i] instanceof String) && ((String) values[i])
						         .trim().equals(""))) {
								if (first) {
										q += fieldsNames[i] + " = ? ";
										first = false;

									} else {
										q += " ,  " + fieldsNames[i] + " = ? ";
									}

								values2[j] = values[i];
								j++;
							}
					}

				q += " where "+idStr+"= ?";
				System.out.println(q);
				pstmt = (PreparedStatement) conn.prepareStatement(q);
				Object[] values3 = new Object[j];

				for (int i = 0; i < j; i++)
					values3[i] = values2[i];

				setValuesIfNotNull(pstmt, values3);

				pstmt.setInt(j + 1, id);

			}

		public int ExecuteUpdate() throws SQLException {
				return pstmt.executeUpdate();
			}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String[] getFieldsNames() {
			return fieldsNames;
		}

		public void setFieldsNames(String[] fieldsNames) {
			this.fieldsNames = fieldsNames;
			count = fieldsNames.length;
		}

		public int[] getTypes() {
			return types;
		}

		public void setTypes(int[] types) {
			this.types = types;
		}

		public PreparedStatement getPstmt() {
			return pstmt;
		}

		public void setPstmt(PreparedStatement pstmt) {
			this.pstmt = pstmt;
		}

		public int selectLastInserted() throws SQLException {
				String query2 = "SELECT LAST_INSERT_ID() as id";
				PreparedStatement pstmt2 = (PreparedStatement) conn
				                                     .prepareStatement(query2);
				ResultSet rs = pstmt2.executeQuery();
				rs.next();
				int id = rs.getInt("id");
				return id+1;

			}

		public String getIdStr() {
			return idStr;
		}

		public void setIdStr(String idStr) {
			this.idStr = idStr;
		}

		public String getQ() {
			return q;
		}

		public void setQ(String q) {
			this.q = q;
		}

	}

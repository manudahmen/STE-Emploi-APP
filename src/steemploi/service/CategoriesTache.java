/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package steemploi.service;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author manuel
 */

public class CategoriesTache extends ActionForm {
		private int id;
		private String code;
		private String title;


		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

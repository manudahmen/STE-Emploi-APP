/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package steemploi.service;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author manuel
 */

public class Login extends ValidatorForm {
		public enum USERTYPE {ROLE_ETUDIANT, ROLE_FORMATEUR};
		private String username;
		private String password;
		private String email;
		private String usertype;

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}


	}

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package steemploi.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author manuel
 */

public interface Event extends Bean {
		public void setDate(Calendar calendar);
		public Calendar getDate();

		public void setTitle(String title);
		public String getTitle();

		public String getDescription();
		public void setDescription(String description);

	}

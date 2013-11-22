/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package com.myapp.struts;

import java.util.ArrayList;

/**
 *
 * @author manuel
 */

public class SelectOptions extends ArrayList<Object> {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;
		private int property;
		private String label;
		private int value;
		private String labelProperty;

		public String getLabelProperty() {
			return labelProperty;
		}

		public void setLabelProperty(String label) {
			this.labelProperty = label;
		}

		public int getProperty() {
			return property;
		}

		public void setProperty(int property) {
			this.property = property;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

	}

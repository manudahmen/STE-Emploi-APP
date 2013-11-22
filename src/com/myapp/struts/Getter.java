package com.myapp.struts;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manuel
 */

public class Getter {
		Object object;

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}

		public String encode(String page, String[] args) {
			String encoded = page + "?";

			for (int i = 0; i < args.length; i += 2) {
					if (i > 0) encoded += "&" ;

					encoded += args[i] + "=" + args[i + 1] ;
				}

			try {
					return URLEncoder.encode(encoded, "UTF-8");

				} catch (UnsupportedEncodingException ex) {
					Logger.getLogger(Getter.class.getName()).log(Level.SEVERE, null, ex);
				}

			return encoded;
		}


	}

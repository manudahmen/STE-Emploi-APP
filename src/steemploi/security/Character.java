package steemploi.security;

//CharacterEncodingFilter.java

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Character implements Filter {

		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

				HttpServletRequest request = (HttpServletRequest) req;
				HttpServletResponse response = (HttpServletResponse) res;

				response.setContentType("text/html; charset=UTF-8");
				request.setCharacterEncoding("UTF-8");

				chain.doFilter(req, res);        //do it again, since JSPs will set it to the default

				response.setContentType("text/html; charset=UTF-8");
				request.setCharacterEncoding("UTF-8");
				Logger logger = Logger.getLogger("security.Character");
				logger.log(Level.INFO, "Character UTF-8 : "+req.getCharacterEncoding() + " url : " + ((HttpServletRequest) req).getRequestURI());
			}

		public void init(FilterConfig filterConfig) {
		}

		public void destroy() {
		}
	}

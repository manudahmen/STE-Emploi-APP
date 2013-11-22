package steemploi.security;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SubmitFilter implements Filter {

		private FilterConfig filterConfig;



		public void destroy() {
			// TODO Auto-generated method stub

		}

		
		public void doFilter(ServletRequest request, ServletResponse response,
		                     FilterChain filterChain) throws IOException, ServletException {

				HttpSession session = ((HttpServletRequest)request).getSession(false);

				if (session == null || session.getAttribute("user") == null) {
						request.getRequestDispatcher("/login.jsp").forward(request, response);
						filterChain.doFilter(request, response);

					} else
					filterChain.doFilter(request, response);
			}



		public void init(FilterConfig filterConfig) throws ServletException {
				this.filterConfig = filterConfig;
			}
	}

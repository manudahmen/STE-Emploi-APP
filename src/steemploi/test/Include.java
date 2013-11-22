package steemploi.test;

import java.io.IOException;

import javax.servlet.ServletException;

import org.directwebremoting.WebContextFactory;

public class Include {

		private String page = "";


		public void setPage(String s) {
			this.page = s;

		}

		public String getInclude() throws IOException, ServletException {
				org.directwebremoting.WebContext wbctx = WebContextFactory.get();
				return wbctx.forwardToString(page);

			}
	}

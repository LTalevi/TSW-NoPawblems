package control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.utente.Utente;

@WebFilter("/AdminFilter")
public class AdminFilter extends HttpFilter implements Filter {
       
    public AdminFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession session = httpRequest.getSession(false);
		
		if (session == null || session.getAttribute("utente") == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/LoginServlet");
			return;
		} 
		
		Utente utente = (Utente) session.getAttribute("utente");
		if (!utente.isAdmin()) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/403.jsp");
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}
}

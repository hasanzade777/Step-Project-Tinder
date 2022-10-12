package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        boolean hasLoggedIn = Arrays.stream(req.getCookies()).anyMatch(cookie -> cookie.getName().equals("c_user"));
        if (hasLoggedIn) {
            chain.doFilter(request, response);
        }
        else {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
}

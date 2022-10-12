package filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Optional<Cookie[]> cookiesOpt = Optional.ofNullable(req.getCookies());
        if (cookiesOpt.isEmpty() || Arrays.stream(req.getCookies()).noneMatch(cookie -> cookie.getName().equals("c_user"))) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/login");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

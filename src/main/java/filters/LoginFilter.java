package filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Optional<Cookie[]> cookiesOpt = Optional.ofNullable(req.getCookies());
        if (cookiesOpt.isEmpty() || Arrays.stream(cookiesOpt.get()).noneMatch(cookie -> cookie.getName().equals("c_user"))) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}

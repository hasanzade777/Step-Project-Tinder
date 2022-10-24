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
    public void init(FilterConfig filterConfig) {

    }

    private boolean isHTTP(ServletRequest req, ServletResponse resp) {
        return req instanceof HttpServletRequest &&
                resp instanceof HttpServletResponse;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!isHTTP(request, response)) {
            chain.doFilter(request, response);
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Optional<Cookie[]> cookiesOpt = Optional.ofNullable(req.getCookies());
        if (cookiesOpt.isEmpty() || Arrays.stream(cookiesOpt.get()).noneMatch(cookie -> cookie.getName().equals("c_user"))) {
            resp.sendRedirect("/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}

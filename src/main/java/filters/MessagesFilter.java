package filters;

import dao.controllers.DBController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class MessagesFilter implements Filter {

    private DBController dbc;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.dbc = (DBController) filterConfig.getServletContext().getAttribute("dbc");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getPathInfo() == null) {
            chain.doFilter(request, response);
        }
        String userIdToChatWith = req.getPathInfo().substring(1);
        boolean idIsNumeric = userIdToChatWith.matches("[0-9]+");
        Long userLoggedInId = Long.valueOf(Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("c_user"))
                .findAny()
                .get()
                .getValue());
        if (idIsNumeric &&
            dbc.userExistsById(Long.valueOf(userIdToChatWith)) &&
            !userLoggedInId.equals(Long.valueOf(userIdToChatWith))) {
            try {
                chain.doFilter(request, response);
            }
            catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            RequestDispatcher rd = req.getRequestDispatcher("/noSuchUser");
            try {
                rd.forward(req, resp);
            }
            catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void destroy() {

    }
}

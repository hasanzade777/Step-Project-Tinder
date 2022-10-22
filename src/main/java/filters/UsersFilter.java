package filters;

import dao.controllers.DBController;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UsersFilter implements Filter {

    private DBController dbc;

    @Override
    public void init(FilterConfig filterConfig) {
        this.dbc = (DBController) filterConfig.getServletContext().getAttribute("dbc");
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
        HttpSession session = req.getSession();
        if (session.isNew()) {
            List<User> usersNotChecked = dbc.getAllUsers();
            long userLoggedInId =  Long.parseLong(Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("c_user"))
                    .findAny()
                    .get()
                    .getValue());
            usersNotChecked.removeIf(user -> user.getId() == userLoggedInId);
            if (usersNotChecked.isEmpty()) {
                resp.setContentType("text/html");
                try (PrintWriter pw = resp.getWriter()) {
                    pw.println("There is no user to display.<br>");
                }
            }
            else {
                session.setAttribute("usersNotChecked", usersNotChecked);
                session.setAttribute("usersLiked", new ArrayList<User>());
                session.setAttribute("userDisplayIndex", 0);
                chain.doFilter(request, response);
            }
        }
        else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}

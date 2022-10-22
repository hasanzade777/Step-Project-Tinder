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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.isNew()) {
            List<User> allUsers = dbc.getAllUsers();
            long userLoggedInId = Integer.parseInt(Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("c_user"))
                    .findAny()
                    .get().getValue());
            allUsers.removeIf(user -> user.getId() == userLoggedInId);
            if (allUsers.isEmpty()) {
                resp.setContentType("text/html");
                try (PrintWriter pw = resp.getWriter()) {
                    pw.println("No user to display<br>");
                }
            }
            session.setAttribute("usersNotChecked", allUsers);
            session.setAttribute("usersLiked", new ArrayList<User>());
            session.setAttribute("userDisplayIndex", 0);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

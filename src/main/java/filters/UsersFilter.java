package filters;

import dao.controllers.DBController;
import entities.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static freemarker.template.Configuration.VERSION_2_3_28;

public class UsersFilter implements Filter {

    private DBController dbc;
    private Template template;

    @Override
    public void init(FilterConfig filterConfig) {
        this.dbc = (DBController) filterConfig.getServletContext().getAttribute("dbc");
        Configuration configuration = new Configuration(VERSION_2_3_28);
        configuration.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        try {
            configuration.setDirectoryForTemplateLoading(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("templates")).toURI()));
            template = configuration.getTemplate("simple-page.ftl");
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
            long userLoggedInId = Long.parseLong(Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("c_user"))
                    .findAny()
                    .get()
                    .getValue());
            usersNotChecked.removeIf(user -> user.getId() == userLoggedInId);
            if (usersNotChecked.isEmpty()) {
                try (PrintWriter pw = resp.getWriter()) {
                    template.process(
                            Map.of("message", "THERE IS NO USER TO DISPLAY"),
                            pw);
                } catch (TemplateException e) {
                    throw new RuntimeException(e);
                }
            } else {
                session.setAttribute("usersNotChecked", usersNotChecked);
                session.setAttribute("usersLiked", new ArrayList<User>());
                session.setAttribute("userDisplayIndex", 0);
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}

package servlets;

import dao.controllers.DBController;
import entities.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


// http://localhost:8080/like-page
public class LikePageServlet extends HttpServlet {
    private Template templ;
    private DBController dbc;

    @Override
    public void init() throws ServletException {
        dbc = (DBController) getServletContext().getAttribute("DBController");
        Configuration conf = new Configuration(Configuration.VERSION_2_3_28);
        conf.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        try {
            conf.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            templ = conf.getTemplate("like-page.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        List<User> usersNotChecked = (List<User>) session.getAttribute("usersNotChecked");
        int userDisplayIndex = (int) session.getAttribute("userDisplayIndex");
        try {
            User user = usersNotChecked.get(userDisplayIndex);
            session.setAttribute("userToDisplay", user);
            try (PrintWriter pw = resp.getWriter()) {
                templ.process(Map.of("user", user), pw);
            }
        }
        catch (IndexOutOfBoundsException e) {
            resp.sendRedirect("/liked");
        }
        catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String preference = req.getParameter("button");
        HttpSession session = req.getSession();
        int userDisplayIndex = (int) session.getAttribute("userDisplayIndex");
        List<User> usersLiked = (List<User>) session.getAttribute("usersLiked");
        User user = (User) session.getAttribute("userToDisplay");
        if (preference.equals("like")) {
            usersLiked.add(user);
        }
        session.setAttribute("userDisplayIndex", ++userDisplayIndex);
        resp.sendRedirect("/like-page");
    }
}

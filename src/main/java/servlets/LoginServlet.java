package servlets;

import dao.controllers.DBController;
import entities.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


// http://localhost:8080/login
public class LoginServlet extends HttpServlet {

    private DBController dbc;

    @Override
    public void init() {
        this.dbc = (DBController) getServletContext().getAttribute("dbc");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName = Objects.requireNonNull(getClass().getClassLoader().getResource("templates/login.html"))
                .getFile().substring(1);
        try (PrintWriter pw = resp.getWriter()) {
            Files.readAllLines(Path.of(fileName)).forEach(pw::println);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inputEmail = req.getParameter("inputEmail");
        String inputPassword = req.getParameter("inputPassword");
        boolean loginIsCorrect = dbc.loginIsCorrect(inputEmail, inputPassword);
        if (!loginIsCorrect) {
            resp.sendRedirect("/login");
        } else {
            req.getSession().invalidate();
            User userLoggedIn = dbc.getUser(inputEmail, inputPassword).get();
            long userLoggedInId = userLoggedIn.getId();
            dbc.updateLastLoginDateTime(userLoggedInId);
            Cookie cookie = new Cookie("c_user", String.valueOf(userLoggedInId));
            cookie.setMaxAge(10 * 24 * 60 * 60); //10 days
            resp.addCookie(cookie);
            resp.sendRedirect("/users");
        }
    }
}
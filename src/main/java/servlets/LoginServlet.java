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
import java.util.List;
import java.util.Objects;
import java.util.Optional;


// http://localhost:8080/login
public class LoginServlet extends HttpServlet {

    private DBController dbc;

    @Override
    public void init() {
        this.dbc = (DBController) getServletContext().getAttribute("DBController");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName = Objects.requireNonNull(getClass().getClassLoader().getResource("templates/login.html")).getFile().substring(1);
        List<String> lines = Files.readAllLines(Path.of(fileName));
        try (PrintWriter pw = resp.getWriter()) {
            for (String line : lines) {
                pw.println(line);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inputEmail = req.getParameter("inputEmail");
        String inputPassword = req.getParameter("inputPassword");
        Optional<User> userOpt = dbc.getUser(inputEmail, inputPassword);
        if (userOpt.isEmpty()) {
            resp.sendRedirect("/login"); //wrong username or password message to be added
        } else {
            User userLoggedIn = userOpt.get();
            long userLoggedInId = userLoggedIn.getId();
            dbc.updateLastLogin(userLoggedInId);
            Cookie cookie = new Cookie("c_user", String.valueOf(userLoggedInId));
            cookie.setMaxAge(10 * 24 * 60 * 60); //10 days
            resp.addCookie(cookie);
            resp.sendRedirect("/users");
        }
    }
}
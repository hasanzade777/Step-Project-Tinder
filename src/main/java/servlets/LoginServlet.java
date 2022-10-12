package servlets;

import dao.controllers.DBController;
import entities.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




// http://localhost:8080/login
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName = Objects.requireNonNull(getClass().getClassLoader().getResource("templates/login.html")).getFile();
        List<String> lines = Files.readAllLines(Path.of(fileName));
        try (PrintWriter pw = resp.getWriter()) {
            for (String line : lines) {
                pw.println(line);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBController dbc = (DBController) getServletContext().getAttribute("DBController");
        Optional<User> userOpt = dbc.getUser(req.getParameter("inputEmail"), req.getParameter("inputPassword"));
        if (userOpt.isEmpty()) {
            resp.sendRedirect("/login"); //wrong username or password message to be added
        }
        else {
            dbc.updateLastLogin(userOpt.get().getId());
            Cookie cookie = new Cookie("c_user", String.valueOf(userOpt.get().getId()));
            cookie.setMaxAge(10 * 24 * 60 * 60); //10 days
            resp.addCookie(cookie);
            resp.sendRedirect("/like-page");
        }
    }
}
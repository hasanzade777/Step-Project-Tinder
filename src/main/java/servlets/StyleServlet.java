package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class StyleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName = Objects.requireNonNull(getClass().getClassLoader().getResource("templates/css/style.css"))
                .getFile().substring(1);
        try (PrintWriter pw = resp.getWriter()) {
            Files.readAllLines(Path.of(fileName)).forEach(pw::println);
        }
    }
}

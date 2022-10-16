package servlets;

import dao.controllers.DBController;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static freemarker.template.Configuration.VERSION_2_3_28;

public class MessageServlet extends HttpServlet {
    private Template template;

    private DBController dbc;

    @Override
    public void init() {
        this.dbc = (DBController) getServletContext().getAttribute("DBController");
        log("Initializing Message");
        Configuration configuration = new Configuration(VERSION_2_3_28);
        configuration.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        try {
            configuration.setDirectoryForTemplateLoading(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("templates")).toURI()));
            template = configuration.getTemplate("chat.ftl");
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(req.getPathInfo().substring(1));
        if (req.getPathInfo().substring(1).startsWith("3")) {
            var allMessages = dbc.getAllMessages(Long.valueOf(req.getPathInfo().substring(1)));
            var user = dbc.getUserByID(Long.valueOf(req.getPathInfo().substring(1)));
            if (allMessages.isEmpty()) {
                resp.sendRedirect("/users");
            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("messages", allMessages);
            data.put("name", user.get().getFullName());
            data.put("profileImgUrl", user.get().getProfilePicLink());
            try (PrintWriter pw = resp.getWriter()) {
                template.process(data, pw);
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (PrintWriter pw = resp.getWriter()) {
                template.dump(pw);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

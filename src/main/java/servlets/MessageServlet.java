package servlets;

import dao.controllers.DBController;
import entities.Message;
import entities.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import static freemarker.template.Configuration.VERSION_2_3_28;

public class MessageServlet extends HttpServlet {
    private Template template;

    private DBController dbc;
    private static final HashMap<String, Object> data = new HashMap<>();

    @Override
    public void init() {
        this.dbc = (DBController) getServletContext().getAttribute("dbc");
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
    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        User userToChatWith = dbc.getUser(
                        Long.valueOf(req.getPathInfo().substring(1)))
                .get();
        Long userLoggedInId = Long.valueOf(Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("c_user"))
                .findAny()
                .get()
                .getValue());
        var allMessages = dbc.getAllMessagesBetween(
                userToChatWith.getId(),
                userLoggedInId);
        data.clear();
        data.put("allMessages", allMessages);
        data.put("name", userToChatWith.getFullName());
        data.put("toId", userToChatWith.getId());
        data.put("profileImgUrl", userToChatWith.getProfilePicLink());
        try (PrintWriter pw = resp.getWriter()) {
            template.process(data, pw);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log("Sending message");
        Long userIdToChatWith = Long.valueOf(req.getPathInfo().substring(1));
        Long userLoggedInId = Long.valueOf(Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("c_user"))
                .findAny()
                .get()
                .getValue());
        var messageContent = req.getParameter("send-message");
        dbc.saveMessage(new Message(userLoggedInId, userIdToChatWith, messageContent));
        resp.sendRedirect(String.format("/messages/%d", userIdToChatWith));
    }
}

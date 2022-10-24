package servlets;

import entities.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// http://localhost:8080/liked
public class ShowLikedServlet extends HttpServlet {

    private Template mainTempl;
    private Template errorPageTempl;

    @Override
    public void init() {
        Configuration conf = new Configuration(Configuration.VERSION_2_3_28);
        conf.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        try {
            conf.setDirectoryForTemplateLoading(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("templates")).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            mainTempl = conf.getTemplate("people-list.ftl");
            errorPageTempl = conf.getTemplate("simple-page.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<User> usersLiked = (List<User>) session.getAttribute("usersLiked");
        if (usersLiked.isEmpty()) {
            try (PrintWriter pw = resp.getWriter()) {
                errorPageTempl.process(
                        Map.of("message", "THERE IS NO USER TO DISPLAY"),
                        pw);
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("usersLiked", usersLiked);
        try (PrintWriter pw = resp.getWriter()) {
            mainTempl.process(data, pw);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}

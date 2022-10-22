package servlets;

import entities.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowLikedServlet extends HttpServlet {

    private Template templ;

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
            templ = conf.getTemplate("people-list.ftl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<User> usersLiked = (List<User>) session.getAttribute("usersLiked");
        if (usersLiked.isEmpty()) {
            resp.sendRedirect("/users");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("usersLiked", usersLiked);
        try (PrintWriter pw = resp.getWriter()) {
            templ.process(data, pw);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}

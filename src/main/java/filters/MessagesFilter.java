package filters;

import dao.controllers.DBController;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static freemarker.template.Configuration.VERSION_2_3_28;

public class MessagesFilter implements Filter {

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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (!isHTTP(request, response)) {
            chain.doFilter(request, response);
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getPathInfo() == null) {
            chain.doFilter(request, response);
        }
        String userIdToChatWith = req.getPathInfo().substring(1);
        boolean idIsNumeric = userIdToChatWith.matches("[0-9]+");
        Long userLoggedInId = Long.valueOf(Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("c_user"))
                .findAny()
                .get()
                .getValue());
        if (idIsNumeric &&
            dbc.userExistsById(Long.valueOf(userIdToChatWith)) &&
            !userLoggedInId.equals(Long.valueOf(userIdToChatWith))) {
            try {
                chain.doFilter(request, response);
            }
            catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try (PrintWriter pw = resp.getWriter()) {
                template.process(
                        Map.of("message", "NO SUCH USER EXISTS"),
                        pw);
            }
            catch (TemplateException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void destroy() {

    }
}

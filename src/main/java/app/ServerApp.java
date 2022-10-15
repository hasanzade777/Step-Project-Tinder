package app;

import filters.LikeFilter;
import filters.LoginFilter;
import java.util.EnumSet;
import java.util.Objects;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServlet;
import listeners.MyServletContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.LikePageServlet;
import servlets.LikedUsersShowServlet;
import servlets.LoginServlet;

public class ServerApp {
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            handler.setSessionHandler(new SessionHandler());
            //servlets
            handler.addServlet(LoginServlet.class, "/login");
            handler.addServlet(LikePageServlet.class, "/users");
            handler.addServlet(LikedUsersShowServlet.class, "/liked");
            //filters
            handler.addFilter(LoginFilter.class, "/users", EnumSet.of(DispatcherType.REQUEST));
            handler.addFilter(LoginFilter.class, "/liked", EnumSet.of(DispatcherType.REQUEST));
            handler.addFilter(LikeFilter.class, "/users", EnumSet.of(DispatcherType.REQUEST));
            handler.addFilter(LikeFilter.class, "/liked", EnumSet.of(DispatcherType.REQUEST));
            //context-listener
            handler.addEventListener(new MyServletContextListener());
            //context-params
            handler.setInitParameter("dbHost", "ec2-52-203-118-49.compute-1.amazonaws.com");
            handler.setInitParameter("dbPort", "5432");
            handler.setInitParameter("dbName", "dsq4s45dhepp6");
            handler.setInitParameter("dbUser", "vwktrcuywyclvw");
            handler.setInitParameter("dbPassword", "4cf47f217fec1d3ce6628d934794f6ad4bef3a2e62fb3ef66f13580b3e461e0f");
            server.setHandler(handler);
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}

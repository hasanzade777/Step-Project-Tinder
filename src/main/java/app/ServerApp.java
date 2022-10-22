package app;

import filters.LoginFilter;
import filters.MessagesFilter;
import filters.UsersFilter;
import listeners.MyServletContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.*;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class ServerApp {
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            handler.setSessionHandler(new SessionHandler());
            //servlets
            handler.addServlet(LoginServlet.class, "/login");
            handler.addServlet(UsersServlet.class, "/users");
            handler.addServlet(ShowLikedServlet.class, "/liked");
            handler.addServlet(BootStrapServlet.class, "/css/bootstrap.min.css");
            handler.addServlet(StyleServlet.class, "/css/style.css");
            handler.addServlet(MessageServlet.class,"/message/*");
            handler.addServlet(BootStrapServlet.class,"/message/css/bootstrap.min.css");
            handler.addServlet(StyleServlet.class, "/message/css/style.css");
            //filters
            handler.addFilter(LoginFilter.class, "/users", EnumSet.of(DispatcherType.REQUEST));
            handler.addFilter(LoginFilter.class, "/liked", EnumSet.of(DispatcherType.REQUEST));
            handler.addFilter(LoginFilter.class,"/message/*",EnumSet.of(DispatcherType.REQUEST));
            handler.addFilter(UsersFilter.class, "/users", EnumSet.of(DispatcherType.REQUEST));
            handler.addFilter(UsersFilter.class, "/liked", EnumSet.of(DispatcherType.REQUEST));
            handler.addFilter(MessagesFilter.class, "/message/*", EnumSet.of(DispatcherType.REQUEST));
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
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}

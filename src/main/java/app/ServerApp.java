package app;

import com.mysql.cj.log.Log;
import filters.LoginFilter;
import listeners.MyServletContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.LikedUsersShowServlet;
import servlets.LoginServlet;
import servlets.LikePageServlet;

import javax.servlet.DispatcherType;
import java.util.Arrays;
import java.util.EnumSet;

public class ServerApp {
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase("jetbrains://idea/navigate/reference?project=Step-Project-Tinder&fqn=templates");
            handler.setSessionHandler(new SessionHandler());
            //servlets
            handler.addServlet(LoginServlet.class, "/login");
            handler.addServlet(LikePageServlet.class, "/like-page");
            handler.addServlet(LikedUsersShowServlet.class, "/liked");
            //filters
            handler.addFilter(LoginFilter.class, "/like-page", EnumSet.of(DispatcherType.REQUEST));
            handler.addFilter(LoginFilter.class, "/liked", EnumSet.of(DispatcherType.REQUEST));
            //context-listener
            handler.addEventListener(new MyServletContextListener());
            //context-params
            handler.setInitParameter("dbHost", "ec2-52-203-118-49.compute-1.amazonaws.com");
            handler.setInitParameter("dbPort", "5432");
            handler.setInitParameter("dbName", "dsq4s45dhepp6");
            handler.setInitParameter("dbUser", "vwktrcuywyclvw");
            handler.setInitParameter("dbPassword", "4cf47f217fec1d3ce6628d934794f6ad4bef3a2e62fb3ef66f13580b3e461e0f");
            HandlerList handlerList = new HandlerList(handler, resourceHandler);
            server.setHandler(handlerList);
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}

package listeners;

import dao.controllers.DBController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String dbHost = sc.getInitParameter("dbHost");
        String dbPort = sc.getInitParameter("dbPort");
        String dbName = sc.getInitParameter("dbName");
        String dbUser = sc.getInitParameter("dbUser");
        String dbPassword = sc.getInitParameter("dbPassword");
        String url = String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName);
        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
            DBController dbc = new DBController(conn);
            sc.setAttribute("dbc", dbc);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBController dbc = (DBController) sce.getServletContext().getAttribute("dbc");
        dbc.closeConn();
    }
}

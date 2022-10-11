package listeners;

import dao.controllers.DBController;
import dao.dao.DaoSqlLike;
import dao.dao.DaoSqlMessage;
import dao.dao.DaoSqlUser;
import dao.services.LikeService;
import dao.services.MessageService;
import dao.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServletContextListener implements javax.servlet.ServletContextListener {
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
            sc.setAttribute("DBController", dbc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection conn = (Connection) sce.getServletContext().getAttribute("dbConnection");
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

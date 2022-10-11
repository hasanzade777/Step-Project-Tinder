package entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements Identifiable {
    private long id;
    private String name;
    private String surname;
    private String emailAddress;
    private String username;
    private String password;
    private String profilePicLink;

    public User(long id, String name, String surname, String emailAddress, String username, String password, String profilePicLink) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.profilePicLink = profilePicLink;
    }

    public User(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public static User of(long id, String name, String surname, String emailAddress, String username, String password, String profilePicLink) {
        return new User(id, name, surname, emailAddress, username, password, profilePicLink);
    }

    public static User getFromResultSet(ResultSet rs) {
        try {
            return User.of(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("email_address"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("profile_pic_link"));
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

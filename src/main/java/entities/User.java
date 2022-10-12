package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class User implements Identifiable {
    private long id;
    private String name;
    private String surname;
    private String job;
    private String emailAddress;
    private String username;
    private String password;
    private String profilePicLink;
    private LocalDateTime lastLoginDateTime;
    private LocalDate lastLoginDate;

    public User(long id, String name, String surname, String job, String emailAddress, String username, String password, String profilePicLink, LocalDateTime lastLoginDateTime) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.job = job;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.profilePicLink = profilePicLink;
        this.lastLoginDateTime = lastLoginDateTime;
        this.lastLoginDate = lastLoginDateTime.toLocalDate();
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

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public String getProfilePicLink() {
        return profilePicLink;
    }

    public String getUsername() {
        return username;
    }



    public static User getFromResultSet(ResultSet rs) {
        try {
            return new User(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("job"),
                    rs.getString("email_address"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("profile_pic_link"),
                    rs.getTimestamp("last_login_date_time").toLocalDateTime());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

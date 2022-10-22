package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class User implements Identifiable {
    private Long id;
    private String name;
    private String surname;
    private String job;
    private String emailAddress;
    private String username;
    private String password;
    private String profilePicLink;
    private LocalDateTime lastLoginDateTime;

    public User(Long id, String name, String surname, String job, String emailAddress, String username, String password, String profilePicLink, LocalDateTime lastLoginDateTime) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.job = job;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.profilePicLink = profilePicLink;
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public User(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public User() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDateTime.toLocalDate();
    }

    public String getProfilePicLink() {
        return profilePicLink;
    }

    public String getUsername() {
        return username;
    }


    public String getJob() {
        return job;
    }

    public String getInactiveTimePeriod() {
        String durationInWords = DurationFormatUtils.formatDurationWords(Math.abs(
                        Duration.between(
                                lastLoginDateTime,
                                LocalDateTime.now()).toMillis()),
                true,
                true);
        String[] firstUnit = durationInWords.split(" ");
        return String.format("%s %s", firstUnit[0], firstUnit[1]);
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

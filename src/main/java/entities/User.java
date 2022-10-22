package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.time.DurationFormatUtils;


@Getter
@Setter
@EqualsAndHashCode(of = {"username", "emailAddress"})
@ToString
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

    public String getFullName() {
        return String.format("%s %s", name, surname);
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDateTime.toLocalDate();
    }

    public String getInactiveTimePeriod() {
        String durationInWords = DurationFormatUtils.formatDurationWords(
                Math.abs(Duration.between(lastLoginDateTime, LocalDateTime.now()).toMillis()),
                true,
                true);
        String[] words = durationInWords.split(" ");
        return String.format("%s %s", words[0], words[1]);
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

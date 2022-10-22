package entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.postgresql.copy.CopyDual;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Message implements Identifiable {
    private Long id;
    private Long fromId;
    private Long toId;
    private String message;
    private LocalDateTime dateTimeSent;

    public Message(Long fromId, Long toId, String message) {
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.dateTimeSent = LocalDateTime.now();
    }

    public Message(Long id, Long fromId, Long toId, String message, LocalDateTime dateTimeSent) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.dateTimeSent = dateTimeSent;
    }

    public Message() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public static Message getFromResultSet(ResultSet rs) {
        try {
            return new Message(rs.getLong("ID"),
                    rs.getLong("from_Id"),
                    rs.getLong("to_Id"),
                    rs.getString("message"),
                    rs.getTimestamp("date_time_sent").toLocalDateTime());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String sentWhen() {
        Duration dur = Duration.between(dateTimeSent, LocalDateTime.now());
        if (dur.compareTo(Duration.ofDays(1)) > 0) {
            return dateTimeSent.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        }
        else {
            String durationInWords = DurationFormatUtils.formatDurationWords(
                    Math.abs(dur.toMillis()),
                    true,
                    true);
            String[] words = durationInWords.split(" ");
            return String.format("%s %s ago", words[0], words[1]);
        }
    }
}

package entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
}

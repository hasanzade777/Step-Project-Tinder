package entities;

import java.time.LocalDateTime;

public class Message implements Identifiable {
    private long id;
    private long fromId;
    private long toId;
    private LocalDateTime dateTimeSent;

    public Message(long id, long fromId, long toId, LocalDateTime dateTimeSent) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.dateTimeSent = dateTimeSent;
    }

    @Override
    public long getId() {
        throw new RuntimeException();
    }
}

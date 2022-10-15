package entities;

import java.time.LocalDateTime;

public class Message implements Identifiable {
    private long id;
    private long fromId;
    private long toId;
    private String message;
    private LocalDateTime dateTimeSent;

    public Message(long id, long fromId, long toId, String message, LocalDateTime dateTimeSent) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.dateTimeSent = dateTimeSent;
    }
    public Message(){}

    @Override
    public long getId() {
        throw new RuntimeException();
    }
}

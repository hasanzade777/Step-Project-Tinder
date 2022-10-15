package entities;

import java.time.LocalDateTime;

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
    public Message(){}

    @Override
    public Long getId() {
        throw new RuntimeException();
    }
}

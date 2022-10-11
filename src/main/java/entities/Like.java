package entities;

public class Like implements Identifiable {
    private long id;
    private long likerUserId;
    private long likedUserId;

    public Like(long id, long likerUserId, long likedUserId) {
        this.id = id;
        this.likerUserId = likerUserId;
        this.likedUserId = likedUserId;
    }

    @Override
    public long getId() {
        throw new RuntimeException();
    }
}

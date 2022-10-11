package entities;

public class User implements Identifiable {
    private long id;
    private String username;
    private String profilePicLink;

    public User(long id, String username, String profilePicLink) {
        this.id = id;
        this.username = username;
        this.profilePicLink = profilePicLink;
    }

    @Override
    public long getId() {
        throw new RuntimeException();
    }
}

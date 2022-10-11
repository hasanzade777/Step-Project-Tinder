package entities;

public class User implements Identifiable {
    private long id;
    private String name;
    private String surname;
    private String username;
    private String profilePicLink;

    public User(long id, String name, String surname, String username, String profilePicLink) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.profilePicLink = profilePicLink;
    }

    @Override
    public long getId() {
        throw new RuntimeException();
    }
}

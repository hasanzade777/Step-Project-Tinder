package entities;

public class User implements Identifiable {
    private long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String profilePicLink;

    public User(long id, String name, String surname, String username, String password, String profilePicLink) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.profilePicLink = profilePicLink;
    }

    @Override
    public long getId() {
        throw new RuntimeException();
    }
}

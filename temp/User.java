@IgnoreExtraProperties
public class User {

    public String username;
    public String description;
    public String photoURL;

    public User() {
        username = "";
        description = "";
        photoURL = "";
    }

    public User(String username, String email, String photoURL) {
        this.username = username;
        this.description = email;
        this.photoURL = photoURL;
    }

}
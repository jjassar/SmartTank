public class User {


    public String username;
    public String email;


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public User() {
     //Must
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}

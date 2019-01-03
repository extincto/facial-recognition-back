package epsi.javamspr.springbootapi.Models;


public class User {
    public String FirstName;
    public String LastName;

    public User(String format) {}
    public User(String FirsName, String LastName) {
        this.FirstName = FirsName;
        this.LastName = LastName;
    }
}


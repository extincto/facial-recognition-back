package epsi.javamspr.springbootapi.Models;


public class User {
    public Number Id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Sexe;
    public String ImageUrl;

    //********** CONSTRUCTEUR ************

    public User(){

    }
    public User(String FirsName, String LastName, String Email, String Sexe, String ImageUrl) {
        this.FirstName = FirsName;
        this.LastName = LastName;
        this.Email = Email;
        this.Sexe = Sexe;
        this.ImageUrl = ImageUrl;
    }

    public User(String FirsName, String LastName, String Email, String Sexe, Number Id, String ImageUrl) {
        this.Id = Id;
        this.FirstName = FirsName;
        this.LastName = LastName;
        this.Email = Email;
        this.Sexe = Sexe;
        this.ImageUrl = ImageUrl;

    }
    //**********GETTER SETTER ************
    //Id
    public Number getId() {
        return Id;
    }
    public void setId(Number id) {
        Id = id;
    }

    // FirstName
    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    // LastName
    public String getLastName() {
        return LastName;
    }
    public void setLastName(String lastName) {
        LastName = lastName;
    }

    // Email
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }

    // Sexe
    public String getSexe() {
        return Sexe;
    }
    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    //ImageUrl
    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}


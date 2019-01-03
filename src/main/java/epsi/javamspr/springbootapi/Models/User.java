package epsi.javamspr.springbootapi.Models;


public class User {
    private int Id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Sexe;

    //********** CONSTRUCTEUR ************
    public User(String FirsName, String LastName, String Email, String Sexe) {
        this.FirstName = FirsName;
        this.LastName = LastName;
        this.Email = Email;
        this.Sexe = Sexe;
    }

    public User(String FirsName, String LastName, String Email, String Sexe, int Id) {
        this.Id = Id;
        this.FirstName = FirsName;
        this.LastName = LastName;
        this.Email = Email;
        this.Sexe = Sexe;
    }
    //**********GETTER SETTER ************
    //Id
    public int getId() {
        return Id;
    }
    public void setId(int id) {
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
}


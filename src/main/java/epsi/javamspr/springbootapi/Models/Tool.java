package epsi.javamspr.springbootapi.Models;

import com.google.firebase.internal.NonNull;

import java.util.Map;

public class Tool {
    private int Id;
    private String Name;
    private int Quantity;

    //********** CONSTRUCTEUR ************
    public Tool(String Name, int Quantity, int Id){
        this.Id = Id;
        this.Name = Name;
        this.Quantity = Quantity;
    }

    public Tool(String Name, int Quantity){
        this.Name = Name;
        this.Quantity = Quantity;
    }
    public Tool()  {

    }

    //**********GETTER SETTER ************
    //Id
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }

    //Name
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    // Quantity
    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Tool withId(@NonNull final String Id) {
        int NewId = Integer.parseInt(Id);
        this.Id = NewId;
        return (Tool) this;
    }
}


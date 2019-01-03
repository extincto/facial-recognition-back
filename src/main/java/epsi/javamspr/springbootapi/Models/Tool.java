package epsi.javamspr.springbootapi.Models;

public class Tool {
    private int Id;
    private String Name;
    private Number Quantity;

    //********** CONSTRUCTEUR ************
    public Tool(String Name, Number Quantity, int Id){
        this.Id = Id;
        this.Name = Name;
        this.Quantity = Quantity;
    }

    public Tool(String Name, Number Quantity){
        this.Name = Name;
        this.Quantity = Quantity;
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
    public Number getQuantity() {
        return Quantity;
    }
    public void setQuantity(Number quantity) {
        Quantity = quantity;
    }
}

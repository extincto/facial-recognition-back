package epsi.javamspr.springbootapi.Models;

public class Tool {
    public Object Name;
    public Number Quantity;

    public Tool(String format) {
    }
    public Tool(String Name, Number Quantity){
        this.Name = Name;
        this.Quantity = Quantity;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setQuantity(Number Quantity) {
        this.Quantity = Quantity;
    }

    @Override
    public String toString() {
        return String.format("Tool [Name=%s, Quantity=%s]", Name, Quantity);
    }
}

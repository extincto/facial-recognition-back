package epsi.javamspr.springbootapi.Models;
import java.util.ArrayList;

public class Loan {
    private int Id;
    private ArrayList<Double> ToolId;
    private int UserId;

    //********** CONSTRUCTEUR ************
    public Loan(int Id, ArrayList<Double> ToolId, int UserId){
        this.Id = Id;
        this.ToolId = ToolId;
        this.UserId = UserId;
    }

    public Loan( ArrayList<Double> ToolId, int UserId){
        this.ToolId = ToolId;
        this.UserId = UserId;
    }

    public Loan (){
    }

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }

    public ArrayList<Double> getToolId() {
        return ToolId;
    }
    public void setToolId(ArrayList<Double> toolId) {
        ToolId = toolId;
    }

    public int getUserId() {
        return UserId;
    }
    public void setUserId(int userId) {
        UserId = userId;
    }
}

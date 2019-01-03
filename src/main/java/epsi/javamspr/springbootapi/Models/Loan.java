package epsi.javamspr.springbootapi.Models;
import java.util.Date;

public class Loan {
    private int Id;
    private Date LoanDate;
    private Date ReturnDate;
    private Number ToolId;
    private Number UserId;
    private int Quantity;

    //********** CONSTRUCTEUR ************
    public Loan(int Id, Date LoanDate, Date ReturnDate, Number ToolId, Number UserId, int Quantity){
        this.Id = Id;
        this.LoanDate = LoanDate;
        this.ReturnDate = ReturnDate;
        this.ToolId = ToolId;
        this.UserId = UserId;
        this.Quantity = Quantity;
    }

    public Loan(Date LoanDate, Date ReturnDate, Number ToolId, Number UserId, int Quantity){
        this.LoanDate = LoanDate;
        this.ReturnDate = ReturnDate;
        this.ToolId = ToolId;
        this.UserId = UserId;
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

    // LoanDate
    public Date getLoanDate() {
        return LoanDate;
    }
    public void setLoanDate(Date loanDate) {
        LoanDate = loanDate;
    }

    // ReturnDate
    public Date getReturnDate() {
        return ReturnDate;
    }
    public void setReturnDate(Date returnDate) {
        ReturnDate = returnDate;
    }

    // ToolId
    public Number getToolId() {
        return ToolId;
    }
    public void setToolId(Number toolId) {
        ToolId = toolId;
    }

    // UserId
    public Number getUserId() {
        return UserId;
    }
    public void setUserId(Number userId) {
        UserId = userId;
    }

    // Quantity
    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}

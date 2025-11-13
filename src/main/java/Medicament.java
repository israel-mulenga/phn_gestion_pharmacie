import java.util.Date;

public class Medicament {
    public String commercialName;
    public String code;
    public String productorLaboratory;
    public int stock;
    public double price;
    public Date expirationDate;

    public Medicament(String commercialName, String code, String productorLaboratory, int stock, double price, Date expirationDate) {
        this.commercialName = commercialName;
        this.code = code;
        this.productorLaboratory = productorLaboratory;
        this.stock = stock;
        this.price = price;
        this.expirationDate = expirationDate;
    }
    public void displayInfo(){
        System.out.println("Commercial Name: " + this.commercialName);
        System.out.println("Code: " + this.code);
        System.out.println("Productor Laboratory: " + this.productorLaboratory);
        System.out.println("Stock: " + this.stock);
        System.out.println("Price: " + this.price);
        System.out.println("Expiration Date: " + this.expirationDate);
    }
    public void updateStock(int quantity){
        this.stock += quantity;
        System.out.println("Updated stock: " + this.stock);
    }
}

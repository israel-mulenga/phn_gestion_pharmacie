import java.util.Date;

public class Pomade extends Medicament{
    public int quantity;
    public String spot;

    public Pomade(String commercialName, String code, String productorLaboratory, int stock, int price, int quantity, Date expirationDate, String spot) {
        super(commercialName, code, productorLaboratory, stock, price, expirationDate);
        this.quantity = quantity;
        this.spot = spot;
    }

    @Override
    public void displayInfo()
    {
        super.displayInfo();
        System.out.println("Quantity: " + quantity);
        System.out.println("Spot: " + spot);
    }
}
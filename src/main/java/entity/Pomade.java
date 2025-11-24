package entity;

import java.time.LocalDate;

public class Pomade extends Medicament{
    private int quantity;
    private String spot;

    public Pomade(String commercialName, String code, String productorLaboratory, int stock, double price, int quantity, LocalDate expirationDate, String spot) {
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

    @Override
    public String getSpecificData() {
        return String.format("Qte: %d g, Zone: %s", quantity, spot);
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }
    public String getSpot() {
        return spot;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }
}
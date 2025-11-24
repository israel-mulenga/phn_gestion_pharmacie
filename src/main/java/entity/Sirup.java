package entity;

import java.time.LocalDate;

public class Sirup extends Medicament {
    private int volumeMl;
    private String taste;

    public Sirup(String commercialName, String code, String productorLaboratory, int stock, double price, LocalDate expirationDate, int volumeMl, String taste) {
        super(commercialName, code, productorLaboratory, stock, price, expirationDate);
        this.volumeMl = volumeMl;
        this.taste = taste;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Volume: " + volumeMl + " ml");
        System.out.println("Goût: " + taste);
    }

    @Override
    public String getSpecificData() {
        return String.format("Vol: %d ml, Goût: %s", volumeMl, taste);
    }

    // Getters et Setters
    public int getVolumeMl() { return volumeMl; }
    public void setVolumeMl(int volumeMl) { this.volumeMl = volumeMl; }
    public String getTaste() { return taste; }
    public void setTaste(String taste) { this.taste = taste; }
}
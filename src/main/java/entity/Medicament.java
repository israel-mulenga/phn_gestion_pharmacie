package entity;

import java.time.LocalDate;

public abstract class Medicament {
    private String commercialName;
    private String code;
    private String productorLaboratory;
    private int stock;
    private double price;
    private LocalDate expirationDate;

    public Medicament(String commercialName, String code, String productorLaboratory, int stock, double price, LocalDate expirationDate) {
        this.commercialName = commercialName;
        this.code = code;
        this.productorLaboratory = productorLaboratory;
        this.stock = stock;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    public void displayInfo(){
        System.out.println("Nom commercial: " + this.commercialName);
        System.out.println("Code: " + this.code);
        System.out.println("Laboratoire: " + this.productorLaboratory);
        System.out.println("Stock: " + this.stock);
        System.out.println("Prix: " + this.price);
        System.out.println("Date d'expiration: " + this.expirationDate);
    }

    public void updateStock(int quantity){
        this.stock += quantity;
        System.out.println("Stock mis à jour: " + this.stock);
    }

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductorLaboratory() {
        return productorLaboratory;
    }

    public void setProductorLaboratory(String productorLaboratory) {
        this.productorLaboratory = productorLaboratory;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public abstract String getSpecificData();
}
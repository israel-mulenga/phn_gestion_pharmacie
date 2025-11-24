package entity;

import java.time.LocalDate;

public  class Injection  extends Medicament{
    private int volumeMl;
    private String administrationRoad;

    public Injection(String commercialName, String code, String productorLaboratory, int stock, double price, int volumeMl, LocalDate expirationDate, String administrationRoad) {
        super(commercialName, code, productorLaboratory, stock, price, expirationDate);
        this.volumeMl = volumeMl;
        this.administrationRoad = administrationRoad;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Volume Ml: " + volumeMl);
        System.out.println("Administration Road: " + administrationRoad);
    }
    @Override
    public String getSpecificData() {
        return String.format("Vol: %d ml, Voie: %s", volumeMl, administrationRoad);
    }
    public int getVolumeMl() {
        return this.volumeMl;
    }
    public String getAdministrationRoad() {
        return this.administrationRoad;
    }
    public void setVolumeMl(int volumeMl) {
        this.volumeMl = volumeMl;
    }
    public void setAdministrationRoad(String administrationRoad) {
        this.administrationRoad = administrationRoad;
    }
}
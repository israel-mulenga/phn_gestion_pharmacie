import java.util.Date;

public  class Injection  extends Medicament{
    public int volumeMl;
    public String administrationRoad;

    public Injection(String commercialName, String code, String productorLaboratory, int stock, int price, int volumeMl, Date expirationDate, String administrationRoad) {
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
}
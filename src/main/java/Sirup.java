import java.util.Date;

public class Sirup extends Medicament {
    public int volumeMl;

    public Sirup(String commercialName, String code, String productorLaboratory, int stock, int price, int volumeMl, Date expirationDate) {
        super(commercialName, code, productorLaboratory, stock, price, expirationDate);
        this.volumeMl = volumeMl;
    }
    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Volume Ml: " + volumeMl);
    }
}
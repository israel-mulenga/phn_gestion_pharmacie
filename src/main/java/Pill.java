import java.util.Date;

public class Pill extends Medicament {
    private final int dosageMg;
    private final int nbrPills;

    public Pill(String commercialName, String code, String productorLaboratory, int stock, int price, Date expirationDate,int dosageMg, int  nbrPills) {
        super(commercialName, code, productorLaboratory, stock, price, expirationDate);
        this.dosageMg = dosageMg;
        this.nbrPills = nbrPills;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Dosage: " + dosageMg + " mg");
        System.out.println("Pills number: " + nbrPills);
    }
}
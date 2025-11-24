import java.time.LocalDate;

public class Pill extends Medicament {
    private int dosageMg;
    private int nbrPills;

    public Pill(String commercialName, String code, String productorLaboratory, int stock, double price, LocalDate expirationDate,int dosageMg, int  nbrPills) {
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
    public void setDosageMg(int dosageMg) {
        this.dosageMg = dosageMg;
    }
    public int getDosageMg() {
        return dosageMg;
    }
    public void setNbrPills(int nbrPills) {
        this.nbrPills = nbrPills;
    }
    public int getNbrPills() {
        return nbrPills;
    }
}
import java.time.LocalDate;

public class Sirup extends Medicament {
    private int volumeMl;
    private String gout;

    public Sirup(String commercialName, String code, String productorLaboratory, int stock, double price, int volumeMl, LocalDate expirationDate, String gout) {
        super(commercialName, code, productorLaboratory, stock, price, expirationDate);
        this.volumeMl = volumeMl;
        this.gout = gout;
    }
    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Volume Ml: " + volumeMl);
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(int volumeMl) {
        this.volumeMl = volumeMl;
    }
    private String getGout() {
        return gout;
    }
    public void setGout(String gout) {
        this.gout = gout;
    }
}
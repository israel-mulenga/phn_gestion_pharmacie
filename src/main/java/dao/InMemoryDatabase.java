package dao;

import entity.Medicament;

import java.util.List;

public class InMemoryDatabase {
    public static List<Medicament> medicaments = JsonManager.load();

    public static void saveState(){
        JsonManager.save(medicaments);
    }

    public static boolean existsByCode(String code) {
        for (Medicament m : medicaments) {
            if (m.getCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public static Medicament findByCode(String code) {
        for (Medicament m : medicaments) {
            if (m.getCode().equalsIgnoreCase(code)) {
                return m;
            }
        }
        return null;
    }
}
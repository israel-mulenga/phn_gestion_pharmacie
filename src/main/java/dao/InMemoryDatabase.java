package dao;

import entity.Medicament;
import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabase {
    public static List<Medicament> medicaments = new ArrayList<>();

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
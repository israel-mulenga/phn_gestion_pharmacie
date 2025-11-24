package dao;

import entity.Medicament;
import entity.Pill;
import java.util.ArrayList;
import java.util.List;

public class PillService implements IMedicamentService<Pill> {

    @Override
    public boolean create(Pill item) {
        if (InMemoryDatabase.existsByCode(item.getCode())) {
            System.out.println("Erreur : Code existant !");
            return false;
        }
        InMemoryDatabase.medicaments.add(item);
        return true;
    }

    @Override
    public List<Pill> readAll() {
        List<Pill> pills = new ArrayList<>();
        for (Medicament m : InMemoryDatabase.medicaments) {
            if (m instanceof Pill) pills.add((Pill) m);
        }
        return pills;
    }

    @Override
    public Pill readOne(String code) {
        Medicament m = InMemoryDatabase.findByCode(code);
        return (m instanceof Pill) ? (Pill) m : null;
    }

    @Override
    public boolean update(String code, Pill newItem) {
        Pill current = readOne(code);
        if (current != null) {
            current.setCommercialName(newItem.getCommercialName());
            current.setProductorLaboratory(newItem.getProductorLaboratory());
            current.setStock(newItem.getStock());
            current.setPrice(newItem.getPrice());
            current.setExpirationDate(newItem.getExpirationDate());
            // Spécifique Pill
            current.setDosageMg(newItem.getDosageMg());
            current.setNbrPills(newItem.getNbrPills());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String code) {
        Pill p = readOne(code);
        if (p != null) {
            InMemoryDatabase.medicaments.remove(p);
            return true;
        }
        return false;
    }
}
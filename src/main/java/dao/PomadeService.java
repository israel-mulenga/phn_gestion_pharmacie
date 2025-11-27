package dao;

import entity.Medicament;
import entity.Pomade;
import java.util.ArrayList;
import java.util.List;

public class PomadeService implements IMedicamentService<Pomade> {

    @Override
    public boolean create(Pomade item) {
        if (InMemoryDatabase.existsByCode(item.getCode())) {
            System.out.println("Erreur : Code existant !");
            return false;
        }
        InMemoryDatabase.medicaments.add(item);
        InMemoryDatabase.saveState();
        return true;
    }

    @Override
    public List<Pomade> readAll() {
        List<Pomade> Pomades = new ArrayList<>();
        for (Medicament m : InMemoryDatabase.medicaments) {
            if (m instanceof Pomade) Pomades.add((Pomade) m);
        }
        return Pomades;
    }

    @Override
    public Pomade readOne(String code) {
        Medicament m = InMemoryDatabase.findByCode(code);
        return (m instanceof Pomade) ? (Pomade) m : null;
    }

    @Override
    public boolean update(String code, Pomade newItem) {
        Pomade current = readOne(code);
        if (current != null) {
            current.setCommercialName(newItem.getCommercialName());
            current.setProductorLaboratory(newItem.getProductorLaboratory());
            current.setStock(newItem.getStock());
            current.setPrice(newItem.getPrice());
            current.setExpirationDate(newItem.getExpirationDate());
            current.setQuantity(newItem.getQuantity());
            current.setSpot(newItem.getSpot());
            InMemoryDatabase.saveState();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String code) {
        Pomade p = readOne(code);
        if (p != null) {
            InMemoryDatabase.medicaments.remove(p);
            InMemoryDatabase.saveState();
            return true;
        }
        return false;
    }
}
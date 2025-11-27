package dao;

import entity.Medicament;
import entity.Sirup;
import java.util.ArrayList;
import java.util.List;

public class SirupService implements IMedicamentService<Sirup> {

    @Override
    public boolean create(Sirup item) {
        if (InMemoryDatabase.existsByCode(item.getCode())) {
            System.out.println("Erreur : Un médicament avec ce code existe déjà !");
            return false;
        }
        InMemoryDatabase.medicaments.add(item);
        InMemoryDatabase.saveState();
        return true;
    }

    @Override
    public List<Sirup> readAll() {
        List<Sirup> sirups = new ArrayList<>();
        for (Medicament m : InMemoryDatabase.medicaments) {
            if (m instanceof Sirup) {
                sirups.add((Sirup) m);
            }
        }
        return sirups;
    }

    @Override
    public Sirup readOne(String code) {
        Medicament m = InMemoryDatabase.findByCode(code);
        if (m instanceof Sirup) {
            return (Sirup) m;
        }
        return null;
    }

    @Override
    public boolean update(String code, Sirup newItem) {
        Sirup current = readOne(code);
        if (current != null) {
            // Mise à jour des attributs communs
            current.setCommercialName(newItem.getCommercialName());
            current.setProductorLaboratory(newItem.getProductorLaboratory());
            current.setStock(newItem.getStock());
            current.setPrice(newItem.getPrice());
            current.setExpirationDate(newItem.getExpirationDate());

            // Mise à jour des attributs spécifiques
            current.setVolumeMl(newItem.getVolumeMl());
            current.setTaste(newItem.getTaste());
            InMemoryDatabase.saveState();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String code) {
        Sirup s = readOne(code);
        if (s != null) {
            InMemoryDatabase.medicaments.remove(s);
            InMemoryDatabase.saveState();
            return true;
        }
        return false;
    }
}
package dao;

import entity.Medicament;
import entity.Injection;
import java.util.ArrayList;
import java.util.List;

public class InjectionService implements IMedicamentService<Injection> {

    @Override
    public boolean create(Injection item) {
        if (InMemoryDatabase.existsByCode(item.getCode())) {
            System.out.println("Erreur : Un médicament avec ce code existe déjà !");
            return false;
        }
        InMemoryDatabase.medicaments.add(item);
        return true;
    }

    @Override
    public List<Injection> readAll() {
        List<Injection> injections = new ArrayList<>();
        for (Medicament m : InMemoryDatabase.medicaments) {
            if (m instanceof Injection) {
                injections.add((Injection) m);
            }
        }
        return injections;
    }

    @Override
    public boolean update(String code, Injection newItem) {
        Injection current = readOne(code);
        if (current != null) {
            // Mise à jour des attributs communs
            current.setCommercialName(newItem.getCommercialName());
            current.setProductorLaboratory(newItem.getProductorLaboratory());
            current.setStock(newItem.getStock());
            current.setPrice(newItem.getPrice());
            current.setExpirationDate(newItem.getExpirationDate());

            // Mise à jour des attributs spécifiques
            current.setVolumeMl(newItem.getVolumeMl());
            current.setAdministrationRoad(newItem.getAdministrationRoad());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String code) {
        Injection injection = readOne(code);
        if (injection != null) {
            InMemoryDatabase.medicaments.remove(injection);
            return true;
        }
        return false;
    }

    @Override
    public Injection readOne(String code) {
        Medicament injection = InMemoryDatabase.findByCode(code);
        if (injection instanceof Injection) {
            return (Injection) injection;
        }
        return null;
    }

}
import dao.*;
import entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Instanciation des services spécifiques
    private static SirupService sirupService = new SirupService();
    private static PillService pillService = new PillService();
    private static InjectionService injectionService = new InjectionService(); // A créer
    private static PomadeService pomadeService = new PomadeService(); // A créer

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1: menuCreation(); break;
                case 2: menuSuppression(); break;
                case 3: menuModification(); break;
                case 4: menuAffichage(); break;
                case 0:
                    System.out.println("Au revoir !");
                    System.exit(0);
                default: System.out.println("Option invalide.");
            }
        }
    }

    // ================= NAVIGATION PRINCIPALE =================
    private static void showMainMenu() {
        System.out.println("\n=== PHARMACIE GESTION ===");
        System.out.println("1. Ajouter un médicament");
        System.out.println("2. Supprimer un médicament");
        System.out.println("3. Modifier un médicament");
        System.out.println("4. Afficher les médicaments");
        System.out.println("0. Quitter");
        System.out.print("Choix : ");
    }

    // ================= 1. MENU CRÉATION =================
    private static void menuCreation() {
        System.out.println("\n--- AJOUT ---");
        System.out.println("1. Ajouter Sirop");
        System.out.println("2. Ajouter Comprimé");
        System.out.println("3. Ajouter Injection");
        System.out.println("4. Ajouter Pommade");
        System.out.println("0. Retour");

        int type = getIntInput();
        if (type == 0) return;

        // Saisie commune
        System.out.print("Code : "); String code = scanner.nextLine();

        // Vérification immédiate du doublon avant de continuer
        if (InMemoryDatabase.existsByCode(code)) {
            System.out.println("❌ Ce code existe déjà ! Annulation.");
            return;
        }

        System.out.print("Nom : "); String name = scanner.nextLine();
        System.out.print("Labo : "); String lab = scanner.nextLine();
        System.out.print("Stock : "); int stock = getIntInput();
        System.out.print("Prix : "); double price = getDoubleInput();
        LocalDate date = getDateInput();

        switch (type) {
            case 1:
                System.out.print("Volume (ml) : "); int vol = getIntInput();
                System.out.print("Goût : "); String taste = scanner.nextLine();
                sirupService.create(new Sirup(name, code, lab, stock, price, date, vol, taste));
                break;
            case 2:
                System.out.print("Dosage (mg) : "); int dosage = getIntInput();
                System.out.print("Nbr Comprimés : "); int nbr = getIntInput();
                pillService.create(new Pill(name, code, lab, stock, price, date, dosage, nbr));
                break;
            case 3:
                System.out.print("Volume (ml) : "); int volInj = getIntInput();
                System.out.print("Voie : "); String road = scanner.nextLine();
                injectionService.create(new Injection(name, code, lab, stock, price, volInj, date, road));
                break;
            case 4:
                System.out.print("Quantité : "); int qte = getIntInput();
                System.out.print("Zone : "); String spot = scanner.nextLine();
                pomadeService.create(new Pomade(name, code, lab, stock, price, qte, date, spot));
                break;
        }
        System.out.println("✅ Médicament ajouté.");
    }

    // ================= 2. MENU SUPPRESSION =================
    private static void menuSuppression() {
        System.out.println("\n--- SUPPRESSION ---");
        System.out.print("Entrez le code : ");
        String code = scanner.nextLine();

        // On peut utiliser n'importe quel service ou la DB directement car delete vérifie tout
        // Mais pour rester cohérent avec l'interface, on cherche le type
        Medicament m = InMemoryDatabase.findByCode(code);
        if (m == null) {
            System.out.println("❌ Code introuvable.");
            return;
        }

        boolean res = false;
        if (m instanceof Sirup) res = sirupService.delete(code);
        else if (m instanceof Pill) res = pillService.delete(code);
        else if (m instanceof Injection) res = injectionService.delete(code);
        else if (m instanceof Pomade) res = pomadeService.delete(code);

        if(res) System.out.println("✅ Supprimé.");
    }

    // ================= 3. MENU MODIFICATION =================
    private static void menuModification() {
        System.out.println("\n--- MODIFICATION ---");
        System.out.print("Entrez le code du médicament à modifier : ");
        String code = scanner.nextLine();

        // On cherche le médicament dans la base globale
        Medicament m = InMemoryDatabase.findByCode(code);

        if (m == null) {
            System.out.println("❌ Erreur : Aucun médicament trouvé avec ce code.");
            return;
        }

        System.out.println(">> Médicament trouvé : " + m.getCommercialName());

        // Détection automatique du type pour afficher le bon menu
        if (m instanceof Sirup) {
            updateSirupMenu((Sirup) m);
        } else if (m instanceof Pill) {
            updatePillMenu((Pill) m);
        } else if (m instanceof Injection) {
            updateInjectionMenu((Injection) m);
        } else if (m instanceof Pomade) {
            updatePomadeMenu((Pomade) m);
        } else {
            System.out.println("Type de médicament non géré.");
        }
    }


    private static void updateSirupMenu(Sirup s) {
        boolean editing = true;
        while (editing) {
            System.out.println("\n--- ÉDITION SIROP [" + s.getCommercialName() + "] ---");
            System.out.println("1. Modifier Nom commercial");
            System.out.println("2. Modifier Prix");
            System.out.println("3. Modifier Stock");
            System.out.println("4. Modifier Labo");
            System.out.println("---------------------------");
            System.out.println("5. Modifier Volume (Spécifique)");
            System.out.println("6. Modifier Goût (Spécifique)");
            System.out.println("0. Terminer et Enregistrer");
            System.out.print("Votre choix : ");

            int choice = getIntInput();

            switch (choice) {
                case 1: s.setCommercialName(getStringInput("Nouveau nom : ")); break;
                case 2: s.setPrice(getDoubleInput()); break;
                case 3: s.setStock(getIntInput()); break;
                case 4: s.setProductorLaboratory(getStringInput("Nouveau Labo : ")); break;
                case 5: s.setVolumeMl(getIntInput()); break; // Spécifique
                case 6: s.setTaste(getStringInput("Nouveau Goût : ")); break; // Spécifique
                case 0:
                    editing = false;
                    System.out.println("✅ Modifications enregistrées pour le Sirop.");
                    break;
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private static void updatePillMenu(Pill p) {
        boolean editing = true;
        while (editing) {
            System.out.println("\n--- ÉDITION COMPRIMÉ [" + p.getCommercialName() + "] ---");
            System.out.println("1. Modifier Nom commercial");
            System.out.println("2. Modifier Prix");
            System.out.println("3. Modifier Stock");
            System.out.println("4. Modifier Labo");
            System.out.println("---------------------------");
            System.out.println("5. Modifier Dosage mg (Spécifique)");
            System.out.println("6. Modifier Nb Comprimés (Spécifique)");
            System.out.println("0. Terminer et Enregistrer");
            System.out.print("Votre choix : ");

            int choice = getIntInput();

            switch (choice) {
                case 1: p.setCommercialName(getStringInput("Nouveau nom : ")); break;
                case 2: p.setPrice(getDoubleInput()); break;
                case 3: p.setStock(getIntInput()); break;
                case 4: p.setProductorLaboratory(getStringInput("Nouveau Labo : ")); break;
                case 5: p.setDosageMg(getIntInput()); break; // Spécifique
                case 6: p.setNbrPills(getIntInput()); break; // Spécifique
                case 0:
                    editing = false;
                    System.out.println("✅ Modifications enregistrées pour le Comprimé.");
                    break;
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private static void updateInjectionMenu(Injection i) {
        boolean editing = true;
        while (editing) {
            System.out.println("\n--- ÉDITION INJECTION [" + i.getCommercialName() + "] ---");
            System.out.println("1. Modifier Nom / 2. Prix / 3. Stock / 4. Labo");
            System.out.println("---------------------------");
            System.out.println("5. Modifier Volume ml (Spécifique)");
            System.out.println("6. Modifier Voie d'admin (Spécifique)");
            System.out.println("0. Terminer");
            System.out.print("Votre choix : ");

            int choice = getIntInput();

            switch (choice) {
                case 1: i.setCommercialName(getStringInput("Nouveau nom : ")); break;
                case 2: i.setPrice(getDoubleInput()); break;
                case 3: i.setStock(getIntInput()); break;
                case 4: i.setProductorLaboratory(getStringInput("Nouveau Labo : ")); break;
                case 5: i.setVolumeMl(getIntInput()); break;
                case 6: i.setAdministrationRoad(getStringInput("Nouvelle voie (IV/IM) : ")); break;
                case 0: editing = false; break;
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private static void updatePomadeMenu(Pomade po) {
        boolean editing = true;
        while (editing) {
            System.out.println("\n--- ÉDITION POMMADE [" + po.getCommercialName() + "] ---");
            System.out.println("1. Modifier Nom / 2. Prix / 3. Stock / 4. Labo");
            System.out.println("---------------------------");
            System.out.println("5. Modifier Quantité (Spécifique)");
            System.out.println("6. Modifier Zone (Spécifique)");
            System.out.println("0. Terminer");
            System.out.print("Votre choix : ");

            int choice = getIntInput();

            switch (choice) {
                case 1: po.setCommercialName(getStringInput("Nouveau nom : ")); break;
                case 2: po.setPrice(getDoubleInput()); break;
                case 3: po.setStock(getIntInput()); break;
                case 4: po.setProductorLaboratory(getStringInput("Nouveau Labo : ")); break;
                case 5: po.setQuantity(getIntInput()); break;
                case 6: po.setSpot(getStringInput("Nouvelle zone : ")); break;
                case 0: editing = false; break;
                default: System.out.println("Choix invalide.");
            }
        }
    }
    // ================= 4. MENU AFFICHAGE =================
    private static void menuAffichage() {
        System.out.println("\n--- AFFICHAGE ---");
        System.out.println("1. Tout afficher");
        System.out.println("2. Afficher Sirops uniquement");
        System.out.println("3. Afficher Comprimés uniquement");
        System.out.println("4. Afficher Injections uniquement");
        System.out.println("5. Afficher Pommades uniquement");
        System.out.println("0. Retour");

        int choice = getIntInput();
        if (choice == 0) return;

        List<? extends Medicament> list = null;

        switch (choice) {
            case 1: list = InMemoryDatabase.medicaments; break; // Tout
            case 2: list = sirupService.readAll(); break;
            case 3: list = pillService.readAll(); break;
            case 4: list = injectionService.readAll(); break;
            case 5: list = pomadeService.readAll(); break;
            default: System.out.println("Option invalide"); return;
        }

        if (list == null || list.isEmpty()) {
            System.out.println("Aucun élément trouvé.");
        } else {
            for (Medicament m : list) {
                System.out.println("--------------------");
                m.displayInfo();
            }
        }
    }

    // ================= HELPERS =================
    private static int getIntInput() {
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (Exception e) { return -1; }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static double getDoubleInput() {
        try { System.out.print("Valeur : "); return Double.parseDouble(scanner.nextLine()); }
        catch (Exception e) { return 0.0; }
    }

    private static LocalDate getDateInput() {
        System.out.print("Date (2025-01-01) : ");
        try { return LocalDate.parse(scanner.nextLine()); }
        catch (Exception e) { return LocalDate.now(); }
    }
}
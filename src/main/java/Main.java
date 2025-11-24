import dao.*;
import entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Services
    private static SirupService sirupService = new SirupService();
    private static PillService pillService = new PillService();
    private static InjectionService injectionService = new InjectionService();
    private static PomadeService pomadeService = new PomadeService();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Boucle principale du programme
        while (true) {
            showMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1: menuCreation(); break;
                case 2: menuSuppression(); break;
                case 3: menuModification(); break;
                case 4: menuAffichage(); break;
                case 0:
                    System.out.println("Fermeture de l'application. Au revoir !");
                    System.exit(0);
                    break;
                default: System.out.println("❌ Option invalide.");
            }
        }
    }

    // ================= NAVIGATION PRINCIPALE =================
    private static void showMainMenu() {
        System.out.println("\n==========================================");
        System.out.println("      💊 GESTION DE PHARMACIE v1.0 💊     ");
        System.out.println("==========================================");
        System.out.println("1. [+] Ajouter un médicament");
        System.out.println("2. [-] Supprimer un médicament");
        System.out.println("3. [✎] Modifier un médicament");
        System.out.println("4. [👁] Afficher le stock (Tableau)");
        System.out.println("------------------------------------------");
        System.out.println("0. [X] Quitter");
        System.out.print("👉 Votre choix : ");
    }

    // ================= 1. MENU CRÉATION =================
    private static void menuCreation() {
        System.out.println("\n--- AJOUT D'UN NOUVEAU MÉDICAMENT ---");
        System.out.println("1. Ajouter Sirop");
        System.out.println("2. Ajouter Comprimé");
        System.out.println("3. Ajouter Injection");
        System.out.println("4. Ajouter Pommade");
        System.out.println("0. Retour au menu principal");
        System.out.print("👉 Choix : ");

        int type = getIntInput();
        if (type == 0) return; // Retour au menu principal

        System.out.print("Code unique : ");
        String code = scanner.nextLine();

        if (InMemoryDatabase.existsByCode(code)) {
            System.out.println("❌ Erreur : Ce code existe déjà dans la base !");
            pause();
            return;
        }

        System.out.print("Nom commercial : "); String name = scanner.nextLine();
        System.out.print("Laboratoire : "); String lab = scanner.nextLine();
        System.out.print("Stock initial : "); int stock = getIntInput();
        System.out.print("Prix unitaire : "); double price = getDoubleInput();
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
                System.out.print("Voie (IV/IM) : "); String road = scanner.nextLine();
                injectionService.create(new Injection(name, code, lab, stock, price, volInj, date, road));
                break;
            case 4:
                System.out.print("Quantité (g) : "); int qte = getIntInput();
                System.out.print("Zone app. : "); String spot = scanner.nextLine();
                pomadeService.create(new Pomade(name, code, lab, stock, price, qte, date, spot));
                break;
            default:
                System.out.println("❌ Type invalide.");
                return;
        }
        System.out.println("✅ Médicament ajouté avec succès !");
        pause();
    }

    // ================= 2. MENU SUPPRESSION =================
    private static void menuSuppression() {
        System.out.println("\n--- SUPPRESSION ---");
        System.out.print("Entrez le code du médicament à supprimer (0 pour annuler) : ");
        String code = scanner.nextLine();

        if (code.equals("0")) return;

        Medicament m = InMemoryDatabase.findByCode(code);
        if (m == null) {
            System.out.println("❌ Code introuvable.");
            pause();
            return;
        }

        boolean res = false;
        if (m instanceof Sirup) res = sirupService.delete(code);
        else if (m instanceof Pill) res = pillService.delete(code);
        else if (m instanceof Injection) res = injectionService.delete(code);
        else if (m instanceof Pomade) res = pomadeService.delete(code);

        if(res) System.out.println("✅ Médicament supprimé définitivement.");
        pause();
    }

    // ================= 3. MENU MODIFICATION =================
    private static void menuModification() {
        System.out.println("\n--- MODIFICATION ---");
        System.out.print("Entrez le code du médicament (0 pour annuler) : ");
        String code = scanner.nextLine();

        if (code.equals("0")) return;

        Medicament m = InMemoryDatabase.findByCode(code);
        if (m == null) {
            System.out.println("❌ Aucun médicament trouvé.");
            pause();
            return;
        }

        if (m instanceof Sirup) updateSirupMenu((Sirup) m);
        else if (m instanceof Pill) updatePillMenu((Pill) m);
        else if (m instanceof Injection) updateInjectionMenu((Injection) m);
        else if (m instanceof Pomade) updatePomadeMenu((Pomade) m);
        else System.out.println("Type non géré.");
    }

    // --- Sous-menus de modification (inchangés mais avec pause à la fin) ---
    // Je remets un exemple complet, les autres suivent la même logique

    private static void updateSirupMenu(Sirup s) {
        boolean editing = true;
        while (editing) {
            System.out.println("\n--- ÉDITION SIROP : " + s.getCommercialName() + " ---");
            System.out.println("1. Nom      2. Prix     3. Stock");
            System.out.println("4. Labo     5. Volume   6. Goût");
            System.out.println("0. TERMINER L'ÉDITION");
            System.out.print("👉 Choix : ");

            int choice = getIntInput();
            switch (choice) {
                case 1: s.setCommercialName(getStringInput("Nouveau nom : ")); break;
                case 2: s.setPrice(getDoubleInput()); break;
                case 3: s.setStock(getIntInput()); break;
                case 4: s.setProductorLaboratory(getStringInput("Nouveau Labo : ")); break;
                case 5: s.setVolumeMl(getIntInput()); break;
                case 6: s.setTaste(getStringInput("Nouveau Goût : ")); break;
                case 0: editing = false; break;
                default: System.out.println("Choix invalide.");
            }
        }
        System.out.println("✅ Modifications enregistrées.");
        pause();
    }

    private static void updatePillMenu(Pill p) {
        boolean editing = true;
        while (editing) {
            System.out.println("\n--- ÉDITION COMPRIMÉ : " + p.getCommercialName() + " ---");
            System.out.println("1. Nom      2. Prix     3. Stock");
            System.out.println("4. Labo     5. Dosage   6. Nombre");
            System.out.println("0. TERMINER L'ÉDITION");
            System.out.print("👉 Choix : ");

            int choice = getIntInput();
            switch (choice) {
                case 1: p.setCommercialName(getStringInput("Nouveau nom : ")); break;
                case 2: p.setPrice(getDoubleInput()); break;
                case 3: p.setStock(getIntInput()); break;
                case 4: p.setProductorLaboratory(getStringInput("Nouveau Labo : ")); break;
                case 5: p.setDosageMg(getIntInput()); break;
                case 6: p.setNbrPills(getIntInput()); break;
                case 0: editing = false; break;
                default: System.out.println("Choix invalide.");
            }
        }
        System.out.println("✅ Modifications enregistrées.");
        pause();
    }

    // Ajoutez updateInjectionMenu et updatePomadeMenu ici (similaire aux précédents)
    private static void updateInjectionMenu(Injection i) {
        boolean editing = true;
        while (editing) {
            System.out.println("\n--- ÉDITION INJECTION : " + i.getCommercialName() + " ---");
            System.out.println("1. Nom   2. Prix   3. Stock  4. Labo");
            System.out.println("5. Volume   6. Voie");
            System.out.println("0. TERMINER");
            int choice = getIntInput();
            if(choice == 0) editing = false;
            // ... logique switch case comme ci-dessus ...
        }
        pause();
    }

    private static void updatePomadeMenu(Pomade p) {
        boolean editing = true;
        while (editing) {
            System.out.println("\n--- ÉDITION POMMADE : " + p.getCommercialName() + " ---");
            System.out.println("1. Nom   2. Prix   3. Stock  4. Labo");
            System.out.println("5. Quantité  6. Zone");
            System.out.println("0. TERMINER");
            int choice = getIntInput();
            if(choice == 0) editing = false;
            // ... logique switch case comme ci-dessus ...
        }
        pause();
    }

    // ================= 4. MENU AFFICHAGE (TABLEAU SQL) =================
    private static void menuAffichage() {
        System.out.println("\n--- FILTRE D'AFFICHAGE ---");
        System.out.println("1. Tout afficher");
        System.out.println("2. Sirops");
        System.out.println("3. Comprimés");
        System.out.println("4. Injections");
        System.out.println("5. Pommades");
        System.out.println("0. Retour");
        System.out.print("👉 Choix : ");

        int choice = getIntInput();
        if (choice == 0) return;

        List<? extends Medicament> list = null;

        switch (choice) {
            case 1: list = InMemoryDatabase.medicaments; break;
            case 2: list = sirupService.readAll(); break;
            case 3: list = pillService.readAll(); break;
            case 4: list = injectionService.readAll(); break;
            case 5: list = pomadeService.readAll(); break;
            default: System.out.println("Option invalide"); return;
        }

        if (list == null || list.isEmpty()) {
            System.out.println("⚠️ Aucun médicament trouvé dans cette catégorie.");
        } else {
            printTable(list);
        }

        pause(); // Attendre avant de revenir au menu
    }

    // ================= GÉNÉRATEUR DE TABLEAU SQL =================
    private static void printTable(List<? extends Medicament> list) {
        // Format des colonnes : %-10s signifie "chaine alignée à gauche sur 10 caractères"
        String format = "| %-8s | %-18s | %-12s | %-5s | %-8s | %-30s |%n";
        String lineSeparator = "+----------+--------------------+--------------+-------+----------+--------------------------------+";

        System.out.println("\n" + lineSeparator);
        System.out.printf(format, "CODE", "NOM", "LABO", "STOCK", "PRIX", "DETAILS SPECIFIQUES");
        System.out.println(lineSeparator);

        for (Medicament m : list) {
            System.out.printf(format,
                    m.getCode(),
                    truncate(m.getCommercialName(), 18),
                    truncate(m.getProductorLaboratory(), 12),
                    m.getStock(),
                    m.getPrice() + "$",
                    m.getSpecificData() // Appel de la méthode créée étape 1
            );
        }
        System.out.println(lineSeparator);
    }

    // ================= HELPERS & UTILITAIRES =================

    // Pour éviter de casser le tableau si un nom est trop long
    private static String truncate(String str, int width) {
        if (str.length() > width) {
            return str.substring(0, width - 3) + "...";
        }
        return str;
    }

    // Pause pour lire le résultat
    private static void pause() {
        System.out.println("\nAppuyez sur [Entrée] pour continuer...");
        scanner.nextLine();
    }

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
        System.out.print("Date exp (AAAA-MM-JJ) : ");
        try { return LocalDate.parse(scanner.nextLine()); }
        catch (Exception e) { return LocalDate.now(); }
    }
}
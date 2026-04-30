import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EvidenceZamestnancu evidence = new EvidenceZamestnancu();
        Scanner scanner = new Scanner(System.in);
        
        // SQL záloha
        DatabazaManager db = new DatabazaManager();
        
        // Načítanie z SQL pri štarte
        if (db.pripoj()) {
            ArrayList<Map<String, Object>> zamData = db.stiahniZamestnancov();
            ArrayList<Map<String, Object>> spolData = db.stiahniSpoluprace();
            
            if (!zamData.isEmpty()) {
                int maxId = 0;
                for (Map<String, Object> z : zamData) {
                    int id = (int) z.get("id");
                    String jmeno = (String) z.get("jmeno");
                    String prijmeni = (String) z.get("prijmeni");
                    int rok = (int) z.get("rokNarozeni");
                    String typ = (String) z.get("typ");
                    
                    if ("analytik".equals(typ)) {
                        evidence.pridejDatovehoAnalytika(jmeno, prijmeni, rok);
                    } else {
                        evidence.pridejBezpecnostnihoSpecialistu(jmeno, prijmeni, rok);
                    }
                    maxId = Math.max(maxId, id);
                }
                evidence.setDalsiID(maxId + 1);
                
                for (Map<String, Object> s : spolData) {
                    int idZam = (int) s.get("idZamestnance");
                    int idKol = (int) s.get("idKolegy");
                    KvalitaSpoluprace kvalita = KvalitaSpoluprace.valueOf((String) s.get("kvalita"));
                    evidence.pridejSpolupraci(idZam, idKol, kvalita);
                }
                System.out.println("[DB] Obnovené z SQL.");
            }
        }

        int volba;

        do {
            System.out.println();
            System.out.println("┌────────────────────────────────────────────┐");
            System.out.println("│            EVIDENCE ZAMESTNANCU            │");
            System.out.println("├─────┬──────────────────────────────────────┤");
            System.out.println("│  1  │  Pridat datoveho analytika           │");
            System.out.println("│  2  │  Pridat bezpecnostniho specialistu   │");
            System.out.println("│  3  │  Vypsat vsechny zamestnance          │");
            System.out.println("│  4  │  Najit zamestnance podle ID          │");
            System.out.println("│  5  │  Spustit dovednost zamestnance       │");
            System.out.println("│  6  │  Pridat spolupraci                   │");
            System.out.println("│  7  │  Odebrat zamestnance                 │");
            System.out.println("│  8  │  Abecedny vypis ve skupinach         │");
            System.out.println("│  9  │  Statistiky spoluprace               │");
            System.out.println("│ 10  │  Pocty zamestnancu ve skupinach      │");
            System.out.println("│ 11  │  Ulozit zamestnance do souboru       │");
            System.out.println("│ 12  │  Nacist zamestnance ze souboru       │");
            System.out.println("│  0  │  Konec                               │");
            System.out.println("└─────┴──────────────────────────────────────┘");
            System.out.println();
            volba = nactiCeleCislo(scanner, "Zadej volbu:");
            System.out.println();

            if (volba == 1) {
                System.out.print("Jmeno: ");
                String jmeno = scanner.nextLine();

                System.out.print("Prijmeni: ");
                String prijmeni = scanner.nextLine();

                int rokNarozeni = nactiCeleCislo(scanner, "Rok narozeni: ");

                evidence.pridejDatovehoAnalytika(jmeno, prijmeni, rokNarozeni);

            } else if (volba == 2) {
                System.out.print("Jmeno: ");
                String jmeno = scanner.nextLine();

                System.out.print("Prijmeni: ");
                String prijmeni = scanner.nextLine();

                int rokNarozeni = nactiCeleCislo(scanner, "Rok narozeni: ");

                evidence.pridejBezpecnostnihoSpecialistu(jmeno, prijmeni, rokNarozeni);

            } else if (volba == 3) {
                evidence.vypisVsechnyZamestnance();

            } else if (volba == 4) {
                int id = nactiCeleCislo(scanner, "Zadej ID: ");

                Zamestnanec zamestnanec = evidence.najdiZamestnancePodleID(id);

                if (zamestnanec != null) {
                    zamestnanec.vypisInfo();
                    zamestnanec.vypisStatistikySpoluprace();
                } else {
                    System.out.println("Zamestnanec nebyl nalezen.");
                }

            } else if (volba == 5) {
                int id = nactiCeleCislo(scanner, "Zadej ID: ");

                evidence.spustDovednostZamestnance(id);

            } else if (volba == 6) {
                int idZamestnance = nactiCeleCislo(scanner, "ID zamestnance: ");

                int idKolegy = nactiCeleCislo(scanner, "ID kolegy: ");

                System.out.println("Kvalita spoluprace:");
                System.out.println("┌─────┬───────────────────┐");
                System.out.println("│  1  │ Spatna            │");
                System.out.println("│  2  │ Prumerna          │");
                System.out.println("│  3  │ Dobra             │");
                System.out.println("└─────┴───────────────────┘");
                int volbaKvality = nactiCeleCislo(scanner, "Zadej volbu: ");

                KvalitaSpoluprace kvalita;

                if (volbaKvality == 1) {
                    kvalita = KvalitaSpoluprace.SPATNA;
                } else if (volbaKvality == 2) {
                    kvalita = KvalitaSpoluprace.PRUMERNA;
                } else if (volbaKvality == 3) {
                    kvalita = KvalitaSpoluprace.DOBRA;
                } else {
                    System.out.println("Neplatna kvalita spoluprace.");
                    continue;
                }

                evidence.pridejSpolupraci(idZamestnance, idKolegy, kvalita);

            } else if (volba == 7) {
                int id = nactiCeleCislo(scanner, "Zadej ID zamestnance k odebrani: ");

                evidence.odeberZamestnance(id);

            } else if (volba == 8) {
                evidence.vypisAbecedneVeSkupinach();

            } else if (volba == 9) {
                evidence.vypisPrevazujiciKvalituSpoluprace();
                System.out.println();
                evidence.vypisZamestnanceSNejviceVazbami();

            } else if (volba == 10) {
                evidence.vypisPoctyVeSkupinach();

            } else if (volba == 11) {
                int id = nactiCeleCislo(scanner, "Zadej ID zamestnance: ");

                System.out.print("Zadej nazev souboru: ");
                String nazevSouboru = scanner.nextLine();

                evidence.ulozZamestnanceDoSouboru(id, nazevSouboru);
            } else if (volba == 12) {
                System.out.print("Zadej nazev souboru: ");
                String nazevSouboru = scanner.nextLine();

                evidence.nactiZamestnanceZeSouboru(nazevSouboru);
            }else if (volba == 0) {
                // Ulož do SQL zálohy
                if (db.jePripojeny()) {
                    db.ulozZamestnancov(evidence);
                    db.odpoj();
                }
                System.out.println("Program se ukoncuje...");

            } else {
                System.out.println("Neplatna volba.");
            }

            System.out.println();

        } while (volba != 0);

        scanner.close();
    }

    private static int nactiCeleCislo(Scanner scanner, String vyzva) {
        while (true) {
            System.out.print(vyzva);
            String vstup = scanner.nextLine();

            try {
                return Integer.parseInt(vstup);
            } catch (NumberFormatException e) {
                System.out.println("Zadej prosim cele cislo.");
            }
        }
    }
}

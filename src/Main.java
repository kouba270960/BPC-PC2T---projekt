import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EvidenceZamestnancu evidence = new EvidenceZamestnancu();
        Scanner scanner = new Scanner(System.in);

        int volba;

        do {
            System.out.println("===== Evidence zamestnancu =====");
            System.out.println("1 - Pridat datoveho analytika");
            System.out.println("2 - Pridat bezpecnostniho specialistu");
            System.out.println("3 - Vypsat vsechny zamestnance");
            System.out.println("4 - Najit zamestnance podle ID");
            System.out.println("5 - Spustit dovednost zamestnance");
            System.out.println("6 - Pridat spolupraci");
            System.out.println("7 - Odebrat zamestnance");
            System.out.println("8 - Abecedni vypis ve skupinach");
            System.out.println("9 - Statistiky spoluprace");
            System.out.println("10 - Pocty zamestnancu ve skupinach");
            System.out.println("11 - Ulozit zamestnance do souboru");
            System.out.println("12 - Nacist zamestnance ze souboru");
            System.out.println("0 - Konec");
            System.out.print("Zadej volbu: ");

            volba = scanner.nextInt();
            scanner.nextLine();

            if (volba == 1) {
                System.out.print("Jmeno: ");
                String jmeno = scanner.nextLine();

                System.out.print("Prijmeni: ");
                String prijmeni = scanner.nextLine();

                System.out.print("Rok narozeni: ");
                int rokNarozeni = scanner.nextInt();
                scanner.nextLine();

                evidence.pridejDatovehoAnalytika(jmeno, prijmeni, rokNarozeni);

            } else if (volba == 2) {
                System.out.print("Jmeno: ");
                String jmeno = scanner.nextLine();

                System.out.print("Prijmeni: ");
                String prijmeni = scanner.nextLine();

                System.out.print("Rok narozeni: ");
                int rokNarozeni = scanner.nextInt();
                scanner.nextLine();

                evidence.pridejBezpecnostnihoSpecialistu(jmeno, prijmeni, rokNarozeni);

            } else if (volba == 3) {
                evidence.vypisVsechnyZamestnance();

            } else if (volba == 4) {
                System.out.print("Zadej ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                Zamestnanec zamestnanec = evidence.najdiZamestnancePodleID(id);

                if (zamestnanec != null) {
                    zamestnanec.vypisInfo();
                    zamestnanec.vypisStatistikySpoluprace();
                } else {
                    System.out.println("Zamestnanec nebyl nalezen.");
                }

            } else if (volba == 5) {
                System.out.print("Zadej ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                evidence.spustDovednostZamestnance(id);

            } else if (volba == 6) {
                System.out.print("ID zamestnance: ");
                int idZamestnance = scanner.nextInt();
                scanner.nextLine();

                System.out.print("ID kolegy: ");
                int idKolegy = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Kvalita spoluprace:");
                System.out.println("1 - Spatna");
                System.out.println("2 - Prumerna");
                System.out.println("3 - Dobra");
                System.out.print("Zadej volbu: ");

                int volbaKvality = scanner.nextInt();
                scanner.nextLine();

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
                System.out.print("Zadej ID zamestnance k odebrani: ");
                int id = scanner.nextInt();
                scanner.nextLine();

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
                System.out.print("Zadej ID zamestnance: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Zadej nazev souboru: ");
                String nazevSouboru = scanner.nextLine();

                evidence.ulozZamestnanceDoSouboru(id, nazevSouboru);
            } else if (volba == 12) {
                System.out.print("Zadej nazev souboru: ");
                String nazevSouboru = scanner.nextLine();

                evidence.nactiZamestnanceZeSouboru(nazevSouboru);
            }else if (volba == 0) {
                System.out.println("Program se ukoncuje.");

            } else {
                System.out.println("Neplatna volba.");
            }

            System.out.println();

        } while (volba != 0);

        scanner.close();
    }
}

import java.util.ArrayList;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class EvidenceZamestnancu {

    private ArrayList<Zamestnanec> zamestnanci;
    private int dalsiID;

    public EvidenceZamestnancu(){
        zamestnanci = new ArrayList<>();
        dalsiID = 1;
    }
    
    public ArrayList<Zamestnanec> getZamestnanci() {
        return zamestnanci;
    }
    
    public int getDalsiID() {
        return dalsiID;
    }
    
    public void setDalsiID(int id) {
        this.dalsiID = id;
    }

    public void pridejDatovehoAnalytika(String jmeno,String prijmeni, int rokNarozeni){
        DatovyAnalytik analytik = new DatovyAnalytik(dalsiID, jmeno, prijmeni, rokNarozeni);
        zamestnanci.add(analytik);
        dalsiID = dalsiID + 1;
    }

    public void pridejBezpecnostnihoSpecialistu(String jmeno,String prijmeni,int rokNarozeni){
        BezpecnostniSpecialista specialista = new BezpecnostniSpecialista(dalsiID, jmeno, prijmeni, rokNarozeni);
        zamestnanci.add(specialista);
        dalsiID = dalsiID + 1;
    }

    public void vypisVsechnyZamestnance(){
        for(Zamestnanec zamestnanec : zamestnanci){
            zamestnanec.vypisInfo();
            System.out.println();
        }
    }

    public Zamestnanec najdiZamestnancePodleID(int id){
        for(Zamestnanec zamestnanec : zamestnanci){
            if (zamestnanec.getID() == id){
                return zamestnanec;
            }
        }
        return null;
    }

    public void spustDovednostZamestnance(int id){
        Zamestnanec zamestnanec = najdiZamestnancePodleID(id);

        if (zamestnanec != null){
            zamestnanec.spustDovednost(this);
        }
        else{
            System.out.println("Zamestnanec s ID " + id + " nebyl nalezen.");
        }
    }
    
    public void pridejSpolupraci(int idZamestnance, int idKolegy, KvalitaSpoluprace kvalita) {
        if (idZamestnance == idKolegy) {
            System.out.println("Zamestnanec nemuze spolupracovat sam se sebou.");
            return;
        }

        Zamestnanec zamestnanec = najdiZamestnancePodleID(idZamestnance);
        Zamestnanec kolega = najdiZamestnancePodleID(idKolegy);

        if (zamestnanec == null) {
            System.out.println("Zamestnanec s ID " + idZamestnance + " nebyl nalezen.");
            return;
        }

        if (kolega == null) {
            System.out.println("Kolega s ID " + idKolegy + " nebyl nalezen.");
            return;
        }

        if (zamestnanec.maSpolupraciSKolegou(idKolegy)) {
            System.out.println("Tato spoluprace uz existuje.");
            return;
        }

        zamestnanec.pridejSpolupraci(idKolegy, kvalita);
        System.out.println("Spoluprace byla pridana.");
    }

    public void odeberZamestnance(int id) {
        Zamestnanec zamestnanec = najdiZamestnancePodleID(id);

        if (zamestnanec == null) {
            System.out.println("Zamestnanec s ID " + id + " nebyl nalezen.");
            return;
        }

        zamestnanci.remove(zamestnanec);

        for (Zamestnanec ostatniZamestnanec : zamestnanci) {
            ostatniZamestnanec.odeberSpolupraciSKolegou(id);
        }

        System.out.println("Zamestnanec s ID " + id + " byl odebran.");
    }

    public void vypisPoctyVeSkupinach() {
        int pocetAnalytiku = 0;
        int pocetSpecialistu = 0;

        for (Zamestnanec zamestnanec : zamestnanci) {
            if (zamestnanec instanceof DatovyAnalytik) {
                pocetAnalytiku++;
            } else if (zamestnanec instanceof BezpecnostniSpecialista) {
                pocetSpecialistu++;
            }
        }

        System.out.println("Pocet datovych analytiku: " + pocetAnalytiku);
        System.out.println("Pocet bezpecnostnich specialistu: " + pocetSpecialistu);
    }

    public void vypisAbecedneVeSkupinach() {
        ArrayList<Zamestnanec> analytici = new ArrayList<>();
        ArrayList<Zamestnanec> specialisti = new ArrayList<>();

        for (Zamestnanec zamestnanec : zamestnanci) {
            if (zamestnanec instanceof DatovyAnalytik) {
                analytici.add(zamestnanec);
            } else if (zamestnanec instanceof BezpecnostniSpecialista) {
                specialisti.add(zamestnanec);
            }
        }

        analytici.sort(Comparator.comparing(Zamestnanec::getPrijmeni));
        specialisti.sort(Comparator.comparing(Zamestnanec::getPrijmeni));

        System.out.println("Datovi analytici:");
        for (Zamestnanec analytik : analytici) {
            System.out.println(analytik.getPrijmeni() + " " + analytik.getJmeno());
        }

        System.out.println();

        System.out.println("Bezpecnostni specialiste:");
        for (Zamestnanec specialista : specialisti) {
            System.out.println(specialista.getPrijmeni() + " " + specialista.getJmeno());
        }
    }

    public void vypisPrevazujiciKvalituSpoluprace() {
        int pocetSpatnych = 0;
        int pocetPrumernych = 0;
        int pocetDobrych = 0;

        for (Zamestnanec zamestnanec : zamestnanci) {
            for (Spoluprace spoluprace : zamestnanec.getSpoluprace()) {
                if (spoluprace.getKvalita() == KvalitaSpoluprace.SPATNA) {
                    pocetSpatnych++;
                } else if (spoluprace.getKvalita() == KvalitaSpoluprace.PRUMERNA) {
                    pocetPrumernych++;
                } else if (spoluprace.getKvalita() == KvalitaSpoluprace.DOBRA) {
                    pocetDobrych++;
                }
            }
        }

        if (pocetSpatnych == 0 && pocetPrumernych == 0 && pocetDobrych == 0) {
            System.out.println("Zatim nejsou evidovane zadne spoluprace.");
        } else if (pocetDobrych >= pocetPrumernych && pocetDobrych >= pocetSpatnych) {
            System.out.println("Prevazujici kvalita spoluprace: DOBRA");
        } else if (pocetPrumernych >= pocetDobrych && pocetPrumernych >= pocetSpatnych) {
            System.out.println("Prevazujici kvalita spoluprace: PRUMERNA");
        } else {
            System.out.println("Prevazujici kvalita spoluprace: SPATNA");
        }
    }


    public void vypisZamestnanceSNejviceVazbami() {
        if (zamestnanci.isEmpty()) {
            System.out.println("Evidence je prazdna.");
            return;
        }

        Zamestnanec nejviceVazeb = zamestnanci.get(0);

        for (Zamestnanec zamestnanec : zamestnanci) {
            if (zamestnanec.getSpoluprace().size() > nejviceVazeb.getSpoluprace().size()) {
                nejviceVazeb = zamestnanec;
            }
        }

        System.out.println("Zamestnanec s nejvice vazbami:");
        nejviceVazeb.vypisInfo();
        System.out.println("Pocet vazeb: " + nejviceVazeb.getSpoluprace().size());
    }

    public void ulozZamestnanceDoSouboru(int id, String nazevSouboru) {
        Zamestnanec zamestnanec = najdiZamestnancePodleID(id);

        if (zamestnanec == null) {
            System.out.println("Zamestnanec s ID " + id + " nebyl nalezen.");
            return;
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(nazevSouboru, true));

            if (zamestnanec instanceof DatovyAnalytik) {
                writer.println("DATOVY_ANALYTIK");
            } else if (zamestnanec instanceof BezpecnostniSpecialista) {
                writer.println("BEZPECNOSTNI_SPECIALISTA");
            }

            writer.println(zamestnanec.getID());
            writer.println(zamestnanec.getJmeno());
            writer.println(zamestnanec.getPrijmeni());
            writer.println(zamestnanec.getRokNarozeni());

            writer.println("SPOLUPRACE");
            for (Spoluprace spoluprace : zamestnanec.getSpoluprace()) {
                writer.println(spoluprace.getIdKolegy() + ";" + spoluprace.getKvalita());
            }
            writer.println("KONEC_ZAMESTNANCE");

            writer.close();

            System.out.println("Zamestnanec byl ulozen do souboru.");

        } catch (IOException e) {
            System.out.println("Chyba pri ukladani do souboru.");
        }
    }

    public void nactiZamestnanceZeSouboru(String nazevSouboru) {
        try {
            Scanner scanner = new Scanner(new File(nazevSouboru));
            int pocetNactenych = 0;

            while (scanner.hasNextLine()) {
                String typ = scanner.nextLine();

                if (typ.isEmpty()) {
                    continue;
                }

                int id = Integer.parseInt(scanner.nextLine());
                String jmeno = scanner.nextLine();
                String prijmeni = scanner.nextLine();
                int rokNarozeni = Integer.parseInt(scanner.nextLine());

                Zamestnanec zamestnanec;

                if (typ.equals("DATOVY_ANALYTIK")) {
                    zamestnanec = new DatovyAnalytik(id, jmeno, prijmeni, rokNarozeni);
                } else if (typ.equals("BEZPECNOSTNI_SPECIALISTA")) {
                    zamestnanec = new BezpecnostniSpecialista(id, jmeno, prijmeni, rokNarozeni);
                } else {
                    System.out.println("Neznamy typ zamestnance v souboru.");
                    scanner.close();
                    return;
                }

                if (scanner.hasNextLine()) {
                    String radek = scanner.nextLine();

                    if (radek.equals("SPOLUPRACE")) {
                        while (scanner.hasNextLine()) {
                            String spolupraceRadek = scanner.nextLine();

                            if (spolupraceRadek.equals("KONEC_ZAMESTNANCE")) {
                                break;
                            }

                            String[] casti = spolupraceRadek.split(";");

                            int idKolegy = Integer.parseInt(casti[0]);
                            KvalitaSpoluprace kvalita = KvalitaSpoluprace.valueOf(casti[1]);

                            zamestnanec.pridejSpolupraci(idKolegy, kvalita);
                        }
                    }
                }

                if (najdiZamestnancePodleID(id) != null) {
                    System.out.println("Zamestnanec s ID " + id + " uz v evidenci existuje, nebude nacten znovu.");
                    continue;
                }

                zamestnanci.add(zamestnanec);
                pocetNactenych++;

                if (id >= dalsiID) {
                    dalsiID = id + 1;
                }
            }

            scanner.close();

            System.out.println("Pocet nactenych zamestnancu: " + pocetNactenych);

        } catch (FileNotFoundException e) {
            System.out.println("Soubor nebyl nalezen.");
        }
    }



}

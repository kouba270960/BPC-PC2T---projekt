import java.util.ArrayList;
import java.util.Comparator;

public class EvidenceZamestnancu {

    private ArrayList<Zamestnanec> zamestnanci;
    private int dalsiID;

    public EvidenceZamestnancu(){
        zamestnanci = new ArrayList<>();
        dalsiID = 1;
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
            zamestnanec.spustDovednost();
        }
        else{
            System.out.println("Zamestnanec s ID " + id + " nebyl nalezen.");
        }
    }
    
    public void pridejSpolupraci(int idZamestnance, int idKolegy, KvalitaSpoluprace kvalita) {
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


}
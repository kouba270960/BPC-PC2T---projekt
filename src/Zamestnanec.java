import java.util.ArrayList;
public abstract class Zamestnanec {
    
    private int id;
    private String jmeno;
    private String prijmeni;
    private int rokNarozeni;
    private ArrayList<Spoluprace> spoluprace;

    public Zamestnanec(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
        this.spoluprace = new ArrayList<>();
    }

    public void vypisStatistikySpoluprace() {
    int pocetSpatnych = 0;
    int pocetPrumernych = 0;
    int pocetDobrych = 0;

    for (Spoluprace jednaSpoluprace : spoluprace) {
        if (jednaSpoluprace.getKvalita() == KvalitaSpoluprace.SPATNA) {
            pocetSpatnych++;
        } else if (jednaSpoluprace.getKvalita() == KvalitaSpoluprace.PRUMERNA) {
            pocetPrumernych++;
        } else if (jednaSpoluprace.getKvalita() == KvalitaSpoluprace.DOBRA) {
            pocetDobrych++;
        }
    }

    System.out.println("Pocet spolupraci: " + spoluprace.size());
    System.out.println("Spatne spoluprace: " + pocetSpatnych);
    System.out.println("Prumerne spoluprace: " + pocetPrumernych);
    System.out.println("Dobre spoluprace: " + pocetDobrych);
}

    public void pridejSpolupraci(int idKolegy, KvalitaSpoluprace kvalita) {
        Spoluprace novaSpoluprace = new Spoluprace(idKolegy, kvalita);
        spoluprace.add(novaSpoluprace);
    }   

    public void vypisInfo() {
        System.out.println("ID: " + id);
        System.out.println("Jméno: " + jmeno);
        System.out.println("Příjmení: " + prijmeni);
        System.out.println("Rok narození: " + rokNarozeni);
    }

    public abstract void spustDovednost(EvidenceZamestnancu evidence);


    public int getID(){
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }   

    public int getRokNarozeni() {
        return rokNarozeni;
    }


    public ArrayList<Spoluprace> getSpoluprace() {
        return spoluprace;
    }

    public void odeberSpolupraciSKolegou(int idKolegy) {
        for (int i = 0; i < spoluprace.size(); i++) {
            if (spoluprace.get(i).getIdKolegy() == idKolegy) {
                spoluprace.remove(i);
                i--;
            }
        }
    }

}

public class DatovyAnalytik extends Zamestnanec {

    public DatovyAnalytik(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void spustDovednost(EvidenceZamestnancu evidence) {
        if (getSpoluprace().isEmpty()) {
            System.out.println("Datovy analytik nema zadne spolupracovniky.");
            return;
        }

        int nejlepsiIdKolegy = -1;
        int nejviceSpolecnych = -1;

        for (Spoluprace mojeSpoluprace : getSpoluprace()) {
            Zamestnanec kolega = evidence.najdiZamestnancePodleID(mojeSpoluprace.getIdKolegy());

            if (kolega == null) {
                continue;
            }

            int pocetSpolecnych = spocitejSpolecneSpolupracovniky(kolega);

            if (pocetSpolecnych > nejviceSpolecnych) {
                nejviceSpolecnych = pocetSpolecnych;
                nejlepsiIdKolegy = kolega.getID();
            }
        }

        if (nejlepsiIdKolegy == -1) {
            System.out.println("Nepodarilo se najit vhodneho kolegu.");
        } else {
            System.out.println("Nejvice spolecnych spolupracovniku ma s kolegou ID: " + nejlepsiIdKolegy);
            System.out.println("Pocet spolecnych spolupracovniku: " + nejviceSpolecnych);
        }
    }

    private int spocitejSpolecneSpolupracovniky(Zamestnanec kolega) {
        int pocet = 0;

        for (Spoluprace mojeSpoluprace : getSpoluprace()) {
            for (Spoluprace kolegovaSpoluprace : kolega.getSpoluprace()) {
                if (mojeSpoluprace.getIdKolegy() == kolegovaSpoluprace.getIdKolegy()) {
                    pocet++;
                }
            }
        }

        return pocet;
    }

}
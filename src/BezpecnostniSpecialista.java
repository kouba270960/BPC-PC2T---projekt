public class BezpecnostniSpecialista extends Zamestnanec {

    public BezpecnostniSpecialista(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void spustDovednost(EvidenceZamestnancu evidence) {
        if (getSpoluprace().isEmpty()) {
            System.out.println("Bezpecnostni specialista nema zadne spoluprace.");
            return;
        }

        int soucetRizika = 0;

        for (Spoluprace spoluprace : getSpoluprace()) {
            if (spoluprace.getKvalita() == KvalitaSpoluprace.SPATNA) {
                soucetRizika += 3;
            } else if (spoluprace.getKvalita() == KvalitaSpoluprace.PRUMERNA) {
                soucetRizika += 2;
            } else if (spoluprace.getKvalita() == KvalitaSpoluprace.DOBRA) {
                soucetRizika += 1;
            }
        }

        double prumerneRiziko = (double) soucetRizika / getSpoluprace().size();
        double rizikoveSkore = prumerneRiziko * getSpoluprace().size();

        System.out.println("Rizikove skore spoluprace: " + rizikoveSkore);
    }

}
public class Spoluprace {
    private int idKolegy;
    private KvalitaSpoluprace kvalita;

    public Spoluprace(int idKolegy, KvalitaSpoluprace kvalita) {
        this.idKolegy = idKolegy;
        this.kvalita = kvalita;
    }

    public int getIdKolegy() {
        return idKolegy;
    }

    public KvalitaSpoluprace getKvalita() {
        return kvalita;
    }
}
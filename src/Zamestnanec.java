public abstract class Zamestnanec {
    
    private int id;
    private String jmeno;
    private String prijmeni;
    private int rokNarozeni;

    public Zamestnanec(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
    }

    public void vypisInfo() {
        System.out.println("ID: " + id);
        System.out.println("Jméno: " + jmeno);
        System.out.println("Příjmení: " + prijmeni);
        System.out.println("Rok narození: " + rokNarozeni);
    }

    public abstract void spustDovednost();

}

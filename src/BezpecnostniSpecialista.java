public class BezpecnostniSpecialista extends Zamestnanec {

    public BezpecnostniSpecialista(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void spustDovednost() {
    System.out.println("Bezpecnostni specialista pocita rizikove skore.");
    }
}
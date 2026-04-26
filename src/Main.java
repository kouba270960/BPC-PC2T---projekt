public class Main {
   
    public static void main(String[] args) {

        DatovyAnalytik analytik = new DatovyAnalytik(1, "jakub", "kriva", 2004);
        BezpecnostniSpecialista specialista = new BezpecnostniSpecialista(2, "karel", "kopriva", 2000);

        analytik.vypisInfo();
        analytik.spustDovednost();

        System.out.println();

        specialista.vypisInfo();
        specialista.spustDovednost();

    }



}

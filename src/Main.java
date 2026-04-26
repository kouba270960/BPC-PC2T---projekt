public class Main {
   
    public static void main(String[] args) {

        EvidenceZamestnancu evidence = new EvidenceZamestnancu();

        evidence.pridejDatovehoAnalytika("Jan", "Novak", 2000);
        evidence.pridejBezpecnostnihoSpecialistu("Petr", "Svoboda", 1998);

        Zamestnanec nalezeny = evidence.najdiZamestnancePodleID(3);

        if (nalezeny != null){
            nalezeny.vypisInfo();
        }
        else{
            System.out.println("zamestnanec nebyl nalezen ");
        }

    }

}

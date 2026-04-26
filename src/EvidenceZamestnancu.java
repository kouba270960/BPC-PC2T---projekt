import java.util.ArrayList;
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
    
}
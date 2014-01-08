package spring.ov13.domene.utils;

import java.util.List;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Bruker;


public class UtilsBean {
    private Database db;
    private List<Bruker> alleBrukere = null;
    private List<Bruker> valgteBrukere = null;
    
    public UtilsBean(){
        System.out.println("starting utilbean");
        db = new Database("jdbc:mysql://mysql.stud.aitel.hist.no:3306/14-ing2-t5;username=14-ing2-t5;password=aXJff+6e");
     //   alleBrukere = db.getAlleBrukere();
     //   System.out.println(toString());
    }
    
    public boolean registrerBruker(Bruker bruker){
        return db.registrerBruker(bruker);
    }
    
    public void setValgteBrukere(List<Bruker> valgteBrukere){
        this.valgteBrukere = valgteBrukere;
    }
    public Database getDb(){
        return db;
    }
    public List<Bruker> getAlleBrukere(){
        return alleBrukere;
    }
    public List<Bruker> getValgteBrukere(){
        return valgteBrukere;
    }
    public Bruker get(String brukernavn){
        for(Bruker b: alleBrukere){
            if(b.getBrukernavn().equals(brukernavn)){
                return b;
            }
        }
        return null;
    }
    /*
    public boolean registrerBruker(Bruker vare){
        return db.registrerBruker(vare);
    }
    public boolean slettBruker(Bruker v){
        return db.slettBruker(v);
    }
    
    public boolean oppdaterBruker(Bruker v){
        return db.oppdaterBruker(v);
    }
    */
    @Override
    public String toString() {
        return "UtilsBean{" + "db=" + db + ", alleBrukere=" + alleBrukere + '}';
    }

}
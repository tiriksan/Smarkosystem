package spring.ov13.domene.utils;

import java.math.BigInteger;
import java.util.List;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;

public class UtilsBean {
    private Database db;
    private List<Bruker> alleBrukere = null;
    private List<Bruker> valgteBrukere = null;
    private List<Emne> alleFag = null;
    private List<Emne> valgteEmner = null;
    private List<Bruker> faglærere = null;
    
    
    public UtilsBean(){
        System.out.println("starting utilbean");
        db = new Database("jdbc:mysql://mysql.stud.aitel.hist.no:3306/14-ing2-t5?", "14-ing2-t5", "aXJff+6e");
        alleBrukere = db.getAlleBrukere();
        alleFag = db.getAlleFag();
       // faglærere = db.getAlleFaglærere(); // denne er noe muffins med //
     //   System.out.println(toString());
    }
    
    public boolean registrerBruker(Bruker bruker){
        bruker.setPassord(java.util.UUID.randomUUID().toString().substring(0,10));
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
    */
    public boolean oppdaterBruker(Bruker b){
        return db.oppdaterBruker(b);
    }
    
    @Override
    public String toString() {
        return "UtilsBean{" + "db=" + db + ", alleBrukere=" + alleBrukere + '}';
    }
    
    //fag //
    
    
      public boolean registrerEmner(Emne fag){
        
        return db.registrerEmner(fag);
    }
      
      public void setValgteFag(List<Emne> valgteEmner){
        this.valgteEmner = valgteEmner;
    }
      
       public List<Emne> getAlleFag(){
        return alleFag;
    }
    public List<Emne> getValgteEmner(){
        return valgteEmner;
    }
    public Emne hent(String fagnavn){
        for(Emne f: alleFag){
            if(f.getEmnenavn().equals(fagnavn)){
                return f;
            }
        }
        return null;
    }
    
    public List <Bruker> getAlleFaglærere(String faglærer){
        return faglærere;
    }
      
      
    
    

}
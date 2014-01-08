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
        db = new Database("jdbc:derby://localhost:1527/Oving13;user=ov13;password=ov13");
        alleBrukere = db.getAlleBrukere();
        System.out.println(toString());
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
    public Bruker get(int varenr){
        for(Bruker v: alleBrukere){
            if(v.getBrukernr() == varenr){
                return v;
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
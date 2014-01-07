package spring.ov13.domene.utils;

import java.util.List;
import spring.ov13.domene.Vare;


public class UtilsBean {
    private Database db;
    private List<Vare> alleVarer = null;
    private List<Vare> valgteVarer = null;
    
    public UtilsBean(){
        System.out.println("starting utilbean");
        db = new Database("jdbc:derby://localhost:1527/Oving13;user=ov13;password=ov13");
        alleVarer = db.getAlleVarer();
        System.out.println(toString());
    }
    
    public void setValgteVarer(List<Vare> valgteVarer){
        this.valgteVarer = valgteVarer;
    }
    public Database getDb(){
        return db;
    }
    public List<Vare> getAlleVarer(){
        return alleVarer;
    }
    public List<Vare> getValgteVarer(){
        return valgteVarer;
    }
    public Vare get(int varenr){
        for(Vare v: alleVarer){
            if(v.getVarenr() == varenr){
                return v;
            }
        }
        return null;
    }
    
    public boolean registrerVare(Vare vare){
        return db.registrerVare(vare);
    }
    public boolean slettVare(Vare v){
        return db.slettVare(v);
    }
    
    public boolean oppdaterVare(Vare v){
        return db.oppdaterVare(v);
    }
    
    @Override
    public String toString() {
        return "UtilsBean{" + "db=" + db + ", alleVarer=" + alleVarer + '}';
    }

}
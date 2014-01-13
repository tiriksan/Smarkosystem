package spring.ov13.domene.utils;

import java.util.ArrayList;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import spring.ov13.domene.Innlegg;
import spring.ov13.domene.Øving;

public class UtilsBean {
    private final Database db;
    private ArrayList<Bruker> alleBrukere = null;
    private ArrayList<Bruker> valgteBrukere = null;
    private ArrayList<Emne> alleFag = null;
    private ArrayList<Emne> valgteEmner = null;
    
    
    public UtilsBean(){
        System.out.println("starting utilbean");
        db = new Database("jdbc:mysql://mysql.stud.aitel.hist.no:3306/14-ing2-t5?", "14-ing2-t5", "aXJff+6e");
        alleBrukere = db.getAlleBrukere();
        alleFag = db.getAlleFag();
       // faglærere = db.getAlleFaglærere(); // denne er noe muffins med //
     //   System.out.println(toString());
    }
    
    public boolean registrerBruker(Bruker bruker){
        return db.registrerBruker(bruker);
    }
    public boolean registrerBrukere(ArrayList<Bruker> brukere){
        return db.registrerBrukere(brukere);
    }
    
    public void setValgteBrukere(ArrayList<Bruker> valgteBrukere){
        this.valgteBrukere = valgteBrukere;
    }
    public Database getDb(){
        return db;
    }
    public ArrayList<Bruker> getAlleBrukere(){
        return alleBrukere;
    }
    public ArrayList<Bruker> getValgteBrukere(){
        return valgteBrukere;
    }
    public boolean endrePassord(Bruker bruker){
        return db.endrePassord(bruker);
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
    
    
      public boolean registrerEmne(Emne fag){
        return db.registrerEmne(fag);
    }
      
      public void setValgteFag(ArrayList<Emne> valgteEmner){
        this.valgteEmner = valgteEmner;
    }
      
       public ArrayList<Emne> getAlleFag(){
        return alleFag;
    }
       public Emne getEmne(String emnekode){
        return db.getFag(emnekode);
    }
    public ArrayList<Emne> getValgteEmner(){
        return valgteEmner;
    }
    public boolean leggTilBrukereIEmner(ArrayList<Emne> emner, ArrayList<Bruker> brukere){
        return db.leggTilBrukereIEmner(emner, brukere);
    }
    public Emne hent(String fagnavn){
        for(Emne f: alleFag){
            if(f.getEmnenavn().equals(fagnavn)){
                return f;
            }
        }
        return null;
    }
    
    public ArrayList <Bruker> getAlleBrukereAvBrukertype(int brukertype){
        return db.getAlleBrukereAvBrukertype(brukertype);
    }
    
    public boolean registrerØving(Øving o) {
        return db.registrerØving(o);
    }
    
    public boolean oppdaterØving(Øving o, int øvingsnr, String emnekode) {
        return db.oppdaterØving(o, øvingsnr, emnekode);
    }
      
  public int getBrukertypeiEmne(String brukernavn, String emnekode){
      return db.getBrukertypeiEmne(brukernavn, emnekode);
  }
  public boolean updateFagKoAktiv(String emnekode, boolean aktiv){
      return db.updateFagKoAktiv(emnekode, aktiv);
  }
       
    public ArrayList<String> getInfoTilBruker(String brukernavn) {
        return db.getInfoTilBruker(brukernavn);
    }
    
    public ArrayList<Emne> getFageneTilBruker(String brukernavn) {
        return db.getFageneTilBruker(brukernavn);
    }
    
    public Boolean getFagKoAktiv(String emnekode) {
        return db.getFagKoAktiv(emnekode);
    }
      

          public ArrayList<Innlegg> getFulleInnleggTilKo(String emnekode) {
        return db.getFulleInnleggTilKo(emnekode);
    }
 
    
    
    
}
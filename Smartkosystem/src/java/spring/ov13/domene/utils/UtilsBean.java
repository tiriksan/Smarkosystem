package spring.ov13.domene.utils;

import java.util.ArrayList;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import spring.ov13.domene.Innlegg;
import spring.ov13.domene.Kravgruppe;
import spring.ov13.domene.Plassering;
import spring.ov13.domene.Øving;

public class UtilsBean {

    private final Database db;
    private ArrayList<Bruker> alleBrukere = null;
    private ArrayList<Bruker> valgteBrukere = null;
    private ArrayList<Emne> alleFag = null;
    private ArrayList<Emne> valgteEmner = null;

    public UtilsBean() {
        System.out.println("starting utilbean");
        db = new Database("jdbc:mysql://mysql.stud.aitel.hist.no:3306/14-ing2-t5?", "14-ing2-t5", "aXJff+6e");
        alleBrukere = db.getAlleBrukere();
        alleFag = db.getAlleFag();
        // faglærere = db.getAlleFaglærere(); // denne er noe muffins med //
        //   System.out.println(toString());
    }

    public boolean registrerBruker(Bruker bruker) {
        return db.registrerBruker(bruker);
    }

    public boolean registrerBrukere(ArrayList<Bruker> brukere) {
        return db.registrerBrukere(brukere);
    }

    public void setValgteBrukere(ArrayList<Bruker> valgteBrukere) {
        this.valgteBrukere = valgteBrukere;
    }

    public Database getDb() {
        return db;
    }

    public ArrayList<Bruker> getAlleBrukere() {
        return alleBrukere;
    }

    public ArrayList<Bruker> getStudenterIEmnet(String emnekode) {
        return db.getStudenterIEmnet(emnekode);
    }

    public ArrayList<Bruker> getValgteBrukere() {
        return valgteBrukere;
    }

    public Bruker getBruker(String brukernavn) {
        return db.getBruker(brukernavn);
    }

    public boolean endrePassord(Bruker bruker) {
        return db.endrePassord(bruker);
    }

    public ArrayList<Kravgruppe> getKravGruppetilEmne(String emnekode) {
        return db.getKravGruppertilEmne(emnekode);

    }
//ENDRE PÅ DENNE?

    public Bruker get(String brukernavn) {
        for (Bruker b : alleBrukere) {
            if (b.getBrukernavn().equals(brukernavn)) {
                return b;
            }
        }
        return null;
    }

    public boolean setInnOvingerGodkjent(String godkjenner, String emnekode, String brukernavn, ArrayList<Integer> øvinger) {
        return db.setInnOvingerGodkjent(godkjenner, emnekode, brukernavn, øvinger);
    }

    public boolean sjekkOvingErGodkjent(String brukernavn, String emnekode, int ovingsnr) {
        return db.sjekkOmOvingErGodkjent(brukernavn, emnekode, ovingsnr);
    }

    public int[] getGodkjentOvingerForBrukerIEmne(String brukernavn, String emnekode, int antØving) {
        return db.getGodkjentOvingerForBrukerIEmne(brukernavn, emnekode, antØving);
    }
    public int getAntOvingerIEmne(String emnekode){
        return db.getAntOvingerIEmne(emnekode);
    }

    public ArrayList<Bruker> getBrukerePåBokstav(String sokeboksnavn) {
        return db.getBrukerepaabokstav(sokeboksnavn);
    }

    /*
     public boolean registrerBruker(Bruker vare){
     return db.registrerBruker(vare);
     }
     public boolean slettBruker(Bruker v){
     return db.slettBruker(v);
     }
     */
    public boolean oppdaterBruker(Bruker b) {
        return db.oppdaterBruker(b);
    }

    @Override
    public String toString() {
        return "UtilsBean{" + "db=" + db + ", alleBrukere=" + alleBrukere + '}';
    }

    //fag //
    public boolean registrerEmne(Emne fag) {
        return db.registrerEmne(fag);
    }

    public void setValgteFag(ArrayList<Emne> valgteEmner) {
        this.valgteEmner = valgteEmner;
    }

    public ArrayList<Emne> getAlleFag() {
        return alleFag;
    }

    public Emne getEmne(String emnekode) {
        return db.getFag(emnekode);
    }

    public boolean oppdaterEmne(Emne emne, String emnekode) {
        return db.oppdaterEmne(emne, emnekode);
    }

    public ArrayList<Emne> getEmnePåBokstav(String bokstav) {
        return db.getEmnepaabokstav(bokstav);
    }

    public ArrayList<Emne> getValgteEmner() {
        return valgteEmner;
    }

    public boolean leggTilBrukereIEmner(ArrayList<Emne> emner, ArrayList<Bruker> brukere) {
        return db.leggTilBrukereIEmner(emner, brukere);
    }

    public ArrayList<Bruker> getFaglærerBruker(String fornavn, String etternavn, int brukertype) {
        return db.getFaglærerBruker(fornavn, etternavn, brukertype);
    }

    public ArrayList<Bruker> getAlleBrukereAvBrukertype(int brukertype) {
        return db.getAlleBrukereAvBrukertype(brukertype);
    }

    public boolean registrerØving(Øving o) {
        return db.registrerØving(o);
    }

    public boolean oppdaterØving(Øving o, int øvingsnr, String emnekode) {
        return db.oppdaterØving(o, øvingsnr, emnekode);
    }

    public boolean erBrukerIFag(String brukernavn, String emnekode) {
        return db.erBrukerIFag(brukernavn, emnekode);
    }

    public int getBrukertypeiEmne(String brukernavn, String emnekode) {
        return db.getBrukertypeiEmne(brukernavn, emnekode);
    }

    public boolean updateFagKoAktiv(String emnekode, boolean aktiv) {
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

    public ArrayList<Øving> getØvingerIEmnet(String emnekode) {
        return db.getØvingerIEmnet(emnekode);
    }

    public ArrayList<Innlegg> getFulleInnleggTilKo(String emnekode) {
        return db.getFulleInnleggTilKo(emnekode);
    }

    public Innlegg getInnleggFraID(int innleggsID) {
        return db.getInnleggFraID(innleggsID);
    }

    public ArrayList<Bruker> getBrukereIInnlegg(int id) {
        return db.getBrukereIInnlegg(id);
    }

    public boolean registrerKøInnlegg(int id, int kønummer, String brukernavn, Plassering lokasjon, String kommentar) {
        return db.registrerKøInnlegg(id, kønummer, brukernavn, lokasjon, kommentar);
    }

    public boolean setKøinnleggHjelpBruker(Bruker bruker, int køinnleggid) {
        return db.setKøinnleggHjelpBruker(bruker, køinnleggid);
    }

    public Innlegg getInnleggFraHjelpEmne(String hjelp, String emnekode) {
        return db.getInnleggFraHjelpEmne(hjelp, emnekode);
    }

    public boolean fjernKoInnleggFraID(int koID) {
        return db.fjernKoInnlegg(koID);
    }

    public String[] getUnikeBygg(String emnekode) {
        return db.getUnikeBygg(emnekode);
    }

    public String[] getUnikeEtasjer(String emnekode, String bygg) {
        return db.getUnikeEtasjer(emnekode, bygg);
    }

    public String[] getUnikeRom(String emnekode, String bygg, String etasje) {
        return db.getUnikeRom(emnekode, bygg, etasje);
    }

    public String[] getUnikeBord(String emnekode, String bygg, String etasje, String bord) {
        return db.getUnikeBord(emnekode, bygg, etasje, bord);
    }

    public synchronized String oppdaterØvingsBeskrivelse(String emnekode, String ovingsbeskrivelse) {
        return db.oppdaterØvingsBeskrivelse(emnekode, ovingsbeskrivelse);
    }

    /*    public static void main(String[] args) {
     UtilsBean ub = new UtilsBean();
     Plassering p = new Plassering("Sukkerhuset", 1, 1, 1, "IFUD1043");
     ub.registrerKøInnlegg(50, 1, "haakon.jarle.hassel@gmail.com", p, "HJELP!");
     }
     */
}

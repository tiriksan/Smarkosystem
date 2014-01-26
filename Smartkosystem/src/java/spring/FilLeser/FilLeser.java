package spring.FilLeser;

/**
 * @author Sindre
 */
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import spring.ov13.domene.utils.SendEpost;
import spring.ov13.domene.utils.UtilsBean;

public class FilLeser {

    private boolean riktigValg = false;
    private boolean feil = false;
    private ArrayList<Bruker> brukere = new ArrayList<Bruker>();
    private ArrayList<Emne> emner = null;
    private UtilsBean ub = new UtilsBean();
    private String filInnhold;

    public FilLeser(ArrayList<Emne> emner, String filInnhold) {
        this.emner = emner;
        this.filInnhold = filInnhold;
    }

    /**
     * ****************************************************************************************
     * lesFil()-metoden lar bruker velge en tekstfil med brukere som systemet
     * leser inn * og registrerer i databasen * formatet er:
     * fornavn,etternavn,epost * *
     * ****************************************************************************************
     */
    public void lesFil() throws Exception {

        /**
         * *******************************************************
         * Sjekker at det ikke ar skjedd en feilmelding. * sjekker om det er en
         * .txt fil som brukeren velger. * Om det ikke er en .txt fil så vil man
         * få en feilmelding*
         * ********************************************************
         */
        if (!feil) {
            try {
                String[] oppdeling = new String[3];
                oppdeling = filInnhold.split(",");

                for (int i = 0; i < oppdeling.length; i++) {
                    String fornavn = oppdeling[i];
                    i++;
                    String etternavn = oppdeling[i];
                    i++;
                    String epost = oppdeling[i];
                   
                    Bruker nyBruker = new Bruker(epost, fornavn, etternavn, 1, (java.util.UUID.randomUUID().toString().substring(0, 10)));
                    brukere.add(nyBruker);
                    if (fornavn.length() > 30 || etternavn.length() > 100 || !epost.contains("@")) {
                        feil = true;
                        throw new Exception("En feil har oppstått, kontroller om innholdet i filen du valgte å lese er gyldig.");
                    }
                }
                if (!feil) {
                    if (ub.registrerBrukere(brukere)) {
                        SendEpost se = new SendEpost();
                         for(Bruker b : brukere){
                         se.sendEpost(b.getBrukernavn(), "Passord", b.getPassord());
                         }
                    }
                    ub.leggTilBrukereIEmner(emner, brukere);
                }
            } catch (Exception e) {
                throw new Exception("Filens innhold er ikke gyldig, vennligst kontroller.");
            }
        }
    }
}

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
import javax.swing.UIManager;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import spring.ov13.domene.utils.SendEpost;
import spring.ov13.domene.utils.UtilsBean;

public class FilLeser {

    private boolean riktigValg = false;
    private boolean feil = false;
    private String sti = "";
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
         * ************************************
         * Oppretter en kobling til databasen.*
         * ************************************
         */

        /**
         * ****************************************************
         * Løkke kjører til riktig valg eller feil har oppstått*
         * *****************************************************
         */
        while (!riktigValg && !feil) {
             try {
                /**
                 * ******************************************************************************
                 * Oppretter en filvelger, setter innstilliger og lar brukeren
                 * velge fil * Sjekker om brukeren har trykket "velg", om det
                 * skjedde lagrer man filstien * om brukeren ikke velger fil,
                 * eller annen feil oppstår kommern en feilmelding *
                 * ******************************************************************************
                 */

                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setMultiSelectionEnabled(false);
                fc.setApproveButtonText("Velg");
                fc.setApproveButtonToolTipText("Trykk her for å velge markert fil");
                fc.setDialogTitle("Velg filen du ønsker å lese fra");
                JFrame forelder = new JFrame();
                forelder.setVisible(true);
                forelder.toFront();
                int returVerdi = fc.showOpenDialog(forelder);
                forelder.dispatchEvent(new WindowEvent(forelder, WindowEvent.WINDOW_CLOSING));

                if (returVerdi == JFileChooser.APPROVE_OPTION) {
                    File fil = fc.getSelectedFile();
                    sti = fil.getAbsolutePath();
                } else {
                    throw new IOException("Du har valgt å avbryte.");
                }

            } catch (NullPointerException e) {
                feil = true;
                throw new Exception("Du må velge en fil!");
            } catch (IOException e) {
                feil = true;
                throw new Exception(e.getMessage());

            } catch (Exception e) {
                feil = true;
                throw new Exception("En feil har oppstått, vennligst prøv igjen.");
            }

            /**
             * *******************************************************
             * Sjekker at det ikke ar skjedd en feilmelding. * sjekker om det er
             * en .txt fil som brukeren velger. * Om det ikke er en .txt fil så
             * vil man få en feilmelding*
             * ********************************************************
             */
            if (!feil) {

                if (sti.endsWith("txt")) {
                    riktigValg = true;

                    // Oppretter en filleser som leser riktig tegnsett.
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sti), Charset.forName("ISO-8859-1")));
                    try {
                        /**
                         * ************************************************************************
                         * Leser linje for linje i filen. * Splitter på komma
                         * slik at man kan lage en bruker med *
                         * fornavn,etternavn,epost, og autogenerer passord. *
                         * Sjekker at fornavn ikke er større enn 30, etternavn
                         * ikke større enn 100.* Sjekker samtidig om eposten
                         * inneholder en @ * Sjekker om det skjedde feil. Om det
                         * gjorde det blir brukerene ikke * registrert i
                         * databasen. (opprydderklassen kjører en rulltilbake) *
                         * *************************************************************************
                         */

                        String linje = br.readLine();
                        String[] oppdeling = new String[3];

                        while (linje != null) {
                            oppdeling = linje.split(",");
                            String fornavn = oppdeling[0];
                            String etternavn = oppdeling[1];
                            String epost = oppdeling[2];
                            Bruker nyBruker = new Bruker(epost, fornavn, etternavn, 1, (java.util.UUID.randomUUID().toString().substring(0, 10)));
                            brukere.add(nyBruker);
                            linje = br.readLine();
                            if (fornavn.length() > 30 || etternavn.length() > 100 || !epost.contains("@")) {
                                feil = true;
                                throw new Exception("En feil har oppstått, kontroller om innholdet i filen du valgte å lese er gyldig.");
                            }
                        }
                        if (!feil) {
                            if(ub.registrerBrukere(brukere)){
                                /*SendEpost se = new SendEpost();
                                for(Bruker b : brukere){
                                    se.sendEpost(b.getBrukernavn(), "Passord", b.getPassord());
                                }*/
                            }
                            ub.leggTilBrukereIEmner(emner, brukere);

                        }
                    } catch (Exception e) {
                        throw new Exception("Filens innhold er ikke gyldig, vennligst kontroller.");
                    } finally {
                        br.close();
                    }
                } else {
                    throw new Exception("Du må velge fil med riktig format (.txt).");
                }
            }
        }
    }
}

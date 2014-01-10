package spring.FilLeser;

/**
 * @author Sindre
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.UIManager;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import spring.ov13.domene.utils.Database;
import spring.ov13.domene.utils.UtilsBean;

public class FilLeser {

    private boolean riktigValg = false;
    private boolean feil = false;
    private String sti = "";
    private ArrayList<Bruker> brukere = new ArrayList<Bruker>();
    private Emne emne = null;
    private UtilsBean ub = new UtilsBean();
    
    public FilLeser(Emne emne){
        this.emne = emne;
    }
     /******************************************************************************************
      *lesFil()-metoden lar bruker velge en tekstfil med brukere som systemet leser inn        *
      *og registrerer i databasen                                                              *
      * formatet er: fornavn,etternavn,epost                                                   *
      *                                                                                        *
      ******************************************************************************************/
    public void lesFil() throws Exception {
        /**************************************
         * Oppretter en kobling til databasen.*
         **************************************/
        
        
        
         /******************************************************
         * Løkke kjører til riktig valg eller feil har oppstått*
         *******************************************************/
        while (!riktigValg && !feil) {
            try {
         /******************************************************************
         * Sjekker hvilket operativsystem som kjøres og velger hvilken stil*
         * som skal brukes når man skal velge fil.                         *
         *******************************************************************/
                if (System.getProperty("os.name").startsWith("Windows")) {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }

            } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (InstantiationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
        /********************************************************************************
         *Oppretter en filvelger, setter innstilliger og lar brukeren velge fil         *
         * Sjekker om brukeren har trykket "velg", om det skjedde lagrer man filstien   *
         * om brukeren ikke velger fil, eller annen feil oppstår kommern en feilmelding *
         ********************************************************************************/

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
                
                File fil = fc.getSelectedFile();
                if (returVerdi == JFileChooser.APPROVE_OPTION) {
                    sti = fil.getAbsolutePath();
                } else {
                    showMessageDialog(null, "Du har valgt å avbryte.");
                    break;
                }

            } catch (NullPointerException e) {
                showMessageDialog(null, "Du må velge en fil!");
                feil = true;
            } catch (Exception e) {
                showMessageDialog(null, "En feil har oppstått, vennligst prøv igjen.");
                feil = true;
            }
            
         /*********************************************************
         * Sjekker at det ikke ar skjedd en feilmelding.          *
         * sjekker om det er en .txt fil som brukeren velger.     *
         * Om det ikke er en .txt fil så vil man få en feilmelding*
         **********************************************************/
            
            if (!feil) {

                if (sti.endsWith("txt")) {
                    riktigValg = true;
                    
                    // Oppretter en filleser som leser riktig tegnsett.
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sti), Charset.forName("ISO-8859-1")));
                    try {
         /**************************************************************************
         * Leser linje for linje i filen.                                          *         
         * Splitter på komma slik at man kan lage en bruker med                    *
         * fornavn,etternavn,epost, og autogenerer passord.                        *
         * Sjekker at fornavn ikke er større enn 30, etternavn ikke større enn 100.*
         * Sjekker samtidig om eposten inneholder en @                             *
         * Sjekker om det skjedde feil. Om det gjorde det blir brukerene ikke      *
         * registrert i databasen. (opprydderklassen kjører en rulltilbake)         *
         ***************************************************************************/
                        
                        String linje = br.readLine();
                        String[] oppdeling = new String[3];

                        while (linje != null) {
                            oppdeling = linje.split(",");
                            String fornavn = oppdeling[0];
                            String etternavn = oppdeling[1];
                            String epost = oppdeling[2];
                            Bruker nyBruker = new Bruker(epost, fornavn, etternavn, 1, (java.util.UUID.randomUUID().toString().substring(0,10)));
                            brukere.add(nyBruker);
                            linje = br.readLine();
                            if (fornavn.length() > 30 || etternavn.length() > 100 || !epost.contains("@")) {
                                feil = true;
                                showMessageDialog(null, "En feil har oppstått, kontroller om innholdet i filen du valgte å lese er gyldig.");
                                break;
                            }
                        }
                        if (!feil) {
                           ub.registrerBrukere(brukere);
                           ub.leggTilBrukereIEmne(emne, brukere);
                           
                        }
                    } catch (Exception e) {
                        System.out.println("En feil er oppstått, feilmelding: " + e + "\n Prøv på nytt.");
                    } finally {
                        br.close();
                    }
                } else {
                    showMessageDialog(null, "Du må velge fil med riktig format (.txt).");
                }
            }
        }
    }
}

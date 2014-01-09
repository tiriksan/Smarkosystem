package spring.FilLeser;

/**
 * @author Sindre
 */
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.UIManager;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.utils.Database;

public class FilLeser1 {

    private boolean riktigValg = false;
    private boolean feil = false;
    private String sti = "";
    private ArrayList<Bruker> listeMidlertidig = new ArrayList<Bruker>();

    /*lesFil()-metoden lar bruker velge en tekstfil med brukere som systemet leser inn 
     og registrerer i databasen*/
    public void lesFil() throws Exception {
        Database db = new Database();
        while (!riktigValg && !feil) {
            try {

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

                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setMultiSelectionEnabled(false);
                fc.setApproveButtonText("Velg");
                fc.setApproveButtonToolTipText("Trykk her for å velge markert fil");
                fc.setDialogTitle("Velg filen du ønsker å lese fra");

                Component forelder = null;
                int returVerdi = fc.showOpenDialog(forelder);
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
            if (!feil) {

                if (sti.endsWith("txt")) {
                    riktigValg = true;
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sti), "UTF-8"));
                    try {
                        String linje = br.readLine();
                        String[] oppdeling = new String[3];

                        while (linje != null) {
                            oppdeling = linje.split(",");
                            String fornavn = oppdeling[0];
                            String etternavn = oppdeling[1];
                            String epost = oppdeling[2];
                            Bruker nyBruker = new Bruker(epost, fornavn, etternavn, 1, "passord");
                            listeMidlertidig.add(nyBruker);
                            linje = br.readLine();
                            if (fornavn.length() > 30 || etternavn.length() > 100 || !epost.contains("@")) {
                                feil = true;
                                showMessageDialog(null, "En feil har oppstått, kontroller om innholdet i filen du valgte å lese er gyldig.");
                                break;
                            }
                        }
                        if (!feil) {
                            for (int i = 0; i < listeMidlertidig.size(); i++) {
                                db.registrerBruker(listeMidlertidig.get(i));
                            }
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

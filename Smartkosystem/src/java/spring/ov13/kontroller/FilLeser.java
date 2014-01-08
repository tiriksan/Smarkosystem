package spring.ov13.kontroller;

/**
 * @author Sindre
 */
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.utils.Database;

public class FilLeser {

    public void lesFil() throws Exception {
       Database db = new Database();
       try
        {   
            
            if (System.getProperty("os.name").startsWith("Windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            
        }
        catch (ClassNotFoundException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (InstantiationException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        ArrayList<Bruker> listeMidlertidig = new ArrayList<Bruker>();
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setApproveButtonText("Velg");
        fc.setApproveButtonToolTipText("Trykk her for å velge markert fil");
        fc.setDialogTitle("Velg filen du ønsker å lese fra");
       
        
        
        Component forelder = null;
        int returVerdi = fc.showOpenDialog(forelder);
        File fil = fc.getSelectedFile();
        String sti = fil.getAbsolutePath();
        
        BufferedReader br = new BufferedReader(new FileReader(sti));
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
            }
            for (int i = 0; i < listeMidlertidig.size(); i++) {
               
               db.registrerBruker(listeMidlertidig.get(i));
            }
        } catch (Exception e) {
            System.out.println("En feil er oppstått, feilmelding: " + e +"\n Prøv på nytt.");
        } finally {
            br.close();
        }
    }
}

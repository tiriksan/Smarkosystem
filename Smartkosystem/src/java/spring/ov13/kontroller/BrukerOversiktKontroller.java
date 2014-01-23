package spring.ov13.kontroller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import spring.ov13.domene.Kravgruppe;
import spring.ov13.domene.utils.SendEpost;
import spring.ov13.domene.utils.UtilsBean;

/**
 * @author Sindre
 */
@SessionAttributes("brukerinnlogg")
@Controller
public class BrukerOversiktKontroller {

    @RequestMapping(value = "/velgEmneBrukeroversikt.htm")
    public String velgEmneBrukerOversikt(Model model, @ModelAttribute(value = "brukerinnlogg") Bruker innloggetBruker, HttpServletRequest request) {
        UtilsBean ub = new UtilsBean();

        ArrayList<Emne> fagene = ub.getFageneTilBruker(innloggetBruker.getBrukernavn());
        model.addAttribute("fagene", fagene);
        request.getSession().setAttribute("emne", null);
        return "velgEmne";
    }

    @RequestMapping(value = "/valgtBrukeroversikt.htm")
    public String sendVidereTilBrukerOversikt(Model model, @ModelAttribute(value = "brukerinnlogg") Bruker innloggetBruker, HttpServletRequest request, @RequestParam(value = "emnekode", required = false) String emnekode) {
        if(emnekode == null){
            emnekode = (String)request.getSession().getAttribute("emnekode");
        }
        UtilsBean ub = new UtilsBean();
        ArrayList<Bruker> studenter = new ArrayList<Bruker>();
        innloggetBruker.setBrukertype(ub.getBrukertypeiEmne(innloggetBruker.getBrukernavn(), emnekode));
        int antØvinger = ub.getAntOvingerIEmne(emnekode);
        request.getSession().setAttribute("emne", ub.getEmne(emnekode));
        ArrayList<Kravgruppe> kravgrupper = ub.getKravGruppetilEmne(emnekode);
        ArrayList<Boolean> godkjenteKrav = ub.getBrukerGodkjentArbeidskravIEmne(innloggetBruker.getBrukernavn(),  emnekode);
        model.addAttribute("kravgrupper", kravgrupper);
        model.addAttribute("godkjentKrav", godkjenteKrav);
        if (innloggetBruker.getBrukertype() == 1) {
            System.out.println("here1");
            int[] godkjenteOvinger = ub.getGodkjentOvingerForBrukerIEmne(innloggetBruker.getBrukernavn(), emnekode, antØvinger);
            model.addAttribute("ovinger", godkjenteOvinger);
            System.out.println(godkjenteOvinger);
        } else {

            studenter = ub.getStudenterIEmnet(emnekode);
            model.addAttribute("studenter", studenter);

            
            int[][] alleBrukereGodkjenteOvinger = new int[studenter.size()][antØvinger];
            int[] godkjenteOvinger;

            for (int i = 0; i < studenter.size(); i++) {
                godkjenteOvinger = ub.getGodkjentOvingerForBrukerIEmne(studenter.get(i).getBrukernavn(), emnekode, antØvinger);
                                System.arraycopy(godkjenteOvinger, 0, alleBrukereGodkjenteOvinger[i], 0, antØvinger);
            }
            model.addAttribute("aGO", alleBrukereGodkjenteOvinger);
            
            Boolean[][] alleBrukereGodkjenteKrav = new Boolean[studenter.size()][kravgrupper.size()];
            Boolean[] currBrukerGkKrav;
            for(int i = 0; i< studenter.size(); i++){
                currBrukerGkKrav = (Boolean[])(ub.getBrukerGodkjentArbeidskravIEmne(studenter.get(i).getBrukernavn(), emnekode).toArray(new Boolean[kravgrupper.size()]));
                alleBrukereGodkjenteKrav[i] = currBrukerGkKrav;
            }
            boolean[] kanBrukereTilEksamen = new boolean[studenter.size()];
            for(int i = 0; i< studenter.size(); i++){
                boolean kanGåOppTilEksamen = true;
                for(int j = 0; j<kravgrupper.size();j++){
                    if(alleBrukereGodkjenteKrav[i][j] == false){
                        kanGåOppTilEksamen = false;
                    }
                }
                kanBrukereTilEksamen[i] = kanGåOppTilEksamen;
            }
            request.getSession().setAttribute("eksamenTabell", kanBrukereTilEksamen);
            request.getSession().setAttribute("studenterIFaget", studenter);
            request.getSession().setAttribute("emnekode", emnekode);
            model.addAttribute("kravgruppeBruker", alleBrukereGodkjenteKrav);
            
        }
        return "velgEmne";
    }
    
    @RequestMapping(value = "/sendAdvarselMail.htm")
    public String eksamensListe(Bruker bruker, HttpServletRequest request, RedirectAttributes ra)throws IOException{
        
        boolean[] brukerEksamen = (boolean[])request.getSession().getAttribute("eksamenTabell");
        ArrayList<Bruker> studenter = (ArrayList<Bruker>)request.getSession().getAttribute("studenterIFaget");
        String emnekode = (String)request.getSession().getAttribute("emnekode");
        
        File fi = new File("Varsel for " + emnekode + ".txt");
        if(!fi.exists()){
            fi.createNewFile();
        }

        PrintWriter pw = new PrintWriter(fi, "UTF-8");
        
        String advarsel = "";
        
        String melding = "Du har ikke oppfylt kravene for " + emnekode;
        SendEpost epost = new SendEpost();
        for(int r = 0; r < brukerEksamen.length; r++){
            if(brukerEksamen[r] == false){
                epost.sendEpost(studenter.get(r).getBrukernavn(), "Advarsel", melding);
                pw.println("" + studenter.get(r).getFornavn() + " " + studenter.get(r).getEtternavn() + ", " + studenter.get(r).getBrukernavn());
            }
        }
        pw.close();
        
        SendEpost se = new SendEpost();
        se.sendEpost("msnorc@gmail.com", "Varsel liste for " + emnekode, "Elever som ikke har godkjente arbeidskrav i " + emnekode, fi);
        
        ra.addFlashAttribute("emnekode", emnekode);
        return "redirect:valgtBrukeroversikt.htm";
    }
    
    @RequestMapping(value = "/resepsjonListe.htm")
    public String listeResepsjon(Bruker bruker, HttpServletRequest request, RedirectAttributes ra) throws IOException{
        
        boolean[] brukerEksamen = (boolean[])request.getSession().getAttribute("eksamenTabell");
        ArrayList<Bruker> studenter = (ArrayList<Bruker>)request.getSession().getAttribute("studenterIFaget");
        String emnekode = (String)request.getSession().getAttribute("emnekode");
        
        File f = new File("/ListeOver"+emnekode+".txt");
        System.out.println(f.exists());
        if(!f.exists()){
            f.createNewFile();
        }
        PrintWriter writer = new PrintWriter(f, "UTF-8");
        for(int a = 0; a < brukerEksamen.length; a++){
            if(brukerEksamen[a] == true){
                writer.println("" + studenter.get(a).getFornavn() + " " + studenter.get(a).getEtternavn() + ", " + studenter.get(a).getBrukernavn());
            }
        }
        System.out.println("here?");
        writer.close();
        
        SendEpost se = new SendEpost();
        //TODO:
        se.sendEpost("kristian.aabrekk@gmail.com", "Eksamen info i " + emnekode, "Her kommer en liste med studenter som får gå opp til eksamen i emnet " +emnekode , f);
        
        System.out.println(f.exists());
        System.out.println(f.getPath());
        ra.addFlashAttribute("emnekode", emnekode);
        return "redirect:valgtBrukeroversikt.htm";
        
    }

}

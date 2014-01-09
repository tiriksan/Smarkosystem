package spring.ov13.kontroller;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import spring.ov13.domene.utils.UtilsBean;
import org.springframework.web.bind.annotation.RequestParam;
import spring.FilLeser.FilLeser;
import spring.ov13.domene.utils.SendEpost;

@Controller
public class Kontroller {

    @RequestMapping(value = "/*")
    public String visIndex() {
        return "index";
    }
    @RequestMapping(value = "/bruker.htm")
    public String visInnsetting(Model model, @RequestParam(value = "x", required = false) String getValg) {
        Bruker bruker = new Bruker();
        Emne fag = new Emne();
        model.addAttribute("bruker", bruker);
        model.addAttribute("fag", fag);
        
        model.addAttribute("valget", getValg);
        
        
        return "bruker";
    }
    @RequestMapping(value = "/registrerBrukereFraFil.htm")
    public String regBrukereFraFil(Model model, @RequestParam(value = "x", required = false) String getValg) {
        
        FilLeser fl = new FilLeser();
        try {
            fl.lesFil();
        } catch (Exception ex) {
            showMessageDialog(null, "Feil ved registrering oppstått, avbryter.");
        }
        model.addAttribute("valget", getValg);
        
        
        return "redirect:/bruker.htm?x=2";
    }
    
       @RequestMapping(value = "/logginn.htm")
    public String visLogginn(Model model, @ModelAttribute(value="brukerinnlogg") Bruker bruker) {
        
        Bruker bruker2 = new Bruker();
        model.addAttribute("bruker", bruker2);
        return "logginn";
    }
    
           @RequestMapping(value = "login")
           @ResponseBody
    public String Logginn(@ModelAttribute(value="brukerinnlogg") Bruker bruker, BindingResult error) {
        
       String brukernavn = bruker.getBrukernavn();
       String passord = bruker.md5(bruker.getPassord());
       
       
        return bruker.getBrukernavn();
    }
    
    @RequestMapping(value = "/brukerinnsetning.htm")
    public String visBrukerinnsetning(@Validated @ModelAttribute("bruker") Bruker bruker, @ModelAttribute("fag") Emne fag, BindingResult error, Model modell, HttpServletRequest request){
        
        
        
        
        if (error.hasErrors()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av bruker.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
            return "bruker";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerBruker(bruker)) {
            modell.addAttribute("melding", "Bruker " + bruker + " er registrert");
            return "bruker";
        }
        
        
       return "bruker"; 
    }
    //FAG //
    
   
    
    
    
      @RequestMapping(value = "/fag.htm")
    public String visInnsetting2(Model model, @RequestParam(value = "x", required = false) String getValg) {
        Emne fag = new Emne();
        model.addAttribute("fag", fag);
        
       // model.addAttribute("valget", getValg);
        
        
        return "fag";
    }
    
     @RequestMapping(value = "/faginnsetting.htm")
    public String visFaginnsetning(@Validated @ModelAttribute("fag") Emne fag, BindingResult error, Model modell, HttpServletRequest request){
        
        
        
        
        if (error.hasErrors()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av fag.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
            return "fag";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerFag(fag)) {
            modell.addAttribute("melding", "Fag" + fag + " er registrert");
            return "fag";
        }
        
        
       return "fag"; 
    }
    
    
    @RequestMapping(value = "/glemtpassordsvar.htm")
    public String glemtPassordSvar(@Validated @ModelAttribute("glemtpassordbruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request){
        System.out.println("Glemtpassord");
        System.out.println(bruker.getBrukernavn());    
     //   if (error.hasErrors()) {
     //       System.out.println("feil");
     //       return "glemtpassord";
     //  }
        UtilsBean utilsBean = new UtilsBean();
        if(utilsBean.get(bruker.getBrukernavn()) == null){
            modell.addAttribute("errorMelding", "Brukeren med dette brukernavnet eksisterer ikke. Sjekk om brukernavnet stemmer");
            System.out.println("Finner ikke i databasen");
            return "glemtpassord";
        } else {
            System.out.println("Sender epost?");
            SendEpost epost = new SendEpost();
            epost.sendEpost(bruker.getBrukernavn(), "http://localhost:8079/Smartkosystem/endrepassord.htm?bruker="+bruker.getBrukernavn());
            return "glemtpassord";
        }
    }
    @RequestMapping(value = "/glemtpassord.htm")
    public String glemtPassord(Model modell){
            Bruker b = new Bruker();
            modell.addAttribute("glemtpassordbruker", b);
            return "glemtpassord";
    }
    @RequestMapping(value = "/endrepassord.htm")
    public String endrePassord(Model model, @RequestParam(value = "bruker", required = false) String getValg, HttpServletRequest request){
        UtilsBean ub = new UtilsBean();
        Bruker bruker = ub.get(request.getParameter("bruker"));
        model.addAttribute("endrepassordbruker", bruker);
        //bruker.setBrukernavn();
        return "endrepassord";
        
    }
    @RequestMapping(value = "/endrepassordsvar.htm")
    public String endrePassordSvar(@Validated @ModelAttribute("endrepassordbruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request){
        if(error.hasErrors()){
            return "endrepassord";
        }
        
        
        UtilsBean ub = new UtilsBean();
        
        ub.oppdaterBruker(bruker);
        
        //bruker.setBrukernavn();
        return "logginn";
        
    }
  
    /*
    @RequestMapping(value = "/bruker.htm")
    public String visVare(Model model) {
        model.addAttribute("utilsBean", new UtilsBean());
        return "bruker";
    }
*//*
    @RequestMapping(value = "/nyvare.htm")
    public String visNyVare(Model model) {
        Bruker v = new Bruker();
        model.addAttribute("vare", v);

        return "nyvare";
    }

    @RequestMapping(value = "svarside.htm")
    public String svarside(@Validated @ModelAttribute("bruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request) {
        if (error.hasErrors()) {
            modell.addAttribute("melding", "Vare ikke fylt ut riktig"); // kun ibruk v return svarside
            return "nyvare";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerBruker(bruker)) {
            modell.addAttribute("melding", "Vare " + bruker + " er registrert");
            return "svarside";
        } else {
            return "nyvare";
        }
    }
}
*/
/*
    @RequestMapping(value = "/oversikt.htm")
    public String visOversikt(@ModelAttribute(value = "utilsBean") UtilsBean utilsBean, Model modell, HttpServletRequest request, HttpServletResponse response) {
        String slett = request.getParameter("slett");
        String send = request.getParameter("send");
        if (send != null) {
            if (utilsBean.getValgteVarer() != null && utilsBean.getValgteVarer().size() > 0) {
                return "oversikt";
            } else {
                System.out.println("Ingen personer valgt");
                return "vare";
            }
        } else if (slett != null) {
            List<Bruker> vv = utilsBean.getValgteVarer();
            System.out.println("Troll");
            if (vv == null) {
                System.out.println("asdfsfdafdsafsdafsda");
            } else {
                for (Bruker v : vv) {

                    System.out.println(v.toString());
                }
            }
            System.out.println("*** slett vare **** ");
            if (vv != null) {
                for (Bruker v : vv) {
                    System.out.println("Sletting av " + v + ": " + utilsBean.slettVare(v));

                }
                modell.addAttribute("utilsBean", new UtilsBean());
                return "vare";
            }
        } else {

            List<Bruker> alle = utilsBean.getAlleVarer();
            if (alle != null) {
                for (Bruker v : alle) {
                    System.out.println("oppdatering av " + v + ":" + utilsBean.oppdaterVare(v));
                }
            }
            modell.addAttribute("utilsBean", new UtilsBean());
            return "vare";
        }
        return "vare";
    }*/

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Bruker.class, new BrukerEditor(new UtilsBean()));
    }
}
package spring.ov13.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMethod;
import spring.ov13.domene.utils.UtilsBean;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import spring.FilLeser.FilLeser;

@SessionAttributes("skjema")
@Controller
public class Kontroller {

    @RequestMapping(value = "/*")
    public String visIndex() {
        return "index";
    }

    @RequestMapping(value = "/bruker.htm")
    public String visInnsetting(Model model, @ModelAttribute("feilmelding") String feil, @RequestParam(value = "x", required = false) String getValg) {
        Bruker bruker = new Bruker();
        Emne emne = new Emne();
        model.addAttribute("bruker", bruker);
        model.addAttribute("emne", emne);
        UtilsBean ub = new UtilsBean();
        model.addAttribute("valget", getValg);
        ArrayList<Emne> fag = ub.getAlleFag();
        ArrayList<String> fagtabell = new ArrayList<String>();
        for (int i = 0; i < fag.size(); i++) {
            fagtabell.add(fag.get(i).getEmnenavn());
        }
        model.addAttribute("allefagene", fagtabell);

        if (getValg != null) {
            if (getValg.equals("3")) {

                ArrayList<Bruker> faget = ub.getAlleBrukereAvBrukertype(3);
                ArrayList<String> brukertabell = new ArrayList<String>();
                for (int i = 0; i < faget.size(); i++) {
                    brukertabell.add(faget.get(i).getFornavn() + " " + faget.get(i).getEtternavn());
                }
                model.addAttribute("allelaerere", brukertabell);
            }
        }

        return "bruker";
    }

    @RequestMapping(value = "/registrerBrukereFraFil.htm", method = RequestMethod.POST)
    public String regBrukereFraFil(Model model, @ModelAttribute(value = "emne") Emne emne, BindingResult error) {
        UtilsBean ub = new UtilsBean();
        Emne emnet = ub.getEmne(emne.getEmnekode());
        if (emnet != null) {
            FilLeser fl = new FilLeser(emnet);
            try {
                fl.lesFil();
            } catch (Exception ex) {
                model.addAttribute("feilmelding", ex.getMessage());
            }
        } else {
            if (emne.getEmnekode().equals("")) {
                model.addAttribute("feilmelding", "Feilmelding: Du har ikke fyllt inn emnekoden.");
            } else {
                model.addAttribute("feilmelding", "Feil ved registrering inntruffet, emnet du oppgav eksisterer ikke, vennligst kontroller opplysningene du oppgav eller registrer emnet først.");
            }
        }
        emne = null;
        return "redirect:/bruker.htm?x=2";
    }

    @RequestMapping(value = "/logginn.htm")
    public String visLogginn(Model model, @ModelAttribute(value = "brukerinnlogg") Bruker bruker) {

        Bruker bruker2 = new Bruker();
        model.addAttribute("bruker", bruker2);
        return "logginn";
    }

    @RequestMapping(value = "login")
    @ResponseBody
    public String Logginn(@ModelAttribute(value = "brukerinnlogg") Bruker bruker, BindingResult error) {

        String brukernavn = bruker.getBrukernavn();
        String passord = bruker.md5(bruker.getPassord());

        return bruker.getBrukernavn();
    }

    @RequestMapping(value = "/brukerinnsetning.htm")
    public String visBrukerinnsetning(@Validated @ModelAttribute(value = "bruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request) {

        if (error.hasErrors()) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av bruker.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
            return "bruker";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerBruker(bruker)) {
            modell.addAttribute("melding", "Bruker " + bruker + " er registrert");

        }

        return "bruker";
    }
    //FAG //

    @RequestMapping(value = "/bruker.htm?x=3")
    public String visInnsetting2(Model model) {
        Emne emne = new Emne();
        model.addAttribute("emne", emne);

        UtilsBean ub = new UtilsBean();
        System.out.println("----------------Henter ut getAlleFagLærerre-------------");
        ArrayList<Bruker> faget = ub.getAlleBrukereAvBrukertype(3);
        ArrayList<String> brukertabell = new ArrayList<String>();
        for (int i = 0; i < faget.size(); i++) {
            brukertabell.add(faget.get(i).getFornavn() + " " + faget.get(i).getEtternavn());
        }
        model.addAttribute("allelaerere", brukertabell);

        // model.addAttribute("valget", getValg);
        return "emne";
    }

    @RequestMapping(value = "/faginnsetting.htm")
    public String visEmneinnsetning(@Validated @ModelAttribute("emne") Emne emne, BindingResult error, Model modell, HttpServletRequest request) {

        if (error.hasErrors()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av fag.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
            return "emne";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerEmne(emne)) {
            modell.addAttribute("melding", "Fag" + emne + " er registrert");
            return "emne";
        }

        /*  UtilsBean utilsBean2 = new UtilsBean();
         if (utilsBean2.hentUtFagListe(fag)) {  // forandre til riktig metodenavn //
           
         }*/
        return "emne";
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

package spring.ov13.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.naming.factory.SendMailFactory;
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
import spring.ov13.domene.utils.SendEpost;
import spring.ov13.domene.Øving;

//@SessionAttributes("skjema")
@SessionAttributes("brukerinnlogg")
@Controller
public class Kontroller {

    @ModelAttribute(value = "brukerinnlogg")
    public Bruker lage2() {

        return new Bruker();
    }

    @RequestMapping(value = "/*")
    public String visIndex(@ModelAttribute("brukerinnlogg") Bruker bruker) {
        if (bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")) {
            System.out.println(bruker.getBrukernavn() + " TESTER BRUKERNAVN");
            return "redirect:/logginn.htm";
        } else {
            return "index";
        }
    }

    @RequestMapping(value = "/bruker.htm")
    public String visInnsetting(Model model, @ModelAttribute("feilmelding") String feil, @RequestParam(value = "x", required = false) String getValg) {
        Bruker bruker = new Bruker();
        Emne emne = new Emne();
        Emne emner = new Emne();
        model.addAttribute("bruker", bruker);
        model.addAttribute("emne", emne);
        model.addAttribute("emner", emner);
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
    public String regBrukereFraFil(Model model, @ModelAttribute(value = "emner") Emne emner, BindingResult error) {
        UtilsBean ub = new UtilsBean();
        boolean emneSjekkOk = true;
        ArrayList<Emne> emnerListe = new ArrayList<Emne>();
        String[] emnekoder = emner.getEmnekode().split(",");
        for (int i = 0; i < emnekoder.length; i++) {
            if (ub.getEmne(emnekoder[i]) == null) {
                emneSjekkOk = false;
            }
        }
        if (emneSjekkOk) {
            for (int i = 0; i < emnekoder.length; i++) {
                emnerListe.add(ub.getEmne(emnekoder[i]));
            }
            if (emnerListe != null) {
                FilLeser fl = new FilLeser(emnerListe);
                try {
                    fl.lesFil();
                } catch (Exception ex) {
                    model.addAttribute("feilmelding", ex.getMessage());
                }
            } else {
                if (emnerListe.size() == 0) {
                    model.addAttribute("feilmelding", "Feilmelding: Du har ikke fyllt inn emnekode(r).");
                } else {
                    model.addAttribute("feilmelding", "Feil ved registrering inntruffet, emnet/noen av emenene du oppgav eksisterer ikke, vennligst kontroller opplysningene");
                }
            }
        }
        emner = null;
        return "redirect:/bruker.htm?x=2";
    }

    @RequestMapping(value = "/brukerinnsetning.htm")
    public String visBrukerinnsetning(@Validated @ModelAttribute(value = "bruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request) {

        if (error.hasErrors()) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av bruker.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
            return "bruker";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerBruker(bruker)) {
            SendEpost se = new SendEpost();
            se.sendEpost(bruker.getBrukernavn(), "Passord", bruker.getPassord());
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
    
     <<<<<<< HEAD
     =======
     @RequestMapping(value = "/regov2.htm")
     public String visØvinginnsetning(@Validated @ModelAttribute(value = "øving") Øving øving, BindingResult error, Model modell, HttpServletRequest request) {

     if (error.hasErrors()) {
     //javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av øving.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
     return "regov2";
     }
     UtilsBean utilsBean = new UtilsBean();
     if (utilsBean.registrerØving(øving)) {
     modell.addAttribute("melding", "Øving" + øving + " er registrert");

     }

     return "regov2";
     }
     */
    @RequestMapping(value = "/regov2.htm")
    public String visØvinginnsetning(@ModelAttribute(value = "øving") Øving øving, BindingResult error) {

        System.out.println("--------------kommerinn-----------");
        return "regov2";
    }

    @RequestMapping(value = "regov23")
    public String regØv(@Validated @ModelAttribute(value = "regov23") Øving øving, BindingResult error, Model modell, HttpServletRequest request) {

        if (error.hasErrors()) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av bruker.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
            return "regov2";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerØving(øving)) {
            modell.addAttribute("melding", "Øving " + øving + " er registrert");

        }

        return "regov2";

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

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
import org.springframework.web.servlet.ModelAndView;
import spring.FilLeser.FilLeser;
import spring.ov13.domene.Kravgruppe;
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
    
    
//************************ Viser innleggingssiden ***********************
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
   
            
            
            
            
//*******************************Registerer bruker fra fil**********************************
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
    
    
    
    
    
    
    
// ********************Registrer bruker************************
    @RequestMapping(value = "/brukerinnsetning.htm")
    public String visBrukerinnsetning(@Validated @ModelAttribute(value = "bruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request) {
/*
        if (error.hasErrors()) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av bruker.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
            return "bruker";
        }
        */
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerBruker(bruker)) {
            SendEpost se = new SendEpost();
            se.sendEpost(bruker.getBrukernavn(), "Passord", bruker.getPassord());
            modell.addAttribute("melding", "Bruker " + bruker + " er registrert");

        }

        return "bruker";
    }
 //**************************Registerer emner********************************************
    @RequestMapping(value="/innsettemne.htm")
    public ModelAndView regEmne(@Validated @ModelAttribute(value = "emne") Emne emne, BindingResult error, Model modell, HttpServletRequest request, @RequestParam(value = "laerer") String [] laerer){
        System.out.println("-------------------- kommer inn i regEmne---------------");
        String [] values = request.getParameterValues("laerer");
              System.out.println("Her skal det komme opp noe nå" + values[0]);
       /*
        if(error.hasErrors()){
            System.out.println("----------------------- kommer inn i hasErrors()------------");
        modell.addAttribute("Noeerfeil", "En feil har oppstått");
            return new ModelAndView("redirect:/bruker.htm?x=3","modell",modell);
            }
       */
        UtilsBean utilsBean = new UtilsBean();
        if(utilsBean.registrerEmne(emne)) {
            System.out.println("----------------------kommer inn i registrerEmne() i db-----------------");
            modell.addAttribute("melding", "Emne " + emne + " er registrert");
        }
     return new ModelAndView("redirect:/bruker.htm?x=3","modell",modell);
    }
      
    
    
    
    
    
    
    //******************* viser registreringen av en ny øving*****************************************************
   @RequestMapping(value = "regov2")
   public String visØvinginnsetning(Model model, @ModelAttribute(value = "øving") Øving øving, BindingResult error) {
        
        Bruker bruker = new Bruker();
        Emne emne = new Emne();
        Kravgruppe kravg = new Kravgruppe();
        UtilsBean ub = new UtilsBean();
        ArrayList<Emne> em = ub.getAlleFag();
        String emnekoden = null;
       
        ArrayList<String> emnetabell = new ArrayList<String>();
        
        for (int i = 0; i < em.size(); i++) {
            emnetabell.add(em.get(i).getEmnenavn());
           emnekoden = em.get(i).getEmnenavn();
            System.out.println("----------Skrives emnekode ut her? ser ut som emnenavn  " + emnekoden );
            model.addAttribute("emnekode", emnekoden);
          
        }
        model.addAttribute("allefagene", emnetabell);
       
        
         
          ArrayList<Kravgruppe> gr = ub.getKravGruppetilEmne(emnekoden);
          ArrayList<Integer> grid = new ArrayList<Integer>();
          for (int i = 0; i < gr.size(); i++) {
            grid.add(gr.get(i).getGruppeID());
            
          }
          
           model.addAttribute("allegruppeid", grid);
           
           
        
        return "regov2";
    }
    
    //*************************Registrerer en ny øving*****************************
    @RequestMapping(value = "regov23", method = RequestMethod.POST)
    public ModelAndView regØv(@Validated @ModelAttribute(value = "øving") Øving øving, BindingResult error, Model model, HttpServletRequest request, @RequestParam(value="obligatorisk") boolean obligatorisk, @RequestParam(value = "Emner") String [] Emner) {

     
      //Øving øvinga = new Øving();
      
      //int nr = øving.getØvingsnr();
      // String ja = request.getAttribute("Emner");
       String [] values = request.getParameterValues("Emner");
      // for(int i= 0; i<values.length; i++){
          System.out.println("Her skal det komme opp noe nå" + values[0]);
          
          
          
         // ub.oppdaterØving(øving, nr , "100");
         // øvinga.setEmnekode("100");
    //   }
       
       
       
        
     if (error.hasErrors()) {
             System.out.println("--------------kommerinniERROOOOOOOOOOOOOOOOOOOOOOOOOOOR-----------");
            //javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av bruker.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
          //  return "regov2";
            return new ModelAndView("redirect:/regov2.htm?x=3","modell",model);
        }
        UtilsBean utilsBean = new UtilsBean();
      //utilsBean.oppdaterØving(øving, nr , values[0]);
      //hente inn den oppdaterte øving her // // trenger metode i database.java for å klare det //
      øving.setEmnekode(values[0]);
      øving.setGruppeid(1);
        String hentekode = øving.getEmnekode();
       System.out.println("EMNEKODE IKKE VÆR NULL!" + hentekode);
      
        if (utilsBean.registrerØving(øving)) {
            System.out.println("KJEMPESUKSESS-----------------------------------------------------------HURRA FOR SPRING");
            model.addAttribute("melding", "Øving " + øving + " er registrert");

        }

       // return "regov2";
        return new ModelAndView("redirect:/regov2.htm?x=3","modell",model);

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

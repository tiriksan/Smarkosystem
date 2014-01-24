package spring.ov13.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import spring.ov13.domene.FilOpplasting;
import spring.ov13.domene.Kravgruppe;
import spring.ov13.domene.utils.SendEpost;
import spring.ov13.domene.Øving;

//@SessionAttributes("skjema")
@SessionAttributes("brukerinnlogg")
@Controller
public class Kontroller {

    private ArrayList<Integer> valgteOvinger = new ArrayList<Integer>();

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
        FilOpplasting filOpp = new FilOpplasting();
        model.addAttribute("bruker", bruker);
        model.addAttribute("emne", emne);
        model.addAttribute("filOpplasting", filOpp);
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
    public String regBrukereFraFil(Model model, @RequestParam(value = "filInnhold", required = true) String tekst, @ModelAttribute(value = "filOpplasting") FilOpplasting filOpp, BindingResult error) {

        filOpp.setFilInnhold(tekst);
        UtilsBean ub = new UtilsBean();
        boolean emneSjekkOk = true;
        ArrayList<Emne> emnerListe = new ArrayList<Emne>();
        String[] emnekoder = filOpp.getEmner().split(",");
        for (int i = 0; i < emnekoder.length; i++) {
            if (ub.getEmne(emnekoder[i]) == null) {
                emneSjekkOk = false;
            }
        }
        if (emneSjekkOk) {
            for (int i = 0; i < emnekoder.length; i++) {
                emnerListe.add(ub.getEmne(emnekoder[i]));
            }
            if (!emnerListe.isEmpty()) {
                FilLeser fl = new FilLeser(emnerListe, filOpp.getFilInnhold());
                try {
                    fl.lesFil();
                } catch (Exception ex) {
                    model.addAttribute("feilmelding", ex.getMessage());
                }
            } else {
                if (emnerListe.isEmpty()) {
                    model.addAttribute("feilmelding", "Feilmelding: Du har ikke fyllt inn emnekode(r).");
                } else {
                    model.addAttribute("feilmelding", "Feil ved registrering inntruffet, emnet/noen av emenene du oppgav eksisterer ikke, vennligst kontroller opplysningene");
                }
            }
        }
        filOpp = null;
        return "redirect:/bruker.htm?x=2";
    }

// ********************Registrer bruker************************
    @RequestMapping(value = "/brukerinnsetning.htm")
    public String visBrukerinnsetning(@Validated @ModelAttribute(value = "bruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request, @RequestParam(value = "fagene") String[] fagene) {

        String[] values = request.getParameterValues("fagene");
        System.out.println("Her skal det komme opp noe nå " + values[0]);
        ArrayList<Emne> emneliste = new ArrayList<Emne>();
        UtilsBean utilsBean = new UtilsBean();
        Emne returnen = utilsBean.getEmne(values[0]);
        emneliste.add(returnen);

        bruker.setFagene(emneliste);
        if (utilsBean.registrerBruker(bruker)) {
            SendEpost se = new SendEpost();
            se.sendEpost(bruker.getBrukernavn(), "Passord", bruker.getPassord());
            modell.addAttribute("melding", "Bruker " + bruker + " er registrert");

        }

        return "bruker";
    }

    //**************************Registerer emner********************************************
    @RequestMapping(value = "/innsettemne.htm")
    public ModelAndView regEmne(@Validated @ModelAttribute(value = "emne") Emne emne, BindingResult error, Model modell, HttpServletRequest request, @RequestParam(value = "laerer") String[] laerer) {
        System.out.println("-------------------- kommer inn i regEmne---------------");
        String[] values = request.getParameterValues("laerer");
        System.out.println("Her skal det komme opp noe nå " + values[0]);
        String[] tabell = values[0].split(" ");
        String fornavn = tabell[0];
        String etternavn = tabell[1];
        System.out.println("fornavn " + fornavn + " etternavn " + etternavn);
        UtilsBean utilsBean = new UtilsBean();
        ArrayList<Bruker> returnen = utilsBean.getFaglærerBruker(fornavn, etternavn, 3);

        /*
         if(error.hasErrors()){
         System.out.println("----------------------- kommer inn i hasErrors()------------");
         modell.addAttribute("Noeerfeil", "En feil har oppstått");
         return new ModelAndView("redirect:/bruker.htm?x=3","modell",modell);
         }
         */

        if (utilsBean.registrerEmne(emne) && emne.getHaMedLaerer()) {
            utilsBean.leggTilBrukereIEmne(returnen, emne);
            System.out.println("----------------------kommer inn i registrerEmne() i db-----------------");
            modell.addAttribute("melding", "Emne " + emne + " er registrert");
        }
        return new ModelAndView("redirect:/bruker.htm?x=3", "modell", modell);
    }

    //****************** Viser siden for Endre Bruker, som har en søkeboks *************************** 
    @RequestMapping(value = "/endreBruker.htm")
    public String visSøkeboks(Model model) {
        Bruker valgtBruker = new Bruker();
        model.addAttribute("valgtBruker", valgtBruker);
        return "endreBruker";

    }

    //************* sjekker inputten på endre bruker søkemotor *************
    @RequestMapping(value = "endrebruker2")
    public String Søkeboks(Model model, HttpServletRequest request, @RequestParam(value = "zoom_query") String brukersøk, @ModelAttribute(value = "valgtBruker") Bruker valgtBruker) {

        String navnet = null;
        // String values = request.getParameterValue("zoom_query");
        System.out.println(brukersøk);
        UtilsBean ub = new UtilsBean();
        ArrayList<Bruker> bruk = ub.getBrukerePåBokstav(brukersøk);
        System.out.println(bruk.size() + " jerherhheruhre");
        model.addAttribute("sokeresultat", bruk);
        for (int i = 0; i < bruk.size(); i++) {
            //brukertabell.add(faget.get(i).getFornavn() + " " + faget.get(i).getEtternavn());
            System.out.println("Du søkte på følgende navn " + bruk.get(i).getFornavn());
        }

        // model.addAttribute("navn", fornavn);
        return "endreBruker";

    }

    @RequestMapping(value = "endreBruker3")
    public String endreBruker(Model model, HttpServletRequest request, @ModelAttribute(value = "valgtBruker") Bruker brukeren) {
        UtilsBean ub = new UtilsBean();
        Bruker br = ub.getBruker(brukeren.getBrukernavn());
        model.addAttribute("brukerTilEndring", br);

        return "endreBruker";

    }

    @RequestMapping(value = "endreBruker4")
    public String endreBrukeroppl(Model model, HttpServletRequest request, @ModelAttribute(value = "valgtBruker") Bruker endretBruker) {
        UtilsBean ub = new UtilsBean();
        Bruker br = ub.getBruker(endretBruker.getBrukernavn());
        br.setFornavn(endretBruker.getFornavn());
        br.setEtternavn(endretBruker.getEtternavn());
        System.out.println(br.getFornavn());
        System.out.println(endretBruker.getFornavn());

        if (ub.oppdaterBruker(br)) {
            System.out.println("funka");
            model.addAttribute("funkafint", true);
        }
        System.out.println(br.getFornavn());

        return "endreBruker";

    }

    @RequestMapping(value = "endreBruker4.htm")
    public String slettBruker(Model model, HttpServletRequest request, @ModelAttribute(value = "valgtEmne") Bruker slettBruker) {
        System.out.println("hull" + slettBruker.getBrukernavn());
        UtilsBean ub = new UtilsBean();
        ub.slettBruker(slettBruker);
        return "endreBruker";
    }
    
     @RequestMapping(value = "/endreBruker5.htm")
    public String visAlleFag(Model model, @ModelAttribute("brukerinnlogg") Bruker bruker) {
        UtilsBean ub = new UtilsBean();
        ArrayList<Emne> lis = ub.getFageneTilBruker(bruker.getBrukernavn());
        model.addAttribute("emneliste", true);
        model.addAttribute("valgtEmneListe", lis);
        return "endreBruker";
    }

    @RequestMapping(value = "/endreBruker6.htm")
    public String visAlleBrukerneIEmnet(Model model, @ModelAttribute(value = "emne") Emne emne) {

        UtilsBean ub = new UtilsBean();
        ArrayList brukerne = ub.getBrukereIEmnet(emne.getEmnenavn());
        
        model.addAttribute("brukereIEmnet", true);
        model.addAttribute("valgtEmneBrukere", brukerne);
        return "endreBruker";
    }

    //************ Viser siden for ENDRE emne som har en søkeboks ***************************
    @RequestMapping(value = "/endreEmne.htm")
    public String visSøkeboksEmne(Model model) {
        Emne valgtEmne = new Emne();
        model.addAttribute("valgtEmne", valgtEmne);
        return "endreEmne";

    }

    //************* sjekker inputten på endre emne søkemotor *************
    @RequestMapping(value = "endreemne2")
    public String SøkeboksEmne(Model model, HttpServletRequest request, @RequestParam(value = "zoom_query") String soketekst, @ModelAttribute(value = "valgtEmne") Emne valgtEmne) {

        String emnenavnet = null;

        System.out.println(soketekst);
        UtilsBean ub = new UtilsBean();
        ArrayList<Emne> emn = ub.getEmnePåBokstav(soketekst);
        System.out.println(emn.size() + " jabbadabba");
        model.addAttribute("sokeresultat", emn);
        for (int i = 0; i < emn.size(); i++) {

            System.out.println("Du søkte på følgende emne " + emn.get(i).getEmnekode());
        }

        return "endreEmne";

    }

    @RequestMapping(value = "endreEmne3")
    public String endreEmne(Model model, HttpServletRequest request, @ModelAttribute(value = "valgtEmne") Emne emnet) {
        UtilsBean ub = new UtilsBean();
        Emne em = ub.getEmne(emnet.getEmnenavn());
        model.addAttribute("emneTilEndring", em);
        System.out.println("KOMMER HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIT");
        return "endreEmne";

    }

    @RequestMapping(value = "endreEmne4")
    public String endreEmneoppl(Model model, HttpServletRequest request, @ModelAttribute(value = "valgtEmne") Emne endretEmne) {
        System.out.println("STILL GOINNGGGG");
        UtilsBean ub = new UtilsBean();
        Emne em = ub.getEmne(endretEmne.getEmnenavn());
        em.setEmnekode(endretEmne.getEmnenavn());
        em.setBeskrivelse(endretEmne.getBeskrivelse());
        System.out.println(endretEmne.getEmnenavn());
        System.out.println(endretEmne.getEmnekode());

        if (ub.oppdaterEmne(em)) {
            System.out.println("funka med emne også");
            model.addAttribute("funkafint", true);
        }

        System.out.println(em.getEmnenavn());

        return "endreEmne";

    }

    /*-- SLETT EMNE --*/
    @RequestMapping(value = "endreEmne4.htm")
    public String slettEmne(Model model, HttpServletRequest request, @ModelAttribute(value = "valgtEmne") Emne slettEmne) {
        System.out.println("hull" + slettEmne.getEmnekode());
        UtilsBean ub = new UtilsBean();
        ub.slettEmne(slettEmne);
        return "endreEmne";
    }

    //******************* viser registreringen av en ny øving*****************************************************
    @RequestMapping(value = "regov2.htm")
    public String visØvinginnsetning(Model model, @ModelAttribute(value = "øving") Øving øving, BindingResult error, @RequestParam(value = "emnevalgt", required = false) String emnevalgt, HttpServletRequest request,@RequestParam(value = "submitted", required = false) String submitted) {
System.out.println("KOmmer inn i visOv--------------------------------"+ request.getParameter("submitted"));
String svaret = request.getParameter("submitted");

        UtilsBean ub = new UtilsBean();
        ArrayList<Emne> em = ub.getAlleFag();
        ArrayList<String> emnetabell = new ArrayList<String>();
        
        model.addAttribute("valg", emnevalgt);
        emnetabell.add(emnevalgt);
        for (int i = 0; i < em.size(); i++) {
            emnetabell.add(em.get(i).getEmnenavn());
        }

        model.addAttribute("allefagene", emnetabell);
        if (emnevalgt != null) {
            int antallOv = ub.getAntallOvingerIFag(emnevalgt);
            model.addAttribute("ovinger", antallOv);
            System.out.println("KOmmer inn i if(emnevalgt != null--------------------------------");  
        }
        
        if(svaret != null){
        if(svaret.equals("Registrer "+emnevalgt)){
            System.out.println("KOmmer inn i regØv--------------------------------");
        UtilsBean utilsBean = new UtilsBean();

        øving.setEmnekode(emnevalgt);
        øving.setGruppeid(-1);
        øving.setObligatorisk(false);
        int øvnr = utilsBean.getAntOvingerIEmne(emnevalgt);
        øvnr+=1;
        øving.setØvingsnr(øvnr);
        System.out.println("henter ut det øvingsnr som skal bli lagt til i databasen: "+øvnr);

        if (utilsBean.registrerØving(øving)) {
            model.addAttribute("melding", "Øving " + øving + " er registrert");
        }
        }
        
        }
return "regov2";
    }
    
     @RequestMapping(value = "/emnetilbruker.htm")
         
    public String visEmnetilbruker(Model model){
        UtilsBean ub = new UtilsBean();
        
               //<form:forEach items="${brukerne}" var="bruker">
                       
                       ArrayList<Bruker> brukere= ub.getAlleBrukere();
                       ArrayList<Emne> emner = ub.getAlleFag();
        model.addAttribute("brukerne", brukere);
        model.addAttribute("emnene",emner);
        for(int k=0; k<emner.size(); k++){
            System.out.println(emner.get(k).getEmnekode());
        }
        return "emnetilbruker";
    }
    
     @RequestMapping(value = "/emnetilbruker2.htm")
    public String visEmnetilbruker2(Model model,@RequestParam(value = "brukerne2") Bruker bruker,@ModelAttribute(value="brukerhentet")String[] brukervalgt){
        UtilsBean ub = new UtilsBean();
       
        System.out.println("brukeren som er valgt" +brukervalgt[0]);
          
        return "emnetilbruker";
    }
/*
    //*************************Registrerer en ny øving*****************************
    @RequestMapping(value = "regov23.htm", method = RequestMethod.POST)
    public String regØv(@Validated @ModelAttribute(value = "øving") Øving øving, BindingResult error, Model model, HttpServletRequest request, @RequestParam(value = "emnevalgt") String[] emnevalgt) {
System.out.println("KOmmer inn i regØv--------------------------------");
        String[] values = request.getParameterValues("emnene");
        UtilsBean utilsBean = new UtilsBean();

        øving.setEmnekode(values[0]);
        øving.setGruppeid(-1);
        øving.setObligatorisk(false);
        øving.setØvingsnr(utilsBean.getAntallOvingerIFag(values[0])+1);
        System.out.println("henter ut det øvingsnr som skal bli lagt til i databasen: "+utilsBean.getAntallOvingerIFag(values[0])+1);

        if (utilsBean.registrerØving(øving)) {
            model.addAttribute("melding", "Øving " + øving + " er registrert");
        }
        return "regov2";
        
    }
*/
//*************************** Viser administrer lærer siden*************************
    @RequestMapping(value = "/adminlaerer.htm")
    public String visLaerer(Model model, @ModelAttribute(value = "brukerinnlogg") Bruker bruker, BindingResult error, @RequestParam(value = "x", required = false) String getValg, @RequestParam(value = "valget", required = false) String getAntall, HttpServletRequest request) {
        System.out.println("JEG VISER visLaerer!!!!");
        System.out.println("getAntall eeeeeer: " + getAntall);
        if (!valgteOvinger.isEmpty()) {

            for (int i = 0; i < valgteOvinger.size(); i++) {
                System.out.println("visLarer() har snappet opp: " + valgteOvinger.get(i) + " fra valgtOvinger");
            }
        } else {
            System.out.println("valgteOvinger er tom for meg, hilsen visLaerer()");
        }
        UtilsBean ub = new UtilsBean();
        Emne emne = new Emne();
        Emne valgtEmne = new Emne();
        Øving øving = new Øving();
        String emnekoden = null;
        String oppdater = request.getParameter("oppdater");
        String oppdater2 = request.getParameter("oppdater2");
        String oppdaterbeskrivelse = request.getParameter("beskrivelseinput");
        ArrayList<Øving> øvingtabell1 = new ArrayList<Øving>();
        ArrayList<Øving> valgteØvinger = new ArrayList<Øving>();

        model.addAttribute("øving", øving);
        model.addAttribute("emne", emne);
        model.addAttribute("valg", getValg);

        ArrayList<Emne> em = ub.getFageneTilBruker(bruker.getBrukernavn());
        Emne valgtemne = new Emne();

        if (getValg != null) {
            /*
             if(oppdater !=null){
             ArrayList<Øving> øvingsliste = ub.getØvingerIEmnet(getValg);
             if(øvingsliste!=null){
             for(Øving ø: øvingsliste){
             System.out.println("oppdatering av " + ø + ":" + ub.oppdaterØving(ø, øvingsnrpåendretcheckbox, emnekoden));
             }
             }
             }
             */
            String emnesendt = getValg;
            if (oppdater2 != null) {
                System.out.println("--------------------------- KNAPPEN FOR BESKRIVELSE ER TRYKKET INN");

                ub.oppdaterØvingsBeskrivelse(emnesendt, oppdaterbeskrivelse);
            }

            valgtEmne = ub.getEmne(getValg);
            model.addAttribute("emnevalg", valgtEmne);

            ArrayList<Øving> øv = ub.getØvingerIEmnet(getValg);

            for (int i = 0; i < øv.size(); i++) {
                øvingtabell1.add(øv.get(i));

            }
            model.addAttribute("alleovinger", øvingtabell1);

        }
        /*
         ArrayList<Emne> emnet = new ArrayList<Emne>();
         for(int i=0; i<em.size();i++){
         emnet.add(em.get(i));
         }
         model.addAttribute("beskrivelse",emnet);
         */

        ArrayList<String> emnetabell = new ArrayList<String>();
        emnetabell.add(getValg);
        if (em != null) {
            for (int i = 0; i < em.size(); i++) {
                emnetabell.add(em.get(i).getEmnenavn());
                emnekoden = em.get(i).getEmnenavn();
            }
        }
        model.addAttribute("allefagene", emnetabell);

        if (oppdater != null || getAntall != null) {
            System.out.println("Han tytjepelk han kom inj");
            ArrayList<String> antallet = new ArrayList();
            antallet.add("Velg antall");

            if (!valgteOvinger.isEmpty()) {

                for (int i = 1; i < valgteOvinger.size() + 1; i++) {
                    String a = String.valueOf(i);
                    antallet.add(a);
                    System.out.println("Valgte øvinger................................................" + valgteOvinger.get(i - 1));

                }

                int a = antallet.size();
                model.addAttribute("iftest", a);
                model.addAttribute("alleAntall", antallet);

                if (getAntall != null) {

                    int gruppeID = ub.getMaxGruppeIDIEmne() + 1;
                    Kravgruppe kr = new Kravgruppe();
                    kr.setAntallgodkj(Integer.parseInt(getAntall));
                    kr.setGruppeID(gruppeID);
                    kr.setEmnekode(valgtEmne.getEmnekode());
                    kr.setBeskrivelse("Beskrivelse ikke lagt inn");

                    ub.registrerKravGruppe(kr);

                    for (int i = 0; i < valgteOvinger.size(); i++) {
                        valgteØvinger.add(ub.getØvingIEmnet(valgteOvinger.get(i), valgtEmne.getEmnekode()));
                    }
                    for (int i = 0; i < valgteØvinger.size(); i++) {

                        valgteØvinger.get(i).setGruppeid(gruppeID);
                        ub.oppdaterØving(valgteØvinger.get(i));
                    }
                }

            }
        }
        return "adminlaerer";
    }
    /*
     int r = ub.getØvingerIEmnet(emnekoden).size();
     int mid[] = new int[r];
     for(int k=0; k<ub.getØvingerIEmnet(emnekoden).size(); k++){
     if(k<Integer.parseInt(ob[k])){
     mid[k]=k;
     System.out.println("Greit " + k);
     }else{
     mid[k]= Integer.parseInt(ob[k]);
     System.out.println("Greit2 " + mid[k]);
     }
               
     }
            
            
     Kravgruppe krav = new Kravgruppe();
     krav.setEmnekode(emnekoden);
     krav.setAntallgodkj(1); ////////////////// fiks dette etter
     ub.getØvingerIEmnet(emnekoden);
     for (int i = 1; i < ub.getØvingerIEmnet(emnekoden).size(); i++) {
     if (ub.getØvingerIEmnet(emnekoden).get(i).getØvingsnr() == Integer.parseInt(ob[i-1])) {
     krav.setGruppeID(i);
     ub.registrerKravGruppe(krav);
     ub.getØvingerIEmnet(emnekoden).get(i).setGruppeid(i);
     ub.oppdaterØving(ub.getØvingerIEmnet(emnekoden).get(i),Integer.parseInt(ob[i]) , emnekoden);
                    

     }
     }
     }
        
     //    System.out.println(ob[1]);
     //  System.out.println(ob[2]);      
             
     // ob = request.getParameter("obliga");
             
             
     /*  System.out.println(ob[0]);
     System.out.println(ob[1]);
     System.out.println(ob[2]);*/

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

    @RequestMapping(value = "/oppdaterselect.htm")
    @ResponseBody
    public String oppdater(Model model, @RequestParam(value = "x") String alle, @RequestParam(value = "emnekode") String emnekode, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Content-Type", "text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        String returnen = "";
        UtilsBean ub = new UtilsBean();
        ArrayList<Øving> øvinger = ub.getØvingerIEmnet(emnekode);

        System.out.println(alle);
        ArrayList<String> alleovingsnr = new ArrayList<String>();
        String[] splitten = alle.split(",");
        if (splitten.length > 0) {
            for (int i = 0; i < splitten.length; i++) {
                if (!splitten[i].equals(",") && !splitten[i].equals("") && !splitten[i].equals(" ")) {
                    alleovingsnr.add(splitten[i]);
                }

            }
        } else {
            System.out.println("Her kom jeg");
            alleovingsnr.add(alle);
        }
        if(alleovingsnr.size() > 0){
        System.out.println("LEGGER TIL " + alleovingsnr.get(alleovingsnr.size() - 1) + " i valgteOvinger");
        
        valgteOvinger.add(Integer.parseInt(alleovingsnr.get(alleovingsnr.size() - 1)));
        
        System.out.println("LA TIL " + alleovingsnr.get(alleovingsnr.size() - 1) + " i valgteOvinger");
        System.out.println("Størrelse på øvinger fra db: " + øvinger.size() + ", Størrelse på antall hentet ut fra js: " + alleovingsnr.size());
        }
        System.out.println("Fra db:");
        for (int i = 0; i < øvinger.size(); i++) {
            System.out.println(øvinger.get(i).getØvingsnr());
        }

        System.out.println("Fra js:");
        for (int i = 0; i < alleovingsnr.size(); i++) {
            System.out.println(alleovingsnr.get(i));
        }

        ArrayList<Integer> liste1 = new ArrayList<Integer>();
        ArrayList<Integer> liste2 = new ArrayList<Integer>();

        for (int i = 0; i < øvinger.size(); i++) {
            boolean funnet = false;
            for (int a = 0; a < alleovingsnr.size(); a++) {
                if (øvinger.get(i).getØvingsnr() == Integer.parseInt(alleovingsnr.get(a))) {
                    funnet = true;
                    break;
                }

            }
            if (funnet == true) {
                liste2.add(øvinger.get(i).getØvingsnr());
            } else {
                liste1.add(øvinger.get(i).getØvingsnr());
            }

        }

        returnen += "<table><tr><td valign=top><table><thead><tr><th>&Oslash;ving</th></tr>"
                + "<tbody>";

        for (int i = 0; i < liste1.size(); i++) {
            returnen += "<tr><td class=\"admlaerer\" onclick=\"endreselect('" + liste1.get(i) + "', '" + emnekode + "');\">" + liste1.get(i) + "</td>"
                    + "</tr>";

        }

        returnen += "                    <tr><td> <input type=\"submit\" value=\"G&aring; videre\" name=\"oppdater\" /></td>\n"
                + "                        \n"
                + "                        <td><td>&nbsp;</td></table>"
                + "<td valign=top>&nbsp;";
        
        if(liste2.size() > 0){
               returnen += "<table><tr><td class=\"tdoveradmin\"> &Oslash;ving</td><td class=\"tdoveradmin\">Obligatorisk</td></tr>";
                

        if (liste2.size() > 0) {

            for (int i = 0; i < liste2.size(); i++) {
                returnen += "                                       <tr><td class=\"tdadmin\" onclick=\"slett('" + liste2.get(i) + "', '" + emnekode + "');\">Nr.: " + liste2.get(i) + " er valgt.</td><td class=\"tdadmin\"><input type=checkbox name=check></td></tr>";
            }

        }

        returnen += "</table>";
                }
               returnen  += "</td></tr></table>\n";
             

        return returnen;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Bruker.class, new BrukerEditor(new UtilsBean()));
    }
}

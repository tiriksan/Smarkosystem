/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ov13.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import spring.ov13.domene.Innlegg;
import spring.ov13.domene.Øving;
import spring.ov13.domene.utils.UtilsBean;
import org.springframework.web.bind.annotation.SessionAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.junit.runner.Request.method;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SessionAttributes({"brukerinnlogg"})
@Controller
public class KøKontroller {

    @ModelAttribute(value = "brukerinnlogg")
    public Bruker lage2() {

        return new Bruker();
    }

    @RequestMapping(value = "/aktiverko.htm")
    public String aktiverko(Model model, @ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x", required = false) String emnekode) {
        UtilsBean ub = new UtilsBean();
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
}
        if(bruker.getBrukertype() < 2){
            return "index";
        }
        if(!ub.sjekkString(emnekode, 4, 8)){
            return "feil";
        }
        System.out.println("DB: " + ub.getFagKoAktiv(emnekode));
        ub.updateFagKoAktiv(emnekode, !ub.getFagKoAktiv(emnekode));
        System.out.println("DB2: " + ub.getFagKoAktiv(emnekode));
        return "redirect:studentko.htm?x=" + emnekode;
    }

    @RequestMapping(value = "/studentko.htm")
    public String visKø(Model model, @ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x", required = false) String emnekode, HttpServletRequest request) {
        if (bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")) {
            return "logginn";
        } else {
            UtilsBean ub = new UtilsBean();
 
            
            if (request.getAttribute("hjelp") != null) {
                if(ub.getInnleggFraHjelpEmne(bruker.getBrukernavn(), emnekode) == null){
                    model.addAttribute("hjelp", false);
                }else {
                    model.addAttribute("hjelp", true);
                }
            }
            

            ArrayList<Emne> fagene = ub.getFageneTilBruker(bruker.getBrukernavn());
            if (fagene != null) {
                if (fagene.size() > 0) {
                    model.addAttribute("fagene", fagene);
                }
            }
            if (emnekode == null) {

            } else {
                if (!ub.erBrukerIFag(bruker.getBrukernavn(), emnekode)) {
                    model.addAttribute("feilmelding", "Brukeren eksisterer ikke i dette faget");
                    return "studentko";
                }
                bruker.setBrukertype(ub.getBrukertypeiEmne(bruker.getBrukernavn(), emnekode));
                Innlegg innlegg = ub.getInnleggFraHjelpEmne(bruker.getBrukernavn(), emnekode);
                if (innlegg != null) {
                    model.addAttribute("hjelp", true);
                    request.getSession().setAttribute("emne", emnekode);
                    request.getSession().setAttribute("id", innlegg.getId());
                    request.getSession().setAttribute("innlegg", ub.getInnleggFraID(innlegg.getId()));
                }
                System.out.println(bruker.getBrukertype());
                boolean open = ub.getFagKoAktiv(emnekode);
                model.addAttribute("aktiv", open);
                model.addAttribute("emnenavnvalgt", emnekode);
        //        if (open == false) {
                //            model.addAttribute("IngenAktiv", "Det er ingen aktiv kø for dette faget");
                //    } else {
                ArrayList<Innlegg> innleggene = new ArrayList<Innlegg>();
                innleggene = ub.getFulleInnleggTilKo(emnekode);  //henter ut alle innlegene til køen med valgt emnekode
                System.out.println(innleggene.size() + "er størrelsen på tabellen");
                ArrayList<String> ovingtekster = new ArrayList<String>();
                for (int i = 0; i < innleggene.size(); i++) {
                    String tekstenforinnlegg = "";
                    ArrayList<Integer> nummeriliste = new ArrayList<Integer>();
                    for (int a = 0; a < innleggene.get(i).getOvinger().size(); a++) { // Finner ArrayList<Øving> per bruker
                        for (int k = 0; k < innleggene.get(i).getOvinger().get(a).size(); k++) { // Henter alle øvingene per bruker
                            boolean funnet = false;
                            for (int e = 0; e < nummeriliste.size(); e++) {
                                if (nummeriliste.get(e) == innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr()) {
                                    funnet = true;
                                }
                            }
                            //lager til teksten som skal vises under øvinger
                            if (funnet == false) {
                                if (innleggene.get(i).getOvinger().get(a).size() == 1) {
                                    tekstenforinnlegg += innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr() + "";
                                } else {
                                    if (k == (innleggene.get(i).getOvinger().get(a).size()) - 1) {
                                        tekstenforinnlegg += "og " + innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr();
                                    } else if (k == (innleggene.get(i).getOvinger().get(a).size()) - 2) {
                                        tekstenforinnlegg += innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr() + " ";
                                    } else {
                                        tekstenforinnlegg += innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr() + ", ";
                                    }
                                }
                                nummeriliste.add(innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr());
                            }
                        }
                    }
                    // tekstenforinnlegg += innleggene.get(i).getEier().getBrukernavn();
                    System.out.println("tekstenforinnlegg: " + tekstenforinnlegg);
                    ovingtekster.add(tekstenforinnlegg);
                }
                model.addAttribute("ovingtekster", ovingtekster);
                model.addAttribute("innleggene", innleggene);
                //           }
            }
            return "studentko";
        }
    }

    @RequestMapping(value = "fjernInnlegg.htm")
    public String fjernInnlegg(@ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x") String emne, @RequestParam(value = "id") int id, HttpServletRequest request) {
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
}
        if(bruker.getBrukertype() < 2){
            return "index";
        }
        UtilsBean ub = new UtilsBean();
        if(!ub.sjekkString(emne, 4, -1) || !ub.sjekkString(id + "", 4, -1)){
            return "feil";
        }
        request.getSession().setAttribute("emne", emne);
        ub.fjernKoInnleggFraID(id);
        return "redirect:studentko.htm?x=" + emne;
    }

    @RequestMapping(value = "tømKø.htm")
    public String tømKø(@ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x") String emne) {
        UtilsBean ub = new UtilsBean();
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
}
        if(bruker.getBrukertype() < 2){
            return "index";
        }
                if(!ub.sjekkString(emne, 4, -1)){
            return "feil";
        }
        ub.fjernAlleKoinnleggIEmne(emne);
        return "redirect:studentko.htm?x=" + emne;
    }

    @RequestMapping(value = "hjelp.htm")
    public String handlePost(@ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x") String emne, @RequestParam(value = "id") int id, HttpServletRequest request, RedirectAttributes ra) {
        UtilsBean ub = new UtilsBean();
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
}
        if(bruker.getBrukertype() < 2){
            return "index";
        }
        if(!ub.sjekkString(emne, 4, -1) || !ub.sjekkString(id + "", 4, -1)){
            return "feil";
        }
        ra.addFlashAttribute("hjelp", true);
        request.getSession().setAttribute("emne", emne);
        request.getSession().setAttribute("id", id);
        request.getSession().setAttribute("brukere", ub.getBrukereIInnlegg(id));
        ub.setKøinnleggHjelpBruker(bruker, id);
        request.getSession().setAttribute("innlegg", ub.getInnleggFraID(id));
        return "redirect:studentko.htm?x=" + emne;
    }

    @RequestMapping(value = "godkjennalle.htm")
    public String godkjennAlle(Model model, HttpServletRequest request, @RequestParam(value = "x") String emne, @ModelAttribute("brukerinnlogg") Bruker bruker) {
        request.getSession().setAttribute("hjelp", false);
        UtilsBean ub = new UtilsBean();
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
}
        if(!ub.sjekkString(emne, 4, -1)){
            return "feil";
        }
        Innlegg innlegg = ub.getInnleggFraID((Integer) request.getSession().getAttribute("id"));
        for (Bruker b : innlegg.getBrukere()) {
            ArrayList<Integer> ovnr = new ArrayList();
            for (Øving ov : innlegg.getOvinger().get(innlegg.getBrukere().indexOf(b))) {
                ovnr.add(ov.getØvingsnr());
            }
            ub.setInnOvingerGodkjent(bruker.getBrukernavn(), (String) request.getSession().getAttribute("emne"), b.getBrukernavn(), ovnr);
        }

        ub.setKøinnleggHjelpBruker(null, (Integer) request.getSession().getAttribute("id"));
        ub.fjernKoInnleggFraID((Integer) request.getSession().getAttribute("id"));
        return "redirect:studentko.htm?x=" + request.getSession().getAttribute("emne");
    }

    @RequestMapping("godkjennValgte.htm")
    public String godkjentValgte(Model modell, HttpServletRequest request, @RequestParam(value = "x") String emne, @ModelAttribute("brukerinnlogg") Bruker bruker) {
        UtilsBean ub = new UtilsBean();
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
}
        if(bruker.getBrukertype() < 2){
            return "index";
        }
                if(!ub.sjekkString(emne, 4, -1)){
            return "feil";
        }
        Enumeration<String> paramNames = request.getParameterNames();
        String brukernavnMedListe = "";
        ArrayList<Integer> liste = new ArrayList();
        while (paramNames.hasMoreElements()) {
            String[] verdier = paramNames.nextElement().split(",");
            if (verdier.length > 1) {
                if (!verdier[0].equals(brukernavnMedListe)) {
                    if (!liste.isEmpty()) {
                        ub.setInnOvingerGodkjent(bruker.getBrukernavn(), (String) request.getSession().getAttribute("emne"), brukernavnMedListe, liste);
                    }
                    brukernavnMedListe = verdier[0];
                    liste = new ArrayList();
                }
                liste.add(Integer.parseInt(verdier[1]));
            }
        }
        if (!liste.isEmpty()) {
            ub.setInnOvingerGodkjent(bruker.getBrukernavn(), (String) request.getSession().getAttribute("emne"), brukernavnMedListe, liste);
        }
        request.getSession().setAttribute("hjelp", false);
        ub.setKøinnleggHjelpBruker(null, (Integer) request.getSession().getAttribute("id"));
        ub.fjernKoInnleggFraID((Integer) request.getSession().getAttribute("id"));
        return "redirect:studentko.htm?x=" + request.getSession().getAttribute("emne");

    }

    @RequestMapping(value = "utsett.htm")
    public String utsett(@ModelAttribute("brukerinnlogg") Bruker bruker, Model model, HttpServletRequest request, @RequestParam(value = "x") String emne) {
        request.getSession().setAttribute("hjelp", false);
        UtilsBean ub = new UtilsBean();
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
}
        if(bruker.getBrukertype() < 2){
            return "index";
        }
                if(!ub.sjekkString(emne, 4, -1)){
            return "feil";
        }
        ub.setKøinnleggHjelpBruker(null, (Integer) request.getSession().getAttribute("id"));
        return "redirect:studentko.htm?x=" + request.getSession().getAttribute("emne");
    }

    @RequestMapping(value = "fjern.htm")
    public String fjern(@ModelAttribute("brukerinnlogg") Bruker bruker, Model model, HttpServletRequest request, @RequestParam(value = "x") String emne) {
        request.getSession().setAttribute("hjelp", false);
        UtilsBean ub = new UtilsBean();
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
}
        if(bruker.getBrukertype() < 2){
            return "index";
        }
                if(!ub.sjekkString(emne, 4, -1)){
            return "feil";
        }
        ub.setKøinnleggHjelpBruker(null, (Integer) request.getSession().getAttribute("id"));
        ub.fjernKoInnleggFraID((Integer) request.getSession().getAttribute("id"));
        return "redirect:studentko.htm?x=" + request.getSession().getAttribute("emne");
    }

}

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
    public String aktiverko(Model model, @ModelAttribute("brukerinnlogg") Bruker bruker,@RequestParam(value = "x", required = false) String emnekode){
        UtilsBean ub = new UtilsBean();
        System.out.println("DB: " + ub.getFagKoAktiv(emnekode));
        ub.updateFagKoAktiv(emnekode, !ub.getFagKoAktiv(emnekode));
        System.out.println("DB2: " + ub.getFagKoAktiv(emnekode));
        return "redirect:studentko.htm?x="+emnekode;
    }

    @RequestMapping(value = "/studentko.htm")
    public String visKø(Model model, @ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x", required = false) String emnekode, HttpServletRequest request) {
        if (bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")) {
            return "logginn";
        } else {
            
            if(request.getAttribute("hjelp") != null){
                model.addAttribute("hjelp",true);
            }
            
            
            UtilsBean ub = new UtilsBean();

            ArrayList<Emne> fagene = ub.getFageneTilBruker(bruker.getBrukernavn());
            if (fagene != null) {
                if (fagene.size() > 0) {
                    model.addAttribute("fagene", fagene);
                }
            }
            if (emnekode == null) {

            } else {
                if(!ub.erBrukerIFag(bruker.getBrukernavn(),emnekode)){
                    model.addAttribute("feilmelding", "Brukeren eksisterer ikke i dette faget");
                    return "studentko";
                }
                bruker.setBrukertype(ub.getBrukertypeiEmne(bruker.getBrukernavn(),emnekode));
                System.out.println(bruker.getBrukertype());
                boolean open = ub.getFagKoAktiv(emnekode);
                model.addAttribute("aktiv", open);
                System.out.println("Open: " + open);
                model.addAttribute("emnenavnvalgt", emnekode);
                if (open == false) {
                    model.addAttribute("IngenAktiv", "Det er ingen aktiv kø for dette faget");
                } else {
                    ArrayList<Innlegg> innleggene = new ArrayList<Innlegg>();
                    innleggene = ub.getFulleInnleggTilKo(emnekode);
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

                        ovingtekster.add(tekstenforinnlegg);

                    }
                    model.addAttribute("ovingtekster", ovingtekster);
                    model.addAttribute("innleggene", innleggene);
                }

            }

            return "studentko";
        }
    }
    
    @RequestMapping(value = "hjelp.htm")
    public String handlePost(@ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x")String emne, @RequestParam(value = "id") int id, HttpServletRequest request, RedirectAttributes ra){
        UtilsBean ub = new UtilsBean();

        ra.addFlashAttribute("hjelp", true);
        request.getSession().setAttribute("emne", emne);
        request.getSession().setAttribute("id", id);
        request.getSession().setAttribute("brukere", ub.getBrukereIInnlegg(id));
        ub.setKøinnleggHjelpBruker(bruker, id);
        request.getSession().setAttribute("innlegg", ub.getInnleggFraID(id));
        return "redirect:studentko.htm?x="+emne;
    }
    
    @RequestMapping(value = "godkjennalle.htm")
    public String something(Model model, HttpServletRequest request, @RequestParam(value = "x") String emne){
        request.getSession().setAttribute("hjelp", false);
        UtilsBean ub = new UtilsBean();
        /*
        for(bruker b : brukereiinnlegg){
        godkjenn
        
        }
        
        */
        ub.setKøinnleggHjelpBruker(null, (Integer)request.getSession().getAttribute("id"));
        return "redirect:studentko.htm?x=" + request.getSession().getAttribute("emne");   
    }
    
    @RequestMapping(value = "utsett.htm")
    public String utsett(Model model, HttpServletRequest request, @RequestParam(value = "x") String emne){
        request.getSession().setAttribute("hjelp", false);
        return "redirect:studentko.htm?x=" + request.getSession().getAttribute("emne");   
    }
    
}

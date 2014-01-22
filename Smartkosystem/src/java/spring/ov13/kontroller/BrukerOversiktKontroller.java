package spring.ov13.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
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

    @RequestMapping(value = "/valgtBrukeroversikt.htm", method = RequestMethod.POST)
    public String sendVidereTilBrukerOversikt(Model model,@ModelAttribute(value = "brukerinnlogg") Bruker innloggetBruker, HttpServletRequest request,@RequestParam(value = "emnekode", required = false) String emnekode) {
        
        
        UtilsBean ub = new UtilsBean();
        ArrayList<Bruker> studenter = new ArrayList<Bruker>();
        innloggetBruker.setBrukertype(ub.getBrukertypeiEmne(innloggetBruker.getBrukernavn(),emnekode));
        studenter = ub.getStudenterIEmnet(emnekode);
        model.addAttribute("studenter", studenter);
        
        request.getSession().setAttribute("emne", ub.getEmne(emnekode));
        int antØvinger = ub.getAntOvingerIEmne(emnekode);
        int[][] alleBrukereGodkjenteOvinger = new int [studenter.size()][antØvinger];
        int[] godkjenteOvinger;
        
        for (int i = 0; i < studenter.size(); i++) {
         godkjenteOvinger = ub.getGodkjentOvingerForBrukerIEmne(studenter.get(i).getBrukernavn(), emnekode, antØvinger);
            for (int j = 0; j < antØvinger; j++) {
                alleBrukereGodkjenteOvinger[i][j] = godkjenteOvinger[j];
            }
        }
        model.addAttribute("aGO", alleBrukereGodkjenteOvinger);
        
        return "velgEmne";
    }

}
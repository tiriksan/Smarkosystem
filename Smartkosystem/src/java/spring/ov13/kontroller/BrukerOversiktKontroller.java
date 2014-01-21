package spring.ov13.kontroller;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.utils.UtilsBean;

/**
 * @author Sindre
 */

@SessionAttributes("brukerinnlogg")
@Controller
public class BrukerOversiktKontroller {
    
    @RequestMapping(value = "/valgtBrukeroversikt.htm")
    public String sendVidereTilBrukerOversikt(Model model, @RequestParam(value = "x") String emnekode){
        
        UtilsBean ub = new UtilsBean();
        ArrayList<Bruker> studenter = new ArrayList<Bruker>();
        studenter = ub.getStudenterIEmnet(emnekode);
        model.addAttribute("studenter", studenter);
        
        return "brukeroversikt.htm";
    }
    
    @RequestMapping(value = "/brukeroversikt.htm")
    public String visBrukerOversikt(Model model, @RequestParam(value = "x") String emnekode) {
        return "brukeroversikt.htm";
    }

}

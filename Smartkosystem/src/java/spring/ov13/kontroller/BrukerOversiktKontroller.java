package spring.ov13.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import spring.ov13.domene.Bruker;

/**
 * @author Sindre
 */

@SessionAttributes("brukerinnlogg")
@Controller
public class BrukerOversiktKontroller {
    
    /*@RequestMapping(value = "/brukeroversikt.htm")
    public String visBrukerOversikt() {
        return "brukeroversikt.htm";
    }*/

}

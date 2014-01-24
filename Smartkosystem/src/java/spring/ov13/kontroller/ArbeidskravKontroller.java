package spring.ov13.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import spring.ov13.domene.utils.UtilsBean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import spring.ov13.domene.*;

@SessionAttributes("brukerinnlogg")
@Controller
public class ArbeidskravKontroller {
    @RequestMapping(value = "/arbeidskrav.htm")
    public String arbeidsKrav(Model modell,@ModelAttribute(value = "brukerinnlogg")Bruker bruker, HttpServletRequest request){
        UtilsBean ub = new UtilsBean();
        ArrayList<Emne> emner = ub.getFageneTilBruker(bruker.getBrukernavn());
        request.getSession().setAttribute("emner", emner);
        
        return "arbeidskrav";
    }
    @RequestMapping("/arbeidskravOving.htm")
    public String arbeidskravØving(Model model, @RequestParam(value= "emnet")String emnekode,HttpServletRequest request){
        System.out.println("here=");
        UtilsBean ub = new UtilsBean();
        ArrayList<Øving> øvinger = ub.getØvingerIEmnet(emnekode);
        model.addAttribute("øvinger", øvinger);
        request.getSession().setAttribute("emne", emnekode);
        
        
        return "arbeidskrav";
    }
    
    @RequestMapping(value = "/arbeidskravVelgAnt.htm")
    public String arbeidskravVelgAnt(Model model,HttpServletRequest request){
        String[] valgteØvigner = request.getParameterValues("valgteØvinger");
        model.addAttribute("valgteØvingerListe", valgteØvigner);
        model.addAttribute("gåVidere",true);
        System.out.println(valgteØvigner.length);
        for (String s : valgteØvigner) {
            System.out.println(s);
        }
        return "arbeidskrav";
    }
    
}

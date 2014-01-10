package spring.ov13.kontroller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.utils.UtilsBean;
import org.springframework.stereotype.Controller;
import spring.ov13.domene.utils.SendEpost;

@Controller
public class GlemtEndrePassordKontroller {
    @RequestMapping(value = "/glemtpassord.htm")
    public String glemtPassord(Model modell){
            Bruker b = new Bruker();
            modell.addAttribute("glemtpassordbruker", b);
            return "glemtpassord";
    }
     @RequestMapping(value = "/glemtpassordsvar.htm")
    public String glemtPassordSvar(@Validated @ModelAttribute("glemtpassordbruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request){
        System.out.println("Glemtpassord");
        System.out.println(bruker.getBrukernavn());    
     //   if (error.hasErrors()) {
     //       System.out.println("feil");
     //       return "glemtpassord";
     //  }
        UtilsBean utilsBean = new UtilsBean();
        if(utilsBean.get(bruker.getBrukernavn()) == null){
            modell.addAttribute("errorMelding", "Brukeren med dette brukernavnet eksisterer ikke. Sjekk om brukernavnet stemmer");
            System.out.println("Finner ikke i databasen");
            return "glemtpassord";
        } else {
            System.out.println("Sender epost?");
            SendEpost epost = new SendEpost();
            epost.sendEpost(bruker.getBrukernavn(), "http://localhost:8079/Smartkosystem/endrepassord.htm?bruker="+bruker.getBrukernavn());
            return "glemtpassord";
        }
    }    
    @RequestMapping(value = "/endrepassord.htm")
    public String endrePassord(Model model, @Validated @ModelAttribute("endrepassordbruker") Bruker bruker, @RequestParam(value = "bruker", required = false) String getValg, HttpServletRequest request){
        UtilsBean ub = new UtilsBean();
        bruker = ub.get(request.getParameter("bruker"));
       // model.addAttribute("endrepassordbruker", bruker);
        System.out.println("Endrepassordbruker: " + bruker);
        return "endrepassord";
        
    }
    @RequestMapping(value = "/endrepassordsvar.htm")
    public String endrePassordSvar(@Validated @ModelAttribute("endrepassordbruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request){
       /* if(error.hasErrors()){
            return "endrepassord";
        }*/
        //System.out.println((Bruker)request.getAttribute("endrepassordbruker"));
        System.out.println(bruker);
        UtilsBean ub = new UtilsBean();
        
        ub.oppdaterBruker(bruker);
        
        //bruker.setBrukernavn();
        return "index";
        
    }
}

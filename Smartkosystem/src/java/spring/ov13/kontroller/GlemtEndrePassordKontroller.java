package spring.ov13.kontroller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.utils.UtilsBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String endrePassord(Model model, @RequestParam(value = "bruker", required = false) String getValg, HttpServletResponse response){
        //System.out.println("Endrepassordbruker: " + bruker);
        UtilsBean ub = new UtilsBean();
        String brukernavn = getValg;
        System.out.println(brukernavn);
        response.addCookie(new Cookie("brukernavn", brukernavn));
        model.addAttribute("endrepassordbrukernavn", brukernavn);
        Bruker bruker = new Bruker();
        bruker.setBrukernavn(brukernavn);
        model.addAttribute("endrepassordbruker", bruker);
        
        System.out.println("Endrepassordbruker: " + bruker);
        return "endrepassord";
        
    }
    @RequestMapping(value = "/endrepassordsvar.htm", method = RequestMethod.POST)
    public String endrePassordSvar(@ModelAttribute(value = "endrepassordbruker") Bruker bruker, Model modell, HttpServletRequest request){
       /* if(error.hasErrors()){
            return "endrepassord";
        }*/
        
        for(Cookie c : request.getCookies()){
            if(c.getName().equals("brukernavn")){
                bruker.setBrukernavn(c.getValue());
            }
            
        }
        //System.out.println((Bruker)request.getAttribute("endrepassordbruker"));
        
        System.out.println(bruker);
        
        UtilsBean ub = new UtilsBean();
        
        ub.oppdaterBruker(bruker);
        
        //bruker.setBrukernavn();
        return "endrepassord";
        
    }
}

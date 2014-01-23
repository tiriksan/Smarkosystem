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
import org.springframework.validation.FieldError;
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
        UtilsBean ub = new UtilsBean();
        if(ub.get(bruker.getBrukernavn()) == null){
            modell.addAttribute("errorMelding", "Brukeren med dette brukernavnet eksisterer ikke. Sjekk om brukernavnet stemmer");
            System.out.println("Finner ikke i databasen");
            return "glemtpassord";
        } else {
            System.out.println("Sender epost?");
            SendEpost epost = new SendEpost();
            String brukernavn = bruker.getBrukernavn();
            
            String endrePassordMD5 = ub.getEndrePassordMD5(brukernavn);
            brukernavn = krypterRot13(brukernavn);
            //TODO endre dersom man endrer server 
            epost.sendEpost(bruker.getBrukernavn(), "http://localhost:8080/Smartkosystem/endrepassord.htm?bruker="+brukernavn);
            modell.addAttribute("sendMelding", "Epost med passordendring er blitt sendt til din epost");
            bruker.setBrukernavn("");
            return "glemtpassord";
        }
    }    
    @RequestMapping(value = "/endrepassord.htm")
    public String endrePassord(Model model, @RequestParam(value = "bruker", required = false) String getValg, HttpServletResponse response){
        //System.out.println("Endrepassordbruker: " + bruker);
        String brukernavn = getValg;
        brukernavn =dekrypterRot13(brukernavn);
        System.out.println(brukernavn);
        response.addCookie(new Cookie("brukernavn", brukernavn));
        model.addAttribute("endrepassordbrukernavn", brukernavn);
        Bruker bruker = new Bruker();
        model.addAttribute("endrepassordbruker", bruker);
        
        
        return "endrepassord";
        
    }
    @RequestMapping(value = "/endrepassordsvar.htm", method = RequestMethod.POST)
    public String endrePassordSvar(@Validated @ModelAttribute(value = "endrepassordbruker") Bruker bruker,BindingResult error, Model modell, HttpServletRequest request){
        if(error.hasErrors()){
            for(FieldError f : error.getFieldErrors()){
                if(f.getField().equals("passord")){
                    return "endrepassord";
                }
            }
        }
        for(FieldError f : error.getFieldErrors()){
            System.out.println(f.getField());
        }
        
        for(Cookie c : request.getCookies()){
            if(c.getName().equals("brukernavn")){
                bruker.setBrukernavn(c.getValue());
            }
        }
        //System.out.println((Bruker)request.getAttribute("endrepassordbruker"));
        
        System.out.println(bruker);
        bruker.setPassord(bruker.md5(bruker.getPassord()));
        UtilsBean ub = new UtilsBean();
        
        ub.endrePassord(bruker);
        
        //bruker.setBrukernavn();
        return "endrepassord";
        
    }
    
    public String krypterRot13(String string){
        String kryptert = "";
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            kryptert += c;
        }
        return kryptert;
    
    }
    public String dekrypterRot13(String string){
        String dekryptert = "";
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            dekryptert += c;
        }
        return dekryptert;
    
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import spring.ov13.domene.Bruker;
import javax.servlet.http.Cookie;
import spring.ov13.domene.utils.UtilsBean;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
/**
 *
 * @author Petter
 */


@Controller
  @SessionAttributes({"brukerinnlogg"})
public class LoggInnKontroller {
    
@ModelAttribute(value="brukerinnlogg")
public Bruker lage2(){
    
return new Bruker();
}

    
@RequestMapping(value = "/logginn.htm")
    public String visLogginn(@ModelAttribute(value="brukerinnlogg") Bruker bruker, HttpServletRequest request) {
if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
} else {
    

        return "index";
        
}
    }
    
    @RequestMapping(value = "loggut")
      
    public ModelAndView loggut(HttpServletRequest request, @ModelAttribute("brukerinnlogg") Bruker bruker, Model model){
        HttpSession session = request.getSession();
        session.invalidate();
        
        model.addAttribute("brukerinnlogg", new Bruker());
        return new ModelAndView("logginn");
    }
    
           @RequestMapping(value = "login", method = RequestMethod.POST)
          
             
    public String Logginn(HttpServletRequest request, @RequestParam("brukernavninnlogging") String bruk, @RequestParam("passordinnlogging") String pass, Model model, @ModelAttribute("brukerinnlogg") Bruker bruker, BindingResult error) {
       
        
                if(pass.length() < 1) return "logginn";
      pass = bruker.md5(pass);
       HttpSession session = request.getSession();
       
       UtilsBean ub = new UtilsBean();
       
       ArrayList<String> infofradb = ub.getInfoTilBruker(bruk); // brukernavn, passord, fornavn, etternavn, brukertype
       
       if(infofradb.size() > 0){
       if(pass.equals(infofradb.get(1))){
           
           
             int brukertypen = Integer.parseInt(infofradb.get(4));
           
           Bruker brukeren = new Bruker(infofradb.get(0), infofradb.get(2),infofradb.get(3), brukertypen, infofradb.get(1));
           bruker = brukeren;
       model.addAttribute("brukerinnlogg", bruker);
       return "index";
       } else {
      model.addAttribute("error", "Feil passord");
       session.invalidate();
       
        return "logginn";
       }
       } else {
                 model.addAttribute("error", "Feil brukernavn");
       session.invalidate();
       return "logginn";
       }
    }
    

    
    
    
}

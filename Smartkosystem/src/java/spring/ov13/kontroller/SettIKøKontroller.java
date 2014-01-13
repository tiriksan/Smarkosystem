package spring.ov13.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.ov13.domene.Bruker;

@Controller
public class SettIKøKontroller {
    
    @RequestMapping(value = "settiko.htm")
    public String settIKø(Model model, @ModelAttribute("brukerinnlogg") Bruker bruker){
        
        return "settIKø";
    }
}

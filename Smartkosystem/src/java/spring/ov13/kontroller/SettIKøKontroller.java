package spring.ov13.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.utils.UtilsBean;
@SessionAttributes({"brukerinnlogg"})
@Controller
public class SettIKøKontroller {
    
    @ModelAttribute(value = "brukerinnlogg")
    public Bruker lage2() {

        return new Bruker();
    }
    
    
    @RequestMapping(value = "settiko.htm")
    public String metode(Model model, @ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x") String emnekode){
        
                UtilsBean ub = new UtilsBean();
        String[] byggene = ub.getUnikeBygg(emnekode);
        model.addAttribute("byggene", byggene);
    model.addAttribute("emnekode", emnekode);    
        return "settiko";
    }
    
    
    
    
    
    
    
    
        @RequestMapping(value = "/test.htm")
  @ResponseBody
    public String what(@RequestParam(value = "bygg") String bygg, @RequestParam(value = "etasje") String etasje, @RequestParam(value = "rom") String rom, @RequestParam(value = "emnekode") String emnekode){
        UtilsBean ub = new UtilsBean();
        if(!rom.equals("-1") && !etasje.equals("-1") && !bygg.equals("-1")) {
            //Returner ny tabell med bord
            String[] bordene = ub.getUnikeBord(emnekode, bygg, etasje, rom);
            
            
          String returnen = "</br><select name=\"bordene\" id=\"bordene\"><option value=\"-1\">Velg bord</option>";
          for(int i = 0; i < bordene.length; i++){
              returnen += "<option value=\"" + bordene[i] + "\">" + bordene[i] + "</option>";
          }
          returnen += "</select>";  
          return returnen;
        } else if(rom.equals("-1") && !etasje.equals("-1") && !bygg.equals("-1")){
        //Returner tabell med rom
            String[] rommene = ub.getUnikeRom(emnekode, bygg, etasje);
            
            
          String returnen = "</br><select name=\"rommene\" id=\"rommene\" onchange=\"visinfo('" + bygg + "', '" + etasje + "', this.value, '-1', '" + emnekode + "');\"><option value=\"-1\">Velg rom</option>";
          for(int i = 0; i < rommene.length; i++){
              returnen += "<option value=\"" + rommene[i] + "\">" + rommene[i] + "</option>";
          }
          returnen += "</select>";  
          return returnen;
        } else if(rom.equals("-1") && etasje.equals("-1") && !bygg.equals("-1")){
            //Returner tabell med etasjer
          String[] etasjene =  ub.getUnikeEtasjer(emnekode, bygg);
          String returnen = "</br><select name=\"etasjene\" id=\"etasjene\" onchange=\"visinfo('" + bygg + "', this.value, '-1', '-1', '" + emnekode + "');\"><option value=\"-1\">Velg etasje</option>";
          for(int i = 0; i < etasjene.length; i++){
              returnen += "<option value=\"" + etasjene[i] + "\">" + etasjene[i] + "</option>";
          }
          returnen += "</select>";
            return returnen;
        } else {
        return "En feil oppsto";
        }
    }
    
    
    
    
    
    
}

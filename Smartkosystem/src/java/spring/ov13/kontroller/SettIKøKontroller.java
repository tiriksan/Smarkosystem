package spring.ov13.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Øving;
import spring.ov13.domene.Plassering;
import spring.ov13.domene.utils.UtilsBean;
import java.util.ArrayList;
import java.lang.Math;
@SessionAttributes({"brukerinnlogg"})
@Controller
public class SettIKøKontroller {
    
    @ModelAttribute(value = "brukerinnlogg")
    public Bruker lage2() {

        return new Bruker();
    }
    
    
    @RequestMapping(value = "settiko.htm")
    public String metode(Model model, @ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x") String emnekode){
        
        
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
 return "logginn";
}
        
        
        
                UtilsBean ub = new UtilsBean();
                
                if(!ub.sjekkString(emnekode, 4, 8)){
                    return "feil";
                }
                
                
                ArrayList<Bruker> brukerne = new ArrayList<Bruker>();
                
                brukerne.add(bruker);
                ArrayList<Bruker> mid2 = ub.getBrukereIEmnet(emnekode);
                ArrayList<Bruker> alleredeiko = ub.getBrukereAlleredeIKo();
                System.out.println(alleredeiko.size() + " - " + mid2.size());
                ArrayList<Bruker> mid = new ArrayList<Bruker>();
                for(int i = 0; i < mid2.size(); i++){
                    boolean funnet = false;
                    for(int a = 0; a < alleredeiko.size(); a++){
                        if(mid2.get(i).getBrukernavn().equals(alleredeiko.get(a).getBrukernavn())){
                            funnet = true;
                        }
                    }
                    if(funnet == false){
                        mid.add(mid2.get(i));
                    }
                }
                System.out.println(mid.size() + "");
                
                
                ArrayList<Bruker> brukerneiliste = new ArrayList<Bruker>();
                for(int i = 0; i < mid.size(); i++){
                    for(int a = 0; a < brukerne.size(); a++){
                        System.out.println("Sjekker: " + mid.get(i).getBrukernavn() + " - " + brukerne.get(a).getBrukernavn());
                        if(!mid.get(i).getBrukernavn().equals(brukerne.get(a).getBrukernavn())){
                          brukerneiliste.add(mid.get(i));
                          
                            
                        }
                    }
                }
                
                for(int i = 0; i < brukerne.size(); i++){
                brukerne.get(i).setØvinger(ub.getUgjorteØvinger(emnekode, brukerne.get(i).getBrukernavn()));    
                }
                
        int antallovinger = ub.getAntallOvingerIFag(emnekode);
                
                
        String[] byggene = ub.getUnikeBygg(emnekode);
        model.addAttribute("byggene", byggene);
    model.addAttribute("emnekode", emnekode);    
    model.addAttribute("brukerne", brukerne);
    model.addAttribute("brukerneiliste", brukerneiliste);
    model.addAttribute("antallovinger", antallovinger);
        return "settiko";
    }
    
    
    
    
    
    
    
    
        @RequestMapping(value = "/test.htm")
 @ResponseBody
    public String what(@RequestParam(value = "bygg") String bygg, @RequestParam(value = "etasje") String etasje, @RequestParam(value = "rom") String rom, @RequestParam(value = "emnekode") String emnekode){
        
        
        UtilsBean ub = new UtilsBean();
        
        if(!ub.sjekkString(bygg, 4, -1) || !ub.sjekkString(etasje, 4, -1) || !ub.sjekkString(rom, 4, -1) || !ub.sjekkString(emnekode, 4, 8)){
            return "feil";
        }
        
        
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
    
    
    
    @RequestMapping(value = "/leggtilstudent.htm")
  @ResponseBody
    public String leggtilstud(@RequestParam(value = "brukernavn") String brukernavn,@RequestParam(value = "emnekode") String emnekode){
        
        UtilsBean ub = new UtilsBean();
        if(ub.sjekkString(brukernavn, 4, 64)){
            
        ArrayList<String> brukernavnene = new ArrayList<String>();
        String[] splitten = brukernavn.split(",");
        if(splitten.length > 0){
        for(int i = 0; i < splitten.length; i++){
         if(splitten[i].length() > 3){
             brukernavnene.add(splitten[i]);
         }   
        }
        }
        
        ArrayList<Bruker> brukernavnene2 = new ArrayList<Bruker>();
        
        for(int i = 0; i < brukernavnene.size(); i++){
            brukernavnene2.add(ub.getBruker(brukernavnene.get(i)));
        }
        

        
     ArrayList<Bruker> brukerne3 = ub.getBrukereIEmnet(emnekode);
     ArrayList<Bruker> alleredeiko = ub.getBrukereAlleredeIKo();
     ArrayList<Bruker> brukerne = new ArrayList<Bruker>();
     ArrayList<Bruker> brukerne2 = new ArrayList<Bruker>();
     
     
 
     
     
     
     for(int i = 0; i < brukerne3.size(); i++){
                    boolean funnet = false;
                    for(int a = 0; a < alleredeiko.size(); a++){
                        if(brukerne3.get(i).getBrukernavn().equals(alleredeiko.get(a).getBrukernavn())){
                            funnet = true;
                        }
                    }
                    if(funnet == false){
                        brukerne2.add(brukerne3.get(i));
                    }
                }
     
     
     
     
     
     
     for(int i = 0; i < brukerne2.size(); i++){
         boolean funnet = false;
         for(int a = 0; a < brukernavnene.size(); a++){
             
             if(brukerne2.get(i).getBrukernavn().equals(brukernavnene.get(a))){
                funnet = true;
             }
         }
         if(funnet == false){
             brukerne.add(brukerne2.get(i));
         }
     }
     
    for(int i = 0; i < brukernavnene2.size(); i++){
                brukernavnene2.get(i).setØvinger(ub.getUgjorteØvinger(emnekode, brukernavnene2.get(i).getBrukernavn()));    
                }
                
        int antallovinger = ub.getAntallOvingerIFag(emnekode);
        
        System.out.println("Antall øvinger:" + antallovinger);
     
        
        
        // Konstruering av selve oppbyggingen til autorefresh-div
     String returnen = "";
     
     
     
     returnen +=  "<table width=\"100%\"><tr><td>Studenter: <select id=\"studgruppe\" name=\"studgruppe\" onchange=\"leggtilstudent(this.value, '" + emnekode + "');\"><option value=\"-1\">Velg studenter i gruppen</option>";
                                     int check = 0;       
                                                    for(int i = 0; i < brukerne.size(); i++){
                                                        returnen += "<option value=\"" + brukerne.get(i).getBrukernavn() + "\">" + brukerne.get(i).getFornavn() + " " + brukerne.get(i).getEtternavn() + "</option>";
                                                    }
                                                    
                                        returnen += "</select></td></tr>";
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                    for(int i = 0; i < brukernavnene2.size(); i++){
                                        String ekstra = "<a href=\"#\" onclick=\"slett('" + brukernavnene2.get(i).getBrukernavn() + "', '" + emnekode + "');\">X</a> ";
                                        returnen += "<tr><td width=\"40%\" class=\"mainbrukeroversikt\">";
                                                if(i != 0){
                                                    returnen += ekstra;
                                                }
                                                String fornavnet = brukernavnene2.get(i).getFornavn();
                                                String etternavnet = brukernavnene2.get(i).getEtternavn();
                                                
                                                
                                                returnen += fornavnet + " " + etternavnet + "</td><td><table class=\"ovingene\" cellspacing=0 cellpadding=0><tr>";
                                        
                                        for(int a = 0; a < antallovinger; a++){
                                            boolean funnet = false;
                                            
                                            for(int b = 0; b < brukernavnene2.get(i).getØvinger().size(); b++){
                                                if(brukernavnene2.get(i).getØvinger().get(b).getØvingsnr() == a+1){ // Kan skje feil her
                                                    funnet = true;
                                                }
                                            }
                                            int nr = a+1;
                                            if(funnet == false){
                                                returnen += "<td class=\"overovinggodkjent\">#" + nr + "</td>";
                                            } else {
                                                returnen += "<td class=\"overoving\">#" + nr + "</td>";
                                            }
                                            
                                        }
                                        returnen += "</tr><tr>";
                                        
                                          for(int a = 0; a < antallovinger; a++){
                                            boolean funnet = false;
                                            for(int b = 0; b < brukernavnene2.get(i).getØvinger().size(); b++){
                                                if(brukernavnene2.get(i).getØvinger().get(b).getØvingsnr() == a+1){ // Kan skje feil her
                                                    funnet = true;
                                                }
                                            }
                                            
                                            if(funnet == false){
                                                returnen += "<td class=\"oving\"><input type=checkbox disabled></td>";
                                            } else {
                                                returnen += "<td class=\"oving\"><input type=checkbox name=\"oving[]\" value=\"" + check + "\"></td>";
                                            }
                                            check++;
                                        }
                                        returnen += "</tr></table></td></tr>";
                                        
                                        
                                        
                                        
                                    }    
                                        returnen += "</table>";
                                        
      
            
        return returnen;    
            
        } else {
            
            return "";
        }
        
        
    }
    
    
    
    
    
    @RequestMapping(value = "/submitko.htm")
    public String submiten(@RequestParam(value = "bygget", required = false) String bygget, @RequestParam(value = "etasjene", required = false) String etasje, @RequestParam(value = "rommene", required = false) String rom, @RequestParam(value = "bordene", required = false) String bord, @RequestParam(value = "hidden") String brukernavn, @RequestParam(value = "oving[]") String[] ovinger, @RequestParam(value = "emnekode", required = true) String emnekode, @RequestParam(value = "hjelp") String beskrivelse){
        
        
        boolean stop = false;
       
       
       UtilsBean ub = new UtilsBean();
       
       
               if(!ub.sjekkString(bygget, 4, -1) || !ub.sjekkString(etasje, 4, -1) || !ub.sjekkString(bord, 4, -1) || !ub.sjekkString(rom, 4, -1) || !ub.sjekkString(emnekode, 4, 8)){
            return "feil";
        }
       
       
       
       int antallovinger = ub.getAntallOvingerIFag(emnekode);
       if(antallovinger == 0){
           stop = true;
       }
       
        ArrayList<String> brukernavnene = new ArrayList<String>();
        String[] splitten = brukernavn.split(",");
        if(splitten.length > 0){
        for(int i = 0; i < splitten.length; i++){
         if(splitten[i].length() > 3){
             brukernavnene.add(splitten[i]);
         }   
        }
        }
       
       
        ArrayList<Bruker> brukerne = new ArrayList<Bruker>();
        for(int i = 0; i < brukernavnene.size(); i++){
            Bruker bruker = new Bruker();
            bruker.setBrukernavn(brukernavnene.get(i));
            brukerne.add(bruker);
        }
        ArrayList<Øving> ovperpers = new ArrayList<Øving>();
        
        
        for(int i = 0; i < brukerne.size()*antallovinger; i++){
            double mid = i/antallovinger;
            int hvilkenkar = (int) Math.floor(mid);
            int hvilkenoving = i%antallovinger;
            
                        
                
            
            for(int a = 0; a < ovinger.length; a++){
                
                if(i == Integer.parseInt(ovinger[a])){
                    
                    Øving ov = new Øving();
                    ov.setEmnekode(emnekode);
                    ov.setØvingsnr(hvilkenoving+1);
                    ovperpers.add(ov);
                }
                
                    
            }
            
            
            if(i%antallovinger == antallovinger-1){
                        brukerne.get(hvilkenkar).setØvinger(ovperpers);
                
                ovperpers = new ArrayList<Øving>();
        
            }
            
            
            
        }
        
        
        
        
        
    
        // VALIDERING VIKTIGVIKTIG! Sjekk om lokasjon eksisterer, sjekk øvinger opp mot hver enkelt person blabla
        
        
        Plassering plass = new Plassering();
        plass.setBygning(bygget);
        plass.setEtasje(Integer.parseInt(etasje));
        plass.setRom(rom);
        plass.setBord(Integer.parseInt(bord));
        
        ub.mekkeinnlegg(plass, brukerne, emnekode, beskrivelse);
        
        
        
        
        return "redirect:/studentko.htm";
    }
    
    
    
    
    
}

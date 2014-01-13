/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;
import spring.ov13.domene.Innlegg;
import spring.ov13.domene.Øving;
import spring.ov13.domene.utils.UtilsBean;
import org.springframework.web.bind.annotation.SessionAttributes;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@SessionAttributes({"brukerinnlogg"})
@Controller
public class KøKontroller {
    
    
        @ModelAttribute(value="brukerinnlogg")
public Bruker lage2(){
    
return new Bruker();
}
    
    
    @RequestMapping(value = "/studentko.htm")
    public String visKø(Model model, @ModelAttribute("brukerinnlogg") Bruker bruker, @RequestParam(value = "x", required = false) String emnekode){
        if(bruker.getBrukernavn() == null || bruker.getBrukernavn().equals("")){
            return "logginn";
        } else {
           
        UtilsBean ub = new UtilsBean();
        
        ArrayList<Emne> fagene = ub.getFageneTilBruker(bruker.getBrukernavn());
        if(fagene != null){
            if(fagene.size() > 0){
                model.addAttribute("fagene", fagene);           
            }
        }
        if(emnekode == null){

        } else {
            
            boolean open = ub.getFagKoAktiv(emnekode);
            
            
           model.addAttribute("emnenavnvalgt", emnekode);
           if(open == false){
            model.addAttribute("IngenAktiv", "Det er ingen aktiv kø for dette faget");
           } else {
               ArrayList<Innlegg> innleggene = new ArrayList<Innlegg>();
           innleggene = ub.getFulleInnleggTilKo(emnekode);
System.out.println(innleggene.size() + "er størrelsen på tabellen");
           ArrayList<String> ovingtekster = new ArrayList<String>();
           for(int i = 0; i < innleggene.size(); i++){
               
               String tekstenforinnlegg = "";
               ArrayList<Integer> nummeriliste = new ArrayList<Integer>();
               for(int a = 0; a < innleggene.get(i).getOvinger().size(); a++){ // Finner ArrayList<Øving> per bruker
                   
                   for(int k = 0; k < innleggene.get(i).getOvinger().get(a).size(); k++){ // Henter alle øvingene per bruker
                       
                       boolean funnet = false;
                       for(int e = 0; e < nummeriliste.size(); e++){
                       if(nummeriliste.get(e) == innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr()){
                           funnet = true;
                       }
                       }
                       if(funnet == false){
                           if(innleggene.get(i).getOvinger().get(a).size() == 1){
                               tekstenforinnlegg += innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr() + "";
                           } else {
                      if(k == (innleggene.get(i).getOvinger().get(a).size())-1){
                               tekstenforinnlegg += "og " + innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr();
                           } else if(k == (innleggene.get(i).getOvinger().get(a).size())-2) {
                               tekstenforinnlegg += innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr() + " ";
                           } else {
                               tekstenforinnlegg += innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr() + ", ";
                           }
                           }
                           
                       
                       nummeriliste.add(innleggene.get(i).getOvinger().get(a).get(k).getØvingsnr());
                       
                       }
                   }
                   
               }
              // tekstenforinnlegg += innleggene.get(i).getEier().getBrukernavn();
               
               ovingtekster.add(tekstenforinnlegg);
               
           }
           
           
System.out.println(innleggene.get(0).getKønummer() + " er bygningen kake");
           model.addAttribute("ovingtekster", ovingtekster);
           model.addAttribute("innleggene", innleggene);
           }
            
        }

 return "studentko";
       }
    }
}

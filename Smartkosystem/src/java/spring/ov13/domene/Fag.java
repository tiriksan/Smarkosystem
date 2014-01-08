/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ov13.domene;

import java.util.ArrayList;

/**
 *
 * @author Undis
 */
public class Fag {
  
    private String fag_navn;
    private String emnekode;
    ArrayList<Bruker> faglærer = new ArrayList<Bruker>();

    public Fag(String fag_navn, String emnekode){
        this.fag_navn = fag_navn;
        this.emnekode = emnekode;
    }
    
    public String getFag_navn() {
        return fag_navn;
    }

    public void setFag_navn(String fag_navn) {
        this.fag_navn = fag_navn;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }
    
    public Bruker getFaglærer(Bruker bruker){
        for(Bruker b: faglærer){
            if((b.getBrukertype()== 2) && ("select * from emne_bruker where emnekode ="+emnekode+", brukernavn="+b.getBrukernavn()+""){
       return bruker;
            }
             
    }

    return null;  
    
}
}

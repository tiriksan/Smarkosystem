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
  
    private String fagnavn;
    private String emnekode;
    ArrayList<Bruker> faglærer = new ArrayList<Bruker>();

    public Fag(String fagnavn, String emnekode){
        this.fagnavn = fagnavn;
        this.emnekode = emnekode;
    }
    public Fag(){
        
    }
    
    public String getFagnavn() {
        return fagnavn;
    }

    public void setFagnavn(String fagnavn) {
        this.fagnavn = fagnavn;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }
    
    public ArrayList<Bruker> getFaglærer(){
        
     //       if((b.getBrukertype()== 2) && ("select * from emne_bruker where emnekode ="+emnekode+", brukernavn="+b.getBrukernavn()+"")
            
       return faglærer;
      //      }
              
    
}
    public void setFaglærer(ArrayList<Bruker> nye){
        this.faglærer = nye;
    }
}

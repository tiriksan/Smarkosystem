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
public class Emne {
  
    private String emnenavn;
    private String emnekode;
    ArrayList<Bruker> faglærer = new ArrayList<Bruker>();

    public Emne(String fagnavn, String emnekode){
        this.emnenavn = fagnavn;
        this.emnekode = emnekode;
    }
    public Emne(){
        
    }
    
    public String getEmnenavn() {
        return emnenavn;
    }

    public void setEmnenavn(String emnenavn) {
        this.emnenavn = emnenavn;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }
    
    public ArrayList<Bruker> getFaglærer(){
                    
       return faglærer;
      
   }
    public void setFaglærer(ArrayList<Bruker> nye){
        this.faglærer = nye;
    }
    

    
}

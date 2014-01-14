/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ov13.domene;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 *
 * @author Undis
 */
public class Emne {
  @NotNull
    private String emnenavn;
  @Pattern(regexp = "\\b[A-z]{4}[0-9]{4}")
  @NotNull
    private String emnekode;
    ArrayList<Bruker> faglærer = new ArrayList<Bruker>();
    ArrayList<Øving> øvinger = new ArrayList<Øving>();

    public Emne(String emnekode, String emnenavn){
        this.emnenavn = emnenavn;
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

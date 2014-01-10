/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.domene;

import javax.validation.Valid;

/**
 *
 * @author christmw
 */
public class info {
    
    @Valid
    private Bruker bruker;
   
    public info(){
        setBruker(new Bruker());
    }
    public Bruker getBruker(){
        return bruker;
    }
   
    public void setBruker(Bruker brukeren){
        bruker = brukeren;
    }
    
    
    public String toString(){
        return bruker.toString();
    }
    
  public boolean sjekkominnskrevet(){
        
        if(bruker.sjekkominnskrevet2() == true){
            return true;
        }
    return false;
  }
}

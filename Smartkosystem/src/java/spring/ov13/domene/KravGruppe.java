/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.domene;

import java.util.ArrayList;

/**
 *
 * @author Bjornars
 */
public class KravGruppe {

    private int gruppeID;
    
    private String emnekode;
    private int antallgodkj;
    
      public KravGruppe(int gruppeID, String emnekode, int antallgodkj){
        this.emnekode = emnekode;
    }
      public KravGruppe(){
          
    }
 
    public int getGruppeID() {
        return gruppeID;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public int getAntallgodkj() {
        return antallgodkj;
    }

    public void setGruppeID(int gruppeID) {
        this.gruppeID = gruppeID;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }

    public void setAntallgodkj(int Antallgodkj) {
        this.antallgodkj = Antallgodkj;
    }

    @Override
    public String toString() {
        return "KravGruppe{" + "gruppeID=" + gruppeID + ", emnekode=" + emnekode + ", Antallgodkj=" + antallgodkj + '}';
    }
    
    
    
}

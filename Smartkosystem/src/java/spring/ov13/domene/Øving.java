/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.domene;

import javax.validation.constraints.NotNull;


public class Øving {
    private int øvingsnummer;
    
    @NotNull()
    private String emnekode;
    
    
    public Øving(int øvingsnummer, String emnekode){
        this.øvingsnummer = øvingsnummer;
        this.emnekode = emnekode;
    }
    
    public Øving(){
        
    }

    public int getØvingsnummer() {
        return øvingsnummer;
    }

    public void setØvingsnummer(int øvingsnummer) {
        this.øvingsnummer = øvingsnummer;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }
   
}

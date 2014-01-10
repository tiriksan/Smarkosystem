/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.domene;

import javax.validation.constraints.NotNull;


public class Øving {
    private int øvingsantall;
    private int antalløvinger;
    
    @NotNull()
    private String emnekode;
    
    
    public Øving(int øvingsnummer, String emnekode){
        this.øvingsantall = øvingsantall;
        this.emnekode = emnekode;
    }
    
    public Øving(){
        
    }

    public int getØvingantall() {
        return øvingsantall;
    }

    public void setØvingsnummer(int øvingsnummer) {
        this.øvingsantall = øvingsnummer;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }
   
}

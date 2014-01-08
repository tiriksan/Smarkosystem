/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.domene;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Rune
 */

public class Bruker {
    @Min(1)
    @Max(100)
    @NotNull()
    private String brukernavn;
    @Min(1)
    @Max(100)
    @NotNull()
    @Pattern(regexp="\\b[A-z]*")
    private String fornavn;
    @Min(1)
    @Max(100)
    @NotNull()
    @Pattern(regexp="\\b[A-z]*")
    private String etternavn;
    @Min(1)
    private int brukertype;
    private String passord;
    private static int STUDENT = 1;
    private static int STUDENTASSISTENT = 2;
    private static int FAGLÆRER = 3;
    private static int ADMIN= 4;
       
    
    public Bruker(String brukernavn, String fornavn, String etternavn, int brukertype, String passord){
        this.brukernavn = brukernavn;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.brukertype = brukertype;
        this.passord = passord;
        
    }
    
    public Bruker(){
        
    }
    
     public String getBrukernavn() {
        return brukernavn;
    }
    
    public String getFornavn() {
        return fornavn;
    }
    public String getEtternavn() {
        return etternavn;
    }
    
    public int getBrukertype() {
        return brukertype;
    }
    
    public String getPassord() {
        return passord;
    }
    
    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }
    
    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }
    
    public void setBrukertype(int brukertype) {
        this.brukertype= brukertype;
    }
    
    public void setPassord(String passord) {
        this.etternavn = passord;
    }
    
 
    
     public String toString(){
        return  brukernavn + " " +  fornavn + " " + etternavn + " " + brukertype + " " + passord;
    }

    
    
		
            
    
}



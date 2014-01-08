/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.domene;

/**
 *
 * @author Bruker
 */



public class Bruker {
    private int brukertype;
    private String fornavn;
    private String etternavn;
    private String brukernavn;
    private String passord;
    private static int STUDENT = 0;
    private static int STUDENTASSISTENT = 1;
    private static int FAGLÆRER = 2;
    private static int ADMIN= 3;
       
    
    public Bruker(String brukernavn,String fornavn, String etternavn, int brukertype, String passord){
        this.brukernavn = brukernavn;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.brukertype = brukertype;
        this.passord = passord;
        
    }
    
     public String getBrukerNavn() {
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
        return fornavn + " " + etternavn + " " + brukernavn + " " + brukertype;
    }

    
    
		
            
    
}



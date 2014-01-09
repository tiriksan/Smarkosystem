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
import java.security.*;
import java.math.*;
import java.util.ArrayList;
/**
 *
 * @author Rune
 */

public class Bruker {
   
    @NotNull()
    private String brukernavn;
    
    @NotNull()
    @Pattern(regexp="\\b[A-z]*")
    private String fornavn;
    
    @NotNull()
    @Pattern(regexp="\\b[A-z]*")
    private String etternavn;
    @Min(1)
    @Max(4)
    private int brukertype;
    private String passord;
    private ArrayList<Emne> fagene;
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
    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }
    
    public void setBrukertype(int brukertype) {
        this.brukertype= brukertype;
    }
    
    public void setPassord(String passord) {
        this.passord = passord;
    }
    
    public ArrayList<Emne> getFagene(){
        return fagene;
    }
    
    public void setFagene(ArrayList<Emne> fag){
   this.fagene=fag;
    }
    

 
    
     public String toString(){
        return  brukernavn + " " +  fornavn + " " + etternavn + " " + brukertype + " " + passord;
    }

    
    
		
            
     public static String md5(String md55){
		String sendinn = "";
		try {MessageDigest md5 = MessageDigest.getInstance("MD5");

			 md5.update(md55.getBytes(),0,md55.length());
		 sendinn = new BigInteger(1,md5.digest()).toString(16);


} catch (Exception e){}
return sendinn;
	}
    
}



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ov13.domene;

/**
 *
 * @author Undis
 */
public class Fag {
    
    private String navn;
    private String etternavn;
    private String epost;
    private int brukertype;
    
    public Fag(String navn, String etternavn, String epost, int brukertype){
        this.navn = navn;
        this.etternavn = etternavn;
        this.epost = epost;
        this.brukertype = brukertype;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }

    public int getBrukertype() {
        return brukertype;
    }

    public void setBrukertype(int brukertype) {
        this.brukertype = brukertype;
    }
    
    
    
    
}

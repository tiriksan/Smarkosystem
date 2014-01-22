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
public class Kravgruppe {

    private int gruppeID;
    private String emnekode;
    private int antallgodkj;
    private String beskrivelse;

    

    public Kravgruppe(int gruppeID, String emnekode, int antallgodkj, String beskrivelse) {
        this.gruppeID = gruppeID;
        this.emnekode = emnekode;
        this.antallgodkj = antallgodkj;
        this.beskrivelse = beskrivelse;
    }
    
    public Kravgruppe(String emnekode, int antallgodkj) {
        this.emnekode = emnekode;
        this.antallgodkj = antallgodkj;
    }

    public Kravgruppe() {

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
    
    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    @Override
    public String toString() {
        return "KravGruppe{" + "gruppeID=" + gruppeID + ", emnekode=" + emnekode + ", Antallgodkj=" + antallgodkj + '}';
    }

}

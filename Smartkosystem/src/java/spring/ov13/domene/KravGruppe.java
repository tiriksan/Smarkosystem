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

    private ArrayList<Integer> gruppeID = new ArrayList<Integer>();
    private String emnekode;
    private int Antallgodkj;

    public ArrayList<Integer> getGruppeID() {
        return gruppeID;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public int getAntallgodkj() {
        return Antallgodkj;
    }

    public void setGruppeID(ArrayList<Integer> gruppeID) {
        this.gruppeID = gruppeID;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }

    public void setAntallgodkj(int Antallgodkj) {
        this.Antallgodkj = Antallgodkj;
    }

    @Override
    public String toString() {
        return "KravGruppe{" + "gruppeID=" + gruppeID + ", emnekode=" + emnekode + ", Antallgodkj=" + Antallgodkj + '}';
    }
    
    
    
}

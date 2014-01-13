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
public class Plassering {
    private String bygning;
    private int etasje;
    private int rom;
    private String kommentar;

    public String getBygning() {
        return bygning;
    }

    public int getEtasje() {
        return etasje;
    }

    public int getRom() {
        return rom;
    }
    
     public String getKommentar() {
        return kommentar;
    }

    public void setBygning(String bygning) {
        this.bygning = bygning;
    }

    public void setEtasje(int etasje) {
        this.etasje = etasje;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }
    
     public void setKommentar(String kommentar) {
        this.kommentar= kommentar;
    }

    @Override
    public String toString() {
        return "Plassering{" + "bygning=" + bygning + ", etasje=" + etasje + ", rom=" + rom + '}';
    }
    
    
    
}

package spring.ov13.domene;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Bruker
 */
public class Plassering {

    public Plassering(String bygning, int etasje, int rom, int bord, String emnekode) {
        this.bygning = bygning;
        this.etasje = etasje;
        this.rom = rom;
        this.bord = bord;
        this.emnekode = emnekode;
    }
    
    public Plassering() {
        
    }

    private String bygning;
    private int etasje;
    private int rom;
    private int bord;
    @Pattern(regexp = "\\b[A-z]{4}[0-9]{4}")
    @NotNull
    private String emnekode;

    public String getBygning() {
        return bygning;
    }

    public int getEtasje() {
        return etasje;
    }

    public int getRom() {
        return rom;
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

    public int getBord() {
        return bord;
    }

    public void setBord(int bord) {
        this.bord = bord;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }

    @Override
    public String toString() {
        return "Plassering{" + "bygning=" + bygning + ", etasje=" + etasje + ", rom=" + rom + '}';
    }

}

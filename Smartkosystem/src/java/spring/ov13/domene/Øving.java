package spring.ov13.domene;

import javax.validation.constraints.NotNull;


public class Øving {
    private int øvingsnr;
    @NotNull()
    private String emnekode;
    private int gruppeid;
    private boolean obligatorisk;
    
    
    public Øving(int øvingsnr, String emnekode, int gruppeid, boolean obligatorisk){
        this.øvingsnr = øvingsnr;
        this.emnekode = emnekode;
        this.gruppeid = gruppeid;
        this.obligatorisk = obligatorisk;
    }
    
    public Øving(){
    }

    public int getØvingsnr() {
        return øvingsnr;
    }

    public void setØvingsnr(int øvingsnummer) {
        this.øvingsnr = øvingsnummer;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }
    
    public int getGruppeid() {
        return gruppeid;
    }
    
    public void setGruppeid(int gruppeid) {
        this.gruppeid = gruppeid;
    }
    
    public boolean erObligatorisk() {
        return obligatorisk;
    }
    
    public void setObligatorisk(boolean obligatorisk) {
        this.obligatorisk = obligatorisk;
    }
   
}

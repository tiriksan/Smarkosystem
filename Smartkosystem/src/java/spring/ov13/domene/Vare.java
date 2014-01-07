package spring.ov13.domene;


public class Vare {

    private int varenr;
    private String varenavn;
    private int pris;
    public Vare(int varenr, String varenavn, int pris){
        this.varenr = varenr;
        this.varenavn = varenavn;
        this.pris = pris;
    }
    public Vare(){
        
    }


    public int getVarenr() {
        return varenr;
    }

    public void setVarenr(int varenr) {
        this.varenr = varenr;
    }

    public String getVarenavn() {
        return varenavn;
    }

    public void setVarenavn(String varenavn) {
        this.varenavn = varenavn;
    }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }
    
    public String toString(){
        return varenr + " " + varenavn + " " + pris;
    }

}

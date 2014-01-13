/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.domene;

import java.util.ArrayList;
import spring.ov13.domene.Bruker;

/**
 *
 * @author Petter-PC
 */
public class Innlegg {
    
    private ArrayList<Bruker> brukere;
    private ArrayList<ArrayList<Øving>> ovinger;
    private long tid;
    private int kønummer;
    private Bruker eier;
    private Bruker hjelp;
    private int id;
    private Plassering plass;
    
    
    
    
    
    public Innlegg(){
        
    }
    
    public ArrayList<Bruker> getBrukere(){
        return brukere;
    }
    public ArrayList<ArrayList<Øving>> getOvinger(){
        return ovinger;
    }
    public long getTid(){
        return tid;
    }
    public int getKønummer(){
        return kønummer;
    }
    public Bruker getEier(){
        return eier;
    }
    public Bruker getHjelp(){
        return hjelp;
    }
    public int getId(){
        return id;
    }
    public Plassering getPlass(){
        return plass;
    }
    public void setBrukere(ArrayList<Bruker> brukere2){
        this.brukere = brukere2;
    }
    public void setOvinger(ArrayList<ArrayList<Øving>> ovinger2){
        this.ovinger = ovinger2;
    }
    public void setTid(long tid2){
        this.tid = tid2;
    }
    public void setKønummer(int kønummer2){
        this.kønummer = kønummer2;
    }
    public void setEier(Bruker eier2){
        this.eier = eier2;
    }
    public void setHjelp(Bruker hjelp2){
        this.hjelp = hjelp2;
    }
    public void setId(int id2){
        this.id = id2;
    }
    public void setPlass(Plassering plass2){
        this.plass = plass2;
    }
    
}

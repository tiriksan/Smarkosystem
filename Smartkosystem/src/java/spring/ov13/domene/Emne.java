/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.ov13.domene;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Undis
 */
public class Emne {

    @NotNull
    @Pattern(regexp = "\\b[A-z]{4}[0-9]{4}")
    private String emnekode;
    @NotNull
    private String emnenavn;
    ArrayList<Bruker> faglærer = new ArrayList<Bruker>();
    ArrayList<Øving> øvinger = new ArrayList<Øving>();
    ArrayList<Bruker> student = new ArrayList<Bruker>();
    ArrayList<Bruker> studentassistent = new ArrayList<Bruker>();
    @NotNull
    private String beskrivelse;
    private boolean haMedLaerer;

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }
    public Emne(String emnekode, String emnenavn, String beskrivelse) {
        this.emnenavn = emnenavn;
        this.emnekode = emnekode;
        this.beskrivelse = beskrivelse;
    }

    public Emne() {

    }
    public ArrayList<Bruker> getStudent(){
        return student;
    }
    public void setStudent(ArrayList<Bruker> student){
        this.student = student;
    }
      public ArrayList<Bruker> getStudentassistent(){
        return studentassistent;
    }
    public void setStudentassistent(ArrayList<Bruker> studentassistent){
        this.studentassistent = studentassistent;
    }

    public String getEmnenavn() {
        return emnenavn;
    }

    public void setEmnenavn(String emnenavn) {
        this.emnenavn = emnenavn;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }
    
    public ArrayList<Bruker> getFaglærer() {

        return faglærer;

    }

    public void setFaglærer(ArrayList<Bruker> nye) {
        this.faglærer = nye;
    }

    public boolean getHaMedLaerer() {
        return haMedLaerer;
    }

    public void setHaMedLaerer(boolean haMedLaerer) {
        this.haMedLaerer = haMedLaerer;
    }

}

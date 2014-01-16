package spring.ov13.domene;

/**
 * @author Sindre
 */
public class FilOpplasting {

    private String emner;
    private String filInnhold;

    public FilOpplasting(String e, String f){
        emner = e;
        filInnhold = f;
    }
    public FilOpplasting(){
      
    }

    public String getEmner() {
        return emner;
    }

    public void setEmner(String emner) {
        this.emner = emner;
    }

    public String getFilInnhold() {
        return filInnhold;
    }

    public void setFilInnhold(String filInnhold) {
        this.filInnhold = filInnhold;
    }
    
}

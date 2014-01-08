package spring.ov13.kontroller;

import java.beans.PropertyEditorSupport;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.utils.UtilsBean;



public class BrukerEditor extends PropertyEditorSupport {
    private final UtilsBean utilsBean;
    public BrukerEditor(UtilsBean ub){
        this.utilsBean = ub;
    }
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException{
   //     String[] t = text.split(" ");
        Bruker b = new Bruker(); //utilsBean.get((t[0]));
        setValue(b);   
    }
    
}
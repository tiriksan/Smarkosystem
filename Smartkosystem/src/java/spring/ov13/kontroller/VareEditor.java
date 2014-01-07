package spring.ov13.kontroller;

import java.beans.PropertyEditorSupport;
import spring.ov13.domene.utils.UtilsBean;
import spring.ov13.domene.Vare;


public class VareEditor extends PropertyEditorSupport {
    private final UtilsBean utilsBean;
    public VareEditor(UtilsBean ub){
        this.utilsBean = ub;
    }
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException{
        String[] t = text.split(" ");
        Vare v = utilsBean.get(Integer.parseInt((t[0])));
        setValue(v);   
    }
    
}
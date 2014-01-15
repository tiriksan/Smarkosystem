package spring.ov13.kontroller;

/**
 * @author Sindre
 */
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import spring.FilLeser.FilOpplasting;
 
public class FilOpplastingValidator implements Validator{
 
	@Override
	public boolean supports(Class clazz) {
		return FilOpplasting.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object obj, Errors errors) {
 
		FilOpplasting fil = (FilOpplasting)obj;
 
		if(fil.getFil().getSize()==0){
			errors.rejectValue("fil", "kreves.FilOpplasting");
		}
	}
}   

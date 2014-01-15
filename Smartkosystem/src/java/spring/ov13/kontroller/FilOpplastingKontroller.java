package spring.ov13.kontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
 
import spring.FilLeser.FilOpplasting;
 
public class FilOpplastingKontroller{
 
	public FilOpplastingKontroller(){
	}
 
	
	protected ModelAndView onSubmit(HttpServletRequest request,
		HttpServletResponse response, Object command, BindException errors)
		throws Exception {
 
		FilOpplasting fil = (FilOpplasting)command;
 
		MultipartFile multipartFil = fil.getFil();
 
		String filNamn="";
 
		if(multipartFil!=null){
			filNamn = multipartFil.getOriginalFilename();
			//do whatever you want
		}
 
		return new ModelAndView("FileUploadSuccess","fileName",filNamn);
	}
}

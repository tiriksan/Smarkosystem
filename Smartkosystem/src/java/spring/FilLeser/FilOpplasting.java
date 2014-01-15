package spring.FilLeser;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Sindre
 */
public class FilOpplasting {
    
    MultipartFile fil;

    public MultipartFile getFil() {
        return fil;
    }

    public void setFil(MultipartFile fil) {
        this.fil = fil;
    }

}

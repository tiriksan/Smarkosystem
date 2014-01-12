/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.kontroller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.ov13.domene.Øving;
import spring.ov13.domene.utils.UtilsBean;

/**
 *
 * @author Bruker
 */

@Controller
public class ØvingKontroller {
    
    @RequestMapping(value = "/regov2.htm")
    public String visØvinginnsetning(@Validated @ModelAttribute(value = "regov2") Øving øving, BindingResult error, Model modell, HttpServletRequest request) {

        if (error.hasErrors()) {
            //javax.swing.JOptionPane.showMessageDialog(null, "Feil ved registrering av øving.", "ERROR", javax.swing.JOptionPane.ERROR_MESSAGE, null);
            return "øving";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerØving(øving)) {
            modell.addAttribute("melding", "Øving" + øving + " er registrert");

        }

        return "øving";
    }
    
}

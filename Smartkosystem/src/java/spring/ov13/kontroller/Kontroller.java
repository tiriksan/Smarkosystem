package spring.ov13.kontroller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.ov13.domene.Bruker;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import spring.ov13.domene.utils.UtilsBean;

@Controller
public class Kontroller {

    @RequestMapping(value = "/*")
    public String visIndex() {
        return "index";
    }
    @RequestMapping(value = "/bruker.htm")
    public String visInnsetting(Model model) {
        Bruker bruker = new Bruker();
        model.addAttribute("bruker", bruker);
        
        return "bruker";
    }

    /*
    @RequestMapping(value = "/bruker.htm")
    public String visVare(Model model) {
        model.addAttribute("utilsBean", new UtilsBean());
        return "bruker";
    }
*//*
    @RequestMapping(value = "/nyvare.htm")
    public String visNyVare(Model model) {
        Bruker v = new Bruker();
        model.addAttribute("vare", v);

        return "nyvare";
    }

    @RequestMapping(value = "svarside.htm")
    public String svarside(@Validated @ModelAttribute("bruker") Bruker bruker, BindingResult error, Model modell, HttpServletRequest request) {
        if (error.hasErrors()) {
            modell.addAttribute("melding", "Vare ikke fylt ut riktig"); // kun ibruk v return svarside
            return "nyvare";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerBruker(bruker)) {
            modell.addAttribute("melding", "Vare " + bruker + " er registrert");
            return "svarside";
        } else {
            return "nyvare";
        }
    }
}
*/
/*
    @RequestMapping(value = "/oversikt.htm")
    public String visOversikt(@ModelAttribute(value = "utilsBean") UtilsBean utilsBean, Model modell, HttpServletRequest request, HttpServletResponse response) {
        String slett = request.getParameter("slett");
        String send = request.getParameter("send");
        if (send != null) {
            if (utilsBean.getValgteVarer() != null && utilsBean.getValgteVarer().size() > 0) {
                return "oversikt";
            } else {
                System.out.println("Ingen personer valgt");
                return "vare";
            }
        } else if (slett != null) {
            List<Bruker> vv = utilsBean.getValgteVarer();
            System.out.println("Troll");
            if (vv == null) {
                System.out.println("asdfsfdafdsafsdafsda");
            } else {
                for (Bruker v : vv) {

                    System.out.println(v.toString());
                }
            }
            System.out.println("*** slett vare **** ");
            if (vv != null) {
                for (Bruker v : vv) {
                    System.out.println("Sletting av " + v + ": " + utilsBean.slettVare(v));

                }
                modell.addAttribute("utilsBean", new UtilsBean());
                return "vare";
            }
        } else {

            List<Bruker> alle = utilsBean.getAlleVarer();
            if (alle != null) {
                for (Bruker v : alle) {
                    System.out.println("oppdatering av " + v + ":" + utilsBean.oppdaterVare(v));
                }
            }
            modell.addAttribute("utilsBean", new UtilsBean());
            return "vare";
        }
        return "vare";
    }*/

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Bruker.class, new BrukerEditor(new UtilsBean()));
    }
}
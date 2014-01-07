package spring.ov13.kontroller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.ov13.domene.Vare;
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

    @RequestMapping(value = "/vare.htm")
    public String visVare(Model model) {
        model.addAttribute("utilsBean", new UtilsBean());
        return "vare";
    }

    @RequestMapping(value = "/nyvare.htm")
    public String visNyVare(Model model) {
        Vare v = new Vare();
        model.addAttribute("vare", v);

        return "nyvare";
    }

    @RequestMapping(value = "svarside.htm")
    public String svarside(@Validated @ModelAttribute("vare") Vare vare, BindingResult error, Model modell, HttpServletRequest request) {
        if (error.hasErrors()) {
            modell.addAttribute("melding", "Vare ikke fylt ut riktig"); // kun ibruk v return svarside
            return "nyvare";
        }
        UtilsBean utilsBean = new UtilsBean();
        if (utilsBean.registrerVare(vare)) {
            modell.addAttribute("melding", "Vare " + vare + " er registrert");
            return "svarside";
        } else {
            return "nyvare";
        }
    }

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
            List<Vare> vv = utilsBean.getValgteVarer();
            System.out.println("Troll");
            if (vv == null) {
                System.out.println("asdfsfdafdsafsdafsda");
            } else {
                for (Vare v : vv) {

                    System.out.println(v.toString());
                }
            }
            System.out.println("*** slett vare **** ");
            if (vv != null) {
                for (Vare v : vv) {
                    System.out.println("Sletting av " + v + ": " + utilsBean.slettVare(v));

                }
                modell.addAttribute("utilsBean", new UtilsBean());
                return "vare";
            }
        } else {

            List<Vare> alle = utilsBean.getAlleVarer();
            if (alle != null) {
                for (Vare v : alle) {
                    System.out.println("oppdatering av " + v + ":" + utilsBean.oppdaterVare(v));
                }
            }
            modell.addAttribute("utilsBean", new UtilsBean());
            return "vare";
        }
        return "vare";
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Vare.class, new VareEditor(new UtilsBean()));
    }
}

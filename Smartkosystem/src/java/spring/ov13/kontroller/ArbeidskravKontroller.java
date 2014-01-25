package spring.ov13.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import spring.ov13.domene.utils.UtilsBean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import spring.ov13.domene.*;

@SessionAttributes("brukerinnlogg")
@Controller
public class ArbeidskravKontroller {

    @RequestMapping(value = "/arbeidskrav.htm")
    public String arbeidsKrav(Model modell, @ModelAttribute(value = "brukerinnlogg") Bruker bruker, HttpServletRequest request) {
        UtilsBean ub = new UtilsBean();
        ArrayList<Emne> emner = ub.getFageneTilBruker(bruker.getBrukernavn());
        request.getSession().setAttribute("emner", emner);
        request.getSession().setAttribute("emne", null);
        return "arbeidskrav";
    }

    @RequestMapping("/arbeidskravOving.htm")
    public String arbeidskravØving(Model model, @RequestParam(value = "emnet") String emnekode, HttpServletRequest request) {
        System.out.println("here=");
        UtilsBean ub = new UtilsBean();
        if(!ub.sjekkString(emnekode, 4, 8)){
            return "feil";
        }
        ArrayList<Øving> øvinger = ub.getØvingerIEmnet(emnekode);
        request.getSession().setAttribute("øvinger", øvinger);
        request.getSession().setAttribute("antØvinger", øvinger.size());
        request.getSession().setAttribute("emne", emnekode);

        return "arbeidskrav";
    }

    @RequestMapping(value = "/arbeidskravVelgAnt.htm")
    public String arbeidskravVelgAnt(Model model, HttpServletRequest request) {
        String[] valgteØvinger = request.getParameterValues("valgteØvinger");
        if (valgteØvinger == null || valgteØvinger.length < 1) {
            return "arbeidskrav";
        }
        request.getSession().setAttribute("valgteØvingerListe", valgteØvinger);
        model.addAttribute("gåVidere", true);
        int antØvinger = (Integer) (request.getSession().getAttribute("antØvinger"));
        boolean[] erØvingValgt = new boolean[antØvinger];
        System.out.println(valgteØvinger.length);
        for (String s : valgteØvinger) {
            erØvingValgt[Integer.parseInt(s) - 1] = true;
        }
        model.addAttribute("erØvingValgt", erØvingValgt);

        return "arbeidskrav";
    }

    @RequestMapping(value = "/registrerArbeidskrav.htm")
    public String registrerArbeidskrav(Model model, HttpServletRequest request) {
        String arbKravTekst = request.getParameter("arbKravText");
        if (arbKravTekst == null || arbKravTekst.trim().equals("")) {
            String[] valgteØvinger = (String[]) request.getSession().getAttribute("valgteØvingerListe");
            int antØvinger = (Integer) (request.getSession().getAttribute("antØvinger"));
            boolean[] erØvingValgt = new boolean[antØvinger];
            for (String s : valgteØvinger) {
                erØvingValgt[Integer.parseInt(s) - 1] = true;
            }
            model.addAttribute("gåVidere", true);
            model.addAttribute("erØvingValgt", erØvingValgt);
            model.addAttribute("tekstFeilmelding", "Må inneholde en tekstlig beskrivelse av arbeidskravet");
        } else {
            UtilsBean ub = new UtilsBean();
            Kravgruppe kg = new Kravgruppe();
            int nyGruppeID = ub.getMaxGruppeIDIEmne()+1;
            kg.setGruppeID(nyGruppeID);
            kg.setAntallgodkj(Integer.parseInt(request.getParameter("antØvinger")));
            kg.setBeskrivelse(arbKravTekst);
            kg.setEmnekode((String) request.getSession().getAttribute("emne"));
            if (ub.registrerKravGruppe(kg)) {
                String[] valgteØvinger = (String[]) request.getSession().getAttribute("valgteØvingerListe");
                for (String s : valgteØvinger) {
                    int øvingsID = Integer.parseInt(s);
                    Øving øv = ub.getØvingIEmnet(øvingsID, (String) request.getSession().getAttribute("emne"));
                    øv.setGruppeid(nyGruppeID);
                    ub.oppdaterØving(øv);
                }
            }

            model.addAttribute("RegistrertArbeidskrav", true);
        }
        return "arbeidskrav";
    }

    @RequestMapping(value = "/leggTilNyttArbeidskrav.htm")
    public String leggTilNyttArbeidskrav(Model model, HttpServletRequest request) {
        request.getSession().setAttribute("øvinger", null);
        request.getSession().setAttribute("antØvinger", 0);
        request.getSession().setAttribute("emne", null);
        request.getSession().setAttribute("valgteØvingerListe", null);
        model.addAttribute("RegistrertArbeidskrav", false);
        return "arbeidskrav";
    }
}

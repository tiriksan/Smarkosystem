/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spring.ov13.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KøKontroller {
    
    @RequestMapping(value = "/studentko.htm")
    public String visKø(Model model){
        System.out.println("hello");
        return "studentko";
    }
}

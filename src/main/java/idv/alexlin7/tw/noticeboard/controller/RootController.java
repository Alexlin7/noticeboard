package idv.alexlin7.tw.noticeboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String redirectToNotices() {
        return "redirect:/notices";
    }
}

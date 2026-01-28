package idv.alexlin7.tw.noticeboard.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // Default

        if (status != null) {
            try {
                httpStatus = HttpStatus.valueOf(Integer.parseInt(status.toString()));
            } catch (Exception ex) {
                // Keep default status
            }
        }

        model.addAttribute("status", httpStatus.value());
        model.addAttribute("error", httpStatus.getReasonPhrase());
        model.addAttribute("message", "很抱歉，您所尋找的頁面不存在或發生錯誤。");

        return "error";
    }
}

package idv.alexlin7.tw.noticeboard.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import idv.alexlin7.tw.noticeboard.model.Notice;
import idv.alexlin7.tw.noticeboard.service.NoticeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String listNotices(@PageableDefault(size = 10, sort = "publicationDate") Pageable pageable, Model model) {
        Page<Notice> noticePage = noticeService.findAll(pageable);
        model.addAttribute("noticePage", noticePage);
        return "notices/list"; // Corresponds to templates/notices/list.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "notices/form"; // Corresponds to templates/notices/form.html
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable UUID id, Model model) {
        noticeService.findById(id).ifPresent(notice -> model.addAttribute("notice", notice));
        return "notices/form";
    }

    @PostMapping
    public String saveNotice(@ModelAttribute Notice notice, RedirectAttributes redirectAttributes) {
        noticeService.save(notice);
        redirectAttributes.addFlashAttribute("successMessage", "公告已成功儲存！");
        return "redirect:/notices";
    }

    @PostMapping("/{id}/delete")
    public String deleteNotice(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        noticeService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "公告已成功刪除！");
        return "redirect:/notices";
    }

    @GetMapping("/{id}")
    public String viewNotice(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        return noticeService.findById(id)
                .map(notice -> {
                    model.addAttribute("notice", notice);
                    return "notices/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "查無此公告！");
                    return "redirect:/notices";
                });
    }
}

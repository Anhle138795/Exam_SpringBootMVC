package shop.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import shop.demo.models.Type;
import shop.demo.modelviews.PageView;
import shop.demo.repositories.TypeRepository;
import shop.demo.utils.Views;

@Controller
@RequestMapping("/admin/type")
public class TypeController {

    @Autowired
    TypeRepository rep;

    @GetMapping("")
    public String index(Model model, @RequestParam(name = "cp", required = false, defaultValue = "1") int cp,
                        @RequestParam(name = "page_size", required = false, defaultValue = "5") int page_size,
                        @RequestParam(name = "search", required = false) String search) {
        PageView pv = new PageView();
        pv.setPage_current(cp);
        pv.setPage_size(page_size);
        model.addAttribute("types", rep.findAllPaging(pv, search));
        model.addAttribute("pv", pv);
        model.addAttribute("search", search);
        return Views.TYPE_INDEX;
    }

    @GetMapping("/create")
    public String input_type(Model model) {
        Type t = new Type();
        t.setTitle("");
        model.addAttribute("new_item", t);
        return Views.TYPE_CREATE;
    }

    @PostMapping("/new_type")
    public String submit_type(@Validated @ModelAttribute("new_item") Type new_item, BindingResult result, Model model) {
        if (result.hasErrors() || new_item.getTitle() == null || new_item.getTitle().isEmpty()) {
            if (new_item.getTitle() == null || new_item.getTitle().isEmpty()) {
                model.addAttribute("titleError", "Title cannot be empty");
            }
            return Views.TYPE_CREATE;
        }
        rep.newType(new_item);
        return "redirect:/admin/type";
    }

    @GetMapping("/changeitem")
    public String change_type(@RequestParam String id, Model model) {
        int _id_type = Integer.parseInt(id);
        model.addAttribute("up_item", rep.findById(_id_type));
        return Views.TYPE_EDIT;
    }

    @PostMapping("/updatetype")
    public String update_type(@Validated @ModelAttribute("up_item") Type up_item, BindingResult result, Model model) {
        if (result.hasErrors() || up_item.getTitle() == null || up_item.getTitle().isEmpty()) {
            if (up_item.getTitle() == null || up_item.getTitle().isEmpty()) {
                model.addAttribute("titleError", "Title cannot be empty");
            }
            return Views.TYPE_EDIT;
        }
        rep.update(up_item);
        return "redirect:/admin/type";
    }

    @GetMapping("/rmitem")
    public String getMethodName(@RequestParam String id) {
        int _id_type = Integer.parseInt(id);
        rep.deleteType(_id_type);
        return "redirect:/admin/type";
    }
}

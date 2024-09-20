package shop.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import shop.demo.models.Position;
import shop.demo.modelviews.PageView;
import shop.demo.repositories.PositionRepository;
import shop.demo.utils.Views;

@Controller
@RequestMapping("/admin/position")
public class PositionController {

    @Autowired
    PositionRepository rep;

    @GetMapping("")
    public String index(Model model, @RequestParam(name = "cp", required = false, defaultValue = "1") int cp,
                        @RequestParam(name = "page_size", required = false, defaultValue = "5") int page_size,
                        @RequestParam(name = "search", required = false) String search) {
        PageView pv = new PageView();
        pv.setPage_current(cp);
        pv.setPage_size(page_size);
        model.addAttribute("positions", rep.findAllPaging(pv, search));
        model.addAttribute("pv", pv);
        model.addAttribute("search", search);
        return Views.POSITION_INDEX;
    }

    @GetMapping("/create")
    public String input_position(Model model) {
        Position p = new Position();
        p.setTitle("");
        p.setStatus(0);
        model.addAttribute("new_item", p);
        return Views.POSITION_CREATE;
    }

    @PostMapping("/new_position")
    public String submit_position(@Validated @ModelAttribute("new_item") Position new_item, BindingResult result, Model model) {
        if (result.hasErrors() || new_item.getTitle() == null || new_item.getTitle().isEmpty()) {
            if (new_item.getTitle() == null || new_item.getTitle().isEmpty()) {
                model.addAttribute("titleError", "Title cannot be empty");
            }
            return Views.POSITION_CREATE;
        }
        rep.newPosition(new_item);
        return "redirect:/admin/position";
    }

    @GetMapping("/changeitem")
    public String change_position(@RequestParam String id, Model model) {
        int _id_position = Integer.parseInt(id);
        model.addAttribute("up_item", rep.findById(_id_position));
        return Views.POSITION_EDIT;
    }

    @PostMapping("/updateposition")
    public String update_position(@Validated @ModelAttribute("up_item") Position up_item, BindingResult result, Model model) {
        if (result.hasErrors() || up_item.getTitle() == null || up_item.getTitle().isEmpty()) {
            if (up_item.getTitle() == null || up_item.getTitle().isEmpty()) {
                model.addAttribute("titleError", "Title cannot be empty");
            }
            return Views.POSITION_EDIT;
        }
        rep.update(up_item);
        return "redirect:/admin/position";
    }

    @PostMapping("/active")
    public ResponseEntity<?> changeStatusAjax(@RequestBody Position data) {
        rep.updateStatus(data.getId(), data.getStatus());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/rmitem")
    public String getMethodName(@RequestParam String id) {
        int _id_position = Integer.parseInt(id);
        rep.deletePosition(_id_position);
        return "redirect:/admin/position";
    }
}

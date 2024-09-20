package shop.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import shop.demo.models.Category;
import shop.demo.models.Type;
import shop.demo.repositories.CategoryRepository;
import shop.demo.repositories.TypeRepository;
import shop.demo.utils.FileUtility;
import shop.demo.utils.Views;

@Controller
@RequestMapping("/admin/cates")
public class CategoryController {
    @Autowired
    CategoryRepository repCate;
    @Autowired
    TypeRepository repType;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("pathroot", System.getProperty("user.dir"));
        model.addAttribute("categories", repCate.findAll());
        return Views.CATEGORY_INDEX;
    }

    @GetMapping("/create")
    public String newCategory(Model model) {
        Category item = new Category();
        model.addAttribute("newItem", item);
        List<Type> lsType = repType.findAll();
        model.addAttribute("lsType", lsType);
        return Views.CATEGORY_CREATE;
    }

    @PostMapping("/newcategory")
    public String submitCategory(@RequestParam("idType") int idType, @RequestParam("title") String title,
                                 @RequestParam("no") int no, @RequestParam("status") int status,
                                 @RequestParam("image") MultipartFile image) {
        Category item = new Category();
        item.setIdType(idType);
        item.setTitle(title);
        item.setNo(no);
        item.setStatus(status);
        item.setImage(FileUtility.uploadFileImage(image, "uploads"));

        repCate.newCategory(item);
        return "redirect:/admin/cates";
    }

    @GetMapping("/edit")
    public String editCategory(@RequestParam("id") int id, Model model) {
        Category category = repCate.findById(id);
        model.addAttribute("up_item", category);
        List<Type> lsType = repType.findAll();
        model.addAttribute("lsType", lsType);
        return Views.CATEGORY_EDIT;
    }

    @PostMapping("/updatecategory")
    public String updateCategory(@Validated @ModelAttribute("up_item") Category up_item, BindingResult result, Model model, @RequestParam("image") MultipartFile image) {
        if (result.hasErrors() || up_item.getTitle() == null || up_item.getTitle().isEmpty()) {
            if (up_item.getTitle() == null || up_item.getTitle().isEmpty()) {
                model.addAttribute("titleError", "Title cannot be empty");
            }
            List<Type> lsType = repType.findAll();
            model.addAttribute("lsType", lsType);
            return Views.CATEGORY_EDIT;
        }

        if (!image.isEmpty()) {
            String newImage = FileUtility.uploadFileImage(image, "uploads");
            if (!newImage.isEmpty()) {
                String oldImage = up_item.getImage();
                up_item.setImage(newImage);
                if (oldImage != null && !oldImage.isEmpty()) {
                    FileUtility.deleteFile("uploads", oldImage);
                }
            } else {
                model.addAttribute("imageError", "Image upload failed");
                List<Type> lsType = repType.findAll();
                model.addAttribute("lsType", lsType);
                return Views.CATEGORY_EDIT;
            }
        }

        repCate.updateCategory(up_item);
        return "redirect:/admin/cates";
    }

    @GetMapping("/rmitem")
    public String removeCategory(@RequestParam String id) {
        int idCate = Integer.parseInt(id);
        repCate.deleteCategory(idCate);
        return "redirect:/admin/cates";
    }
}

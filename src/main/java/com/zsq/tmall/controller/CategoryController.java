package com.zsq.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsq.tmall.pojo.Category;
import com.zsq.tmall.service.CategoryService;
import com.zsq.tmall.util.ImageUtil;
import com.zsq.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> cs = categoryService.list();
        int total = (int)new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }
    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, MultipartFile image) throws IOException {
        categoryService.add(c);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId()+".jpg");
        //System.out.println(file.getPath());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            //System.out.println(1);
        }
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) throws IOException {
        categoryService.delete(id);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id+".jpg");
        file.delete();

        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_edit")
    public String get(int id, Model model) {
        Category c = categoryService.get(id);
        model.addAttribute("c", c);
        return "admin/editCategory";
    }
    @RequestMapping("admin_category_update")
    public String update(Category c, HttpSession session, MultipartFile image) throws IOException {
        categoryService.update(c);
        if (null != image && !image.isEmpty()) {
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, c.getId()+".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }
}

package com.group4.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group4.entity.CategoryEntity;
import com.group4.service.impl.CategoryServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
	
	@Autowired
	private CategoryServiceImpl categoryService;
	
	public AdminCategoryController(CategoryServiceImpl categoryService) {
		this.categoryService = categoryService;
	}


	// Lấy tất cả category để show lên
	@GetMapping("/categories")
	public String getAll(Model model) {
		model.addAttribute("categories", categoryService.findAll());
		return "admin/categories-list";
	}
	
	
	// Add category
	@GetMapping("/categories/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new CategoryEntity());
        return "admin/category-add";
    }
	
	
	
	@PostMapping("/categories/add")
	public String addCategory(@ModelAttribute("category") CategoryEntity category) {
		categoryService.save(category);
		return "redirect:/admin/categories";
	}
	
	
	
	// Edit category
	@GetMapping("/categories/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category ID")));
        return "admin/category-edit";
    }

    @PostMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute("category") CategoryEntity category) {
        category.setCategoryID(id);
        categoryService.save(category);
        return "redirect:/admin/categories";
    }
    
    
    // Delete category
    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }
}

package com.group4.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group4.entity.ManufacturerEntity;
import com.group4.entity.ProductEntity;
import com.group4.service.impl.ManufacturersServiceImpl;
import com.group4.service.impl.ProductServiceImpl;

@Controller
@RequestMapping("/admin/manufacturers")
public class AdminManufacturerController{
	
	
	@Autowired
	private ManufacturersServiceImpl manufacturersService;
	
	
	public AdminManufacturerController(ManufacturersServiceImpl manufacturersService) {
		this.manufacturersService = manufacturersService;
	}


	@GetMapping
	public String getAll(Model model) {
		model.addAttribute("manufacturers", manufacturersService.findAll());
		return "admin/manufacturer-list";
	}


	@GetMapping("/add")
	public String addCategoryForm(Model model) {
	    // Thêm đối tượng ManufacturerEntity vào model
	    model.addAttribute("manufacturer", new ManufacturerEntity());

	    return "admin/manufacturer-add";
	}
	
	
	@PostMapping("/add")
	public String addCategory(@ModelAttribute("manufacturer") ManufacturerEntity manufacturer) {
		manufacturersService.save(manufacturer);
		return "redirect:/admin/manufacturers";
	}

	// Edit category
	@GetMapping("/edit/{id}")
	public String editManufacturerForm(@PathVariable Long id, Model model) {
		ManufacturerEntity manufacturer = manufacturersService.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid manufacturer ID: " + id));
		model.addAttribute("manufacturer", manufacturer);
		return "admin/manufacturer-edit";
	}

	@PostMapping("edit/{id}")
	public String editCategory(@PathVariable Long id, @ModelAttribute("manufacturer") ManufacturerEntity manufacturer) {
		manufacturer.setId(id);
		manufacturersService.save(manufacturer);
		return "redirect:/admin/manufacturers";
	}

	// Delete category
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable Long id) {
		manufacturersService.deleteById(id);
		return "redirect:/admin/manufacturers";
	}
}

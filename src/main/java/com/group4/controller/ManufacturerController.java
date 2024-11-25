package com.group4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.group4.entity.ManufacturerEntity;
import com.group4.entity.ProductEntity;
import com.group4.service.impl.ManufacturersService;

@Controller
public class ManufacturerController {

	@Autowired
	private ManufacturersService manufacturersService;

	private ProductService productService;
	
	public ManufacturerController(ManufacturersService manufacturersService, ProductService productService) {
		this.manufacturersService = manufacturersService;
		this.productService = productService;
	}


	@GetMapping("/admin/manufacturers")
	public String getAll(Model model) {
		model.addAttribute("manufacturers", manufacturersService.findAll());
		return "manufacturer-list";
	}


	@GetMapping("/admin/manufacturers/add")
	public String addCategoryForm(Model model) {
	    // Thêm đối tượng ManufacturerEntity vào model
	    model.addAttribute("manufacturer", new ManufacturerEntity());

	    // Lấy danh sách các sản phẩm từ service (giả sử bạn có productService)
	    List<ProductEntity> products = productService.findAll();  
	    // Thêm danh sách sản phẩm vào model
	    model.addAttribute("products", products);

	    // Trả về view mà bạn muốn sử dụng, ví dụ: "manufacturer-add"
	    return "manufacturer-add";
	}
	
	
	@PostMapping("/admin/manufacturers/add")
	public String addCategory(@ModelAttribute("manufacturer") ManufacturerEntity manufacturer) {
		manufacturersService.save(manufacturer);
		return "redirect:/admin/manufacturers";
	}

	// Edit category
	@GetMapping("/admin/manufacturers/edit/{id}")
	public String editManufacturerForm(@PathVariable Long id, Model model) {
		ManufacturerEntity manufacturer = manufacturersService.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid manufacturer ID: " + id));
		model.addAttribute("manufacturer", manufacturer);
		return "manufacturer-edit";
	}

	@PostMapping("/admin/manufacturers/edit/{id}")
	public String editCategory(@PathVariable Long id, @ModelAttribute("manufacturer") ManufacturerEntity manufacturer) {
		manufacturer.setId(id);
		manufacturersService.save(manufacturer);
		return "redirect:/admin/manufacturers";
	}

	// Delete category
	@GetMapping("/admin/manufacturers/delete/{id}")
	public String deleteCategory(@PathVariable Long id) {
		manufacturersService.deleteById(id);
		return "redirect:/admin/manufacturers";
	}

}

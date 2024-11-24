package com.group4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group4.entity.ProductEntity;
import com.group4.service.impl.CategoryService;
import com.group4.service.impl.ManufacturersService;
import com.group4.service.impl.ProductDetailService;
import com.group4.service.impl.ProductService;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	private CategoryService categoryService;
	private ManufacturersService manufacturersService;
	private ProductDetailService productDetailService;
	
	
	
	
	public ProductController(ProductService productService, CategoryService categoryService,
			ManufacturersService manufacturersService, ProductDetailService productDetailService) {
		this.productService = productService;
		this.categoryService = categoryService;
		this.manufacturersService = manufacturersService;
		this.productDetailService = productDetailService;
	}



	@GetMapping
	public String getAllProducts(Model model) {
	    model.addAttribute("products", productService.findAll());
	    return "product-list";  // Trang hiển thị danh sách sản phẩm
	}

	@GetMapping("/edit/{id}")
	public String getProductById(@PathVariable("id") Long id, Model model) {
	    Optional<ProductEntity> optionalProduct = productService.findById(id);
	    
	    // Kiểm tra xem sản phẩm có tồn tại không
	    if (optionalProduct.isPresent()) {
	        ProductEntity product = optionalProduct.get();
	        model.addAttribute("product", product);
	        model.addAttribute("categories", categoryService.findAll());
	        model.addAttribute("manufacturers", manufacturersService.findAll());
	        model.addAttribute("productDetails", productDetailService.findAll());
	        return "product-edit";  // Trang chỉnh sửa sản phẩm
	    } else {
	        // Nếu không tìm thấy sản phẩm, có thể chuyển hướng đến trang lỗi hoặc danh sách sản phẩm
	        return "redirect:/admin/products";  // Ví dụ: quay lại danh sách sản phẩm
	    }
	}
		
	
	@PostMapping("/update")
	public String updateProduct(@ModelAttribute("product") ProductEntity productEntity) {
	    productService.save(productEntity);
	    return "redirect:/admin/products";  // Chuyển hướng về danh sách sản phẩm
	}


	@PostMapping("/add")
	public String addProduct(@ModelAttribute("product") ProductEntity productEntity) {
	    productService.save(productEntity);
	    return "redirect:/admin/products";  // Chuyển hướng về danh sách sản phẩm
	}


	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id) {
	    productService.deleteById(id);
	    return "redirect:/admin/products";  // Chuyển hướng về danh sách sản phẩm
	}

}

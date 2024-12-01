package com.group4.controller;

import com.group4.entity.ProductEntity;
import com.group4.service.IProductService;
import com.group4.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    
	@Autowired
    IProductService productService = new ProductServiceImpl();
	
	@GetMapping("/home")
    public String home(Model model) {
        return "MainHome";

	}

    @RequestMapping("/home")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "8") int size, Model model) {
        Page<ProductEntity> productPage = productService.getProducts(page, size);

        // Truyền dữ liệu tới giao diện
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "trangchu";
       
    }


    // Xử lý về chúng tôi
    @GetMapping("/about-us")
    public String aboutUsPage() {
        return "about-us";
    }

    // Xử lý liên hệ
    @GetMapping("/contact")
    public String contactPage() {
        return "contact-us";
    }

	
}

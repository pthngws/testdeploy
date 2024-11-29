package com.group4.controller.admin;

import java.util.List;
import java.util.Optional;

import com.group4.service.IImageItemService;
import com.group4.service.IProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group4.entity.ProductDetailEntity;

@Controller
@RequestMapping("/admin/product/product-details")
public class AdminProductDetailController {

	@Autowired
	private IProductDetailService productDetailService;

	@Autowired
	private IImageItemService imageItemService;

	
	public AdminProductDetailController(IProductDetailService productDetailService,
			IImageItemService imageItemService) {
		super();
		this.productDetailService = productDetailService;
		this.imageItemService = imageItemService;
	}



	@GetMapping
	public String getAllProductDetail(Model model) {
		model.addAttribute("productDetails", productDetailService.findAll());
		return "product-details-list";
	}
	
	
	
	@GetMapping("/add")
	public String addProductDetailForm(Model model) {
		//List<ImageItemEntity> images = imageItemService.getAllImageItem();


		model.addAttribute("productDetail", new ProductDetailEntity());

		return "product-details-add";
	}

	
	@PostMapping("/add")
	public String addProductDetail(@ModelAttribute("productDetail") ProductDetailEntity productDetailEntity) {
	    // Lưu ProductDetailEntity mới vào cơ sở dữ liệu
	    productDetailService.save(productDetailEntity);
	    
	    // Chuyển hướng về danh sách sản phẩm sau khi thêm mới thành công
	    return "redirect:/admin/product/product-details";
	}


	@GetMapping("/edit/{id}")
	public String editProductDetailForm(@PathVariable("id") Long id, Model model) {
	    // Tìm ProductDetailEntity theo id
	    Optional<ProductDetailEntity> productDetailOptional = productDetailService.findById(id);

	    // Kiểm tra nếu không tìm thấy sản phẩm
	    if (productDetailOptional.isEmpty()) {
	        throw new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + id);
	    }

	    // Thêm đối tượng sản phẩm vào model
	    model.addAttribute("productDetail", productDetailOptional.get());

	    // Trả về trang form chỉnh sửa sản phẩm
	    return "product-details-edit"; // Thay đổi tên file JSP hoặc HTML nếu cần
	}
	
	
	@PostMapping("/edit/{id}")
	public String editProductDetail(@PathVariable Long id, 
	                                @ModelAttribute("productDetail") ProductDetailEntity productDetailEntity) {
	    // Kiểm tra xem sản phẩm có tồn tại trong cơ sở dữ liệu hay không
	    Optional<ProductDetailEntity> existingProductDetail = productDetailService.findById(id);
	    
	    if (existingProductDetail.isEmpty()) {
	        // Nếu sản phẩm không tồn tại, ném ra exception hoặc xử lý theo yêu cầu
	        throw new IllegalArgumentException("Sản phẩm với ID: " + id + " không tồn tại.");
	    }
	    
	    // Cập nhật thông tin của sản phẩm
	    ProductDetailEntity productDetail = existingProductDetail.get();
	    productDetail.setRAM(productDetailEntity.getRAM());
	    productDetail.setCPU(productDetailEntity.getCPU());
	    productDetail.setGPU(productDetailEntity.getGPU());
	    productDetail.setMonitor(productDetailEntity.getMonitor());
	    productDetail.setCharger(productDetailEntity.getCharger());
	    productDetail.setDisk(productDetailEntity.getDisk());
	    productDetail.setConnect(productDetailEntity.getConnect());
	    productDetail.setLAN(productDetailEntity.getLAN());
	    productDetail.setWIFI(productDetailEntity.getWIFI());
	    productDetail.setBluetooth(productDetailEntity.getBluetooth());
	    productDetail.setAudio(productDetailEntity.getAudio());
	    productDetail.setWebcam(productDetailEntity.getWebcam());
	    productDetail.setOperationSystem(productDetailEntity.getOperationSystem());
	    productDetail.setWeight(productDetailEntity.getWeight());
	    productDetail.setColor(productDetailEntity.getColor());
	    productDetail.setSize(productDetailEntity.getSize());
	    productDetail.setDescription(productDetailEntity.getDescription());
	    
	    // Lưu lại thay đổi vào cơ sở dữ liệu
	    productDetailService.save(productDetail);

	    // Chuyển hướng người dùng về danh sách sản phẩm sau khi cập nhật
	    return "redirect:/admin/product/product-details";
	}

	
	
	// Không xóa sản phẩm ở đây mà xóa ở Product Controller
//	@GetMapping("/delete/{id}")
//	public String deleteProductDetail(@PathVariable Long id) {
//	    // Tìm sản phẩm theo ID
//	    Optional<ProductDetailEntity> optionalProductDetail = productDetailService.findById(id);
//	    
//	    if (optionalProductDetail.isEmpty()) {
//	        // Nếu không tìm thấy sản phẩm, ném exception hoặc xử lý tùy ý
//	        throw new IllegalArgumentException("Sản phẩm với ID: " + id + " không tồn tại.");
//	    }
//	    
//	    // Lấy sản phẩm và xóa
//	    Long productDetailID = optionalProductDetail.get().getProductDetailID();
//	    productDetailService.deleteById(productDetailID);  // Hoặc sử dụng method productDetailService.deleteById(id) tùy vào cách cài đặt
//
//	    // Chuyển hướng về danh sách sản phẩm sau khi xóa
//	    return "redirect:/admin/product/product-details";
//	
//	}
}

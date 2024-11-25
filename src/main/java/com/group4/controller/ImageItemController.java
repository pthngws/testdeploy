package com.group4.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group4.entity.CategoryEntity;
import com.group4.entity.ImageItemEntity;
import com.group4.entity.ProductDetailEntity;
import com.group4.service.impl.ImageItemService;
import com.group4.service.impl.ProductDetailService;

@Controller
@RequestMapping("/admin/product-details/images")
public class ImageItemController {

	@Autowired
	private ImageItemService imageItemService;
	private ProductDetailService productDetailService;

	public ImageItemController(ImageItemService imageItemService, ProductDetailService productDetailService) {
		this.imageItemService = imageItemService;
		this.productDetailService = productDetailService;
	}

	// Xuất tất cả danh sách ảnh
	@GetMapping
	public String getAllImageItems(Model model) {
		model.addAttribute("imageitems", imageItemService.getAllImageItem());
		return "image-list";
	}

	@GetMapping("/add")
	public String addImageItemsForm(Model model) {
		List<ProductDetailEntity> products = productDetailService.findAll();

		model.addAttribute("imageitem", new ImageItemEntity());

		model.addAttribute("products", products);

		return "image-add";
	}

	@PostMapping("/add")
	public String addImageItems(@ModelAttribute("imageitem") ImageItemEntity imageItemEntity,
			@RequestParam("productDetailId") Long productDetailId) {

		// Tìm ProductDetailEntity bằng productDetailId
		Optional<ProductDetailEntity> optionalProductDetail = productDetailService.findById(productDetailId);

		// Kiểm tra nếu không tìm thấy productDetail
		if (optionalProductDetail.isEmpty()) {
			// Xử lý nếu không tìm thấy sản phẩm
			throw new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + productDetailId);
		}

		// Lấy ProductDetailEntity từ Optional
		ProductDetailEntity productDetail = optionalProductDetail.get();

		// Gán ProductDetailEntity vào ImageItemEntity
		imageItemEntity.setProductDetail(productDetail);

		// Lưu ImageItemEntity vào cơ sở dữ liệu
		imageItemService.addImageItem(imageItemEntity);

		// Chuyển hướng về danh sách ảnh
		return "redirect:/admin/product-details/images";
	}

	@GetMapping("/edit/{id}")
	public String editImageItemsForm(@PathVariable Long id, Model model) {

		// Lấy thông tin ảnh từ id
		Optional<ImageItemEntity> optionalimageItem = imageItemService.findById(id);

		if (optionalimageItem.isEmpty()) {
			// Xử lý nếu không tìm thấy sản phẩm
			throw new IllegalArgumentException("Không tìm thấy ảnh với ID: " + id);
		}

		// Lấy ProductDetailEntity từ Optional
		ImageItemEntity imageItem = optionalimageItem.get();

		// Lấy danh sách sản phẩm
		List<ProductDetailEntity> products = productDetailService.findAll();

		// Đưa dữ liệu vào Model
		model.addAttribute("imageitem", imageItem);
		model.addAttribute("products", products);

		return "image-edit";
	}

	@PostMapping("edit/{id}")
	public String editImageItems(
			@PathVariable Long id,
			@ModelAttribute("imageitem") ImageItemEntity imageItem,
			@RequestParam("productDetailId") Long productDetailId) {

		imageItem.setId(id);

		ProductDetailEntity productDetail = productDetailService.findById(productDetailId)
				.orElseThrow(() -> new IllegalArgumentException("ProductDetail not found with id: " + productDetailId));

		
		imageItem.setProductDetail(productDetail);

		imageItemService.updateImageItem(id, imageItem);
		return "redirect:/admin/product-details/images";
	}
	
	// Delete category
    @GetMapping("/delete/{id}")
    public String deleteImageItem(@PathVariable Long id) {
        imageItemService.deleteImageItem(id);
        return "redirect:/admin/product-details/images";
    }
}

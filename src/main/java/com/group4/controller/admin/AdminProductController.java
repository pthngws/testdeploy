package com.group4.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group4.entity.CategoryEntity;
import com.group4.entity.ImageItemEntity;
import com.group4.entity.InventoryEntity;
import com.group4.entity.ManufacturerEntity;
import com.group4.entity.ProductDetailEntity;
import com.group4.entity.ProductEntity;
import com.group4.service.impl.CategoryServiceImpl;
import com.group4.service.impl.ImageItemServiceImpl;
import com.group4.service.impl.InventoryServiceImpl;
import com.group4.service.impl.ManufacturersServiceImpl;
import com.group4.service.impl.ProductDetailServiceImpl;
import com.group4.service.impl.ProductServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminProductController {

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@Autowired
	private ManufacturersServiceImpl manufacturersServiceImpl;

	@Autowired
	private CategoryServiceImpl categoryServiceImpl;

	@Autowired
	private ImageItemServiceImpl imageItemServiceImpl;

	@Autowired
	private ProductDetailServiceImpl productDetailServiceImpl;
	
	@Autowired
	private InventoryServiceImpl inventoryServiceImpl;

	public AdminProductController(ManufacturersServiceImpl manufacturersServiceImpl,
			CategoryServiceImpl categoryServiceImpl, ProductServiceImpl productServiceImpl,
			ImageItemServiceImpl imageItemServiceImpl, ProductDetailServiceImpl productDetailServiceImpl) {
		this.manufacturersServiceImpl = manufacturersServiceImpl;
		this.categoryServiceImpl = categoryServiceImpl;
		this.productServiceImpl = productServiceImpl;
		this.imageItemServiceImpl = imageItemServiceImpl;
		this.productDetailServiceImpl = productDetailServiceImpl;
	}

//	@GetMapping("/inventory")
//    public String inventory(){
//        return "inventory";
//    }

	@GetMapping("/products")
	public String getProducts(@RequestParam(defaultValue = "1") int page, // Số trang (mặc định là 1)
			@RequestParam(defaultValue = "5") int size, // Số sản phẩm mỗi trang
			Model model) {
		// Lấy danh sách sản phẩm phân trang
		Page<ProductEntity> productPage = productServiceImpl.findAll(PageRequest.of(page - 1, size));

		
		model.addAttribute("products", productPage.getContent()); // Danh sách sản phẩm
		model.addAttribute("currentPage", page); // Trang hiện tại
		model.addAttribute("totalPages", productPage.getTotalPages()); // Tổng số trang

		return "admin/admin-product-list"; // Trả về file `admin/products.html`
	}

	@GetMapping("/products/add")
	public String editProductForm(Model model) {
		model.addAttribute("categories", categoryServiceImpl.findAll());
		model.addAttribute("manufacturers", manufacturersServiceImpl.findAll());
		return "admin/admin-product-add";
	}

	@PostMapping("/products/add")
	public String addProduct(@ModelAttribute ProductEntity productEntity, @RequestParam("ram") String ram,
			@RequestParam("cpu") String cpu, @RequestParam("gpu") String gpu, @RequestParam("monitor") String monitor,
			@RequestParam("color") String color, @RequestParam("wifi") String wifi, @RequestParam("lan") String lan,
			@RequestParam("audio") String audio, @RequestParam("bluetooth") String bluetooth,
			@RequestParam("charger") String charger, @RequestParam("connect") String connect,
			@RequestParam("disk") String disk, @RequestParam("size") String size, @RequestParam("webcam") String webcam,
			@RequestParam("weight") String weight, @RequestParam("operationSystem") String operationSystem,
			@RequestParam("description") String description, @RequestParam("imageName[]") List<String> imageNames,
			@RequestParam("imageURI[]") List<String> imageURIs, RedirectAttributes redirectAttributes) {

		try {
			// Tạo ProductDetailEntity và liên kết với ProductEntity
			ProductDetailEntity detail = new ProductDetailEntity();
			detail.setRAM(ram);
			detail.setCPU(cpu);
			detail.setGPU(gpu);
			detail.setWIFI(wifi);
			detail.setMonitor(monitor);
			detail.setColor(color);
			detail.setLAN(lan);
			detail.setAudio(audio);
			detail.setBluetooth(bluetooth);
			detail.setCharger(charger);
			detail.setConnect(connect);
			detail.setDisk(disk);
			detail.setSize(size);
			detail.setWebcam(webcam);
			detail.setWeight(weight);
			detail.setOperationSystem(operationSystem);
			detail.setDescription(description);
			
			
			
			
			
			
			// Xử lý danh sách ảnh
			List<ImageItemEntity> imageItems = new ArrayList<>();
			for (int i = 0; i < imageNames.size(); i++) {
				ImageItemEntity imageItem = new ImageItemEntity();
				imageItem.setName(imageNames.get(i));
				imageItem.setImageUrl(imageURIs.get(i));
				imageItem.setProductDetail(detail); // Liên kết với ProductDetailEntity
				imageItems.add(imageItem);
			}
			detail.setImages(imageItems);

			// Lưu ProductDetailEntity vào database trước
			productDetailServiceImpl.save(detail); // Lưu ProductDetailEntity vào database

			productEntity.setDetail(detail);

			// Lưu ProductEntity vào database
			productServiceImpl.save(productEntity);
			
			// Tạo và lưu InventoryEntity
	        InventoryEntity inventory = new InventoryEntity();
	        inventory.setProductId(productEntity.getProductID());
	        inventory.setQuantity(0); // Số lượng mặc định là 0
	        inventoryServiceImpl.save(inventory); // Lưu InventoryEntity vào database

			redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to add product: " + e.getMessage());
		}

		return "redirect:/admin/products";
	}

	@GetMapping("products/edit{id}")
	public String editProduct(@PathVariable("id") Long id, Model model) {
		try {
			// Lấy thông tin sản phẩm từ database dưới dạng Optional
			Optional<ProductEntity> optionalProductEntity = productServiceImpl.findById(id);

			// Kiểm tra nếu sản phẩm không tồn tại
			if (optionalProductEntity.isEmpty()) {
				// Nếu không tìm thấy sản phẩm, chuyển hướng về trang danh sách sản phẩm với
				// thông báo lỗi
				model.addAttribute("errorMessage", "Product not found.");
				return "redirect:/admin/products";
			}

			// Nếu sản phẩm tồn tại, lấy thông tin sản phẩm và chi tiết sản phẩm
			ProductEntity productEntity = optionalProductEntity.get();
			ProductDetailEntity productDetail = productEntity.getDetail();

			// Lấy danh sách các category và manufacturer
			List<CategoryEntity> categories = categoryServiceImpl.findAll();
			List<ManufacturerEntity> manufacturers = manufacturersServiceImpl.findAll();

			// Thêm thông tin vào model để hiển thị trên trang sửa
			model.addAttribute("product", productEntity);
			model.addAttribute("productDetail", productDetail);
			model.addAttribute("categories", categories);
			model.addAttribute("manufacturers", manufacturers);

			// Trả về view sửa sản phẩm
			return "admin/admin-product-edit"; // Thay thế bằng view tương ứng của bạn (product-edit.html)
		} catch (Exception e) {
			// Xử lý lỗi chung nếu có lỗi trong quá trình lấy dữ liệu
			model.addAttribute("errorMessage", "Error occurred: " + e.getMessage());
			return "redirect:/admin/products"; // Quay lại danh sách sản phẩm nếu có lỗi
		}
	}

//	@PostMapping("products/edit{id}")
//	public String saveProduct(@PathVariable("id") Long id, @ModelAttribute("product") ProductEntity product,
//			Model model) {
//		try {
//			// Lấy sản phẩm cũ từ database
//			Optional<ProductEntity> optionalProduct = productServiceImpl.findById(id);
//
//			if (optionalProduct.isEmpty()) {
//				// Nếu sản phẩm không tồn tại
//				model.addAttribute("errorMessage", "Product not found.");
//				return "redirect:/admin/products";
//			}
//
//			// Lấy sản phẩm hiện tại và cập nhật thông tin
//			ProductEntity existingProduct = optionalProduct.get();
//
//			// Cập nhật thông tin từ form vào sản phẩm cũ
//			existingProduct.setName(product.getName());
//			existingProduct.setPrice(product.getPrice());
//			existingProduct.setCategory(product.getCategory()); // Cập nhật category
//			existingProduct.setManufacturer(product.getManufacturer()); // Cập nhật manufacturer
//
//			// Cập nhật chi tiết sản phẩm
//			existingProduct.getDetail().setRAM(product.getDetail().getRAM());
//			existingProduct.getDetail().setCPU(product.getDetail().getCPU());
//			existingProduct.getDetail().setGPU(product.getDetail().getGPU());
//			existingProduct.getDetail().setMonitor(product.getDetail().getMonitor());
//			existingProduct.getDetail().setColor(product.getDetail().getColor());
//
//			existingProduct.getDetail().setAudio(product.getDetail().getAudio());
//			existingProduct.getDetail().setBluetooth(product.getDetail().getBluetooth());
//			existingProduct.getDetail().setCharger(product.getDetail().getCharger());
//			existingProduct.getDetail().setConnect(product.getDetail().getConnect());
//			existingProduct.getDetail().setDescription(product.getDetail().getDescription());
//			existingProduct.getDetail().setLAN(product.getDetail().getLAN());
//			existingProduct.getDetail().setOperationSystem(product.getDetail().getOperationSystem());
//			existingProduct.getDetail().setDisk(product.getDetail().getDisk());
//
//			// Cập nhật ảnh nếu có
//			List<ImageItemEntity> images = existingProduct.getDetail().getImages();
//			if (!images.isEmpty()) {
//				for (int i = 0; i < images.size(); i++) {
//					images.get(i).setName(product.getDetail().getImages().get(i).getName());
//					images.get(i).setImageUrl(product.getDetail().getImages().get(i).getImageUrl());
//				}
//			}
//
//			// Lưu sản phẩm đã chỉnh sửa vào cơ sở dữ liệu
//			productServiceImpl.save(existingProduct);
//
//			// Thêm thông báo thành công và chuyển đến trang danh sách sản phẩm
//			model.addAttribute("successMessage", "Product updated successfully.");
//			return "redirect:/admin/products";
//
//		} catch (Exception e) {
//			// Xử lý lỗi nếu có
//			model.addAttribute("errorMessage", "Error occurred: " + e.getMessage());
//			return "redirect:/admin/products";
//		}
//	}
	
	@PostMapping("products/edit{id}")
	public String saveProduct(@PathVariable("id") Long id, @ModelAttribute("product") ProductEntity product,
	                          Model model) {
	    try {
	        // Lấy sản phẩm cũ từ database theo ID
	        Optional<ProductEntity> optionalProduct = productServiceImpl.findById(id);

	        if (optionalProduct.isEmpty()) {
	            // Nếu sản phẩm không tồn tại
	            model.addAttribute("errorMessage", "Sản Phẩm Không Tồn Tại.");
	            return "redirect:/admin/products";
	        }

	        // Lấy tên sản phẩm hiện tại
	        ProductEntity existingProduct = optionalProduct.get();
	        String productName = existingProduct.getName();

	        // Lấy danh sách tất cả các sản phẩm có cùng tên
	        List<ProductEntity> productsToUpdate = productServiceImpl.findByName(productName);

	        if (productsToUpdate.isEmpty()) {
	            // Nếu không có sản phẩm nào cần cập nhật
	            model.addAttribute("errorMessage", "Không Tồn Tại Sản Phẩm Khác Cùng Tên.");
	            return "redirect:/admin/products";
	        }

	        // Cập nhật thông tin cho từng sản phẩm
	        for (ProductEntity prod : productsToUpdate) {
	            prod.setName(product.getName());
	            prod.setPrice(product.getPrice());
	            prod.setCategory(product.getCategory());
	            prod.setManufacturer(product.getManufacturer());

	            // Cập nhật chi tiết sản phẩm
	            prod.getDetail().setRAM(product.getDetail().getRAM());
	            prod.getDetail().setCPU(product.getDetail().getCPU());
	            prod.getDetail().setGPU(product.getDetail().getGPU());
	            prod.getDetail().setMonitor(product.getDetail().getMonitor());
	            prod.getDetail().setColor(product.getDetail().getColor());
	            prod.getDetail().setAudio(product.getDetail().getAudio());
	            prod.getDetail().setBluetooth(product.getDetail().getBluetooth());
	            prod.getDetail().setCharger(product.getDetail().getCharger());
	            prod.getDetail().setConnect(product.getDetail().getConnect());
	            prod.getDetail().setDescription(product.getDetail().getDescription());
	            prod.getDetail().setLAN(product.getDetail().getLAN());
	            prod.getDetail().setOperationSystem(product.getDetail().getOperationSystem());
	            prod.getDetail().setDisk(product.getDetail().getDisk());

	            // Cập nhật ảnh nếu có
	            List<ImageItemEntity> images = prod.getDetail().getImages();
	            if (!images.isEmpty()) {
	                for (int i = 0; i < images.size(); i++) {
	                    images.get(i).setName(product.getDetail().getImages().get(i).getName());
	                    images.get(i).setImageUrl(product.getDetail().getImages().get(i).getImageUrl());
	                }
	            }
	            
	            productServiceImpl.save(prod);
	        }

	        // Lưu tất cả các sản phẩm đã chỉnh sửa vào cơ sở dữ liệu
	        //productServiceImpl.save(productsToUpdate);

	        // Thêm thông báo thành công và chuyển đến trang danh sách sản phẩm
	        model.addAttribute("successMessage", "All products with the same name updated successfully.");
	        return "redirect:/admin/products";

	    } catch (Exception e) {
	        // Xử lý lỗi nếu có
	        model.addAttribute("errorMessage", "Error occurred: " + e.getMessage());
	        return "redirect:/admin/products";
	    }
	}



	// Delete product
	@GetMapping("/products/delete{id}")
	public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		// Tìm sản phẩm theo id
	    Optional<ProductEntity> optionalProduct = productServiceImpl.findById(id);

	    // Kiểm tra nếu sản phẩm không tồn tại
	    if (!optionalProduct.isPresent()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không tồn tại");
	        return "redirect:/admin/products";
	    }

	    // Lấy sản phẩm từ Optional
	    ProductEntity product = optionalProduct.get();

	    // Kiểm tra trạng thái sản phẩm
	    if (product.getStatus() == 0) {
	        redirectAttributes.addFlashAttribute("infoMessage", "Sản phẩm đã được xóa");
	    } else {
	        // Cập nhật trạng thái sản phẩm thành 0 (xóa)
	        product.setStatus(0);
	        productServiceImpl.save(product);  // Lưu lại thay đổi
	        redirectAttributes.addFlashAttribute("successMessage", "Xóa Sản Phẩm Thành Công");
	    }

	    // Quay lại trang quản lý sản phẩm
	    return "redirect:/admin/products";
	}

	@GetMapping("/products/details/{id}")
	public String getProductDetails(@PathVariable("id") Long productId, Model model) {
		Optional<ProductEntity> optionalProduct = productServiceImpl.findById(productId);

		
		
		if (optionalProduct.isPresent()) {
			model.addAttribute("product", optionalProduct.get());
			return "admin/admin-product-detail";
		} else {
			model.addAttribute("errorMessage", "Product not found.");
			return "error"; // Trang lỗi nếu không tìm thấy sản phẩm
		}
	}

}

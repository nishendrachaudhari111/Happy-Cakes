package Bakery.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Bakery.Model.ProductRepository;
import Bakery.Model.product;


@Controller
public class productController {

	@Autowired
	private ProductRepository productRepository;
	

	@GetMapping("/products")
	public String ProductList(Model model) {
		List<product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "/product/product-list";
	}

	@GetMapping("/edit/product")
	public String showProductForm(Model model) {
		model.addAttribute("product", new product());
		return "/product/create-product";
	}

	@GetMapping("/products/add")
	public String AddProductForm(Model model) {
		model.addAttribute("product", new product());
		return "/product/create-product";
	}

	@PostMapping("/save/product")
	public String saveProduct(@ModelAttribute("product") product product, BindingResult bindingResult,
	                          @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes redirectAttributes)
	        throws IOException {

		if (bindingResult.hasErrors()) {
            // Validation errors occurred, return to the form page
            return "/product/create-product";
        }
		
		// Validate the image size here
        if (!imageFile.isEmpty() && imageFile.getSize() > 2048 * 1024) {
            bindingResult.rejectValue("imageFile", "image.size", "Image size exceeds the allowed limit");
            return "/product/create-product";
        }
        // Check if the file's content type is one of the allowed image types
        String contentType = imageFile.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/jpg") && !contentType.equals("image/png")) {
            bindingResult.rejectValue("imageFile", "image.type", "Only JPEG and PNG files are allowed");
            return "/product/create-product";
        }

        // Save the image to the server
        if (!imageFile.isEmpty()) {
            String uploadDirectory = "D:\\github\\Happy_Cakes\\src\\main\\resources\\productImages\\"; //Specify the storage location"
            String originalFileName = imageFile.getOriginalFilename();
            String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

            try {
                File file = new File(uploadDirectory, uniqueFileName);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(imageFile.getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                // Handle any IO exception
                e.printStackTrace();
                // You can return an error message or perform other error handling here
            }
            product.setImageFileName(uniqueFileName);
            
        }

        productRepository.save(product);
	    // Redirect to the product list page
	    redirectAttributes.addFlashAttribute("successMessage", "Product saved successfully");
	    return "redirect:/products";
	}

	@GetMapping("/product/edit/{id}")
	public String showEditProductForm(@PathVariable("id") Long id, Model model) {
		product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
		model.addAttribute("product", product);
		return "update-product-form";
	}

	@PostMapping("/product/update")
	public String updateProduct(@ModelAttribute("product") product product) {
		productRepository.save(product);
		return "redirect:/products/list";
	}

	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id) {
		productRepository.deleteById(id);
		return "redirect:/products";
	}
	/*
	 * @GetMapping(value = "/images/{imageId}", produces =
	 * MediaType.IMAGE_JPEG_VALUE) Resource downloadImage(@PathVariable Long
	 * imageId) { byte[] image = imageRepository.findById(imageId) .orElseThrow(()
	 * -> new ResponseStatusException(HttpStatus.NOT_FOUND)) .getImageData();
	 * 
	 * return new ByteArrayResource(image); }
	 */
}
package com.example.petstore.controller;

import com.example.petstore.model.Category;
import com.example.petstore.model.Pet;
import com.example.petstore.model.Product;
import com.example.petstore.repo.CategoryRepository;
import com.example.petstore.repo.PetRepository;
import com.example.petstore.repo.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PetRepository petRepo;
    private final ProductRepository productRepo;
    private final CategoryRepository catRepo;

    // --- CHANGE #1 ---
    // Save files to a "petstore-uploads" folder in your User's Home Directory.
    // This is outside the project and "live".
    private final String UPLOAD_DIR = System.getProperty("user.home") + "/petstore-uploads/";


    public AdminController(PetRepository petRepo, ProductRepository productRepo, CategoryRepository catRepo) {
        this.petRepo = petRepo;
        this.productRepo = productRepo;
        this.catRepo = catRepo;
    }

    @GetMapping("/login") public String login(){ return "admin/login"; }

    @GetMapping public String dashboard(){ return "admin/dashboard"; }

    // Pets
    @GetMapping("/pets") public String pets(Model m){ m.addAttribute("pets", petRepo.findAll()); return "admin/pets-list"; }
    @GetMapping("/pets/new") public String newPet(Model m){ m.addAttribute("pet", new Pet()); m.addAttribute("categories", catRepo.findAll()); return "admin/pet-form"; }
    @PostMapping("/pets") public String savePet(@ModelAttribute Pet p){ petRepo.save(p); return "redirect:/admin/pets"; }
    @GetMapping("/pets/{id}/edit") public String editPet(@PathVariable Long id, Model m){ m.addAttribute("pet", petRepo.findById(id).orElseThrow()); m.addAttribute("categories", catRepo.findAll()); return "admin/pet-form"; }
    @PostMapping("/pets/{id}/delete") public String deletePet(@PathVariable Long id){ petRepo.deleteById(id); return "redirect:/admin/pets"; }

    // Products
    @GetMapping("/products") public String products(Model m){ m.addAttribute("products", productRepo.findAll()); return "admin/products-list"; }
    @GetMapping("/products/new") public String newProduct(Model m){ m.addAttribute("product", new Product()); return "admin/product-form"; }
    @PostMapping("/products") public String saveProduct(@ModelAttribute Product p){ productRepo.save(p); return "redirect:/admin/products"; }
    @GetMapping("/products/{id}/edit") public String editProduct(@PathVariable Long id, Model m){ m.addAttribute("product", productRepo.findById(id).orElseThrow()); return "admin/product-form"; }
    @PostMapping("/products/{id}/delete") public String deleteProduct(@PathVariable Long id){ productRepo.deleteById(id); return "redirect:/admin/products"; }

    // Categories
    @GetMapping("/categories") public String categories(Model m){ m.addAttribute("categories", catRepo.findAll()); return "admin/categories-list"; }
    @GetMapping("/categories/new") public String newCategory(Model m){ m.addAttribute("category", new Category()); return "admin/category-form"; }


    @PostMapping("/categories")
    public String saveCategory(@ModelAttribute Category c,
                               @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        // Check if an image file was uploaded
        if (imageFile != null && !imageFile.isEmpty()) {
            // Create the upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Create a unique file name to prevent overwriting existing files
            String originalFileName = imageFile.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            Path filePath = uploadPath.resolve(uniqueFileName);

            // Save the file to the server
            try (InputStream inputStream = imageFile.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // --- CHANGE #2 ---
            // Set the URL to our new "live" path. We will configure this URL in Step 2.
            c.setImageUrl("/uploads/" + uniqueFileName);
        }

        // Save the category (with either the new file URL or the text URL from the form)
        catRepo.save(c);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/{id}/edit") public String editCategory(@PathVariable Long id, Model m){ m.addAttribute("category", catRepo.findById(id).orElseThrow()); return "admin/category-form"; }
    @PostMapping("/categories/{id}/delete") public String deleteCategory(@PathVariable Long id){ catRepo.deleteById(id); return "redirect:/admin/categories"; }
}
package com.example.petstore.controller;

import com.example.petstore.model.Category;
import com.example.petstore.model.CategoryType;
import com.example.petstore.repo.CategoryRepository;
import com.example.petstore.repo.PetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.petstore.model.Pet;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@Controller
public class CategoryController {

    private final CategoryRepository catRepo;
    private final PetRepository petRepo;

    public CategoryController(CategoryRepository catRepo, PetRepository petRepo) {
        this.catRepo = catRepo;
        this.petRepo = petRepo;
    }

    @GetMapping("/domestic")
    public String domestic(Model model){
        model.addAttribute("categories", catRepo.findByType(CategoryType.DOMESTIC));
        return "domestic";
    }

    @GetMapping("/exotic")
    public String exotic(Model model){
        model.addAttribute("categories", catRepo.findByType(CategoryType.EXOTIC));
        return "exotic";
    }

   @GetMapping("/category/{id}")
    public String categoryPets(@PathVariable Long id,
                               @PageableDefault(size = 8) Pageable pageable,
                               Model model) {

        Category c = catRepo.findById(id).orElseThrow();
        model.addAttribute("category", c);

        Page<Pet> petsPage;

        if (pageable.getSort().isSorted()) {
            Sort.Order sortOrder = pageable.getSort().iterator().next();
            String property = sortOrder.getProperty();
            String direction = sortOrder.getDirection().name();

            if ("price".equals(property) && "ASC".equals(direction)) {
                petsPage = petRepo.findByCategoryIdOrderByPriceAsc(id, pageable);
            } else if ("price".equals(property) && "DESC".equals(direction)) {
                petsPage = petRepo.findByCategoryIdOrderByPriceDesc(id, pageable);
            } else {
                petsPage = petRepo.findByCategoryId(id, pageable);
            }
        } else {
            petsPage = petRepo.findByCategoryId(id, pageable);
        }

        model.addAttribute("petsPage", petsPage);

        String currentSort = pageable.getSort().isSorted() ? pageable.getSort().iterator().next().toString().replace(": ", ",") : null;
        model.addAttribute("currentSort", currentSort);

        return "category-pets";
    }
}

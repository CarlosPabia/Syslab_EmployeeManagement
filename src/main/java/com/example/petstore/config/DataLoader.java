package com.example.petstore.config;

import com.example.petstore.model.*;
import com.example.petstore.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner loadData(CategoryRepository cats, PetRepository pets, ProductRepository products) {
        return args -> {
            if (cats.count() == 0) {
                var dog = cats.save(Category.builder().name("Dogs").kind(CategoryKind.DOMESTIC).imageUrl(null).build());
                var cat = cats.save(Category.builder().name("Cats").kind(CategoryKind.DOMESTIC).imageUrl(null).build());
                var bird = cats.save(Category.builder().name("Birds").kind(CategoryKind.DOMESTIC).imageUrl(null).build());
                var rabbit = cats.save(Category.builder().name("Rabbits").kind(CategoryKind.DOMESTIC).imageUrl(null).build());
                var bear = cats.save(Category.builder().name("Bears").kind(CategoryKind.EXOTIC).imageUrl(null).build());
                var snake = cats.save(Category.builder().name("Snakes").kind(CategoryKind.EXOTIC).imageUrl(null).build());

                // 3 pets per category (sample). Image URL left null to exercise "Image not shown".
                pets.saveAll(List.of(
                        Pet.builder().name("Buddy").breed("Golden Retriever").price(new BigDecimal("25000")).category(dog).build(),
                        Pet.builder().name("Max").breed("Labrador").price(new BigDecimal("22000")).category(dog).build(),
                        Pet.builder().name("Lily").breed("Pug").price(new BigDecimal("18000")).category(dog).build(),

                        Pet.builder().name("Mittens").breed("Persian").price(new BigDecimal("20000")).category(cat).build(),
                        Pet.builder().name("Shadow").breed("Siamese").price(new BigDecimal("21000")).category(cat).build(),
                        Pet.builder().name("Luna").breed("Bengal").price(new BigDecimal("30000")).category(cat).build(),

                        Pet.builder().name("Tweety").breed("Canary").price(new BigDecimal("5000")).category(bird).build(),
                        Pet.builder().name("Sky").breed("Parakeet").price(new BigDecimal("4500")).category(bird).build(),
                        Pet.builder().name("Rio").breed("Macaw").price(new BigDecimal("80000")).category(bird).build(),

                        Pet.builder().name("Flopsy").breed("Holland Lop").price(new BigDecimal("7000")).category(rabbit).build(),
                        Pet.builder().name("Thumper").breed("Netherland Dwarf").price(new BigDecimal("6500")).category(rabbit).build(),
                        Pet.builder().name("Clover").breed("Lionhead").price(new BigDecimal("7500")).category(rabbit).build(),

                        Pet.builder().name("Bruno").breed("Brown Bear").price(new BigDecimal("200000")).category(bear).build(),
                        Pet.builder().name("Ursa").breed("Polar Bear").price(new BigDecimal("250000")).category(bear).build(),
                        Pet.builder().name("Honey").breed("Black Bear").price(new BigDecimal("180000")).category(bear).build(),

                        Pet.builder().name("Sly").breed("Corn Snake").price(new BigDecimal("9000")).category(snake).build(),
                        Pet.builder().name("Viper").breed("Ball Python").price(new BigDecimal("15000")).category(snake).build(),
                        Pet.builder().name("Cobra").breed("King Cobra").price(new BigDecimal("120000")).category(snake).build()
                ));

                products.saveAll(List.of(
                        Product.builder().name("Chew Rope").description("Durable rope toy").price(new BigDecimal("399")).type(ProductType.TOY).build(),
                        Product.builder().name("Laser Pointer").description("Cat laser toy").price(new BigDecimal("299")).type(ProductType.TOY).build(),
                        Product.builder().name("Bird Mirror").description("Mirror for bird cages").price(new BigDecimal("199")).type(ProductType.TOY).build(),
                        Product.builder().name("Dog Kibble 5kg").description("Balanced diet").price(new BigDecimal("1499")).type(ProductType.FOOD).build(),
                        Product.builder().name("Cat Tuna 1kg").description("High-protein tuna").price(new BigDecimal("899")).type(ProductType.FOOD).build(),
                        Product.builder().name("Rabbit Pellets 1kg").description("Nutritious pellets").price(new BigDecimal("499")).type(ProductType.FOOD).build()
                ));
            }
        };
    }
}

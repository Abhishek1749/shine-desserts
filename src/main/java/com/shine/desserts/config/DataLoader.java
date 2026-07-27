package com.shine.desserts.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.shine.desserts.model.Dessert;
import com.shine.desserts.repository.DessertRepository;

@Component
public class DataLoader implements CommandLineRunner {
    private final DessertRepository dessertRepository;

    public DataLoader(DessertRepository dessertRepository) {
        this.dessertRepository = dessertRepository;
    }

    @Override
    public void run(String... args) {
        if (dessertRepository.count() == 0) {
            dessertRepository.save(new Dessert("Brownies", "Rich fudgy squares with deep chocolate flavor.", 120, "Best Seller"));
            dessertRepository.save(new Dessert("Chocolate Cake", "Soft layered cake with silky chocolate frosting.", 650, "Party Favorite"));
            dessertRepository.save(new Dessert("Tiramisu", "Coffee-soaked layers with smooth cream.", 180, "Premium"));
            dessertRepository.save(new Dessert("Dream Cake", "Chocolate sponge, mousse, ganache, and crunch.", 250, "Trending"));
            dessertRepository.save(new Dessert("Cheesecake", "Creamy cheesecake with a buttery biscuit base.", 220, "Classic"));
            dessertRepository.save(new Dessert("Matilda Cake", "Thick, glossy, rich chocolate celebration cake.", 300, "Rich Bite"));
            dessertRepository.save(new Dessert("Muffins", "Soft bakery-style muffins baked fresh.", 90, "Fresh Daily"));
            dessertRepository.save(new Dessert("Cupcakes", "Cute frosted cupcakes for every celebration.", 80, "Customizable"));
        }
    }
}
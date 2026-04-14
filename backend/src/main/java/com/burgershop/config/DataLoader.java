package com.burgershop.config;

import com.burgershop.entity.Combo;
import com.burgershop.entity.ComboItem;
import com.burgershop.entity.Product;
import com.burgershop.enums.ProductCategory;
import com.burgershop.enums.ProductType;
import com.burgershop.repository.ComboRepository;
import com.burgershop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ComboRepository comboRepository;

    @Override
    public void run(String... args) {
        // Only seed if no products exist
        if (productRepository.count() > 0) {
            return;
        }

        // Seed Products
        Product paneerBurger = productRepository.save(new Product("Paneer Burger", 149.0, ProductCategory.BURGER, ProductType.VEG));
        Product chickenBurger = productRepository.save(new Product("Chicken Burger", 199.0, ProductCategory.BURGER, ProductType.NONVEG));
        Product vegCheeseBurger = productRepository.save(new Product("Veg Cheese Burger", 129.0, ProductCategory.BURGER, ProductType.VEG));
        Product doubleChickenBurger = productRepository.save(new Product("Double Chicken Burger", 249.0, ProductCategory.BURGER, ProductType.NONVEG));

        Product friesRegular = productRepository.save(new Product("French Fries Regular", 99.0, ProductCategory.FRIES, ProductType.VEG));
        Product friesLarge = productRepository.save(new Product("French Fries Large", 129.0, ProductCategory.FRIES, ProductType.VEG));

        Product coke = productRepository.save(new Product("Coke 500ml", 79.0, ProductCategory.DRINK, ProductType.VEG));
        Product pepsi = productRepository.save(new Product("Pepsi 600ml", 99.0, ProductCategory.DRINK, ProductType.VEG));

        Product vegNuggets = productRepository.save(new Product("Veg Nuggets", 149.0, ProductCategory.SIDES, ProductType.VEG));
        Product chickenNuggets = productRepository.save(new Product("Chicken Nuggets", 179.0, ProductCategory.SIDES, ProductType.NONVEG));

        Product vegWrap = productRepository.save(new Product("Veg Wrap", 119.0, ProductCategory.WRAP, ProductType.VEG));
        Product chickenWrap = productRepository.save(new Product("Chicken Wrap", 159.0, ProductCategory.WRAP, ProductType.NONVEG));

        Product paneerPizza = productRepository.save(new Product("Paneer Pizza Slice", 149.0, ProductCategory.PIZZA, ProductType.VEG));
        Product chickenPizza = productRepository.save(new Product("Chicken Pizza Slice", 179.0, ProductCategory.PIZZA, ProductType.NONVEG));

        Product garlicBread = productRepository.save(new Product("Garlic Bread", 109.0, ProductCategory.SIDES, ProductType.VEG));
        Product cheeseBalls = productRepository.save(new Product("Cheese Balls", 139.0, ProductCategory.SIDES, ProductType.VEG));

        Product coldCoffee = productRepository.save(new Product("Cold Coffee", 129.0, ProductCategory.DRINK, ProductType.VEG));
        Product chocolateShake = productRepository.save(new Product("Chocolate Shake", 149.0, ProductCategory.DRINK, ProductType.VEG));

        Product vegMeal = productRepository.save(new Product("Veg Meal", 199.0, ProductCategory.MEAL, ProductType.VEG));
        Product chickenMeal = productRepository.save(new Product("Chicken Meal", 249.0, ProductCategory.MEAL, ProductType.NONVEG));

        // Seed Combos
        // Combo 1: Veg Burger + Fries + Coke = 279 (instead of 149+99+79 = 327)
        Combo vegCombo = new Combo("Veg Burger Combo", 279.0);
        vegCombo.setItems(Arrays.asList(
                new ComboItem(vegCombo, paneerBurger, 1),
                new ComboItem(vegCombo, friesRegular, 1),
                new ComboItem(vegCombo, coke, 1)
        ));
        comboRepository.save(vegCombo);

        // Combo 2: Chicken Burger + Fries + Pepsi = 349 (instead of 199+99+99 = 397)
        Combo chickenCombo = new Combo("Chicken Burger Combo", 349.0);
        chickenCombo.setItems(Arrays.asList(
                new ComboItem(chickenCombo, chickenBurger, 1),
                new ComboItem(chickenCombo, friesRegular, 1),
                new ComboItem(chickenCombo, pepsi, 1)
        ));
        comboRepository.save(chickenCombo);

        // Combo 3: 2 Wraps + Fries = 299 (instead of 119+159+99 = 377)
        Combo wrapCombo = new Combo("Wrap Duo Combo", 299.0);
        wrapCombo.setItems(Arrays.asList(
                new ComboItem(wrapCombo, vegWrap, 1),
                new ComboItem(wrapCombo, chickenWrap, 1),
                new ComboItem(wrapCombo, friesRegular, 1)
        ));
        comboRepository.save(wrapCombo);

        // Combo 4: Double Chicken Burger + Fries + Drink = 399 (instead of 249+129+99 = 477)
        Combo doubleCombo = new Combo("Double Feast Combo", 399.0);
        doubleCombo.setItems(Arrays.asList(
                new ComboItem(doubleCombo, doubleChickenBurger, 1),
                new ComboItem(doubleCombo, friesLarge, 1),
                new ComboItem(doubleCombo, chocolateShake, 1)
        ));
        comboRepository.save(doubleCombo);

        // Combo 5: Nuggets + Pizza + Coke = 359 (instead of 179+179+79 = 437)
        Combo sideMealCombo = new Combo("Sides & Pizza Combo", 359.0);
        sideMealCombo.setItems(Arrays.asList(
                new ComboItem(sideMealCombo, chickenNuggets, 1),
                new ComboItem(sideMealCombo, chickenPizza, 1),
                new ComboItem(sideMealCombo, coke, 1)
        ));
        comboRepository.save(sideMealCombo);

        System.out.println("Sample data loaded: " + productRepository.count() + " products, " + comboRepository.count() + " combos");
    }
}

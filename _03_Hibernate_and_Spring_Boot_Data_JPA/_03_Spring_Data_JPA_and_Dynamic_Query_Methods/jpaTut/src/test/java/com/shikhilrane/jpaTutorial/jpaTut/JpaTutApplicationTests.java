package com.shikhilrane.jpaTutorial.jpaTut;

import com.shikhilrane.jpaTutorial.jpaTut.entities.ProductEntity;
import com.shikhilrane.jpaTutorial.jpaTut.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class JpaTutApplicationTests {

    // Use to create bean object of class
    @Autowired
    ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

    // Method to add row in table as Test
    @Test
    void testRepository(){
        ProductEntity productEntity = ProductEntity.builder()
                .sku("Pepsi")
                .title("Pepsi Drink")
                .price(BigDecimal.valueOf(300))
                .quantity(40)
                .build();
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        System.out.println(savedProductEntity);
    }

    // Use retrieve all entries from DB
    @Test
    void getAllEntities(){
        List<ProductEntity> all = productRepository.findAll();
        System.out.println(all);
    }

    // Use to delete by ID
    @Test
    void deleteById(){
        productRepository.deleteById(2L);
    }

    // Query Derivation
    // Finding by title
    @Test
    void queryDerivation(){
        List<ProductEntity> titles =  productRepository.findByTitle("Colgate Toothpaste");
        System.out.println(titles);
    }

    // Filtering by date after
    @Test
    void filteringByDate(){
        List<ProductEntity> entities = productRepository.findByCreatedAtAfter(LocalDateTime.of(2025, 12, 30, 0,0,0));
        System.out.println(entities);
    }

    // Finding entries that satisfies entered values
    @Test
    void findQtyAndPrice(){
        List<ProductEntity> byQuantityAndPrice = productRepository.findByQuantityAndPrice(14, BigDecimal.valueOf(234));
        System.out.println(byQuantityAndPrice);
    }

    // Finding Quantity greater than and Price lesser than
    @Test
    void findQtygreaterthanAndPriceLesserThan(){
        List<ProductEntity> conditionalmethod = productRepository.findByQuantityGreaterThanAndPriceLessThan(12, BigDecimal.valueOf(300));
        System.out.println(conditionalmethod);
    }

    // Finding by title containing specified character or String
    @Test
    void findByLike(){
        List<ProductEntity> byLike = productRepository.findByTitleLike("%a%");
        System.out.println(byLike);
    }

    // Finding by title containing specified character or String (Same as per Like but don't need to add % symbol)
    @Test
    void findByContaining(){
        List<ProductEntity> byLike = productRepository.findByTitleContaining("Biscuit");
        System.out.println(byLike);
    }

    // Finding by title containing specified character or String (Same as per Like but don't need to add % symbol and also it will ignore the cases)
    @Test
    void findByContainingIgnoringcase(){
        List<ProductEntity> byLike = productRepository.findByTitleContainingIgnoringCase("BiScuiT");
        System.out.println(byLike);
    }

    @Test
    void findByTitleAndPrice(){
        Optional<ProductEntity> pepsiDrink = productRepository.findByTitleAndPrice("Pepsi Drink", BigDecimal.valueOf(300));
        System.out.println(pepsiDrink);
    }
}

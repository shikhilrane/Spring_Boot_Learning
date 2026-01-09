package com.shikhilrane.jpaTutorial.jpaTut.controllers;

import com.shikhilrane.jpaTutorial.jpaTut.entities.ProductEntity;
import com.shikhilrane.jpaTutorial.jpaTut.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final int PAGE_SIZE = 5;    // Hardcoded the page size in Pageable

    private final ProductRepository productRepository;      // As we know, we should not provide Repository in Controller but for learning purpose we are doing that but in production ready code we should always follow MVC architecture

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @GetMapping("/singleProduct")
    public List<ProductEntity> getProduct(){
        return productRepository.findByTitleOrderByPrice("Lays Chips");
    }

    @GetMapping("/priceAscending")
    public List<ProductEntity> getAllProducts(){
        return productRepository.findByOrderByPrice();
    }

    // Using Sort Class
    @GetMapping("/sortByClass")
    public List<ProductEntity> getAllBySortClass(@RequestParam(defaultValue = "id") String sortBy){
        // return productRepository.findBy(Sort.by(sortBy));    // It will sort id in ascending order as we provided default value os id
        // return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy));   // It will sort in descending order
        // return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy, "price"));  // If default value (i.e. if ids are same) then sort them on basis of price
        return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy, "price", "quantity")); // If default value (i.e. if ids are same) then sort them on basis of price and even if price is same then sort them on basis of quantity
    }
    // If we want to sort wrt to title then pass, sortByClass?sortBy=title, so it will sort according to title

    // Using Pageable
    @GetMapping("/pageable")
    public List<ProductEntity> getAllByPageableClass(@RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "0") Integer pageNumber){
        Pageable pageable = PageRequest.of(
                pageNumber,     // pageNumber , If we want to between pages, pageable?pageNumber=2, so it will sort according to title. Index of paging starts from 0
                PAGE_SIZE,      // number of elements on single page
                Sort.by(Sort.Direction.ASC, sortBy, "price", "quantity")); // sorting
        return productRepository.findAll(pageable).getContent();    // getContent() provide only main content and not all the info about page
    }

    // Final Method with Proper sorting and pagination
    @GetMapping("/pageableOpt")
    public List<ProductEntity> getAllByPageableClassOptimised(
            @RequestParam(defaultValue = "") String title,  // If we pass, ?title=co, then it will return all the titles that contains co
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") Integer pageNumber){
        return productRepository.findByTitleContainingIgnoringCase(
                title,
                PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(Sort.Direction.ASC, sortBy, "price", "quantity"))
        );
    }

}

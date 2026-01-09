package com.shikhilrane.jpaTutorial.jpaTut.repositories;

import com.shikhilrane.jpaTutorial.jpaTut.entities.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // Sorting
    List<ProductEntity> findByTitleOrderByPrice(String title);
    List<ProductEntity> findByOrderByPrice();   // It will sort the data on basic of Pricing (But it is tightly coupled and we need to write multiple methods to sorts on different parameters)
    // To overcome this, We should use Sort Class
    List<ProductEntity> findBy(Sort sort);

    // Paging
    List<ProductEntity> findByTitleContainingIgnoringCase (String title, Pageable pageable);
}

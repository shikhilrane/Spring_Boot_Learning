package com.shikhilrane.jpaTutorial.jpaTut.repositories;

import com.shikhilrane.jpaTutorial.jpaTut.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTitle(String title);      // This method is in JpaTutApplicationTests.java
    List<ProductEntity> findByCreatedAtAfter(LocalDateTime after);      // This method is in JpaTutApplicationTests.java (Used After variable here)
    List<ProductEntity> findByQuantityAndPrice (Integer qty, BigDecimal price); // This method is in JpaTutApplicationTests.java (Used And variable here)
    List<ProductEntity> findByQuantityGreaterThanAndPriceLessThan (Integer qty, BigDecimal price); // This method is in JpaTutApplicationTests.java (Used And variable here)
    List<ProductEntity> findByTitleLike (String title); // This method is in JpaTutApplicationTests.java (Used Like variable here)
    List<ProductEntity> findByTitleContaining (String title); // This method is in JpaTutApplicationTests.java (Used Containing variable here)
    List<ProductEntity> findByTitleContainingIgnoringCase (String title); // This method is in JpaTutApplicationTests.java (Used Containing variable and will ignore the case)
//    Optional<ProductEntity> findByTitleAndPrice (String title, BigDecimal price); // This method is in JpaTutApplicationTests.java (getting single entity) (Commented because we will writing our own jpql custom queries)

    // JPQL
//    @Query("SELECT e from ProductEntity e where e.title=?1 and e.price=?2")
    @Query("SELECT e from ProductEntity e where e.title=:title and e.price=:price") // 2nd way of writing custom jpql queries
    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);
}
